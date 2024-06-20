package com.example.mailbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OpenMessage extends AppCompatActivity {
    String messageId;
    TextView addressSender, dateSending, description;
    String[] res;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.open_message);

        init();
    }

    public void init()
    {
        addressSender = findViewById(R.id.address_sender);
        dateSending = findViewById(R.id.date_sending);
        description = findViewById(R.id.description_text);

        Intent i = getIntent();
        if(i != null)
        {
            messageId = i.getStringExtra("address");

            String[] address = messageId.split("_");
            addressSender.setText(address[0]);

            getDataOpenedMessage(messageId);
        }
    }

    public void getDataOpenedMessage(String messageId)
    {
        Client client = new Client("OPENMESS>>"+messageId);
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

        res = answer.split("<<");

        description.setText(res[0]);
        dateSending.setText(res[1]);
    }

    public void onClickOpenAccountButton(View view)
    {
        Intent intent = new Intent(this, AccountViewer.class);
        intent.putExtra("address", addressSender.getText().toString());
        startActivity(intent);
    }
}
