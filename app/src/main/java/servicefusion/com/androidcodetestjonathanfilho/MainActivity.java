package servicefusion.com.androidcodetestjonathanfilho;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;


import servicefusion.com.androidcodetestjonathanfilho.databinding.ActivityMainBinding;
import servicefusion.com.androidcodetestjonathanfilho.model.Contact;

public class MainActivity extends AppCompatActivity {

    List<Contact> contacts;
    ActivityMainBinding binding;

    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainViewModel();
        binding.setViewModel(viewModel);

        setMockContacts();
    }

    private void setMockContacts(){
        contacts = Arrays.asList();
        viewModel.items.addAll(contacts);
    }

    public void addContact(View view){

    }
}
