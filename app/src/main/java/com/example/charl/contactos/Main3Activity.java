package com.example.charl.contactos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Main3Activity extends AppCompatActivity {

    Contactos conta;
    ImageView picture;
    TextView nombre;
    String apellido;
    TextView code;
    TextView address;
    TextView correo;
    TextView bday;
    ImageView call;
    ImageView comp;
    ImageView edit;
    TextView tel;
    String data = "No disponible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        picture = findViewById(R.id.pic);
        nombre = findViewById(R.id.uno);
        address = findViewById(R.id.addr);
        correo = findViewById(R.id.correo2);
        code = findViewById(R.id.identificacion);
        bday = findViewById(R.id.bday);
        call = findViewById(R.id.calling);
        tel = findViewById(R.id.tel);
        comp = findViewById(R.id.sharing);
        edit = findViewById(R.id.modify);

        Intent getinfo = this.getIntent();
        Bundle bundle = getinfo.getExtras();

        conta = (Contactos) bundle.getParcelable("pass");
        Toast toast3 =
                Toast.makeText(getApplicationContext(),
                        conta.getLname(), Toast.LENGTH_SHORT);
        toast3.show();

        //Reviso si la imagen esta vacia. Si no se encuentra le asigno una por defecto
        if (conta.getImgconv() == null) {
            picture.setImageResource(R.drawable.perfil);
        } else {

            picture.setImageURI(conta.getImgconv());
        }

        //Reviso si el nombre esta vacio. Si no se encuentra le asigno un warning por defecto.
        if (conta.getName() == null) {
            nombre.setText(data);
        } else {

            nombre.setText(conta.getName());
        }

        //Reviso si el apellido esta vacio. Si no se encuentra le asigno un warning por defecto.
        if (conta.getLname() == null) {
            apellido= data;
        } else {

            apellido= conta.getLname();
        }

        //Reviso si la direccion esta vacia. Si no se encuentra le asigno un warning por defecto.
        if (conta.getAdress() == null) {
            address.setText(data);
        } else {

            address.setText(conta.getAdress());
        }

        //Reviso si el ID esta vacio. Si no se encuentra le asigno un warning por defecto.
        if (conta.getID() == null) {
            code.setText(data);
        } else {

            code.setText(conta.getID());
        }

        //Reviso si el cumpleaños esta vacio. Si no se encuentra le asigno un warning por defecto.
        if (conta.getCumple() == null) {
            bday.setText(data);
        } else {

            bday.setText(conta.getCumple());
        }

        //Reviso si el correo esta vacio. Si no se encuentra le asigno un warning por defecto.
        if (conta.getMail() == null) {
            correo.setText(data);
        } else {

            correo.setText(conta.getMail());
        }

        //Reviso si el numero esta vacio. Si no se encuentra le asigno un warning por defecto. Si se encuentra revisa los permisos.
        if (conta.getNumero() != null) {

            //Checking Permission is required Marshmallow up
            if (ContextCompat.checkSelfPermission(Main3Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(Main3Activity.this, new String[]{Manifest.permission.CALL_PHONE}, 7);

            } else {

                tel.setText(conta.getNumero());

            }

        } else {
            tel.setText(data);
        }

        //Reviso si el numero esta vacio. Si no se encuentra le asigno un warning por defecto. Si se encuentra revisa los permisos y comienza la llamada.
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conta.getNumero() != null) {

                    //Checking Permission is required Marshmallow up
                    if (ContextCompat.checkSelfPermission(Main3Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(Main3Activity.this, new String[]{Manifest.permission.CALL_PHONE}, 7);


                    } else {

                        Intent call = new Intent(Intent.ACTION_CALL);
                        call.setData(Uri.parse("tel:" + conta.getNumero()));
                        startActivity(call);


                    }


                }
            }
        });

        //Reviso si los datos estan vacios. Si no se encuentran le asigno un warning por defecto. Si se encuentra revisa los permisos y comienza la llamada.
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Share(v);

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modificar = new Intent(getApplicationContext(),Main4Activity.class);
                Bundle box = new Bundle();
                box.putParcelable("pass2",conta);
                modificar.putExtras(box);
                Main3Activity.this.startActivity(modificar);
            }
        });
    }


    public void Share(View view) {

        Bitmap BM;

        BM = getBitmapFromView(picture);

        try {
            File file = new File(this.getExternalCacheDir(), "Contacto.png");

            FileOutputStream Ou = new FileOutputStream(file);

            BM.compress(Bitmap.CompressFormat.PNG, 100, Ou);
            Ou.flush();
            Ou.close();

            file.setReadable(true, false);

            final Intent intent = new Intent(Intent.ACTION_SEND);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.putExtra(Intent.EXTRA_TEXT, "Informacion Sobre  " + nombre.getText() + ":" + "\n Apellido:" + apellido + "\n ID:" + code.getText() + "\n Telefono:" + tel.getText()+ "\n Correo:"+correo.getText() + "\n Cumple:" + bday.getText() + "\n Direccion:" + address.getText());
            intent.setType("*/*");

            startActivity(Intent.createChooser(intent, "Enviar a"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Bitmap getBitmapFromView(View view) {

        Bitmap Result = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas One = new Canvas(Result);
        Drawable Background = view.getBackground();

        if (Background != null)
            Background.draw(One);

        else
            One.drawColor(Color.WHITE);

        view.draw(One);
        return Result;
    }


}

