package com.colebuhmanhw3.api;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import com.colebuhmanhw3.api.models.Conversation;

public class ConversationAdapter extends CustomAdapter<Conversation> {
    ConversationClickListener listener;

    public ConversationAdapter(ObservableArrayList<Conversation> data, ConversationClickListener listener) {
        super(data);
        this.listener = listener;
    }

    @Override
    protected int getLayout() {
        return R.layout.list_item;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Conversation conversation = data.get(position);
        Button button = holder.getItemView().findViewById(R.id.conversation);
        button.setText(conversation.getName());
        button.setOnClickListener((view) -> {
            listener.onClick(conversation);
        });
    }
}
