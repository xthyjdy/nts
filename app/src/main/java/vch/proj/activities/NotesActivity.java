package vch.proj.activities;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import vch.proj.R;
import vch.proj.entities.Note;
import vch.proj.fragments.NoteFragment;
import vch.proj.fragments.NotesFragment;
import vch.proj.view_models.NotesViewModel;

import static vch.proj.classes.Helper.l;

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
    public void saveNote(Note note) {
        Fragment fragment = NoteFragment.newInstance(note);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    public void itemProcessing(Note note, boolean actionAdd) {
        if (actionAdd) {
            mNotesViewModel.insert(note);
            Toast.makeText(this,
                    getResources().getString(R.string.notes_was_added),
                    Toast.LENGTH_SHORT).show();
        } else {
            mNotesViewModel.update(note);
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
