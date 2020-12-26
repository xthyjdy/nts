package vch.proj.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import vch.proj.entities.Note;
import vch.proj.repositories.NoteRepository;

public class NotesViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    private LiveData<List<Note>> mNotes;

    public NotesViewModel(Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mNotes = mRepository.getNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return mNotes;
    }

    public void insert(Note note) {
        mRepository.insert(note);
    }

    public void update(Note note) {
        mRepository.update(note);
    }

    public void delete(Note note) {
        mRepository.delete(note);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}