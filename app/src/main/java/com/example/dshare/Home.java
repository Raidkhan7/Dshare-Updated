package com.example.dshare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Home extends Fragment implements View.OnClickListener {
    Intent intent;

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Button b = (Button) v.findViewById(R.id.buttonH);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.buttonH:
                        Intent intent = new Intent(getActivity(),LocationPicker.class);
                        intent.putExtra("key",0);
                        startActivity(intent);


                }
            }
        });
        return v;


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
}





}

