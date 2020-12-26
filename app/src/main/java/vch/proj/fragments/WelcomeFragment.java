package vch.proj.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vch.proj.R;
import vch.proj.activities.NotesActivity;

import static android.content.Context.WINDOW_SERVICE;
import static vch.proj.classes.Helper.l;

public class WelcomeFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.welcome_activity, container, false);

        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.welcome_liner_layout);

        Drawable backgroundPortrait = getResources().getDrawable(R.drawable.welcome_background_portrait);
        Drawable backgroundLandscape = getResources().getDrawable(R.drawable.welcome_background_landscape);

        WindowManager window = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        if (1 == (int)display.getRotation() || 3 == (int)display.getRotation()) {
            linearLayout.setBackgroundResource(R.drawable.welcome_background_landscape);
        } else {
            linearLayout.setBackgroundResource(R.drawable.welcome_background_portrait);
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NotesActivity.newIntent(getActivity());
                startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
    }
}
