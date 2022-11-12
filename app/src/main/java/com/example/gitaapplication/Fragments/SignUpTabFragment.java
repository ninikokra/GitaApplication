package com.example.gitaapplication.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.google.type.Date;


public class SignUpTabFragment extends Fragment {

    View view;
    EditText userNameSU, passwordSU, repeatPassSU;
    Button signUpButton;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;
    String email, password, repeatPass;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_sign_up_tab, container, false);
        initializeVariables();

        return view;
    }

    public void initializeVariables() {
        userNameSU = view.findViewById(R.id.userNameSignUpET);
        passwordSU = view.findViewById(R.id.passwordSignUpET);
        repeatPassSU = view.findViewById(R.id.repeatPassSignUpET);
        signUpButton = view.findViewById(R.id.signUpButton);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        progressDialog = new ProgressDialog(getContext());

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();

            }
        });
    }

    public void createUser() {

            email = userNameSU.getText().toString().trim();
            password = passwordSU.getText().toString().trim();
            repeatPass = repeatPassSU.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            } else if (password.length() < 6) {
                Toast.makeText(getContext(), "Passwor must be greater then 6 digit", Toast.LENGTH_SHORT).show();
                return;
            } else if (!password.equals(repeatPass)) {
                Toast.makeText(getContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog.setMessage("Please Wait...");
            progressDialog.setTitle("Registration..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();



            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        progressDialog.dismiss();
                        goToLogin();

                        Log.d("TAG", "onComplete: ");
                        Toast.makeText(getContext(), "Account Created", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Error Creating User" + task.getException(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    public void goToLogin() {

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}
