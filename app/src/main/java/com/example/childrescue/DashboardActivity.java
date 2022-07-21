package com.example.childrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    TextView fullNameTextView,phoneNumberTextView;
    Button LogoutBtn;
    CardView SearchChild, ReportChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        rootNode = FirebaseDatabase.getInstance("https://child-rescue-c7aa3-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference("users");

        mAuth = FirebaseAuth.getInstance();

        fullNameTextView = findViewById(R.id.fullName);
        phoneNumberTextView = findViewById(R.id.phoneNumber);
        LogoutBtn = findViewById(R.id.signOut);

        SearchChild = findViewById(R.id.SearchForChild);
        ReportChild = findViewById(R.id.ReportChild);

        SearchChild.setOnClickListener(this);
        ReportChild.setOnClickListener(this);
        LogoutBtn.setOnClickListener(this);

        reference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);
                if (userProfile != null){
                    String nameFromDB = userProfile.getFullName();
                    String usernameFromDB = userProfile.getUsername();
                    String phoneNoFromDB = userProfile.getPhoneNo();
                    String emailFromDB = userProfile.getEmail();

                    fullNameTextView.setText(nameFromDB);
                    phoneNumberTextView.setText(phoneNoFromDB);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signOut:
                mAuth.signOut();
                Intent intent = new Intent(DashboardActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.SearchForChild:
                Intent intent2 = new Intent(DashboardActivity.this,SearchChildChatBotActivity.class);
                startActivity(intent2);
                break;
            case R.id.ReportChild:
                Intent intent3 = new Intent(DashboardActivity.this,ReportChildChatBotActivity.class);
                startActivity(intent3);
                break;
        }
    }
}