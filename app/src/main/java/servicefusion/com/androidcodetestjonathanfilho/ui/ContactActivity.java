package servicefusion.com.androidcodetestjonathanfilho.ui;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import servicefusion.com.androidcodetestjonathanfilho.R;
import servicefusion.com.androidcodetestjonathanfilho.Util.SFConstants;
import servicefusion.com.androidcodetestjonathanfilho.model.Address;
import servicefusion.com.androidcodetestjonathanfilho.model.Contact;
import servicefusion.com.androidcodetestjonathanfilho.model.Email;
import servicefusion.com.androidcodetestjonathanfilho.model.PhoneNumber;
import servicefusion.com.androidcodetestjonathanfilho.model.Type;

public class ContactActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.dobET) EditText dobET;
    @Bind(R.id.nameET) EditText nameET;
    @Bind(R.id.lastNameET) EditText lastNameET;

    @Bind(R.id.fieldsLL)
    LinearLayout fieldsLL;

    @Bind(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;
    LinearLayout phoneLL;
    LinearLayout emailLL;
    LinearLayout addressLL;

    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,android.R.color.white));

        dobET.setOnClickListener(view -> {
            DatePickerBuilder dpb = new DatePickerBuilder()
                    .setFragmentManager(getSupportFragmentManager())
                    .setStyleResId(R.style.BetterPickersDialogFragment)
                    .setYearOptional(true)
                    .addDatePickerDialogHandler((reference, year, monthOfYear, dayOfMonth) -> {
                        dobET.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR,year);
                        cal.set(Calendar.MONTH,(monthOfYear+1));
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        date = cal.getTime();

                    });
            dpb.show();
        });
        createLayouts();

        if(getIntent().hasExtra(SFConstants.CONTACT)){
            loadContact(getIntent().getExtras().getLong(SFConstants.CONTACT));
        }

    }
    private void createLayouts(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        phoneLL = new LinearLayout(this);
        phoneLL.setOrientation(LinearLayout.VERTICAL);
        phoneLL.setLayoutParams(layoutParams);

        phoneLL.addView(createLLHeader(R.string.phone));


        emailLL = new LinearLayout(this);
        emailLL.setOrientation(LinearLayout.VERTICAL);
        emailLL.setLayoutParams(layoutParams);

        emailLL.addView(createLLHeader(R.string.email));

        addressLL = new LinearLayout(this);
        addressLL.setOrientation(LinearLayout.VERTICAL);
        addressLL.setLayoutParams(layoutParams);

        addressLL.addView(createLLHeader(R.string.address));

        fieldsLL.addView(phoneLL);
        fieldsLL.addView(emailLL);
        fieldsLL.addView(addressLL);

    }
    private TextView createLLHeader(int stringResId){
        TextView headerTV = new TextView(this);
        headerTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        headerTV.setText(stringResId);
        return headerTV;

    }
    public void addPhone(View view){
        addPhoneRow(null);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }
    public void addAddress(View view){
        addAddressRow(null);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }
    public void addEmail(View view){
        addEmailRow(null);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    private void loadContact(Long contactId){
        Realm realm = Realm.getDefaultInstance();
        Contact contact = realm.where(Contact.class)
                                            .in("id", new Long[]{contactId})
                                            .findAll()
                                            .first();
        if(contact!= null){
            nameET.setText(contact.getName());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_contact:
                createContact();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createContact(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Contact contact = realm.createObject(Contact.class, UUID.randomUUID().getMostSignificantBits());
        contact.setName(nameET.getText().toString());
        contact.setLastName(lastNameET.getText().toString());
        contact.setDob(date);

        realm.commitTransaction();
        finish();
    }

    private void addPhoneRow(@Nullable PhoneNumber phoneNumber){
        EditText phoneET = new EditText(this);
        phoneET.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,1f));
        phoneET.setInputType(InputType.TYPE_CLASS_PHONE);
        phoneET.setHint(R.string.phone);

        Spinner spinner = createTypeSpinner();

        if(phoneNumber != null){
            phoneET.setText(phoneNumber.getNumber());
            setSelectedType(spinner, phoneNumber.getTypeDescription());
        }

        LinearLayout rowLL = createLinearLayoutRow();
        rowLL.addView(spinner);
        rowLL.addView(phoneET);

        phoneLL.addView(rowLL);

        phoneLL.invalidate();
    }

    private void addEmailRow(Email email){

        EditText emailET = new EditText(this);
        emailET.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,1f));
        emailET.setInputType(InputType.TYPE_CLASS_PHONE);
        emailET.setHint(R.string.email);

        Spinner spinner = createTypeSpinner();
        if(email!= null){
            emailET.setText(email.getEmail());
            setSelectedType(spinner, email.getTypeDescription());
        }

        LinearLayout rowLL = createLinearLayoutRow();
        rowLL.addView(createTypeSpinner());
        rowLL.addView(emailET);

        emailLL.addView(rowLL);

        emailLL.invalidate();
    }

    private void addAddressRow(@Nullable Address address){
        EditText addressET = new EditText(this);
        addressET.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,1f));
        addressET.setInputType(InputType.TYPE_CLASS_PHONE);
        addressET.setHint(R.string.address);

        Spinner spinner = createTypeSpinner();
        if(address!= null){
            addressET.setText(address.getAddress());
            setSelectedType(spinner, address.getTypeDescription());
        }

        LinearLayout rowLL = createLinearLayoutRow();
        rowLL.addView(createTypeSpinner());
        rowLL.addView(addressET);

        addressLL.addView(rowLL);

        addressLL.invalidate();
    }

    private Spinner createTypeSpinner(){
        Spinner typeSpinner = new Spinner(this);
        typeSpinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        typeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Type.values()));
        return typeSpinner;
    }
    private LinearLayout createLinearLayoutRow(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout rowLL = new LinearLayout(this);
        rowLL.setOrientation(LinearLayout.HORIZONTAL);
        rowLL.setLayoutParams(layoutParams);
        return rowLL;
    }

    private void setSelectedType(Spinner spinner, String type ){

    }

    @Override
    public void onBackPressed() {
        if (slidingUpPanelLayout != null &&
                (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED ||
                        slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}
