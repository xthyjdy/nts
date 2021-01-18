package vch.proj.ui.activities;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import vch.proj.R;

import static vch.proj.helpers.Helper.l;

/**
 * Main Abstract Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * Set Fragment - return fragment which will be set for activity
     * @return instance of @{{@link Fragment}}
     */
    protected abstract Fragment setFragment();

    /**
     * Init - initialize additional settings for activity
     */
    protected void init() {}

    /**
     * Get Layout - return base resource
     * @return
     */
    @LayoutRes
    protected int getLayout() {
        return R.layout.main_fraim_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        init();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_container);

        if (null == fragment) {
            fragment = setFragment();
        }

        if (!fragment.isAdded()) {
            fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();
        }
    }
}
