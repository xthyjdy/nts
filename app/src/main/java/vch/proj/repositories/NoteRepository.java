package vch.proj.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import vch.proj.dao.NoteDao;
import vch.proj.db.Database;
import vch.proj.entities.Note;

public class NoteRepository {
    private NoteDao mDao;
    private LiveData<List<Note>> mNotes;

    public NoteRepository(Application application) {
        Database db = Database.getDatabase(application);
        mDao = db.noteDao();
        mNotes = mDao.getNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return mNotes;
    }

    public void insert(Note note) {
        new AsyncInsert(mDao).execute(note);
    }

    public void update(Note note) {
        new AsyncUpdate(mDao).execute(note);
    }

    public void delete(Note note) {
        new AsyncDelete(mDao).execute(note);
    }

    public void deleteAll() {
        new AsyncDeleteAll(mDao).execute();
    }

    private class AsyncInsert extends AsyncTask<Note, Void, Void> {
        private NoteDao dao;

        public AsyncInsert(NoteDao noteDao) {
            dao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.insert(notes[0]);
            return null;
        }
    }

    private class AsyncDelete extends AsyncTask<Note, Void, Void> {
        private NoteDao dao;

        public AsyncDelete(NoteDao noteDao) {
            dao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.delete(notes[0]);
            return null;
        }
    }

    private class AsyncDeleteAll extends AsyncTask<Void, Void, Void> {
        private NoteDao dao;

        public AsyncDeleteAll(NoteDao noteDao) {
            dao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private class AsyncUpdate extends AsyncTask<Note, Void, Void> {
        private NoteDao dao;

        public AsyncUpdate(NoteDao noteDao) {
            dao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.update(notes[0]);
            return null;
        }
    }
}
