package contactbook.androidclient.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import contactbook.androidclient.R;
import contactbook.androidclient.data.Contact;

public class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Contact> contacts;

    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.fragment_contact_data, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Populates data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Contact contact = this.contacts.get(position);

        // Set item views based on your views and data model
        holder.textViewContactId.setText("" + contact.getId());
        holder.textViewFirstName.setText(contact.getFirstName());
        holder.textViewLastName.setText(contact.getLastName());
        holder.textViewEmail.setText(contact.getEmail());
        holder.textViewPhone.setText(contact.getPhone());
        holder.textViewComments.setText(contact.getComments());
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView textViewContactId;
        public TextView textViewFirstName;
        public TextView textViewLastName;
        public TextView textViewEmail;
        public TextView textViewPhone;
        public TextView textViewComments;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            textViewContactId = (TextView) itemView.findViewById(R.id.textViewContactId);
            textViewFirstName = (TextView) itemView.findViewById(R.id.textViewFirstName);
            textViewLastName = (TextView) itemView.findViewById(R.id.textViewLastName);
            textViewEmail = (TextView) itemView.findViewById(R.id.textViewEmail);
            textViewPhone = (TextView) itemView.findViewById(R.id.textViewPhone);
            textViewComments = (TextView) itemView.findViewById(R.id.textViewComments);
        }
    }
}
