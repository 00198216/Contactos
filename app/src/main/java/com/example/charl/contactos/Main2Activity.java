package com.example.charl.contactos;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    Uri imgu;
    ImageView imgv;
    TextView calen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgv = (ImageView)findViewById(R.id.img);
        calen= findViewById(R.id.cal);

        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



        calen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Elige el cumpleaños", Toast.LENGTH_SHORT);

                toast1.show();

                Intent add = new Intent(getApplicationContext(),calendario.class);
                startActivityForResult(add,1);



            }
        });




    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imgu = data.getData();
            imgv.setImageURI(imgu);
        }
        if(resultCode == RESULT_OK && requestCode == 1){
            if(data.hasExtra("Fecha")==true){
                calen.setText(data.getStringExtra("Fecha"));
            }
        }
    }
}

