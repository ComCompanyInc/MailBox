package com.example.mailbox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mailbox.databinding.FragmentHomeBinding;
import com.example.mailbox.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mailbox.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
String[] userInformation = new String[5];
TextView address, name, surname, dateOfReg;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initObj();
    }

    public void initObj()
    {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //Search();
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

    /*
    public void Search()
    {
        TextView t1 = findViewById(R.id.serverAnswText);

        //String[] serverAnswer;

        Client client = new Client("SEARCHPEOPLE>>co");
        Thread thread = new Thread(client);
        thread.start();

        String answer = null;
        synchronized (thread) {
            try {
                thread.join(); // Ожидаем завершения работы потока

                //System.out.println("qwe+"+client.response);

                //serverAnswer = client.response.split("<<");
                answer = client.response;
                //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>(Arrays.asList(arrayThemes)));
                //list.setAdapter(adapter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        t1.setText(answer);
    }

    protected void onDestroy()
    {
        super.onDestroy();
        finish();
    }
     */

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

        //System.out.println(address);
        for(int i = 0; i < res.length; i++)
        {
            userInformation[i+1] = res[i];

            //System.out.println(userInformation[i+1]);
            Toast toast = Toast.makeText(getApplicationContext(), userInformation[i+1], Toast.LENGTH_SHORT);
        }

        address.setText(userInformation[0]);
        name.setText(userInformation[1]);
        surname.setText(userInformation[2]);
        dateOfReg.setText(userInformation[3]);
    }
}