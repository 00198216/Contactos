package com.example.charl.contactos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class calendario extends AppCompatActivity {

    CalendarView Calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        Calendario= findViewById(R.id.cal);

        Calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override public void onSelectedDayChange(
                    CalendarView view,
                    int          year,
                    int          month,
                    int          dayOfMonth ) {

                String Fecha = ""+dayOfMonth+"/"+""+(month+1)+"/"+""+year;
                Intent sendIntent = new Intent(getApplicationContext(),Main2Activity.class);
                sendIntent.putExtra("Fecha",Fecha);
                setResult(Activity.RESULT_OK,sendIntent);
                finish();
            }

        });



    }

}
