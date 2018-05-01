package com.example.charl.contactos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgv = (ImageView)findViewById(R.id.img);
        name = (EditText) findViewById(R.id.one);
        Lname = (EditText) findViewById(R.id.two);
        phone = (EditText) findViewById(R.id.three);
        Id =  (EditText) findViewById(R.id.four);
        adress= (EditText) findViewById(R.id.five);
        calen= (TextView) findViewById(R.id.cal);
        mail= (EditText) findViewById(R.id.six);
        boton= (Button) findViewById(R.id.Button);

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

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty() && !Lname.getText().toString().isEmpty()&& !phone.getText().toString().isEmpty()&& !Id.getText().toString().isEmpty()&& !calen.getText().toString().isEmpty() && !mail.getText().toString().isEmpty()) {
                    Contactos Contacto = new Contactos(name.getText().toString(), Lname.getText().toString(), phone.getText().toString(), Id.getText().toString(), imgv.getResources().getIdentifier("sample_0", "raw", getPackageName()), calen.getText().toString(),mail.getText().toString());
                    Intent sendIntent = new Intent(getApplicationContext(), MainActivity.class);
                    sendIntent.putExtra("Name", Contacto);
                    setResult(Activity.RESULT_OK, sendIntent);
                    finish();
                }
                else{
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Terminar de llenar los datos", Toast.LENGTH_SHORT);
                    toast1.show();
                }
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

