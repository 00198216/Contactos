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
    ImageView call;
    TextView tel;
    String data = "No disponible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        picture= findViewById(R.id.pic);
        nombre= findViewById(R.id.uno);
        call = findViewById(R.id.calling);
        tel = findViewById(R.id.tel);

        Intent getinfo = this.getIntent();
        Bundle bundle = getinfo.getExtras();

        conta = (Contactos)bundle.getParcelable("pass");
        Toast toast3 =
                Toast.makeText(getApplicationContext(),
                       conta.getNumero(), Toast.LENGTH_SHORT);
        toast3.show();

        if(conta.getImgconv() ==null) {
            picture.setImageResource(R.drawable.perfil);
        }
        else {

            picture.setImageURI(conta.getImgconv());
        }


        if(conta.getName() ==null) {
            nombre.setText(data);
        }
        else {

            nombre.setText(conta.getName());
        }

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
