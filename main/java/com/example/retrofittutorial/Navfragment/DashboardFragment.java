package com.example.retrofittutorial.Navfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retrofittutorial.R;
import com.example.retrofittutorial.SharedPrefManager;


public class DashboardFragment extends Fragment {

    TextView etName, etEmail;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        etName = view.findViewById(R.id.dashName);
        etEmail = view.findViewById(R.id.dashEamil);

        sharedPrefManager = new SharedPrefManager(getActivity());
        String username = "Hey "+sharedPrefManager.getUser().getUsername();


        etName.setText(username);
        etEmail.setText(sharedPrefManager.getUser().getEmail());



        return view;
    }
}