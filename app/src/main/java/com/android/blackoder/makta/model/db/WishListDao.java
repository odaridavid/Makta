package com.android.blackoder.makta.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.android.blackoder.makta.model.entities.WishBook;

import java.util.List;

/**
 * Created By blackcoder
 * On 18/03/19
 **/
@Dao
public interface WishListDao {

    @Query("SELECT * FROM wish_list_table WHERE author=:author AND title =:title")
    LiveData<List<WishBook>> getExistingBooks(String author, String title);

    @Query("SELECT * FROM wish_list_table")
    LiveData<List<WishBook>> getWishList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBook(WishBook book);

    @Query("DELETE FROM wish_list_table WHERE author=:author AND title =:title ")
    void deleteBook(String author, String title);
}
