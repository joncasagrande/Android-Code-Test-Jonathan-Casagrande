package servicefusion.com.androidcodetestjonathanfilho.ui;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import servicefusion.com.androidcodetestjonathanfilho.R;
import servicefusion.com.androidcodetestjonathanfilho.model.Contact;
import servicefusion.com.androidcodetestjonathanfilho.ui.adapter.ContactsAdapter;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.contactRV) RecyclerView contactRV;
    @Bind(R.id.noContactRL)
    RelativeLayout noContactRL;
    List<Contact> contacts;
    @Bind(R.id.toolbar)Toolbar toolbar;
    private ContactsAdapter contactsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        contacts = new ArrayList<>();
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,android.R.color.white));
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkContacts();
    }

    private void checkContacts(){
        getContacts();

        if(contacts!= null && !contacts.isEmpty()){
            noContactRL.setVisibility(View.GONE);
            contactRV.setVisibility(View.VISIBLE);
        }else{
            noContactRL.setVisibility(View.VISIBLE);
            contactRV.setVisibility(View.GONE);
        }
    }

    private void getContacts(){
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Contact> contactRealmQuery = realm.where(Contact.class);
        RealmResults<Contact> realmResults = contactRealmQuery.findAll();
        realmResults.sort("name");
        Iterator<Contact> contactIterator = realmResults.iterator();
        while(contactIterator.hasNext()){
            contacts.add(contactIterator.next());
        }

        if(contacts != null && !contacts.isEmpty()){
            contactRV.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            contactRV.setLayoutManager(layoutManager);
            contactsAdapter = new ContactsAdapter(this, contacts);
            contactRV.setAdapter(contactsAdapter);
        }

    }

    public void addContact(View view){
        startActivity(new Intent(this, ContactActivity.class));
    }
}
