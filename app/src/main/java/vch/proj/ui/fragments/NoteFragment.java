package vch.proj.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vch.proj.R;
import vch.proj.entities.NoteModel;

import static vch.proj.helpers.Helper.l;

public class NoteFragment extends Fragment {
    public static final String ARG_NOTE = "ARG_NOTE";
    protected Callbacks mCallbacks;
    private Button saveNote;
    private NoteModel currentNoteModel = null;
    private EditText noteTitle;
    private EditText noteMessage;
    private ImageView backIv;
    //for choosing beetwen create or update interface
    private boolean actionAdd;

    /**
     * New Instance - method which return customized fragment
     * @param noteModel instance of @{@link NoteModel}
     * @return NoteFragment
     */
    public static NoteFragment newInstance(NoteModel noteModel) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE, noteModel);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentNoteModel = (NoteModel) getArguments().getSerializable(ARG_NOTE);
        //for choosing between "create" or "update" action
        actionAdd = (null == currentNoteModel) ? true : false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.note_fragment, container, false);

        noteTitle = (EditText) v.findViewById(R.id.note_title);
        noteMessage = (EditText) v.findViewById(R.id.note_message);

        if (actionAdd) {
            currentNoteModel = new NoteModel();
        } else {
            noteTitle.setText(currentNoteModel.getTitle());
            noteMessage.setText(currentNoteModel.getMessage());
        }

        saveNote = (Button) v.findViewById(R.id.save_note);
        saveNote.setText(
                (actionAdd) ? getResources().getString(R.string.create_note) : getResources().getString(R.string.update_note));
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noteTitle.getText().toString();
                String message = noteMessage.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.title_empty),
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (message.isEmpty()) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.message_empty),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                currentNoteModel.setTitle(title.toString());
                currentNoteModel.setMessage(message.trim().toString());

                mCallbacks.itemProcessing(currentNoteModel, actionAdd);
            }
        });

        backIv = (ImageView) v.findViewById(R.id.back);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.actionBack();
            }
        });

        return v;
    }

    /**
     * Callbacks interface - for send events to main activity of fragment
     */
    public interface Callbacks {
        /**
         * Item Processing - method which handling specified Note (save/update in current version)
         * @param noteModel instance of @{@link NoteModel}
         * @param actionAdd boolean
         */
        void itemProcessing(NoteModel noteModel, boolean actionAdd);

        /**
         * ActionBack - method which get previous activity
         */
        void actionBack();
    }
}
