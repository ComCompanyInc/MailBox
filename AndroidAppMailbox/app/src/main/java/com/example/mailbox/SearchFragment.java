package com.example.mailbox;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SearchFragment extends Fragment {
    //String arg;

    //public SearchFragment (String arg)
    //{
        //this.arg = arg;
    //}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Log.d("SearchFragment", "onCreateView() called");
        //View view = getView();
        ////TextView textView = view.findViewById(R.id.text_view);
        //EditText searchLine = view.findViewById(R.id.searchLine);
        //searchLine.setText("qwe");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText searchLine = view.findViewById(R.id.searchLine);
        searchLine.setText("qwe");
    }
}