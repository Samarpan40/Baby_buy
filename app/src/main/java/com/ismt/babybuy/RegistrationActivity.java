package com.ismt.babybuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class RegistrationActivity extends AppCompatActivity {
    EditText edName, edEmail, edPassword, edCPassword;
    Button btnRegister;
    //string for get input edit text to string
    String name, email, password, confirmPassword;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        edName = findViewById(R.id.editTextName);
        edEmail = findViewById(R.id.editTextEmail);
        edPassword = findViewById(R.id.editTextPassword);
        edCPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.buttonRegister);
        //initialize objects to be used
        databaseHelper = new DatabaseHelper(RegistrationActivity.this);
        user = new User();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edName.getText().toString();
                email = edEmail.getText().toString();
                password = edPassword.getText().toString();
                confirmPassword = edCPassword.getText().toString();
                email = edEmail.getText().toString();
                password = edPassword.getText().toString();
                confirmPassword = edCPassword.getText().toString();
                if (name.isEmpty()) {
                    edName.setError("Name is Required");
                } else if (email.isEmpty()) {
                    edEmail.setError("Email is Required");
                } else if (password.isEmpty()) {
                    edPassword.setError("Password is Required");
                }
                else if (password.length()<8){
                    edPassword.setError("Weak password!!. Try at least eight character long.");

                }else if (confirmPassword.isEmpty()) {
                    edCPassword.setError("Confirm password is Required");
                } else if (!password.equals(confirmPassword)) {
                    edCPassword.setError("Password and confirm password does not match");
                } else {
                    Boolean checkUser = databaseHelper.checkUserName(email);
                    if (checkUser == false || edEmail.getText().toString().trim().matches("[a-zA-Z0-9]+@[a-z]+.+[a-z]+")) {
                        postDataToSQLite();
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Snackbar.make(btnRegister, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
                    }

                }


            }
        });}



    private void postDataToSQLite() {
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        databaseHelper.addUser(user);


        // Snack Bar to show success message that record saved successfully
        Snackbar.make(btnRegister, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();

    }

}