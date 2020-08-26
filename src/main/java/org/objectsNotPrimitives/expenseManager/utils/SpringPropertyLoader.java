package org.objectsNotPrimitives.expenseManager.utils;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

@Component
public class SpringPropertyLoader {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;


    public String getUrl() {
        return url;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public SpringPropertyLoader(){
    }

}
