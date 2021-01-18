package vch.proj.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import vch.proj.R;
import vch.proj.entities.NoteModel;
import vch.proj.ui.fragments.NoteFragment;
import vch.proj.ui.fragments.NotesFragment;
import vch.proj.viewmodels.NotesViewModel;

import static vch.proj.helpers.Helper.l;

/**
 * Main Recycle View Activity
 */
public class NotesActivity extends BaseActivity
        implements NotesFragment.Callbacks, NoteFragment.Callbacks {
    public static boolean frameFirstBoot = false;
    public static String view = "";
    protected NotesFragment mainFragment;
    private NotesViewModel mNotesViewModel;

    /**
     * New Intent - return Intent with current activity(there can be specified additional settings)
     * @param context instance of @{@link Context}
     * @return Intent instance of @{@link Intent}
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, NotesActivity.class);
        return intent;
    }

    @Override
    protected void init() {
        super.init();
        getSupportActionBar().hide();
        mainFragment = new NotesFragment();
        mNotesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        mainFragment.setNotesViewModel(mNotesViewModel);
        l("init");
    }

    @Override
    protected Fragment setFragment() {
        return mainFragment;
    }

    @Override
    public void saveNote(NoteModel noteModel) {
        Fragment fragment = NoteFragment.newInstance(noteModel);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    public void itemProcessing(NoteModel noteModel, boolean actionAdd) {
        if (actionAdd) {
            mNotesViewModel.insert(noteModel);
            Toast.makeText(this,
                    getResources().getString(R.string.notes_was_added),
                    Toast.LENGTH_SHORT).show();
        } else {
            mNotesViewModel.update(noteModel);
            Toast.makeText(this,
                    getResources().getString(R.string.notes_was_updated),
                    Toast.LENGTH_SHORT).show();
        }
        mainFragment.refresh();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, mainFragment)
                .commit();
    }

    @Override
    public void actionBack() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, mainFragment)
                .commit();
    }
}
