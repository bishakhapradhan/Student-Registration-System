package com.example.bit6.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;
import androidx.fragment.app.FragmentTransaction;

import com.example.bit6.R;
import com.example.bit6.content_providers.SqLiteDBHelper;
import com.example.bit6.fragments.FragmentHolderActivity;
import com.example.bit6.models.StudentData;
import com.example.bit6.service.CustomService;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity {

    private NotificationManager notificationManager;

    private NotificationChannel notificationChannel;

    private NotificationCompat.Builder notificationBuilder;

    //Declare variables with proper data types that needs to be manipulated
    private EditText inputFirstName;

    private EditText inputLastName;

    private RadioGroup radioGroupGender;

    private Spinner countrySpinner;

    private EditText inputPhoneNumber;

    private MaterialButton buttonSubmit;

    private MaterialButton buttonShowRecords;

    private SqLiteDBHelper sqLiteDBHelper;

    String firstName, lastName, gender = "", country, phone;

    StudentData studentData;

    Button starMyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteDBHelper = new SqLiteDBHelper(this);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //assign variables
        inputFirstName = findViewById(R.id.etFirstName);
        inputLastName = findViewById(R.id.etLastName);
        radioGroupGender = findViewById(R.id.rgGender);
        countrySpinner = findViewById(R.id.spnCountry);
        inputPhoneNumber = findViewById(R.id.etPhoneNumber);
        buttonSubmit = findViewById(R.id.btnSubmit);

        buttonShowRecords = findViewById(R.id.btnShowRecords);
        starMyService = findViewById(R.id.btnStartService);

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioTest = findViewById(i);
                gender = radioTest.getText().toString();
            }
        });

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //handleRadioGroup();
        //handleCountryData();

        studentData = (StudentData) getIntent().getSerializableExtra("data");

        if (studentData != null) {
            buttonSubmit.setText("Update");
            handleRecordUpdate();
        }
        handleButtonClick();

        //read all student records
        //sqLiteDBHelper.getAllStudentRecord();

    }

    //private void initializeCountrySpinner(){
    //    String countryArray[] = new String[]{"Nepal", "India", "Bhutan"};
    //    ArrayAdapter<String> adapter =
    //            new ArrayAdapter<>(this,
    //                               androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
    //                               countryArray);
    //    adapter.setDropDownViewResource(R.layout.spinner_text);
    //    countrySpinner.setAdapter(adapter);
    //}


    private void handleButtonClick() {
        //Submit btnShowRecords click
    /*    buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFormValid()) {


                    passDataWithSharedPreferences();
                    //startService(new Intent(getApplicationContext(), CustomService.class));
                    startActivity(passDataWithIntent());
                }
            }
        });*/

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFormValid()) {
                    if (studentData != null) {
                        StudentData dataList = new StudentData(studentData.getId(),
                                                               firstName,
                                                               lastName,
                                                               gender,
                                                               country,
                                                               phone);
                        sqLiteDBHelper.updateStudentRecord(dataList);
                    } else {
                        handleFormData();
                        //createNotification();
                        //androidPreferenceExample();
                    }
                }

            }
        });

        buttonShowRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(passDataWithIntent());
                startActivity(new Intent(MainActivity.this, FragmentHolderActivity.class));
            }
        });

        //start service
        starMyService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), CustomService.class));

            }
        });
    }

    private void handleRadioGroup() {
        // first get checked radio btnShowRecords from the RadioGroup
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                gender = radioButton.getText().toString();
            }
        });

    }

    private void handleCountryData() {
        //always check for the changes that might occur when a user selects different item
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //assign value to 'country'
                country = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private Intent passDataWithIntent() {

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Bundle bundle = new Bundle();
        //method 1
        bundle.putString("fname", firstName);
        bundle.putString("lname", lastName);
        bundle.putString("gender", gender);
        bundle.putString("country", country);
        bundle.putString("phone", phone);

        //method 2
        intent.putExtra("fname", firstName);
        intent.putExtra("lname", lastName);
        intent.putExtra("gender", gender);
        intent.putExtra("country", country);
        intent.putExtra("phone", phone);

        //method 3
        ArrayList<StudentData> arrayList = new ArrayList<>();
        arrayList.add(new StudentData(firstName, lastName, gender, country, phone));

        intent.putExtra("bundleData", bundle);
        return intent;
    }

    private void passDataWithSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("prefFirstName", firstName);
        editor.putString("prefLastName", lastName);
        editor.putString("prefGender", gender);
        editor.putString("prefCountry", country);
        editor.putString("prefPhone", phone);
        editor.apply();
    }

    private boolean validateFirstName() {
        if (!inputFirstName.getText().toString().isEmpty()) {
            firstName = inputFirstName.getText().toString();
            return true;
        }
        inputFirstName.setError("First name cannot be empty !");
        return false;
    }

    private boolean validateLastName() {
        if (!inputLastName.getText().toString().isEmpty()) {
            lastName = inputLastName.getText().toString();
            return true;
        }
        inputLastName.setError("Last name cannot be empty !");
        return false;
    }

    private boolean validatePhoneNumber() {
        if (inputPhoneNumber.getText().toString().length() == 10) {
            phone = inputPhoneNumber.getText().toString();
            return true;
        }
        inputPhoneNumber.setError("Phone number must contain 10 digits");
        return false;
    }

    private boolean validateGender() {
        if (!gender.isEmpty()) {
            return true;
        }
        Toast.makeText(this, "Select gender !", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isFormValid() {
        return validateFirstName() && validateLastName() && validateGender() && validatePhoneNumber();
    }

    private void handleFormData() {
        firstName = inputFirstName.getText().toString();
        lastName = inputLastName.getText().toString();
        phone = inputPhoneNumber.getText().toString();
        List<StudentData> studentData = new ArrayList<>();
        studentData.add(new StudentData(firstName,
                                        lastName,
                                        gender,
                                        country,
                                        phone));

        //insert data to database
        sqLiteDBHelper.insertStudentRecord(studentData);
        startActivity(new Intent(MainActivity.this, FragmentHolderActivity.class));
    }

    private void handleRecordUpdate() {

        if (studentData != null) {
            inputFirstName.setText(studentData.getFirstName());
            inputLastName.setText(studentData.getLastName());
            countrySpinner.setPrompt(studentData.getCountry());
            inputPhoneNumber.setText(studentData.getPhone());

            switch (studentData.getGender()) {
                case "Male":
                    RadioButton radioMale = radioGroupGender.findViewById(R.id.radioButton2);
                    radioMale.setChecked(true);
                    break;

                case "Female":
                    RadioButton radioFemale = radioGroupGender.findViewById(R.id.radioButton);
                    radioFemale.setChecked(true);
                    break;

                case "Other":
                    RadioButton radioOther = radioGroupGender.findViewById(R.id.radioButton3);
                    radioOther.setChecked(true);
                    break;
            }
        }

    }

    private void createNotification() {
        //3
        Intent intent = new Intent(this, SecondActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                                                                0,
                                                                intent,
                                                                PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel("100",
                                                          "Notification description",
                                                          NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        notificationBuilder = new NotificationCompat.Builder(this, "100")
                .setContentTitle("Notification")
                .setContentText("Yeah, notification is created!!")
                .setSmallIcon(R.drawable.notification)
                .setContentIntent(pendingIntent);
        notificationManager.notify(100, notificationBuilder.build());
    }

    private void androidPreferenceExample() {
        SharedPreferences preferences = getSharedPreferences("mySharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = preferences.edit();
        prefEditor.putString("key", inputFirstName.getText().toString()).apply();
    }

    //add fragment
    private void addFragment() {

    }
}