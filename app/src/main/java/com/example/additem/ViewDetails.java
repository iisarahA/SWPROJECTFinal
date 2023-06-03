package com.example.additem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDetails extends AppCompatActivity {

    ListView listDetails;
    ItemModel clickedItem ;
DataBaseHelper dataBaseHelper;
TextView textout;
TextView textout2;

TextView textout3;
TextView textout4;
    Button delete;
    Button Rent;

    Button ReturnBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            clickedItem = (ItemModel) getIntent().getSerializableExtra("clickedItem");
        }

        dataBaseHelper=new DataBaseHelper(ViewDetails.this);

        textout = (TextView) findViewById(R.id.txtOutput);
        textout.setText(clickedItem.getName().toString());

        textout2 = (TextView) findViewById(R.id.txtOutput2);
        textout2.setText(clickedItem.getLocation().toString());

        textout3 = (TextView) findViewById(R.id.txtOutput3);
        textout3.setText(String.valueOf(clickedItem.getPrice()));

        textout4 = (TextView) findViewById(R.id.txtOutput4);
        textout4.setText(clickedItem.getSportType().toString());
        delete = findViewById(R.id.Delete);
        ReturnBtn = findViewById(R.id.Return);

        delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper.deleteOne(clickedItem);

                Toast.makeText(ViewDetails.this,"Deleted "+ clickedItem.getName().toString() + " Stadium", Toast.LENGTH_SHORT).show();
                Intent intentL = new Intent(ViewDetails.this, MainActivity2.class);
                startActivity(intentL);
            }
        });

        Rent=findViewById(R.id.Rentbtn);

        Rent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentL = new Intent(ViewDetails.this, RentStadiumForm.class);
                intentL.putExtra("clickedItem" , clickedItem);
                startActivity(intentL);




            }
        });
     //when the user view details of already rented stadium the button return will be displayed to cancel renting this stadium
        if (dataBaseHelper.isStadiumRented(clickedItem)) {
            ReturnBtn.setVisibility(View.VISIBLE);
            Rent.setVisibility(View.GONE);
            ReturnBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataBaseHelper.returnStadium(clickedItem);
                    Toast.makeText(ViewDetails.this, "Returned " + clickedItem.getName() + " Stadium", Toast.LENGTH_SHORT).show();
                    Intent intentL = new Intent(ViewDetails.this, MainActivity2.class);
                    startActivity(intentL);
                }
            });
        } else {
            ReturnBtn.setVisibility(View.GONE);
        }





    }


}