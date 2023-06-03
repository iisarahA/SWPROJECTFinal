package com.example.additem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RentStadiumForm extends AppCompatActivity {
    ItemModel clickedItem ;
    EditText date ;
    EditText time ;
    EditText hours ;
    Button RentButton ;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_stadium_form);
        date=(EditText) findViewById(R.id.editTextDate);
        time=(EditText) findViewById(R.id.editTextTime);
        hours=(EditText) findViewById(R.id.editTextTextHours);
        RentButton = findViewById(R.id.Rentbtn);
        dataBaseHelper=new DataBaseHelper(RentStadiumForm.this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            clickedItem = (ItemModel) getIntent().getSerializableExtra("clickedItem");
        }

        RentButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              boolean x=   dataBaseHelper.Rent(clickedItem,date,time,hours);
if(x){
    Toast.makeText(RentStadiumForm.this,"Rented "+ clickedItem.getName().toString() + " Stadium", Toast.LENGTH_SHORT).show();
}
else {
    Toast.makeText(RentStadiumForm.this,"Failed to Rent "+ clickedItem.getName().toString() + " Stadium", Toast.LENGTH_SHORT).show();
}


            }
        });





    }
}