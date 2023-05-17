package com.radproject.hms;

import static com.radproject.hms.global.GlobalMethods.listenForUserChanges;
import static com.radproject.hms.global.GlobalMethods.redirectToDashboard;
import static com.radproject.hms.global.GlobalMethods.redirectToRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.radproject.hms.global.GlobalMethods;
import com.radproject.hms.global.GlobalVariables;


public class LoginActivity extends AppCompatActivity {
    TextView loginButtonTV, registerClickTV;
    EditText mEmailField, mPasswordField;
    ProgressBar progress_circular;

    String TAG = "LoginActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (FirebaseApp.getApps(this).size() == 0) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }


        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(LoginActivity.this);
        mAuth = FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.username_ET);
        mPasswordField = findViewById(R.id.password_ET);
        loginButtonTV = findViewById(R.id.register_buttonTV);
        progress_circular = findViewById(R.id.progress_circular);
        registerClickTV = findViewById(R.id.registerClickTV);
        isProgress(false);
        initClickEvents();

        if (mAuth.getCurrentUser() != null) {
            redirectToDashboard(LoginActivity.this);
        }
    }

    private void initClickEvents() {
        loginButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        registerClickTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToRegister(LoginActivity.this);
            }
        });
    }

    private void login() {
        // String email = mEmailField.getText().toString();
        //    String password = mPasswordField.getText().toString();
        String email = "dudly@gmail.com";
        String password = "test123";
        isProgress(true);

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.e(TAG, "signInWithEmail:success");
                                redirectToDashboard(LoginActivity.this);
                                isProgress(false);

                                // mapUser(mAuth.getUid());
                                listenForUserChanges(mAuth  .getUid());
                                Log.e(TAG, "onComplete: " + mAuth.getUid());
                                finish();
                            } else {
                                Log.e(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                isProgress(false);
                            }
                        }
                    });
        }
    }

    private void isProgress(Boolean isProgress) {
        if (isProgress) {
            loginButtonTV.setVisibility(View.GONE);
            progress_circular.setVisibility(View.VISIBLE);
        } else {
            loginButtonTV.setVisibility(View.VISIBLE);
            progress_circular.setVisibility(View.GONE);
        }
    }


}
