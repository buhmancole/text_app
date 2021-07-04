package com.colebuhmanhw3.phoneapp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.colebuhmanhw3.api.ConversationAdapter;
import com.colebuhmanhw3.api.viewmodels.UserViewModel;

public class MainFragment extends Fragment {
    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserViewModel viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        if (viewModel.getUserName() == null) {
            return;
        }
        RecyclerView conversationList = view.findViewById(R.id.conversation_list);
        ConversationAdapter adapter = new ConversationAdapter(viewModel.getConversations(), conversation -> {
            ((MainActivity)getActivity()).conversation = conversation.getName();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, ConversationFragment.class, null).addToBackStack(null).commit();
        });

        conversationList.setAdapter(adapter);
        conversationList.setLayoutManager(new LinearLayoutManager(getActivity()));

        view.findViewById(R.id.floating_action_button).setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, AddressFragment.class, null).addToBackStack(null).commit();
        });
    }
}
