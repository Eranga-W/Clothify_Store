package org.example.dto;

import org.example.entity.EmployeeEntity;

public class LoggedUser {
    private static LoggedUser instance = new LoggedUser();
    private EmployeeEntity loggedUser;
    private LoggedUser() {}
    public static LoggedUser getInstance(){
        if (instance==null) {
            return instance = new LoggedUser();
        }
        return instance;
    }

    public EmployeeEntity getUser() {
        return loggedUser;
    }

    public void setUser(EmployeeEntity loggedUser) {
        this.loggedUser = loggedUser;
    }
}
