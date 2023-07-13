package com.example.bit6.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bit6.R;
import com.example.bit6.adapters.StudentAdapter;
import com.example.bit6.content_providers.SqLiteDBHelper;
import com.example.bit6.models.StudentData;
import com.example.bit6.service.CustomService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity
        extends AppCompatActivity
        implements StudentAdapter.StudentRecyclerViewAdapterInterface {

    SqLiteDBHelper sqLiteDBHelper = new SqLiteDBHelper(this);

    Bundle bundle;

    RecyclerView recyclerView;

    StudentAdapter studentAdapter;

    MaterialButton buttonSearchRecord;

    TextInputEditText editTextStudentId;

    SharedPreferences sharedPreferences;
    String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        recyclerView = findViewById(R.id.rvStudentList);

        sharedPreferences = getSharedPreferences("mySharedPrefs", MODE_PRIVATE);

        key = sharedPreferences.getString("key", "");

        Log.d("SecondActivity", "preference value passed from ActivityMain: " + key);

        buttonSearchRecord = findViewById(R.id.btnSearchStudent);
        editTextStudentId = findViewById(R.id.etStudentId);

        bundle = getIntent().getBundleExtra("bundleData");

        readRecordsFromDb(sqLiteDBHelper.getAllStudentRecords());
        //getDataWithBundle();
        //getDataWithIntent();
        //getDataWithSharedPreferences();
        setUpRecyclerView();
        handleButtonClick();

    }

    private void handleButtonClick() {
        buttonSearchRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextStudentId.getText() != null) {
                    //setUpRecyclerView();
                    studentAdapter.setData(
                            sqLiteDBHelper.getSingleStudentRecord(Integer.valueOf(editTextStudentId.getText().toString())));
                }
            }
        });
    }
    
    private void readRecordsFromDb(ArrayList<StudentData> allStudentRecords) {
        for (StudentData data : allStudentRecords) {
            Log.d("SecondActivity", "student id: " + data.getId());
            Log.d("SecondActivity", "student first name: " + data.getFirstName());
            Log.d("SecondActivity", "student last name: " + data.getLastName());
            Log.d("SecondActivity", "student gender : " + data.getGender());
            Log.d("SecondActivity", "student country: " + data.getCountry());
            Log.d("SecondActivity", "student phone: " + data.getPhone());
        }
    }

    private void storeRecordInDb(List<StudentData> studentData) {
        sqLiteDBHelper.insertStudentRecord(studentData);
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //StudentAdapter studentAdapter = new StudentAdapter(this, sqLiteDBHelper.getAllStudentRecords());
        //StudentAdapter studentAdapter = new StudentAdapter(this, bundle.getParcelableArrayList("studentList"));
        studentAdapter = new StudentAdapter(this,
                                            sqLiteDBHelper.getAllStudentRecords(),
                                            this);
        studentAdapter.setData(sqLiteDBHelper.getAllStudentRecords());
        recyclerView.setAdapter(studentAdapter);
    }


    private void getDataWithBundle() {

        Log.d("SecondActivity", "bundle first name: " + bundle.getString("fname"));
        Log.d("SecondActivity", "bundle last name: " + bundle.getString("lname"));
        Log.d("SecondActivity", "bundle gender: " + bundle.getString("gender"));
        Log.d("SecondActivity", "bundle country: " + bundle.getString("country"));
        Log.d("SecondActivity", "bundle phone: " + bundle.getString("phone"));
        Log.d("SecondActivity", "\n");
    }

    private void getDataWithIntent() {
        Intent dataIntent = getIntent();

        Log.d("SecondActivity", "intent first name: " + dataIntent.getStringExtra("fname"));
        Log.d("SecondActivity", "intent last name: " + dataIntent.getStringExtra("lname"));
        Log.d("SecondActivity", "intent gender: " + dataIntent.getStringExtra("gender"));
        Log.d("SecondActivity", "intent country: " + dataIntent.getStringExtra("country"));
        Log.d("SecondActivity", "intent phone: " + dataIntent.getStringExtra("phone"));
        Log.d("SecondActivity", "\n");
    }

    private void getDataWithSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        Log.d("SecondActivity", "prefs first name: " + sharedPreferences.getString("prefFirstName", ""));
        Log.d("SecondActivity", "prefs last name: " + sharedPreferences.getString("prefLastName", ""));
        Log.d("SecondActivity", "prefs gender: " + sharedPreferences.getString("prefGender", ""));
        Log.d("SecondActivity", "prefs country: " + sharedPreferences.getString("prefCountry", ""));
        Log.d("SecondActivity", "prefs phone: " + sharedPreferences.getString("prefPhone", ""));
        Log.d("SecondActivity", "\n");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(getApplicationContext(), CustomService.class));
    }


    @Override
    public void updateStudentData(StudentData studentData) {
        Log.d("SecondActivity", "student id: " + studentData.getId());
        Log.d("SecondActivity", "student name: " + studentData.getFirstName());
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //StudentRecordEditFragment studentRecordEditFragment = new StudentRecordEditFragment();
        //studentRecordEditFragment.setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        //studentRecordEditFragment.show(fragmentManager, "");
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        intent.putExtra("shouldUpdate", true);

        //here we are using Serializable
        intent.putExtra("data", studentData);
        startActivity(intent);
    }

    @Override
    public void deleteStudentData(int studentId) {
        SqLiteDBHelper sqLiteDBHelper = new SqLiteDBHelper(this);
        sqLiteDBHelper.deleteStudentData(studentId);
    }
}
