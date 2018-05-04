package com.example.charl.contactos;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class Main4Activity extends AppCompatActivity {

    Uri imgu;

    EditText name;
    EditText Lname;
    EditText phone;
    EditText Id;
    EditText adress;
    TextView calen;
    ImageView imgv;
    Button boton;
    EditText mail;
    Uri imgp;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        imgv = (ImageView)findViewById(R.id.editimg);
        name = (EditText) findViewById(R.id.editname);
        Lname = (EditText) findViewById(R.id.editapellido);
        phone = (EditText) findViewById(R.id.edittel);
        Id =  (EditText) findViewById(R.id.editid);
        adress= (EditText) findViewById(R.id.editad);
        calen= (TextView) findViewById(R.id.editcal);
        mail= (EditText) findViewById(R.id.editmail);
        boton= (Button) findViewById(R.id.clicker);
    }
}
