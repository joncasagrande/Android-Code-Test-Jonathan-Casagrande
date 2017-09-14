package servicefusion.com.androidcodetestjonathanfilho.ui;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import servicefusion.com.androidcodetestjonathanfilho.R;
import servicefusion.com.androidcodetestjonathanfilho.model.Contact;

public class ContactActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.dobET) EditText dobET;
    @Bind(R.id.nameET) EditText nameET;
    @Bind(R.id.lastNameET) EditText lastNameET;

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
                        dobET.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR,year);
                        cal.set(Calendar.MONTH,monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        date = cal.getTime();
                    });
            dpb.show();
        });

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


}
