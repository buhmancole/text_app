package com.colebuhmanhw3.api.models;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Conversation {
    public String name;
    public ObservableArrayList<String> messages;

    public Conversation() {
        messages = new ObservableArrayList<>();
    };

    public Conversation(String name) {
        this.name = name;
        messages = new ObservableArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addMessage(String s) {
        messages.add(s);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Conversation) {
            Conversation other = (Conversation) obj;
            return other.name.equals(name);
        }
        return false;
    }
}
