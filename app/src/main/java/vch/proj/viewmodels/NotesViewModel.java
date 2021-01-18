package vch.proj.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import vch.proj.entities.NoteModel;
import vch.proj.repositories.NoteRepository;

public class NotesViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    private LiveData<List<NoteModel>> mNotes;

    public NotesViewModel(Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mNotes = mRepository.getNotes();
    }

    public LiveData<List<NoteModel>> getNotes() {
        return mNotes;
    }

    public void insert(NoteModel noteModel) {
        mRepository.insert(noteModel);
    }

    public void update(NoteModel noteModel) {
        mRepository.update(noteModel);
    }

    public void delete(NoteModel noteModel) {
        mRepository.delete(noteModel);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}