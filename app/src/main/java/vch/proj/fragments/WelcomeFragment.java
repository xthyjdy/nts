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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

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
        TextView pressInfoTv = (TextView) v.findViewById(R.id.press_info_tv);
        LinearLayout.LayoutParams pressInfoLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Drawable background;
        WindowManager window = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();

        if (1 == (int)display.getRotation() || 3 == (int)display.getRotation()) {
            pressInfoLp.setMargins(690, 650, 0, 0);
            background = getResources().getDrawable(R.drawable.welcome_background_landscape);
        } else {
            pressInfoLp.setMargins(260, 1100, 0, 0);
            background = getResources().getDrawable(R.drawable.welcome_background_portrait);
        }

        Glide.with(getActivity())
                .load(R.drawable.welcome_background_portrait)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        linearLayout.setBackground(background);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        pressInfoTv.setLayoutParams(pressInfoLp);
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
