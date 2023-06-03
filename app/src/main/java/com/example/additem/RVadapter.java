package com.example.additem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVadapter extends RecyclerView.Adapter<RVadapter.RVViewHolderCLass> {
  ArrayList<ModelClass> objectModelClassList ;

    public RVadapter(ArrayList<ModelClass> objectModelClassList) {
        this.objectModelClassList = objectModelClassList;
    }

    @NonNull
    @Override
    public RVViewHolderCLass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new RVViewHolderCLass(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.single_row,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolderCLass holder, int position) {
      ModelClass objectModelClass = objectModelClassList.get(position);
        RVViewHolderCLass.imageNameTV.setText(objectModelClass.getImageName());
        RVViewHolderCLass.objectImageView.setImageBitmap(objectModelClass.getImage());


    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }

    public static class RVViewHolderCLass extends RecyclerView.ViewHolder{
        static TextView imageNameTV;
        static ImageView objectImageView ;
        public RVViewHolderCLass(@NonNull View itemView) {
            super(itemView);
            imageNameTV=itemView.findViewById(R.id.sr_imageDetailsTV);
            objectImageView=itemView.findViewById(R.id.sr_imageIV);
        }
    }
}
