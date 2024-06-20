package com.example.mailbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountViewer extends AppCompatActivity {
    String address, addressSender;
    int position;

    TextView addressText, nameText, surnameText, dateText;

    EditText descriptionEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_viewer);

        init();
    }

    public void init()
    {
        addressText = findViewById(R.id.acc_address);
        nameText = findViewById(R.id.acc_name);
        surnameText = findViewById(R.id.acc_surname);
        dateText = findViewById(R.id.acc_date);

        descriptionEdit = findViewById(R.id.desc_edit);

        reciveIntent();
    }

    private void reciveIntent()
    {
        Intent i = getIntent();
        if(i != null)
        {
            position = i.getIntExtra("position", 0);
            address = i.getStringExtra("address");
            addressSender = i.getStringExtra("thisUserAddress");

            System.out.println(address);
            openAccountPage(address);
        }
        System.out.println("NULL ADDRESS!!!");
    }

    public void onClickSendButton(View view)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());

        sendMessage(descriptionEdit.getText().toString(), currentDate ,addressSender,address);
    }

    private void openAccountPage(String address)
    {
        Client client = new Client("SHOWACCOUNT>>"+address);
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

        addressText.setText(address);

        nameText.setText(res[0]);
        surnameText.setText(res[1]);
        dateText.setText(res[2]);
    }

    public void sendMessage(String messageDescription, String date, String senderAddress, String reciverAddres)
    {
        Client client = new Client("SENDMESS>>"+messageDescription+">>"+date+">>"+senderAddress+">>"+reciverAddres);
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

        Toast toast = Toast.makeText(this, answer, Toast.LENGTH_SHORT);

        //close activity
        finish();
    }
}
