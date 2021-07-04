package com.colebuhmanhw3.phoneapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colebuhmanhw3.api.viewmodels.UserViewModel;

public class SignInFragment extends Fragment {
    public SignInFragment() {
        super(R.layout.fragment_sign_in);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        EditText email = view.findViewById(R.id.email);
        EditText password = view.findViewById(R.id.password);

        view.findViewById(R.id.signIn).setOnClickListener(v -> {
            viewModel.signIn(email.getText().toString(), password.getText().toString());
        });
        view.findViewById(R.id.toSignUp).setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SignUpFragment.class, null).addToBackStack(null).commit();
        });
    }
}
