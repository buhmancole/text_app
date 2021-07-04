package com.colebuhmanhw3.watchapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.colebuhmanhw3.api.viewmodels.UserViewModel;

public class AddressFragment extends Fragment {
    public AddressFragment() {
        super(R.layout.fragment_address);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserViewModel viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        view.findViewById(R.id.create).setOnClickListener(v -> {
            TextView address = view.findViewById(R.id.address);
            String s = address.getText().toString();
            if (viewModel.getUsers().contains(s) && !viewModel.getUserName().equals(s)) {
                viewModel.addConversation(s);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, MainFragment.class, null).commit();
            }
        });
    }
}
