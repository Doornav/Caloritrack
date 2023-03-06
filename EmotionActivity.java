package com.example.betterhelp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EmotionActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    Date currentTime = Calendar.getInstance().getTime();
    String fullDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
    String[] splitDate = fullDate.split(",");
    String formattedDate = splitDate[1] + "," + splitDate[2];

    Button goBack, save;
    EditText descriptionbox;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String userID = firebaseAuth.getCurrentUser().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);

        Log.d(TAG,"formattedDate" + formattedDate);

        TextView nameTextView = findViewById(R.id.Emotion);
        TextView descriptionTExtView = findViewById(R.id.Description);
        String name = getIntent().getStringExtra("NAME");
        String description = getIntent().getStringExtra("DESCRIPTION");
        nameTextView.setText(name);
        descriptionTExtView.setText(description);

        DocumentReference docRef = firestore.collection("users").document(userID).collection(formattedDate).document(name);
        Log.d(TAG,"EmotionActivity Reflection Name" + name);
        descriptionbox = findViewById(R.id.descriptionBox);
         save = findViewById(R.id.saveThought);
         goBack = findViewById(R.id.backFromEdit);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reflection = descriptionbox.getText().toString();
                if (TextUtils.isEmpty(reflection)) {
                    descriptionbox.setError("Type an entry");
                    return;
                }
                Map<String, Object> dataToSave = new HashMap<>();
                dataToSave.put("REFLECTION", reflection);
                docRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Reflection Save: " + reflection);
                        Toast.makeText(EmotionActivity.this, "Reflection Saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
            }
        });

    }

}
