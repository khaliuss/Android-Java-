package com.example.messengerfirebase.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordViewModel extends AndroidViewModel {

    private FirebaseAuth mAuth;

    private MutableLiveData<Boolean> success = new MutableLiveData<>();

    private MutableLiveData<String> error  = new MutableLiveData<>();

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> getIsSuccess() {
        return success;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void resetPassword(String email){

        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        success.setValue(true);
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
