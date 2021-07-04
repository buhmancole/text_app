package com.colebuhmanhw3.api;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import com.colebuhmanhw3.api.models.Conversation;

public class MessageAdapter extends CustomAdapter<String> {
    public MessageAdapter(ObservableArrayList<String> data) {
        super(data);
    }

    @Override
    protected int getLayout() {
        return R.layout.message_item;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String s = data.get(position);
        TextView text = holder.getItemView().findViewById(R.id.message);
        text.setText(s);
    }
}
