package servicefusion.com.androidcodetestjonathanfilho.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    @Bind(R.id.noContactRL) RelativeLayout noContactRL;
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;

    private List<Contact> contacts;
    private ContactsAdapter contactsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        contacts = new ArrayList<>();
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,android.R.color.white));

        fab.setOnClickListener(view -> addContact(view));

        contactRV.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        contactRV.setLayoutManager(layoutManager);
        contactsAdapter = new ContactsAdapter(this, contacts);
        contactRV.setAdapter(contactsAdapter);

    }

    private List<Contact> filter(List<Contact> contacts, String text){
        if(text.isEmpty()) return contacts;
        List<Contact> filterContact = new ArrayList<>();
        for(Contact contact: contacts){
            if(contact.getName().toLowerCase().contains(text) ||
                    contact.getLastName().toLowerCase().contains(text)){
                filterContact.add(contact);
            }
        }
        return filterContact;
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
        Iterator<Contact> contactIterator = realmResults.sort("name").iterator();
        contacts.clear();

        while(contactIterator.hasNext()){
            contacts.add(contactIterator.next());
        }
        if(contacts!= null && !contacts.isEmpty()){
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Contact> filteredModelList = filter(contacts, newText.toLowerCase());
                contactsAdapter.animateTo(filteredModelList);
                contactRV.scrollToPosition(0);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
