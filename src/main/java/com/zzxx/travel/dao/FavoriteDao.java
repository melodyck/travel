package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Favorite;

import java.util.List;

public interface FavoriteDao {
    Favorite findByUIDAndRID(int uid,int rid);
    long findCountByRid(int rid);
    void addFavorite(int uid, int rid);
    void deleteFavorite(int uid, int rid);
}
