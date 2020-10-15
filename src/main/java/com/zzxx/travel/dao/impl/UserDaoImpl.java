package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.UserDao;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    //检测用户是否存在
    @Override
    public User selectByUsername(String username) {
        String sql = "select username from `tab_user` where username = ?;";
        List<User> user = template.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        if (user == null || user.size() == 0){
            return null;
        }
        return user.get(0);
    }

    @Override
    public void insertUser(User user) throws Exception{
        String sql = "insert into `tab_user` values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        template.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
    }

    @Override
    public int updateStatus(String code) {
        String sql = "update `tab_user` set status = 'Y' where code = ?";
        return template.update(sql, code);
    }

    @Override
    public User selectByUnAndPwd(String username, String password) {
        String sql = "select * from `tab_user` where username = ? and password = ?";
        try {
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
            return user;
        }catch (Exception e){
            //如果有结果
            return null;
        }
    }
}
