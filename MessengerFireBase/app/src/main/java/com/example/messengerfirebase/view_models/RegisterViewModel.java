package com.example.messengerfirebase.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.messengerfirebase.data.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterViewModel extends AndroidViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    user.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void signUp(
            String email,
            String password,
            String name,
            String lastName,
            int age,
            boolean status
    ) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser firebaseUser = authResult.getUser();
                        /*if (firebaseUser == null){
                            return;
                        }*/
                        User user = new User(
                                firebaseUser.getUid(),
                                name,
                                lastName,
                                age,
                                status);

                        databaseReference.child(user.getId()).setValue(user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        error.setValue(e.getMessage());
                    }
                });
    }


}
