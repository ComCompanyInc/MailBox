package com.example.mailbox;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mailbox.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

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
}