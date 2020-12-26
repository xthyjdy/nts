package vch.proj.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import vch.proj.fragments.WelcomeFragment;

/**
 * Start Page Activity
 */
public class WelcomeActivity extends BaseActivity {
    @Override
    protected Fragment setFragment() {
        return new WelcomeFragment();
    }
}
