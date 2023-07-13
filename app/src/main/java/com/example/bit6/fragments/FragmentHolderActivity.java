package com.example.bit6.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bit6.R;

public class FragmentHolderActivity
        extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_holder_activity);
        addFragment();
    }

    //to add fragment
    private void addFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.flRootContainer, new FragmentShowRecord(), "recFragment1");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //to replace fragment
    public void replaceFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flRootContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
