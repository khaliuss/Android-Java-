package com.example.messengerfirebase.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotViewModel extends AndroidViewModel {

    private FirebaseAuth mAuth;

    private MutableLiveData<String> isResetPassword = new MutableLiveData<>();

    private MutableLiveData<String> error  = new MutableLiveData<>();

    public ForgotViewModel(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<String> getIsResetPassword() {
        return isResetPassword;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void resetPassword(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            isResetPassword.setValue("We send you mail in your email");
                        }else {
                            isResetPassword.setValue("Please put valid data!");
                        }
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
