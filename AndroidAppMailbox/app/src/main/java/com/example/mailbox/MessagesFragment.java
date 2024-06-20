package com.example.mailbox;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessagesFragment extends Fragment {
    ListView messageList;
    String[] messagesList;
    ArrayAdapter<String> adapter;

    String thisAddres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

    }

    public void init(View view)
    {
        messageList = view.findViewById(R.id.message_list);

        //bundle (get thisAddress)
        Bundle bundle = getArguments();
        thisAddres = bundle.getString("thisUserAddress");

        getListMessages(thisAddres);

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //position - it position of clicked item in ListView component
                Intent intent = new Intent(getContext(), OpenMessage.class);
                intent.putExtra("position", position);
                intent.putExtra("address", messagesList[position]);
                //intent.putExtra("thisUserAddress", addressSender);
                startActivity(intent);
            }
        });
    }

    public void getListMessages(String thisAddress)
    {
        Client client = new Client("SHOWMESS>>"+thisAddress);
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

        messagesList = answer.split("<<");
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, messagesList);
        messageList.setAdapter(adapter);
    }
}