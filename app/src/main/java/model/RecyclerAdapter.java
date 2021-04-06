package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rewritedvisachecker.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<DataModel> dataSet;
    private Context mContext;

    // Counstructor for the Class
    public RecyclerAdapter(ArrayList<DataModel> contactsList, Context context) {
        this.dataSet = contactsList;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.row, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return dataSet == null? 0: dataSet.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final DataModel contact = dataSet.get(position);

        // Set the data to the views here
        holder.setContactName(contact.getApplicationNumber());
        holder.setContactNumber(contact.getStatus());

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtNumber;

        public ContactHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.application_number);
            txtNumber = itemView.findViewById(R.id.status);
        }

        public void setContactName(String name) {
            txtName.setText(name);
        }

        public void setContactNumber(String number) {
            txtNumber.setText(number);
        }
    }

}
