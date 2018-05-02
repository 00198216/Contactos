package com.example.charl.contactos;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Main3Activity extends AppCompatActivity {
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        picture= findViewById(R.id.pic);

        Intent getinfo = this.getIntent();
        Bundle bundle = getinfo.getExtras();

        Contactos conta = (Contactos)bundle.getParcelable("pass");

        if(conta.getImgconv() ==null) {
            picture.setImageResource(R.drawable.perfil);
        }
        else {

            picture.setImageURI(conta.getImgconv());
        }
    }
}
