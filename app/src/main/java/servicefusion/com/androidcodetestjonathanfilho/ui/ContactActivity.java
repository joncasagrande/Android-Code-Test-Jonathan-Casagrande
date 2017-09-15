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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
    @Bind(R.id.deleteBT)Button deleteBT;
    LinearLayout phoneLL;
    LinearLayout emailLL;
    LinearLayout addressLL;

    List<LinearLayout> addedViews;

    Contact contact;
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
            deleteBT.setVisibility(View.VISIBLE);
        }
        addedViews = new ArrayList<>();
        addedViews.add(phoneLL);
        addedViews.add(emailLL);
        addedViews.add(addressLL);
    }
    private void createLayouts(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        phoneLL = new LinearLayout(this);
        phoneLL.setOrientation(LinearLayout.VERTICAL);
        phoneLL.setLayoutParams(layoutParams);

        phoneLL.addView(createLLHeader(R.string.phone));

        phoneLL.setVisibility(View.GONE);

        emailLL = new LinearLayout(this);
        emailLL.setOrientation(LinearLayout.VERTICAL);
        emailLL.setLayoutParams(layoutParams);

        emailLL.addView(createLLHeader(R.string.email));

        emailLL.setVisibility(View.GONE);

        addressLL = new LinearLayout(this);
        addressLL.setOrientation(LinearLayout.VERTICAL);
        addressLL.setLayoutParams(layoutParams);

        addressLL.addView(createLLHeader(R.string.address));

        addressLL.setVisibility(View.GONE);

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
    public void deleteBT(View view){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        contact.deleteFromRealm();
        realm.commitTransaction();
        finish();
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
        contact = realm.where(Contact.class)
                                            .in("id", new Long[]{contactId})
                                            .findAll()
                                            .first();
        if(contact!= null){
            nameET.setText(contact.getName());
            lastNameET.setText(contact.getLastName()== null ? "" : contact.getLastName());
            if(contact.getDob()!= null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                dobET.setText(sdf.format(contact.getDob()));
            }
            if(contact.getEmails()!= null && contact.getEmails().size()> 0){
                Iterator email = contact.getEmails().iterator();
                while (email.hasNext()){
                    addEmailRow((Email)email.next());
                }
            }
            if(contact.getPhoneNumbers()!= null && contact.getPhoneNumbers().size()> 0){
                Iterator phone = contact.getPhoneNumbers().iterator();
                while (phone.hasNext()){
                    addPhoneRow((PhoneNumber) phone.next());
                }

            }
            if(contact.getAddresses()!= null && contact.getAddresses().size()> 0){
                Iterator address = contact.getAddresses().iterator();
                while (address.hasNext()){
                    addAddressRow((Address) address.next());
                }

            }

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
                    createOrUpdateContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createOrUpdateContact(){
        if(isContactValid()) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            if(contact== null)
            contact = realm.createObject(Contact.class, UUID.randomUUID().getMostSignificantBits());
            for (LinearLayout linearLayout : addedViews) {
                for (int i = 1; i < linearLayout.getChildCount(); i++) {
                    String type = ((TextView) linearLayout.getChildAt(0)).getText().toString();
                    LinearLayout rowLL = (LinearLayout) linearLayout.getChildAt(i);
                    String typeDescription = ((Spinner) rowLL.getChildAt(0)).getSelectedItem().toString();
                    String value = ((EditText) rowLL.getChildAt(1)).getText().toString();
                    switch (type) {
                        case "Email":
                            contact.getEmails().add(new Email(value, typeDescription));
                            break;
                        case "Phone":
                            contact.getPhoneNumbers().add(new PhoneNumber(value, typeDescription));
                            break;
                        case "Address":
                            contact.getAddresses().add(new Address(value, typeDescription));
                            break;
                    }
                }
            }
            contact.setName(nameET.getText().toString());
            contact.setLastName(lastNameET.getText().toString());
            contact.setDob(date);

            realm.commitTransaction();
            finish();
        }
    }

    private boolean isContactValid(){
        if(nameET.getText().toString().isEmpty()){
            nameET.setError(getString(R.string.missing, getString(R.string.first_name)));
            return false;
        }
        return true;
    }


    private void addPhoneRow(@Nullable PhoneNumber phoneNumber){
        phoneLL.setVisibility(View.VISIBLE);
        EditText phoneET = createEditText(InputType.TYPE_CLASS_PHONE, R.string.phone);
        Spinner spinner = createTypeSpinner();

        if(phoneNumber != null){
            phoneET.setText(phoneNumber.getNumber());
            setSelectedType(spinner, phoneNumber.getTypeDescription());
        }

        LinearLayout rowLL = createLinearLayoutRow();
        rowLL.addView(spinner);
        rowLL.addView(phoneET);

        phoneLL.addView(rowLL);
    }

    private void addEmailRow(Email email){
        emailLL.setVisibility(View.VISIBLE);
        EditText emailET = createEditText(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, R.string.email);
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
        addressLL.setVisibility(View.VISIBLE);
        EditText addressET = createEditText(InputType.TYPE_CLASS_TEXT, R.string.address);
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

    private EditText createEditText(int inputType, int hint){
        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,1f));
        editText.setInputType(inputType);
        editText.setHint(hint);
        return editText;
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
