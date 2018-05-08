package com.example.charl.contactos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import icepick.Icepick;
import icepick.State;

public class Main4Activity extends AppCompatActivity {

    private static final int PICK_IMAGE = 101;

    @State Uri imgu;

    EditText name;
    Contactos conta;
    EditText Lname;
    EditText phone;
    EditText Id;
    EditText adress;
    TextView calen;
    ImageView imgv;
    Button boton;
    EditText mail;
    @State Uri imgp;
    Bitmap bitmap;
    String data = "No disponible";
    String pos;
    @State String calen1;

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        calen1 = calen.getText().toString();
        Icepick.saveInstanceState(this, outState);

    }


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

        Icepick.restoreInstanceState(this,savedInstanceState);
        imgv.setImageURI(imgp);
        calen.setText(calen1);



        Intent getinfo = this.getIntent();
        Bundle bundle = getinfo.getExtras();
        conta = (Contactos) bundle.getParcelable("pass2");
        pos= getinfo.getStringExtra(Intent.EXTRA_TEXT);

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
                if(!adress.getText().toString().isEmpty() &&!name.getText().toString().isEmpty() && !Lname.getText().toString().isEmpty()&& !phone.getText().toString().isEmpty()&& !Id.getText().toString().isEmpty()&& !calen.getText().toString().isEmpty() && !mail.getText().toString().isEmpty()) {
                    if (imgu != null) {
                        if(imgp != null) {
                            Contactos ctc = new Contactos(name.getText().toString(), Lname.getText().toString(), phone.getText().toString(), Id.getText().toString(), imgp, calen.getText().toString(), mail.getText().toString(),adress.getText().toString());
                            Intent sendIntent = new Intent(getApplicationContext(), MainActivity.class);
                            Bundle caja =new Bundle();
                            caja.putParcelable("passw",ctc);
                            sendIntent.putExtras(caja);
                            sendIntent.putExtra(Intent.EXTRA_TEXT,pos);
                            Main4Activity.this.startActivity(sendIntent);
                        }
                        else {
                            Toast toast3 =
                                    Toast.makeText(getApplicationContext(),
                                            "permiso aceptado. volver a elegir la imagen", Toast.LENGTH_SHORT);
                            toast3.show();}
                    }

                    else{ Toast toast2 =
                            Toast.makeText(getApplicationContext(),
                                    "Agregar Imagen", Toast.LENGTH_SHORT);
                        toast2.show();}
                }
                else{
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Terminar de llenar los datos", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });


        //Reviso si la imagen esta vacia. Si no se encuentra le asigno una por defecto
        if (conta.getImgconv() == null) {
            imgv.setImageResource(R.drawable.perfil);
        } else {

           imgv.setImageURI(conta.getImgconv());
        }

        //Reviso si el nombre esta vacio. Si no se encuentra le asigno un warning por defecto.
        if (conta.getName() == null) {
            name.setText(data);
        } else {

            name.setText(conta.getName());
        }

        //Reviso si el apellido esta vacio. Si no se encuentra le asigno un warning por defecto.
        if (conta.getLname() == null) {
            Lname.setText(data);
        } else {

            Lname.setText(conta.getLname());
        }

        //Reviso si la direccion esta vacia. Si no se encuentra le asigno un warning por defecto.
        if (conta.getAdress() == null) {
            adress.setText(data);
        } else {

            adress.setText(conta.getAdress());
        }

        //Reviso si el ID esta vacio. Si no se encuentra le asigno un warning por defecto.
        if (conta.getID() == null) {
            Id.setText(data);
        } else {

            Id.setText(conta.getID());
        }

        //Reviso si el cumpleaños esta vacio. Si no se encuentra le asigno un warning por defecto.
        if (conta.getCumple() == null) {
            calen.setText(data);
        } else {

            calen.setText(conta.getCumple());
        }

        //Reviso si el correo esta vacio. Si no se encuentra le asigno un warning por defecto.
        if (conta.getMail() == null) {
            mail.setText(data);
        } else {

            mail.setText(conta.getMail());
        }
        //Reviso si el numero esta vacio. Si no se encuentra le asigno un warning por defecto. Si se encuentra revisa los permisos.
        if (conta.getNumero() != null) {

            //Checking Permission is required Marshmallow up
            if (ContextCompat.checkSelfPermission(Main4Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(Main4Activity.this, new String[]{Manifest.permission.CALL_PHONE}, 7);

            } else {

                phone.setText(conta.getNumero());

            }

        } else {
            phone.setText(data);
        }



    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imgu = data.getData();
            imgv.setImageURI(imgu);

            if ( PermissionChecker.checkSelfPermission(getApplicationContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&  PermissionChecker.checkSelfPermission(getApplicationContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {

                bitmap = decodeSampledBitmapFromUri(getApplicationContext(),imgu,0,500);

                if (bitmap != null) {

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(Main4Activity.this.getContentResolver(), bitmap, "Title", null);
                    imgp = Uri.parse(path);
                }
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 5);
            }
        }



        if(resultCode == RESULT_OK && requestCode == 1){
            if(data.hasExtra("Fecha")==true){
                calen.setText(data.getStringExtra("Fecha"));
            }

        }
    }
    public static Bitmap decodeSampledBitmapFromUri(Context context,
                                                    Uri imageUri, int rotate, int maxDimension) {
        //

        try {// First decode with inJustDecodeBounds=true to check dimensions
            BitmapFactory.Options options = new BitmapFactory.Options();
            InputStream is;
            options.inJustDecodeBounds = true;

            is = context.getContentResolver().openInputStream(imageUri);
            BitmapFactory.decodeStream(is, null, options);
            is.close();

            // rotate as necessary
            int rotatedWidth, rotatedHeight;

            int orientation = 0;

            // if we have a rotation use it otherwise look at the EXIF
            if (rotate > -1) {
                orientation = rotate;
            }
            if (orientation == 90 || orientation == 270) {
                rotatedWidth = options.outHeight;
                rotatedHeight = options.outWidth;
            } else {
                rotatedWidth = options.outWidth;
                rotatedHeight = options.outHeight;
            }

            Bitmap srcBitmap;
            is = context.getContentResolver().openInputStream(imageUri);
            if (rotatedWidth > maxDimension || rotatedHeight > maxDimension) {
                float widthRatio = ((float) rotatedWidth)
                        / ((float) maxDimension);
                float heightRatio = ((float) rotatedHeight)
                        / ((float) maxDimension);
                float maxRatio = Math.max(widthRatio, heightRatio);

                // Create the bitmap from file
                options = new BitmapFactory.Options();
                options.inSampleSize = (int) Math.round(maxRatio);

                srcBitmap = BitmapFactory.decodeStream(is, null, options);
            } else {
                srcBitmap = BitmapFactory.decodeStream(is);
            }

            is.close();
            if (srcBitmap != null) {

                if (orientation > 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(orientation);

                    srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
                            srcBitmap.getWidth(), srcBitmap.getHeight(),
                            matrix, true);

                }
            }

            return srcBitmap;
        }

        catch (Exception e) {
        }
        return null;

    }
}
