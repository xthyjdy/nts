package vch.proj.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import vch.proj.dao.NoteDao;
import vch.proj.entities.NoteModel;

@Singleton
@androidx.room.Database(entities = NoteModel.class, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static volatile Database instance;
    private static final int NUMBER_OF_THREAD = 5;
    public static final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

    public abstract NoteDao noteDao();

    public static Database getDatabase(final Context context) {
        if (null == instance) {
            synchronized (Database.class) {
                if (null == instance) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class,"notes")
                            .build();
                }
            }
        }
        return instance;
    }
}
