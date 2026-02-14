package com.example.messengerfirebase.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsersViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> user  = new MutableLiveData<>();

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public UsersViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    user.setValue(firebaseAuth.getCurrentUser());
            }
        });
    }




    public void signOut(){
            firebaseAuth.signOut();
    }
}
