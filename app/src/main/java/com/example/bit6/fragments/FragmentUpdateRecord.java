package com.example.bit6.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bit6.R;
import com.example.bit6.content_providers.SqLiteDBHelper;
import com.example.bit6.models.StudentData;
import com.google.android.material.button.MaterialButton;

public class FragmentUpdateRecord
        extends Fragment {

    private StudentData studentData;

    public FragmentUpdateRecord(StudentData data) {
        this.studentData = data;
    }

    private SqLiteDBHelper sqLiteDBHelper;

    //declare variables
    private EditText inputFirstName;

    private EditText inputLastName;

    private RadioGroup radioGroupGender;

    private Spinner countrySpinner;

    private EditText inputPhoneNumber;

    private MaterialButton buttonSubmit;

    String firstName, lastName, gender = "", country, phone;

    View view;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        sqLiteDBHelper = new SqLiteDBHelper(requireContext());

        //here 'view' is provided to us by onCreateView() method. It is similar to the one used in Activities
        //assign variables
        inputFirstName = view.findViewById(R.id.etFirstName);
        inputLastName = view.findViewById(R.id.etLastName);
        radioGroupGender = view.findViewById(R.id.rgGender);
        countrySpinner = view.findViewById(R.id.spnCountry);
        inputPhoneNumber = view.findViewById(R.id.etPhoneNumber);
        buttonSubmit = view.findViewById(R.id.btnSubmit);

        handleGenderRadioGroup();
        handleCountrySpinner();
        handleButtonClick();
    }

    private void handleGenderRadioGroup(){
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioTest = view.findViewById(i);
                gender = radioTest.getText().toString();
            }
        });
    }

    private void handleCountrySpinner(){
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void handleButtonClick(){
        if (studentData != null) {
            StudentData dataList = new StudentData(studentData.getId(),
                                                   firstName,
                                                   lastName,
                                                   gender,
                                                   country,
                                                   phone);
            sqLiteDBHelper.updateStudentRecord(dataList);
        }
    }

}
