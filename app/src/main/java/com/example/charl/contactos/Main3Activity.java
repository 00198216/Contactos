package com.example.charl.contactos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    Contactos conta;
    ImageView picture;
    TextView  nombre;
    TextView  code;
    TextView address;
    TextView correo;
    TextView bday;
    ImageView call;
    TextView tel;
    String data = "No disponible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        picture= findViewById(R.id.pic);
        nombre= findViewById(R.id.uno);
        address= findViewById(R.id.addr);
        correo= findViewById(R.id.correo2);
        code =findViewById(R.id.identificacion);
        bday =findViewById(R.id.bday);
        call = findViewById(R.id.calling);
        tel = findViewById(R.id.tel);

        Intent getinfo = this.getIntent();
        Bundle bundle = getinfo.getExtras();

        conta = (Contactos)bundle.getParcelable("pass");
        Toast toast3 =
                Toast.makeText(getApplicationContext(),
                       conta.getLname(), Toast.LENGTH_SHORT);
        toast3.show();

        //Reviso si la imagen esta vacia. Si no se encuentra le asigno una por defecto
        if(conta.getImgconv() ==null) {
            picture.setImageResource(R.drawable.perfil);
        }
        else {

            picture.setImageURI(conta.getImgconv());
        }

        //Reviso si el nombre esta vacio. Si no se encuentra le asigno un warning por defecto.
        if(conta.getName() ==null) {
            nombre.setText(data);
        }
        else {

            nombre.setText(conta.getName());
        }

      //Reviso si la direccion esta vacia. Si no se encuentra le asigno un warning por defecto.
        if(conta.getAdress() ==null) {
            address.setText(data);
        }
        else {

            address.setText(conta.getAdress());
        }

        //Reviso si el ID esta vacio. Si no se encuentra le asigno un warning por defecto.
        if(conta.getID() ==null) {
            code.setText(data);
        }
        else {

            code.setText(conta.getID());
        }

        //Reviso si el cumpleaños esta vacio. Si no se encuentra le asigno un warning por defecto.
        if(conta.getCumple() ==null) {
            bday.setText(data);
        }
        else {

            bday.setText(conta.getCumple());
        }

        //Reviso si el correo esta vacio. Si no se encuentra le asigno un warning por defecto.
        if(conta.getMail() ==null) {
            correo.setText(data);
        }
        else {

            correo.setText(conta.getMail());
        }

        //Reviso si el numero esta vacio. Si no se encuentra le asigno un warning por defecto. Si se encuentra revisa los permisos.
        if(conta.getNumero() != null){

            //Checking Permission is required Marshmallow up
            if (ContextCompat.checkSelfPermission(Main3Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(Main3Activity.this, new String[]{Manifest.permission.CALL_PHONE}, 7);

            }

            else{

               tel.setText(conta.getNumero());

            }

        }
        else{
            tel.setText(data);
        }

        //Reviso si el numero esta vacio. Si no se encuentra le asigno un warning por defecto. Si se encuentra revisa los permisos y comienza la llamada.
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(conta.getNumero() != null){

                    //Checking Permission is required Marshmallow up
                    if (ContextCompat.checkSelfPermission(Main3Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(Main3Activity.this, new String[]{Manifest.permission.CALL_PHONE}, 7);


                    }

                    else{

                        Intent call = new Intent(Intent.ACTION_CALL);
                        call.setData(Uri.parse("tel:"+conta.getNumero()));
                        startActivity(call);


                    }


                }
            }
        });
    }




}
