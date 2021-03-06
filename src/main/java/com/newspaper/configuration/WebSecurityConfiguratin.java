package com.newspaper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
@Configuration
public class  WebSecurityConfiguratin extends WebSecurityConfigurerAdapter{

    @Bean
    public AuthentificationFilter authentificationFilter(){
        return new AuthentificationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/users/register**").permitAll()
                .antMatchers("/users/login**").permitAll()
                .antMatchers("/users/register/**").permitAll()
                .antMatchers("/users/login/**").permitAll()
                .antMatchers("/users/promote/**").hasRole("ADMIN")
                .antMatchers("/users/demote/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        http.addFilterBefore(authentificationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}