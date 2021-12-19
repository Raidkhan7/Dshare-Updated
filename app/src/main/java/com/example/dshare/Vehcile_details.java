package com.example.dshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Vehcile_details extends AppCompatActivity  implements View.OnClickListener  {

    Spinner spinner3,spinner2;
    static String V1,V2;
    ArrayList<String> arrayList_Sp2;
    ArrayAdapter<String> arrayAdapter_Sp2;

    ArrayList<String> arrayList_Car,arrayList_Bike,arrayList_Others;
    ArrayAdapter<String> arrayAdapter_Sp3;
    Button NextbVt;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehcile_details);

        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner3=(Spinner)findViewById(R.id.spinner3);
        arrayList_Sp2=new ArrayList<>();
        arrayList_Sp2.add("Car");
        arrayList_Sp2.add("Bike");
        arrayList_Sp2.add("Other");

        arrayAdapter_Sp2=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_Sp2);
        spinner2.setAdapter(arrayAdapter_Sp2);



        arrayList_Car=new ArrayList<>();
        arrayList_Car.add("1");
        arrayList_Car.add("2");
        arrayList_Car.add("3");
        arrayList_Car.add("4");

        arrayList_Bike = new ArrayList<>();
        arrayList_Bike.add("1");

        arrayList_Others = new ArrayList<>();
        arrayList_Others.add("1");
        arrayList_Others.add("2");
        arrayList_Others.add("3");
        arrayList_Others.add("4");
        arrayList_Others.add("5");
        arrayList_Others.add("6");
        arrayList_Others.add("7");
        arrayList_Others.add("8");
        arrayList_Others.add("9");
        arrayList_Others.add("more");


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner2, View view, int position , long id) {

                if(position==1){
                    arrayAdapter_Sp3 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_Bike);

                }
                if(position==0){
                    arrayAdapter_Sp3 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_Car);

                }
                if(position==2){
                    arrayAdapter_Sp3 = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_Others);

                }
                spinner3.setAdapter(arrayAdapter_Sp3);
            }



            @Override
            public void onNothingSelected(AdapterView<?> spinner2) {

            }
        });
        NextbVt = findViewById(R.id.NexttbtnVT);
        NextbVt.setOnClickListener(this);

        backbtn = findViewById(R.id.imageView3V);
        NextbVt.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageView3V:
                startActivity(new Intent(Vehcile_details.this,LocationPicker_2.class));
                break;

            case R.id.NexttbtnVT:
                V1 = spinner2.getAdapter().toString().trim();
                V2 = spinner3.getAdapter().toString().trim();
                startActivity(new Intent(Vehcile_details.this,Selectdate.class));

        }

    }
}