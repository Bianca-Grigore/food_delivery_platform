package com.pao.proiect.tema.service;

import com.pao.proiect.tema.model.User;

public class UserService {
    private User[] users;

    private UserService() {
        this.users = new User[0];
    }

    private static class Holder{
        private static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance(){
        return UserService.Holder.INSTANCE;
    }
}
