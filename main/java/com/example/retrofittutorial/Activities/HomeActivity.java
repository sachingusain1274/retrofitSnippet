package com.example.retrofittutorial.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.retrofittutorial.ModelResponse.DeleteAccountResponse;
import com.example.retrofittutorial.Navfragment.DashboardFragment;
import com.example.retrofittutorial.Navfragment.ProfileFragment;
import com.example.retrofittutorial.Navfragment.UserFragment;
import com.example.retrofittutorial.R;
import com.example.retrofittutorial.RetrofitClient;
import com.example.retrofittutorial.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    NavigationBarView navigationBarView;
    SharedPrefManager sharedPrefManager;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationBarView = findViewById(R.id.bottomNav);
        navigationBarView.setOnItemSelectedListener(this);

        loadFragment(new DashboardFragment());

        sharedPrefManager = new SharedPrefManager(getApplicationContext());



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.dashbord:
                fragment = new DashboardFragment();
                break;
            case R.id.user:;
                fragment = new UserFragment();
                break;

            case R.id.profile:
                fragment = new ProfileFragment();
                break;
        }

        if(fragment != null){
            loadFragment(fragment);
        }
        return true;
    }

    public void loadFragment(Fragment fragment){

//        To attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout , fragment).commit();

    }


// for logout menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logutmenu,menu) ;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.logout:
                logoutUser();
                break;

            case R.id.deleteAccount:
                deleteAccount();
                break;



        }


        return super.onOptionsItemSelected(item);
    }

    private void deleteAccount() {

        userId = sharedPrefManager.getUser().getId();

        Call<DeleteAccountResponse> call = RetrofitClient.getInstance().getApi().deleteAccount(userId);

        call.enqueue(new Callback<DeleteAccountResponse>() {
            @Override
            public void onResponse(Call<DeleteAccountResponse> call, Response<DeleteAccountResponse> response) {

                DeleteAccountResponse dar = response.body();

                if(response.isSuccessful()){
                    if(dar.getError().equals("200")){

                        logoutUser();
                        Toast.makeText(HomeActivity.this, dar.getMessage(), Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(HomeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<DeleteAccountResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void logoutUser() {

        sharedPrefManager.logoutUser();
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(i);

        Toast.makeText(this, "you have been logout", Toast.LENGTH_SHORT).show();


    }
}