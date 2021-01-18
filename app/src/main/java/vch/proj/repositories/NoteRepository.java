package vch.proj.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import vch.proj.dao.NoteDao;
import vch.proj.db.Database;
import vch.proj.entities.NoteModel;

public class NoteRepository {
    private NoteDao mDao;
    private LiveData<List<NoteModel>> mNotes;

    public NoteRepository(Application application) {
        Database db = Database.getDatabase(application);
        mDao = db.noteDao();
        mNotes = mDao.getNotes();
    }

    public LiveData<List<NoteModel>> getNotes() {
        return mNotes;
    }

    public void insert(NoteModel noteModel) {
        new AsyncInsert(mDao).execute(noteModel);
    }

    public void update(NoteModel noteModel) {
        new AsyncUpdate(mDao).execute(noteModel);
    }

    public void delete(NoteModel noteModel) {
        new AsyncDelete(mDao).execute(noteModel);
    }

    public void deleteAll() {
        new AsyncDeleteAll(mDao).execute();
    }

    private class AsyncInsert extends AsyncTask<NoteModel, Void, Void> {
        private NoteDao dao;

        public AsyncInsert(NoteDao noteDao) {
            dao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteModel... noteModels) {
            dao.insert(noteModels[0]);
            return null;
        }
    }

    private class AsyncDelete extends AsyncTask<NoteModel, Void, Void> {
        private NoteDao dao;

        public AsyncDelete(NoteDao noteDao) {
            dao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteModel... noteModels) {
            dao.delete(noteModels[0]);
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

    private class AsyncUpdate extends AsyncTask<NoteModel, Void, Void> {
        private NoteDao dao;

        public AsyncUpdate(NoteDao noteDao) {
            dao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteModel... noteModels) {
            dao.update(noteModels[0]);
            return null;
        }
    }
}
