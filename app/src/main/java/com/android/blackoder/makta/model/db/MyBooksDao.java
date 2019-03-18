package com.android.blackoder.makta.model.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.blackoder.makta.model.entities.Book;

import java.util.List;

/**
 * Created By blackcoder
 * On 18/03/19
 **/
@Dao
public interface MyBooksDao {
    @Query("SELECT * FROM my_books_table")
    LiveData<List<Book>> getMyBooks();

    @Insert
    void insertBook(Book book);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateBook(Book book);

    @Delete
    void deleteBook(Book book);
}
