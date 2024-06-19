package com.example.mailbox;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchFragment extends Fragment {
    EditText searchLine;
    Button searchButton;
    String[] res;
    ListView surchList;
    ArrayAdapter<String> adapter;

    String addressSender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchLine = view.findViewById(R.id.searchLine);

        surchList = view.findViewById(R.id.surchList);

        Bundle bundle = getArguments();
        addressSender = bundle.getString("thisUserAddress");//requireArguments().getString("thisUserAddress");

        surchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //position - it position of clicked item in ListView component
                Intent intent = new Intent(getContext(), AccountViewer.class);
                intent.putExtra("position", position);
                intent.putExtra("address", res[position]);
                intent.putExtra("thisUserAddress", addressSender);
                startActivity(intent);
            }
        });


        searchButton = view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inpData(searchLine.getText().toString());
            }
        });
    }

    public void inpData(String addressMail)
    {
        Client client = new Client("SEARCHPEOPLE>>"+addressMail);
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
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, res);
        surchList.setAdapter(adapter);
    }
}