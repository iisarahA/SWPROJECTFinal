package com.example.additem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.additem.AddItemForm;
import com.example.additem.DataBaseHandler;
import com.example.additem.DataBaseHelper;
import com.example.additem.ItemModel;
import com.example.additem.LoginActivity;
import com.example.additem.R;
import com.example.additem.RVadapter;

import java.sql.DriverPropertyInfo;
import java.util.List;

import kotlinx.coroutines.MainCoroutineDispatcher;

public class MainActivity2 extends AppCompatActivity {
    ArrayAdapter stadiumArrayAdapter;
    ListView listAll;

    Button AddStadium;
    Button ViewAll;


    Button logout;


    private DataBaseHandler dataBaseHandler;
    private RecyclerView objectRecyclerView;
    RVadapter objectRVadapter;
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        try{
            objectRecyclerView= findViewById(R.id.imagesRV);

            dataBaseHandler=new DataBaseHandler(this);
        }
        catch(Exception e){

        }



        AddStadium=findViewById(R.id.AddStadium);
        ViewAll=findViewById(R.id.ViewAll);
        listAll=findViewById(R.id.ListView);

        AddStadium.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddItemForm.class);
                startActivity(intent);
            }
        });
        ViewAll.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DataBaseHelper dataBaseHelper=new DataBaseHelper(MainActivity2.this);
                ShowStudentsOnListView(dataBaseHelper);

                try{
                    objectRVadapter = new RVadapter(dataBaseHandler.getAllImagesData());
                    objectRecyclerView.setHasFixedSize(true);
                    objectRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
                    objectRecyclerView.setAdapter(objectRVadapter);
                }
                catch(Exception e){
                    Toast.makeText(MainActivity2.this , e.getMessage(), Toast.LENGTH_SHORT).show();

                }



                //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();
            }
        });



        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentL = new Intent(MainActivity2.this, LoginActivity.class);
                startActivity(intentL);
                finish();
                Toast.makeText(MainActivity2.this, "Succesfully LOGOUT", Toast.LENGTH_SHORT).show();
            }
        });

 //View details move to details page
        listAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemModel clickedItem= (ItemModel) parent.getItemAtPosition(position);
                Intent intentL = new Intent(MainActivity2.this, ViewDetails.class);
                intentL.putExtra("clickedItem" , clickedItem);
                startActivity(intentL);




            }
        });







    }
    private void ShowStudentsOnListView(DataBaseHelper dataBaseHelper) {
        stadiumArrayAdapter  = new ArrayAdapter<ItemModel>(MainActivity2.this , android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        listAll.setAdapter(stadiumArrayAdapter);
    }






}




