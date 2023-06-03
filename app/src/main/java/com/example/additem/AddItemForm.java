package com.example.additem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddItemForm extends AppCompatActivity {

//members
    Button btn_Add;
    EditText name , type , location ,price;
    DataBaseHelper dataBaseHelper;
    private ImageView objectImageView;
    private EditText imageDetailsET;

private static final int PICK_IMAGE_REQUEST=100;
private Uri imageFilePath;
private Bitmap imageToStore;
DataBaseHandler objectDataBaseHandel ;
    Button logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_form);

        try{

            imageDetailsET=findViewById(R.id.imageNameET);
            objectImageView=findViewById(R.id.image);
            objectDataBaseHandel=new DataBaseHandler(AddItemForm.this);

        }
        catch (Exception e){
            Toast.makeText(AddItemForm.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        btn_Add=findViewById(R.id.btn_Add);
        name=(EditText) findViewById(R.id.Sname);
        type=(EditText) findViewById(R.id.SportType);
        location=(EditText) findViewById(R.id.loc);
        price=(EditText) findViewById(R.id.price);



        //do you add the list view declaration here ?
        //no

        dataBaseHelper=new DataBaseHelper(AddItemForm.this);


        btn_Add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create model
                ItemModel itemMod;
                try {

                        itemMod = new ItemModel(name.getText().toString(), -1, type.getText().toString(), location.getText().toString(), Integer.parseInt(price.getText().toString()));
                        Toast.makeText(AddItemForm.this, itemMod.toString(), Toast.LENGTH_SHORT).show();


                } catch (Exception e) {
                    Toast.makeText(AddItemForm.this, "Enter Valid input", Toast.LENGTH_SHORT).show();
                    itemMod = new ItemModel("error", -1,"error","error", 0);;
                }



                try {
                    if (!imageDetailsET.getText().toString().isEmpty() && objectImageView.getDrawable() != null && imageToStore != null)  {
                        objectDataBaseHandel.storeImage(new ModelClass(imageDetailsET.getText().toString(),imageToStore));
                        dataBaseHelper = new DataBaseHelper(AddItemForm.this);
                        dataBaseHelper.addOne(itemMod);
                    }
                    else{
                        Toast.makeText(AddItemForm.this,"Please select image and type image name",Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e){
                    Toast.makeText(AddItemForm.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);





            }
        });

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentL = new Intent(AddItemForm.this, LoginActivity.class);
                startActivity(intentL);
                finish();
                Toast.makeText(AddItemForm.this, "Succesfully LOGOUT", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void chooseImages(View objectView){
        try{
            Intent objIntent= new Intent();
            objIntent.setType("image/*");
            objIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objIntent,PICK_IMAGE_REQUEST);

        }
        catch(Exception e){
         Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode , int resultCode, @Nullable Intent data){
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData() != null){
              imageFilePath=data.getData();
                imageToStore= MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
                objectImageView.setImageBitmap(imageToStore);
            }
        }
        catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void storeImage(View view) {
       try {
           if (!imageDetailsET.getText().toString().isEmpty() && objectImageView.getDrawable() != null && imageToStore != null)  {
               objectDataBaseHandel.storeImage(new ModelClass(imageDetailsET.getText().toString(),imageToStore));
           }
           else{
               Toast.makeText(this,"Please select image and type image name",Toast.LENGTH_SHORT).show();
           }
           }
       catch(Exception e){
           Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
           }
       }

    }






