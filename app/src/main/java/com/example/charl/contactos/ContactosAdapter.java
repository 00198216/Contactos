package com.example.charl.contactos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public  class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder>{


    private Context Ctx;
    private LayoutInflater inflater;
    private List<Contactos> contactL;

    public ContactosAdapter(Context Context, List<Contactos> contact){

        contactL = contact;
        Ctx = Context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(Ctx);
        View view = inflater.inflate(R.layout.activity_card_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TextView name;
        ImageView img;

        name=holder.nombre;
        img= holder.image;

        name.setText(contactL.get(position).getName());
        img.setImageURI(contactL.get(position).getImgconv());




    }

    @Override
    public int getItemCount() {
        return contactL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            nombre=itemView.findViewById(R.id.name);
            image= itemView.findViewById(R.id.img);






        }
    }


}
