package com.example.retrofittutorial.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.retrofittutorial.ModelResponse.LoginResponse;
import com.example.retrofittutorial.R;
import com.example.retrofittutorial.RetrofitClient;
import com.example.retrofittutorial.SharedPrefManager;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.retrofittutorial.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email , password;
    Button loginBtn;
    TextView registerLink;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        email = findViewById(R.id.loginEmailEditText);
        password = findViewById(R.id.loginPasswordEditText);
        loginBtn = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);

        loginBtn.setOnClickListener(this);
        registerLink.setOnClickListener(this);

        sharedPrefManager = new SharedPrefManager(getApplicationContext());

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.loginButton:
                loginUser();
                break;

            case R.id.registerLink:
                switchToLogin();
                break;
        }
    }

    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();


        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.requestFocus();
            email.setError("Please Enter your correct email !");
        }
        if(password.length() < 8 || password == null){
            password.requestFocus();
            password.setError("Please Enter your Password !");
        }


        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(userEmail , userPassword);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                LoginResponse loginResponse = response.body();

                if(response.isSuccessful()){

                    if(loginResponse.getError().equals("200")){

                        sharedPrefManager.saveUser(loginResponse.getUser());
                        Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
//                    For prevent back step
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }




                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void switchToLogin() {

        Intent i = new Intent(LoginActivity.this , MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(sharedPrefManager.isLoggedInUser()){
            Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
//                    For prevent back step
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    public void sum(){

    }
}