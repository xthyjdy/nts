package vch.proj.dao;

import android.service.voice.VoiceInteractionService;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import vch.proj.entities.Note;

@Dao
public interface NoteDao {
    @Query("SELECT id, title, message FROM notes ORDER BY ID ASC")
    LiveData<List<Note>> getNotes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Update
    void update(Note note);
}
