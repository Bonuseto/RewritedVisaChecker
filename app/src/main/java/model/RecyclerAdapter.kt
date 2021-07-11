package model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rewritedvisachecker.R
import model.RecyclerAdapter.ContactHolder
import java.util.*

class RecyclerAdapter     // Constructor for the Class
(// List to store all the contact details
        private val dataSet: ArrayList<DataModel>?, private val mContext: Context) : RecyclerView.Adapter<ContactHolder>() {
    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        // Inflate the layout view you have created for the list rows here
        val view = layoutInflater.inflate(R.layout.row, parent, false)
        return ContactHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet?.size ?: 0
    }

    // This method is called when binding the data to the views being created in RecyclerView
    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val contact = dataSet!![position]

        // Set the data to the views here
        holder.setContactName(contact.applicationNumber)
        holder.setContactNumber(contact.status)

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public
    }

    // This is your ViewHolder class that helps to populate data to the view
    inner class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtAppNum: TextView
        private val txtStatus: TextView
        fun setContactName(name: String?) {
            txtAppNum.text = name
        }

        fun setContactNumber(number: String?) {
            txtStatus.text = number
        }

        init {
            txtAppNum = itemView.findViewById(R.id.application_number)
            txtStatus = itemView.findViewById(R.id.status)
        }
    }
}