package com.android.blackoder.makta.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.android.blackoder.makta.model.entities.Book;
import com.android.blackoder.makta.model.entities.WishBook;

/**
 * Created By blackcoder
 * On 18/03/19
 **/
@Database(entities = {Book.class, WishBook.class}, version = 1)
public abstract class BooksDatabase extends RoomDatabase {
    public abstract MyBooksDao mMyBooksDao();

    public abstract WishListDao mWishListDao();

    private static volatile BooksDatabase INSTANCE;

    static BooksDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BooksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            BooksDatabase.class,
                            "books_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
