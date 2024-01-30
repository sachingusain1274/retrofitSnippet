package com.example.retrofittutorial.Navfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.retrofittutorial.ModelResponse.FetchUsersResponse;
import com.example.retrofittutorial.ModelResponse.User;
import com.example.retrofittutorial.R;
import com.example.retrofittutorial.RetrofitClient;
import com.example.retrofittutorial.UserAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {

    RecyclerView recyclerView;
    List<User> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        Retrofit coding

        Call<FetchUsersResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .fetchUsers();

        call.enqueue(new Callback<FetchUsersResponse>() {
            @Override
            public void onResponse(Call<FetchUsersResponse> call, Response<FetchUsersResponse> response) {

                FetchUsersResponse fetchUsersResponse =  response.body();
                if(response.isSuccessful()){

                    userList = fetchUsersResponse.getUserList();
                    recyclerView.setAdapter(new UserAdapter(getActivity(),userList));

                }
                else {
                    Toast.makeText(getActivity(),fetchUsersResponse.getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<FetchUsersResponse> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}