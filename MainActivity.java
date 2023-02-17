package com.example.Caloritrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    public static final String NAME = "name";
    TextView textDate;
    TextView textName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textName = findViewById(R.id.textName);


        Date currentTime = Calendar.getInstance().getTime();
        String fullDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        String[] splitDate = fullDate.split(",");
        String formattedDate = splitDate[1] + ", " + splitDate[2];
        textDate = findViewById(R.id.textDate);
        textDate.setText(formattedDate);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String username = documentSnapshot.getString(NAME);
                    textName.setText(capatalizeString(username));
                }
            }
        });
    }

    private static String capatalizeString(String username) {
        char[] chars = username.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this);

        startActivity(intent);

    }

    public void lougout (View view) {

        new AlertDialog.Builder(this) .setTitle("Confirm Log out.").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Successfully logged out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }).setNegativeButton("Cancel", null).show();
    }

}
