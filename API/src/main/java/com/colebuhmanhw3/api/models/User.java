package com.colebuhmanhw3.api.models;

import com.google.firebase.auth.FirebaseUser;

public class User {
    String uid;
    String email;
    String username;
    public User(FirebaseUser user) {
        this.uid = user.getUid();
        this.email = user.getEmail();
        this.username = user.getDisplayName();
    }

    public String getUsername() {
        return username;
    }
}
