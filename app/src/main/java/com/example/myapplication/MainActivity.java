package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements Fragment2.OnContinueListener, Fragment3.OnConfirmListener {

    private ActivityMainBinding binding;

    private static final String KEY_NAME = "name";

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            name = savedInstanceState.getString(KEY_NAME);
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, Fragment1.newInstance(null, null))
                    .commit();
        }

        binding.buttonContinue.setOnClickListener(view -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, Fragment2.newInstance())
                    .addToBackStack(null)
                    .commit();
        });

        getSupportFragmentManager().addOnBackStackChangedListener(this::updateContinueButton);
        updateContinueButton();
    }

    // The host's own Continue button is only shown on Fragment1
    private void updateContinueButton() {
        boolean onFirstScreen = getSupportFragmentManager().getBackStackEntryCount() == 0;
        binding.buttonContinue.setVisibility(onFirstScreen ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_NAME, name);
    }

    @Override
    public void onContinue(String name, String email, String gender, String age, String university) {
        this.name = name;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Fragment3.newInstance(name))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onConfirmChanged(boolean checked) {
        Log.d("MainActivity", name + " confirmed: " + checked);
    }
}
