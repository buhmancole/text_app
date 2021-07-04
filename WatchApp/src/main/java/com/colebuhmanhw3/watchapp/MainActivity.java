package com.colebuhmanhw3.watchapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.colebuhmanhw3.api.viewmodels.UserViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {
    UserViewModel viewModel;
    String conversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_main, MainFragment.class, null).commit();
        }

        MaterialToolbar toolbar = findViewById(R.id.actionBarTop);
        toolbar.setNavigationOnClickListener(v -> {
            viewModel.signOut();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getUser().observe(this, user -> {
            if (user == null) {
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}