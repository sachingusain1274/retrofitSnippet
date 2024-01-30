package com.example.retrofittutorial.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.retrofittutorial.ModelResponse.RegisterResponse;
import com.example.retrofittutorial.R;
import com.example.retrofittutorial.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.retrofittutorial.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView loginLink;
    EditText name , email , password;
    Button registorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        name = findViewById(R.id.usernameEdittext);
        email = findViewById(R.id.emailEdittext);
        password = findViewById(R.id.passwordEdittext);
        registorBtn = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);


        loginLink.setOnClickListener(this);
        registorBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.registerButton:
                registerUser();
                break;

            case R.id.loginLink:
                switchToLogin();
                break;
        }

    }

    private void registerUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(userName.isEmpty()){
            name.requestFocus();
            name.setError("Please Enter your name !");
            return;
        }
        if(userEmail.isEmpty()){
            email.requestFocus();
            email.setError("Please Enter your email");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.requestFocus();
            email.setError("Please Enter your correct email !");
            return;
        }
//        if(userPassword.isEmpty()){
//            password.requestFocus();
//            password.setError("Please Enter your password");
//        }
        if(password.length() < 8 || password == null){
            password.requestFocus();
            password.setError("Please Enter your Password !");
            return;
        }

        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .registerUser(userName,userEmail,userPassword);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                RegisterResponse registerResponse = response.body();
                if(response.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this , LoginActivity.class);
//                    For prevent back step
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void switchToLogin(){
        Intent i = new Intent(MainActivity.this ,LoginActivity.class);
        startActivity(i);
    }
}