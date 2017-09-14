package servicefusion.com.androidcodetestjonathanfilho.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import servicefusion.com.androidcodetestjonathanfilho.R;
import servicefusion.com.androidcodetestjonathanfilho.model.Contact;
import servicefusion.com.androidcodetestjonathanfilho.ui.ContactActivity;

/**
 * Created by joncasagrande on 13/09/17.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{

    private List<Contact> contacts;
    private Context ctx;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.nameTV)
        TextView nameTV;
        @Bind(R.id.contactLL)
        LinearLayout monitorLayout;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    public ContactsAdapter(Context ctx,List<Contact> contacts) {
        this.contacts = contacts;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_contact,parent,false);
        return new ContactsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        if(contact != null) {

            holder.nameTV.setText(contact.getName());

            holder.monitorLayout.setOnClickListener(view -> {
                Intent intent = new Intent(ctx, ContactActivity.class);
                intent.putExtra("CONTACT", contact.getId());
                ctx.startActivity(intent);
            });
        }

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}