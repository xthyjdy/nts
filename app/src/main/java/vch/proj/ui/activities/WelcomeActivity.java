package vch.proj.ui.activities;

import androidx.fragment.app.Fragment;

import vch.proj.ui.fragments.WelcomeFragment;

/**
 * Start Page Activity
 */
public class WelcomeActivity extends BaseActivity {
    @Override
    protected Fragment setFragment() {
        return new WelcomeFragment();
    }
}
