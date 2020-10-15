package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.CategoryDao;
import com.zzxx.travel.domain.Category;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private static JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> selectAllCategory() {
        String sql = "select * from `tab_category`";
        List<Category> categoryList = template.query(sql, new BeanPropertyRowMapper<>(Category.class));
        return categoryList;
    }
}
