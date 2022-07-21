package com.example.childrescue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button callSignUp, login_btn;
    ImageView logoImage;
    TextView logoText, sloganText;
    TextInputLayout logEmail, logPassword;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        //Hooks
        callSignUp = findViewById(R.id.signup_screen);
        logoImage = findViewById(R.id.subLogoImage);
        logoText = findViewById(R.id.subTextImage);
        sloganText = findViewById(R.id.slogan_name);
        logEmail = findViewById(R.id.email);
        logPassword = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);

        callSignUp.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    private Boolean validateEmail(){
        String val = logEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            logEmail.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(emailPattern)) {
            logEmail.setError("Invalid email address");
            return false;
        }
        else{
            logEmail.setError(null);
            logEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val = logPassword.getEditText().getText().toString();

        if (val.isEmpty()){
            logPassword.setError("Field cannot be empty");
            return false;
        }
        else{
            logPassword.setError(null);
            logPassword.setErrorEnabled(false);
            return true;
        }
    }

    private void isUser() {

        String userEnteredEmail = logEmail.getEditText().getText().toString().trim();
        String userEnteredPassword = logPassword.getEditText().getText().toString().trim();

        rootNode = FirebaseDatabase.getInstance("https://child-rescue-c7aa3-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference("users");

        mAuth.signInWithEmailAndPassword(userEnteredEmail,userEnteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                    startActivity(intent);
                }
                else {

                    Query checkUserEmail = reference.orderByChild("email").equalTo(userEnteredEmail);

                    checkUserEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){

                                logEmail.setError(null);
                                logEmail.setErrorEnabled(false);

                                Query checkUserPassword = reference.orderByChild("password").equalTo(userEnteredPassword);

                                checkUserPassword.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            logPassword.setError(null);
                                            logPassword.setErrorEnabled(false);
                                        }
                                        else {
                                            logPassword.setError("Wrong Password");
                                            logPassword.requestFocus();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                            else {
                                logEmail.setError("No such Email exist");
                                logEmail.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });





    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_screen:

                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(logoImage, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "slogan_text");
                pairs[3] = new Pair<View, String>(logEmail, "fullName_tran");
                pairs[4] = new Pair<View, String>(logPassword, "password_tran");
                pairs[5] = new Pair<View, String>(login_btn, "go_button_tran");
                pairs[6] = new Pair<View, String>(callSignUp, "login_signup_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
                startActivity(intent,options.toBundle());

                break;
            case R.id.login_btn:

                if (!validateEmail() | !validatePassword()){
                    return;
                }
                else{
                    isUser();
                }
                break;
        }
    }


}