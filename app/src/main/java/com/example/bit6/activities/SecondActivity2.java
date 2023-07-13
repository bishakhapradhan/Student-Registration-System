package com.example.bit6.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bit6.R;
import com.example.bit6.adapters.StudentViewAdatper;
import com.example.bit6.content_providers.SqLiteDBHelper;
import com.example.bit6.models.StudentData;

public class SecondActivity2
        extends AppCompatActivity
        implements StudentViewAdatper.StudentAdapterInterface {

    //declare variable
    RecyclerView recyclerView;

    SqLiteDBHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        //initialize variable
        recyclerView = findViewById(R.id.rvStudentList);

        dbHelper = new SqLiteDBHelper(this);

        populateRecyclerView();
    }

    private void populateRecyclerView() {
        //define layout manager type to be used in recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        //data can be passed to the adapter class as a parameter to constructor as well
        StudentViewAdatper adapter = new StudentViewAdatper(this, this);

        //get all records from database and pass it to the setData() of Adapter class
        adapter.setData(dbHelper.getAllStudentRecords());

        //finally set adapter to the recyclerview
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void studentRecordUpdate(StudentData studentData) {

    }

    @Override
    public void deleteStudentRecord(int studentId) {

    }
}
