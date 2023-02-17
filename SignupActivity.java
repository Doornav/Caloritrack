package com.example.betterhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    public static final String NAME = "name";
    public static final String EMAIL = "Email";
    public static final String TAG = "TAG";
    EditText Name, Email, Password;
    Button Signup_btn;
    TextView toLogin_btn;
    FirebaseAuth firebase_auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Name = findViewById(R.id.signupName);
        Email = findViewById(R.id.signupEmail);
        Password = findViewById(R.id.signupPassword);
        Signup_btn = findViewById(R.id.signupConfirm);
        toLogin_btn = findViewById(R.id.signupToLogin);
        toLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        Signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString();
                String name = Name.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Name.setError("Enter a name");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Email.setError("E-mail required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("Password required");
                    return;
                }
                if (password.length() < 8) {
                    Password.setError("Weak Password");
                    return;
                }

                firebase_auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "User Created", Toast.LENGTH_LONG).show();
                            userID = firebase_auth.getCurrentUser().getUid();
                            DocumentReference docRef = firestore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put(NAME,name);
                            user.put(EMAIL, email);
                            docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "User Profile created for " + userID);
                                }
                            });
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignupActivity.this, "Error:" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
