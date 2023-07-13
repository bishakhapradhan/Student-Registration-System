package com.example.bit6.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bit6.R;
import com.example.bit6.models.StudentData;

import java.util.List;

public class StudentViewAdatper
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<StudentData> dataList;
    StudentAdapterInterface studentAdapterInterface;


    public void setData(List<StudentData> allList) {
        dataList = allList;
        notifyDataSetChanged();
    }

    public StudentViewAdatper(Context context, StudentAdapterInterface adapterInterface ) {
        this.dataList = dataList;
        this.studentAdapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    public class StudentViewHolder extends RecyclerView.ViewHolder{

        TextView firstName;
        TextView lasttName;
        TextView gender;
        TextView country;
        TextView phone;

        public StudentViewHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.tvFirstName);
            lasttName = itemView.findViewById(R.id.tvLastName);
            gender = itemView.findViewById(R.id.tvGender);
            country = itemView.findViewById(R.id.tvCountry);
            phone = itemView.findViewById(R.id.tvPhone);
        }
    }

    public interface StudentAdapterInterface{
        public void studentRecordUpdate(StudentData studentData);

        public void deleteStudentRecord(int studentId);

    }
}







