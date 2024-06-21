package com.example.mailbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends Activity {

    EditText eMailInput;
    EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        init();
    }

    public void init()
    {
       eMailInput = findViewById(R.id.id_email_input);
       passwordInput = findViewById(R.id.id_password_input);
    }

    public void entryButtonClick(View view)
    {
        SignUp();
    }

    public void signUpButtonClick(View view)
    {

    }

    public void SignUp()
    {
        //TextView t1 = findViewById(R.id.serverAnswText);

        //String[] serverAnswer;

        Client client = new Client("ENTERSYS>>"+eMailInput.getText()+">>"+passwordInput.getText());
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

        //t1.setText(answer);


        if(answer.equals("true") == true)
        {
            //transit to the main activity
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            intent.putExtra("MailAddress", String.valueOf(eMailInput.getText()));
            startActivity(intent);

            eMailInput.setText("");
            passwordInput.setText("");
        }
        else
        {
            Toast toastRes = Toast.makeText(getApplicationContext(), "Attention: login or password is wrong!", Toast.LENGTH_SHORT);
            toastRes.show();
        }
    }
}
