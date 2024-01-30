package com.example.retrofittutorial.Navfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofittutorial.ModelResponse.UpdatePasswordResponse;
import com.example.retrofittutorial.ModelResponse.UpdateUserResponse;
import com.example.retrofittutorial.R;
import com.example.retrofittutorial.RetrofitClient;
import com.example.retrofittutorial.SharedPrefManager;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements View.OnClickListener {


    EditText userName , userEmail , currentPassword , newPassword;
    SharedPrefManager sharedPrefManager;
    Button btn ,updatePassBtn;
    int userId;
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        userName = view.findViewById(R.id.updateUsername);
        userEmail = view.findViewById(R.id.updateEmail);
        btn = view.findViewById(R.id.updateProfileBtn);
        currentPassword = view.findViewById(R.id.currentPassword);
        newPassword = view.findViewById(R.id.newPassword);
        updatePassBtn = view.findViewById(R.id.newPasswordBtn);

        sharedPrefManager = new SharedPrefManager(getActivity());

        userId = sharedPrefManager.getUser().getId();
        email  = sharedPrefManager.getUser().getEmail();


        btn.setOnClickListener(this);
        updatePassBtn.setOnClickListener(this);


        return  view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.updateProfileBtn:
                updateUserAccount();
                break;
            case R.id.newPasswordBtn:
                updatePassword();
        }

    }

    private void updatePassword() {

        String cPassword = currentPassword.getText().toString().trim();
        String nPassword = newPassword.getText().toString().trim();

        if(cPassword.isEmpty()){
            currentPassword.requestFocus();
            currentPassword.setError("Please Enter your current password !");
            return;
        }
        if(cPassword.length() < 8){
            currentPassword.requestFocus();
            currentPassword.setError("Please Enter your current Password length greater than 8!");
            return;
        }
        if(nPassword.isEmpty()){
            newPassword.requestFocus();
            newPassword.setError("Please Enter your current password !");
            return;
        }
        if(nPassword.length() < 8){
            newPassword.requestFocus();
            newPassword.setError("Please Enter your current Password length greater than 8!");
            return;
        }



        Call<UpdatePasswordResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .updatePassword(email , cPassword ,nPassword);

        call.enqueue(new Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {

                UpdatePasswordResponse updatePasswordResponse = response.body();

                if(response.isSuccessful()){
                    if(updatePasswordResponse.getError().equals("200")){


                        Toast.makeText(getActivity(), updatePasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }


            @Override
            public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void updateUserAccount() {

        String sUsername = userName.getText().toString().trim();
        String sEmail = userEmail.getText().toString().trim();

        if(sUsername.isEmpty()){
            userName.requestFocus();
            userName.setError("Please Enter your name !");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()){
            userEmail.requestFocus();
            userEmail.setError("Please Enter your correct email !");
            return;
        }

        Call<UpdateUserResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateUser(userId ,sUsername ,sEmail);


        call.enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {

                UpdateUserResponse updateUserResponse = response.body();

                if(response.isSuccessful()){
                    if(updateUserResponse.getError().equals("200")){

                        sharedPrefManager.saveUser(updateUserResponse.getUser());

                        Toast.makeText(getActivity(), updateUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }
}