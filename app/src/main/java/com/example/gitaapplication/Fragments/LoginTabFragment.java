package com.example.gitaapplication.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gitaapplication.LoginActivity;
import com.example.gitaapplication.R;
import com.example.gitaapplication.UserInfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;


public class LoginTabFragment extends Fragment {
    View view;
    EditText emailSI, passwordSI;
    Button signInBtn;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String email, password;

    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login_tab, container, false);
        initializeVarXML();

        return view;
    }

    public void initializeVarXML() {
        emailSI = view.findViewById(R.id.userNameSignInET);
        passwordSI = view.findViewById(R.id.passwordSignInET);
        signInBtn = view.findViewById(R.id.signInButton);

        progressDialog = new ProgressDialog(getContext());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });


    }

    public void checkLogin() {
        email = emailSI.getText().toString().trim();
        password = passwordSI.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please Wait While Login...");
        progressDialog.setTitle("Login..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    login();

                    Log.d("TAG", "onComplete: ");
                    Toast.makeText(getContext(), "Sign In Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "User Do Not exist" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void login() {

        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        startActivity(intent);
    }
}

  /*  private void signInUser(){
        if (!userNameSI.getText().toString().isEmpty() &&
                !passwordSI.getText().toString().isEmpty()){
            if (mAuth!=null){

                mAuth.signInWithEmailAndPassword(userNameSI.getText().toString(),
                                passwordSI.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(getActivity().getApplicationContext(), UserInfoActivity.class));
                                getActivity().finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }
        else
        {
            Toast.makeText(getContext(),"Please Fill Both Fields! ", Toast.LENGTH_LONG).show();

        }
    }*/
