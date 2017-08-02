package com.tutorials.andorid.app.tutorials.contentproviders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.Contact;

import java.util.List;

/**
 * Created by mahesh on 8/1/17.
 */

public class ContactsArrayAdapter extends RecyclerView.Adapter<ContactsArrayAdapter.ViewHolder> {

    @NonNull
    private List<Contact> contacts;

    @NonNull
    private Context context;

    public ContactsArrayAdapter(@NonNull Context context, @NonNull List<Contact> contacts) {
        this.contacts = contacts;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.row_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = this.contacts.get(position);
        if (contact != null) {
            holder.txtViewUserName.setText(contact.displayName);
            holder.txtViewPhoneNumber.setText(contact.phoneNumber);
        }
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewUserName;
        TextView txtViewPhoneNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            txtViewUserName = itemView.findViewById(R.id.txt_view_user_name);
            txtViewPhoneNumber = itemView.findViewById(R.id.txt_view_email_address);
        }
    }
}
