package com.colebuhmanhw3.watchapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.colebuhmanhw3.api.viewmodels.UserViewModel;

public class SignInActivity extends AppCompatActivity {
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, SignInFragment.class, null).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getUser().observe(this, (user) -> {
            if (user != null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
