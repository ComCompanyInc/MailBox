package com.example.mailbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
String[] userInformation = new String[5];
TextView address, name, surname, dateOfReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initObj();
    }

    public void initObj()
    {

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
               // R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
               // .build();


        init();
        reciveIntent();
    }


    public void init()
    {
        address = findViewById(R.id.id_address_user);
        name = findViewById(R.id.id_name_user);
        surname = findViewById(R.id.id_surname_user);
        dateOfReg = findViewById(R.id.id_date_user);
    }

    //india.colorado.edu@gmail.com
    private void reciveIntent()
    {

        Intent i = getIntent();

        if(i != null)
        {
            userInformation[0] = i.getStringExtra("MailAddress");

            ShowAccount(userInformation[0]);
        }

    }

    public void ShowAccount(String addressMail)
    {
        Client client = new Client("SHOWACCOUNT>>"+addressMail);
        Thread thread = new Thread(client);
        thread.start();

        String answer = null;
        synchronized (thread) {
            try {
                thread.join(); // Ожидаем завершения работы потока

                answer = client.response;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String[] res = answer.split("<<");

        for(int i = 0; i < res.length; i++)
        {
            userInformation[i+1] = res[i];

            Toast toast = Toast.makeText(getApplicationContext(), userInformation[i+1], Toast.LENGTH_SHORT);
        }

        address.setText(userInformation[0]);
        name.setText(userInformation[1]);
        surname.setText(userInformation[2]);
        dateOfReg.setText(userInformation[3]);
    }

    public void searchButtonClick(View view)
    {
        //Intent intent = new Intent(this, SearchFragment.class);
        //intent.putExtra("thisUserAddress", address.toString());
        //startActivity(intent);
        Bundle bundle = new Bundle();
        bundle.putString("thisUserAddress", address.getText().toString());

        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(bundle);
        setNewFragment(searchFragment);
    }

    public void messageButtonClick(View view)
    {
        Bundle bundle = new Bundle();
        bundle.putString("thisUserAddress", address.getText().toString());

        MessagesFragment messagesFragment = new MessagesFragment();
        messagesFragment.setArguments(bundle);
        setNewFragment(messagesFragment);
    }

    private void setNewFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}