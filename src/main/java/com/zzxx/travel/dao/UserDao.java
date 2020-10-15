package com.zzxx.travel.dao;

import com.zzxx.travel.domain.User;

public interface UserDao {
    User selectByUsername(String username);

    void insertUser(User user) throws Exception;

    int updateStatus(String code);

    User selectByUnAndPwd(String username, String password);
}
