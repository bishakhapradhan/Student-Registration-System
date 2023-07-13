package com.example.bit6.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bit6.R;
import com.example.bit6.adapters.StudentAdapter;
import com.example.bit6.content_providers.SqLiteDBHelper;
import com.example.bit6.models.StudentData;

public class FragmentShowRecord
        extends Fragment
        implements StudentAdapter.StudentRecyclerViewAdapterInterface {

    RecyclerView recyclerView;

    StudentAdapter studentAdapter;

    SqLiteDBHelper sqLiteDBHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_records, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sqLiteDBHelper = new SqLiteDBHelper(requireContext());
        recyclerView = view.findViewById(R.id.rvStudentList);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        studentAdapter = new StudentAdapter(getContext(),
                                            sqLiteDBHelper.getAllStudentRecords(),
                                            this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(studentAdapter);
        studentAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateStudentData(StudentData studentData) {
        //requireActivity() Returns the FragmentActivity this fragment is currently associated with.fragment is hosted
        // and casting that activity to the named one gives access to its member method(s). in our case: "FragmentHolderActivity"
        ((FragmentHolderActivity) requireActivity()).replaceFragment(new FragmentUpdateRecord(studentData));
    }

    @Override
    public void deleteStudentData(int studentId) {

    }
}
