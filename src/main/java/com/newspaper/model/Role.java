package com.newspaper.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {

    ROLE_READER(1),
    ROLE_WRITER(2),
    ROLE_ADMIN(3);

    private Integer id;


    public Integer getId(){
        return id;
    }


    public static Role getById(Integer id){
        for(Role role : values()){
            if (role.getId().equals(id)){
                return role;
            }
        }
        return null;
    }

}