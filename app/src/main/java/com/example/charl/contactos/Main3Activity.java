package com.example.charl.contactos;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    Contactos conta;
    ImageView picture;
    TextView  nombre;
    String data = "No disponible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        picture= findViewById(R.id.pic);
        nombre= findViewById(R.id.one);

        Intent getinfo = this.getIntent();
        Bundle bundle = getinfo.getExtras();

        conta = (Contactos)bundle.getParcelable("pass");
        Toast toast3 =
                Toast.makeText(getApplicationContext(),
                       conta.getAdress(), Toast.LENGTH_SHORT);
        toast3.show();

        if(conta.getImgconv() ==null) {
            picture.setImageResource(R.drawable.perfil);
        }
        else {

            picture.setImageURI(conta.getImgconv());
        }


    }
}
