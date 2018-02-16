package com.newspaper.repository;

import com.newspaper.model.Role;
import com.newspaper.model.User;
import com.newspaper.model.web.LoginRequest;
import com.newspaper.model.web.RegistrationRequest;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.*;

@Repository
public class UserRepository {

    private static Map<String, User> users = new HashMap<>();
    private static List<User> admins = new ArrayList<>();

    public User createUser(RegistrationRequest request) {
        User mapUser = users.get(request.getUsername());
        List<Role> rolesTemp = new ArrayList<>();
        if (mapUser != null) {
            throw new RuntimeException(String.format("User with username: %s, already exists", request.getUsername()));
        }
        if (users.size() == 0) {
            rolesTemp.add(Role.ROLE_READER);
            rolesTemp.add(Role.ROLE_WRITER);
            rolesTemp.add(Role.ROLE_ADMIN);
        } else {
            rolesTemp.add(Role.ROLE_READER);
        }
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .username(request.getUsername())
                .roles(rolesTemp)
                .id(users.size() + 1)
                .build();
        users.put(user.getUsername(), user);
        if (user.getRoles().get(user.getRoles().size()-1).equals(Role.ROLE_ADMIN)) {
            admins.add(user);
        }
        return user;
    }

    public User getByUsernameAndPassword(@NotNull LoginRequest request) {
        User user = users.get(request.getUsername());
        if (user == null) {
            throw new RuntimeException("No such user");
        }
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        return user;
    }

    public User getByUsername(@NotNull String username) {
        return users.get(username);
    }

    public User getById(Integer id) {
        return users.values().stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }


    public User promoteUser(String username) {
        User user = users.get(username);
        if (user == null) {
            throw new RuntimeException(String.format("User with username: %s, not found", username));
        }
        List<Role> rolesTemp = user.getRoles();
        if (rolesTemp.get(rolesTemp.size() - 1).equals(Role.ROLE_ADMIN)) {
            throw new RuntimeException(String.format("User with username: %s, is Admininstrator", username));
        }
        rolesTemp.add(Role.getById(rolesTemp.get(rolesTemp.size() - 1).getId() + 1));
        user.setRoles(rolesTemp);
        if (rolesTemp.get(rolesTemp.size() - 1).equals(Role.ROLE_ADMIN)) {
            admins.add(user);
        }
        return user;
    }

    public User demoteUser(String username) {
        User user = users.get(username);
        if (user == null) {
            throw new RuntimeException(String.format("User with username: %s, not found", username));
        }
        List<Role> rolesTemp = user.getRoles();
        if (rolesTemp.get(rolesTemp.size() - 1).equals(Role.ROLE_ADMIN)) {
            if (admins.size() == 1) {
                throw new RuntimeException(String.format("User with username: %s, isn't able to be demoted, user is the last Adniistrator", username));
            }
            admins.remove(user);
        }
        if (rolesTemp.get(rolesTemp.size() - 1).equals(Role.ROLE_READER)) {
            throw new RuntimeException(String.format("User with username: %s, isn't able to be demoted, user is Reader", username));
        }
        rolesTemp.remove(rolesTemp.get(rolesTemp.size() - 1));
        user.setRoles(rolesTemp);
        return user;
    }
}