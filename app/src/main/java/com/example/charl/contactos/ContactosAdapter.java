package com.example.charl.contactos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public abstract class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ViewHolder> {


    private Context Ctx;
    private LayoutInflater inflater;
    public List<Contactos> contactL;
    public List<Contactos> contactS;
    public int contador=0;


    public ContactosAdapter(Context Context, List<Contactos> contact) {

        contactL = contact;
        Ctx = Context;
        contactS = contactL;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(Ctx);
        View view = inflater.inflate(R.layout.activity_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        TextView name;
        ImageView img;
        CheckBox button;


        name = holder.nombre;
        img = holder.image;
        button = holder.button;

        if (contactL.get(position).getImgconv() == null) {
            name.setText(contactL.get(position).getName());
            img.setImageResource(R.drawable.perfil);
        } else {
            name.setText(contactL.get(position).getName());
            img.setImageURI(contactL.get(position).getImgconv());
        }


        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent info = new Intent(view.getContext(), Main3Activity.class);
                Bundle box = new Bundle();
                box.putParcelable("pass", contactL.get(position));
                info.putExtras(box);
                info.putExtra(Intent.EXTRA_TEXT, String.valueOf(position));
                Ctx.startActivity(info);


            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent info = new Intent(view.getContext(), Main3Activity.class);
                Bundle box = new Bundle();
                box.putParcelable("pass", contactL.get(position));
                info.putExtra(Intent.EXTRA_TEXT, String.valueOf(position));
                info.putExtras(box);
                Ctx.startActivity(info);


            }
        });

        holder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                contactL.get(position).setCheck(isChecked);
                onVerClick(buttonView,position);
                Contador(contador);

            }
        });

        holder.button.setOnClickListener(new View.OnClickListener(){
            int state;

            @Override
            public void onClick(View view){
                if(validar(state)) {
                    Toast.makeText(view.getContext(), "Agrego " + contactL.get(position).getName() + " a favoritos", Toast.LENGTH_LONG).show();
                    state = 1;


                }
                else{

                    state=0;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactL.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        ImageView image;
        CheckBox button;

        public ViewHolder(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.imga);
            button = itemView.findViewById(R.id.favo);


        }
    }
    public boolean validar(int estado) {
        int act = 1;
        boolean flag;
        int desactivado;
        if (act != estado) {
            act = 1;
            return true;
        } else {
            return false;
        }
    }


        public abstract void onVerClick(View v, int pos);

        public abstract void Contador(int cont);


}
