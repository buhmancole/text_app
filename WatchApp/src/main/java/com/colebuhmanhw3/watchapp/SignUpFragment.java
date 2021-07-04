package com.colebuhmanhw3.watchapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colebuhmanhw3.api.viewmodels.UserViewModel;

public class SignUpFragment extends Fragment {
    public SignUpFragment() {
        super(R.layout.fragment_sign_up);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        EditText email = view.findViewById(R.id.email2);
        EditText password = view.findViewById(R.id.password2);
        EditText username = view.findViewById(R.id.username);

        view.findViewById(R.id.signUp).setOnClickListener(v -> {
            viewModel.signUp(email.getText().toString(), password.getText().toString(), username.getText().toString());
        });
    }
}
