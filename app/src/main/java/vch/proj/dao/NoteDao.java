package vch.proj.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vch.proj.entities.NoteModel;

@Dao
public interface NoteDao {
    @Query("SELECT id, title, message FROM notes ORDER BY ID ASC")
    LiveData<List<NoteModel>> getNotes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NoteModel noteModel);

    @Delete
    void delete(NoteModel noteModel);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Update
    void update(NoteModel noteModel);
}
