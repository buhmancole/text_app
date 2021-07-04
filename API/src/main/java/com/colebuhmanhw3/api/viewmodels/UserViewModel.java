package com.colebuhmanhw3.api.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.colebuhmanhw3.api.models.Conversation;
import com.colebuhmanhw3.api.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Date;


public class UserViewModel extends ViewModel {
    FirebaseAuth auth;
    DatabaseReference dbc;
    DatabaseReference dbu;
    MutableLiveData<User> user = new MutableLiveData<>();
    ObservableArrayList<String> users;
    ObservableArrayList<String> conversationNames;
    ObservableArrayList<Conversation> conversations;

    public UserViewModel() {
        this.auth = FirebaseAuth.getInstance();
        this.dbu = FirebaseDatabase.getInstance().getReference().child("users");
        this.dbc = FirebaseDatabase.getInstance().getReference().child("conversations");
        this.auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fbUser = auth.getCurrentUser();
                if (fbUser == null) {
                    user.setValue(null);
                } else {
                    user.setValue(new User(fbUser));
                }
            }
        });
    }

    public void checkConversations() {
        if (conversationNames == null) {
            conversationNames = new ObservableArrayList<String>();
        }
        if (conversations == null) {
            conversations = new ObservableArrayList<Conversation>();
        }
    }

    public ObservableArrayList<String> getConversationNames() {
        if (conversationNames == null) {
            checkConversations();
            loadConversations();
        }
        return conversationNames;
    }

    public ObservableArrayList<Conversation> getConversations() {
        if (conversations == null) {
            checkConversations();
            loadConversations();
        }
        return conversations;
    }

    public Conversation getConversation(String name) {
        for (Conversation conversation : getConversations()) {
            if (conversation.name.equals(name)) {
                return conversation;
            }
        }
        return null;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public String getUserName() {
        if (auth.getCurrentUser() != null) {
            return auth.getCurrentUser().getDisplayName();
        }
        return null;
    }

    public ObservableArrayList<String> getUsers() {
        if (users == null) {
            users = new ObservableArrayList<String>();
            loadUsers();
        }
        return users;
    }

    public void addConversation(String user2) {
        String user1 = getUserName();
        dbc.child(user1).child(user2).setValue("");
        dbc.child(user2).child(user1).setValue("");
    }

    public void addMessage(String user2, String message) {
        String user1 = getUserName();
        long now = new Date().getTime();
        dbc.child(user1).child(user2).child(String.valueOf(now)).setValue(user1 + ": " + message);
        dbc.child(user2).child(user1).child(String.valueOf(now)).setValue(user1 + ": " + message);
    }

    public void loadUsers() {
        dbu.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String s = snapshot.getKey();
                users.add(s);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadConversations() {
        dbc.child(getUserName()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String s = snapshot.getKey();
                conversationNames.add(s);

                Conversation conversation = new Conversation(snapshot.getKey());
                conversations.add(conversation);
                Log.d(s, s);
                snapshot.getRef().addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        conversation.addMessage(snapshot.getValue(String.class));
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void signUp(String email, String password, String name) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = auth.getCurrentUser();
                UserProfileChangeRequest update = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                user.updateProfile(update);
                dbu.child(name).setValue(user.getUid());
            }
        });
    }

    public void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password);
    }

    public void signOut() {
        auth.signOut();
    }
}

