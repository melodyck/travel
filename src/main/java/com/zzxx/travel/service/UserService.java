package com.zzxx.travel.service;

import com.zzxx.travel.domain.User;
import com.zzxx.travel.exception.LoginException;

public interface UserService {
    boolean checkUsername(String username);

    boolean register(User user);

    boolean activeAccount(String code);

    User login(String username, String password) throws LoginException;
}
