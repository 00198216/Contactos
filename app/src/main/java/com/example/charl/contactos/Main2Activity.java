package com.example.charl.contactos;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main2Activity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

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
    File imageFile;
    Uri imgp;
    Bitmap bitmap;
    Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgv = (ImageView)findViewById(R.id.img);
        name = (EditText) findViewById(R.id.one);
        Lname = (EditText) findViewById(R.id.two);
        phone = (EditText) findViewById(R.id.three);
        Id =  (EditText) findViewById(R.id.four);
        adress= (EditText) findViewById(R.id.six);
        calen= (TextView) findViewById(R.id.cal);
        mail= (EditText) findViewById(R.id.five);
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
                if(!adress.getText().toString().isEmpty() &&!name.getText().toString().isEmpty() && !Lname.getText().toString().isEmpty()&& !phone.getText().toString().isEmpty()&& !Id.getText().toString().isEmpty()&& !calen.getText().toString().isEmpty() && !mail.getText().toString().isEmpty()) {
                    if (imgu != null) {
                        if(imgp != null) {
                            Contactos ctc = new Contactos(name.getText().toString(), Lname.getText().toString(), phone.getText().toString(), Id.getText().toString(), imgp, calen.getText().toString(), mail.getText().toString(),adress.getText().toString());
                            Intent sendIntent = new Intent(getApplicationContext(), ContactoFrag.class);
                            sendIntent.putExtra("Name", ctc);
                            setResult(Activity.RESULT_OK, sendIntent);
                            finish();
                        }
                        else {
                            Toast toast3 =
                                    Toast.makeText(getApplicationContext(),
                                            "path null", Toast.LENGTH_SHORT);
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



    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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

            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {


                bitmap = decodeSampledBitmapFromUri(getApplicationContext(),imgu,0,300);

                if (bitmap != null) {

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(Main2Activity.this.getContentResolver(), bitmap, "Title", null);
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

