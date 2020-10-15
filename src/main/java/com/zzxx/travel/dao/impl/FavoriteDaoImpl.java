package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.FavoriteDao;
import com.zzxx.travel.domain.Favorite;

public class FavoriteDaoImpl implements FavoriteDao {
    @Override
    public Favorite findByUIDAndRID(int uid, int rid) {
        return null;
    }

    @Override
    public long findCountByRid(int rid) {
        return 0;
    }

    @Override
    public void addFavorite(int uid, int rid) {

    }

    @Override
    public void deleteFavorite(int uid, int rid) {

    }
}
