package com.example.bit6.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bit6.R;
import com.example.bit6.models.StudentData;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<StudentData> studentDataArrayList;

    StudentRecyclerViewAdapterInterface studentRecyclerViewAdapterInterface;

    public StudentAdapter(Context context,
                          List<StudentData> studentDataList,
                          StudentRecyclerViewAdapterInterface adapterInterface) {
        this.mContext = context;
        this.studentDataArrayList = studentDataList;
        this.studentRecyclerViewAdapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public StudentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.student_records_list_item, parent, false);
        return new StudentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StudentAdapterViewHolder studentAdapterViewHolder = (StudentAdapterViewHolder) holder;
        StudentData data = studentDataArrayList.get(position);
        studentAdapterViewHolder.tvFirstName.setText(data.getFirstName());
        studentAdapterViewHolder.tvLastName.setText(data.getLastName());
        studentAdapterViewHolder.tvGender.setText(data.getGender());
        studentAdapterViewHolder.tvCountry.setText(data.getCountry());
        studentAdapterViewHolder.tvPhone.setText(data.getPhone());

        //for update
        studentAdapterViewHolder.ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentRecyclerViewAdapterInterface.updateStudentData(data);
            }
        });

        //for delete
        studentAdapterViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentRecyclerViewAdapterInterface.deleteStudentData(data.getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return studentDataArrayList == null ? 0 : studentDataArrayList.size();
    }


    private class StudentAdapterViewHolder
            extends RecyclerView.ViewHolder {

        MaterialTextView tvFirstName;

        MaterialTextView tvLastName;

        MaterialTextView tvGender;

        MaterialTextView tvCountry;

        MaterialTextView tvPhone;

        AppCompatImageView ivUpdate;

        AppCompatImageView ivDelete;

        public StudentAdapterViewHolder(View view) {
            super(view);
            tvFirstName = view.findViewById(R.id.tvFirstName);
            tvLastName = view.findViewById(R.id.tvLastName);
            tvGender = view.findViewById(R.id.tvGender);
            tvCountry = view.findViewById(R.id.tvCountry);
            tvPhone = view.findViewById(R.id.tvPhone);
            ivUpdate = view.findViewById(R.id.ivUpdates);
            ivDelete = view.findViewById(R.id.ivDelete);
        }
    }

    public void setData(ArrayList<StudentData> studentData) {
        this.studentDataArrayList = studentData;
        notifyDataSetChanged();
    }

    public interface StudentRecyclerViewAdapterInterface {
        public void updateStudentData(StudentData studentData);
        public void deleteStudentData(int studentId);
    }
}
