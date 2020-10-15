package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.UserDao;
import com.zzxx.travel.dao.impl.UserDaoImpl;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.exception.LoginException;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.util.MailUtils;
import com.zzxx.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private static UserDao ud = new UserDaoImpl();
    @Override
    public boolean checkUsername(String username) {
        User user = ud.selectByUsername(username);
        if (user == null){
            return false;
        }
        return true;
    }

    @Override
    public boolean register(User user) {
        //初始化激活状态为 N
        user.setStatus("N");
        //设置唯一code
        user.setCode(UuidUtil.getUuid());
        //发送激活邮件 <a href="http://localhost:80/travel/ActiveServlet?code=">点击激活帐号</a>
        String text = "<a href=\"http://localhost:80/travel/ActiveServlet?code=" + user.getCode() + "\">点击激活帐号</a>";
        System.out.println(text);
        MailUtils.sendMail(user.getEmail(), text, "帐号激活");
        try{
            ud.insertUser(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean activeAccount(String code) {
        //返回更新的行数
        int count = ud.updateStatus(code);
        return count == 1;
    }

    @Override
    public User login(String username, String password) throws LoginException {
        User user = ud.selectByUnAndPwd(username, password);
        if (user == null){
            throw new LoginException("用户名或密码错误");
        }else{
            return user;
        }
    }
}
