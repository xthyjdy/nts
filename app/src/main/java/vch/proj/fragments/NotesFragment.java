package vch.proj.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import vch.proj.R;
import vch.proj.activities.NotesActivity;
import vch.proj.entities.Note;
import vch.proj.view_models.NotesViewModel;

import static vch.proj.classes.Helper.*;

public class NotesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private NoteAdapter mAdapter;
    private NotesViewModel mNotesViewModel = null;
    private Callbacks mCallbacks;
    private ImageView popapMenuIv;
    private FloatingActionButton addNoteButton;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notes_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        setNoteLayoutManager(null);

        mAdapter = new NoteAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        if (null != mNotesViewModel) {
            mNotesViewModel.getNotes().observe(getActivity(), new Observer<List<Note>>() {
                @Override
                public void onChanged(List<Note> notes) {
                    mAdapter.setNotes(notes);
                }
            });
        }

        addNoteButton = (FloatingActionButton) v.findViewById(R.id.add_note);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.saveNote(null);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mNotesViewModel.delete(mAdapter.getNote(position));
                mAdapter.notifyItemChanged(position);
            }
        }).attachToRecyclerView(mRecyclerView);

        popapMenuIv = (ImageView) v.findViewById(R.id.notes_page_popap_menu);
        popapMenuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopapMenu(v);
            }
        });

        return v;
    }

    public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
        private List<Note> mNotes;
        private Context mContext;

        public NoteAdapter(Context context) {
            mContext = context;
        }

        @NonNull
        @Override
        public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);

            return new NoteViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
            Note note = mNotes.get(position);
            holder.bind(note);
        }

        @Override
        public int getItemCount() {
            if (null == mNotes) {
                return 0;
            }
            return mNotes.size();
        }

        public void setNotes(List<Note> notes) {
            mNotes = notes;
            notifyDataSetChanged();
        }

        public Note getNote(int position) {
            return mNotes.get(position);
        }

        public void refresh() {
            notifyDataSetChanged();
        }

        public List<Note> getNotes() {
            return mNotes;
        }

        public class NoteViewHolder extends RecyclerView.ViewHolder {
            private TextView mTitle;

            public NoteViewHolder(@NonNull View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.title_text_view);
            }

            public void bind(Note note) {
                mTitle.setText(note.getTitle().toString());
                mTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallbacks.saveNote(note);
                    }
                });
            }
        }
    }

    public void  setNotesViewModel(ViewModel viewModel) {
        mNotesViewModel = (NotesViewModel) viewModel;
    }

    public void refresh() {
        mAdapter.refresh();
    }

    private void showPopapMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.notes_page_top_bar_popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.to_list_interface:
                        setNoteLayoutManager(new LinearLayoutManager(getActivity()));
                        return true;
                    case R.id.to_card_interface:
                        setNoteLayoutManager(new GridLayoutManager(getActivity(), 2));
                        return true;
                    case R.id.top_bar_popap_delete_all_notes:
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder
                                .setCancelable(false)
                                .setTitle(getResources().getString(R.string.q_delete_all_notes))
                                .setPositiveButton("Delete?",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mNotesViewModel.deleteAll();
                                                Toast.makeText(getActivity(),
                                                        getResources().getString(R.string.notes_were_deleted),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(getResources().getColor(R.color.colorRed));
                                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                        .setTextColor(getResources().getColor(R.color.colorBlack));
                            }
                        });

                        alertDialog.show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });

        popupMenu.show();
    }

    /**
     * Set Note Layout Manager - method which set specified layout (LinerLayout or GridLayout)
     * @param layoutManager instance of @{@link RecyclerView.LayoutManager}
     */
    private void setNoteLayoutManager(RecyclerView.LayoutManager layoutManager) {
        RecyclerView.LayoutManager layout = null;

        if (false == NotesActivity.frameFirstBoot) {
            layout = new LinearLayoutManager(getActivity());
            NotesActivity.view = layout.getClass().getSimpleName();
            NotesActivity.frameFirstBoot = true;
        } else {
            if (null == layoutManager) {
                if (NotesActivity.view.equals(getResources().getString(R.string.linear_layout_manager))) {
                    layout = new LinearLayoutManager(getActivity());
                } else if (NotesActivity.view.equals(getResources().getString(R.string.grid_layout_manager))) {
                    layout = new GridLayoutManager(getActivity(), 3);
                }
            } else {
                layout = layoutManager;
                NotesActivity.view = layout.getClass().getSimpleName();
            }
        }
        mRecyclerView.setLayoutManager(layout);
    }

    /**
     * Callbacks interface - for send events to main activity of fragment
     */
    public interface Callbacks {
        /**
         * Save Note - send Note to NoteFragment
         * @param note instance of @{@link Note}
         */
        void saveNote(Note note);
    }

    /**
     * for add test notes
     */
    private void addTestModels() {
        for (int i = 0; i < 3; i++) {
            Note note = new Note();
            note.setTitle("title_" + i);
            note.setMessage("message_" + i);
            mNotesViewModel.insert(note);
        }
    }
}