package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category>  selectAllCategory();
}
