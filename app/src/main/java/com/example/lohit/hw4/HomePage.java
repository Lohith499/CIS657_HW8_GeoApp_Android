/**
 * Created by Lohith and Brain
 */

package com.example.lohit.hw4;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.location.Location;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

import com.example.lohit.hw4.dummy.HistoryContent;

import org.joda.time.DateTime;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static android.text.TextUtils.split;


public class HomePage extends AppCompatActivity  {
    EditText x1,y1,x2,y2;
    Button bcal,bclear;
    TextView dist,bear,error;
    String dmeasure, bmeasure;
    public static int HISTORY_RESULT = 2;
    public static int SETTINGS_RESULT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intentcheck = getIntent();

        x1 = (EditText) findViewById(R.id.lat1);
        y1 = (EditText) findViewById(R.id.long1);
        x2 = (EditText) findViewById(R.id.lat2);
        y2 = (EditText) findViewById(R.id.long2);

        bcal = (Button) findViewById(R.id.bCalculate);
        bclear = (Button) findViewById(R.id.bClear);
        dist = (TextView) findViewById(R.id.textViewdistance);
        bear = (TextView) findViewById(R.id.textViewbearinf);





        if (intentcheck.hasExtra("dselected")){
            dmeasure = getIntent().getStringExtra("dselected");
        }else
        {
            dmeasure= "Kilometers";
        }
        if (intentcheck.hasExtra("bselected")){
             bmeasure = getIntent().getStringExtra("bselected");
        }else
        {
            bmeasure= "Degrees";
        }

        if (intentcheck.hasExtra("coordindate")){

            String[] s = split(getIntent().getStringExtra("coordindate"),",");
            x1.setText(s[0]);
            y1.setText(s[1]);
            x2.setText(s[2]);
            y2.setText(s[3]);
            update();
        }else
        {

        }





        //error = (TextView) findViewById(R.id.editText);

        bcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                keyboardhide();
                update();

            }
        });
        bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardhide();
                x1.setText("");
                x2.setText("");
                y1.setText("");
                y2.setText("");
                dist.setText("");
                bear.setText("");
                //error.setText("");
            }
        });


    }


    public void update(){
        String sx1 = x1.getText().toString();
        String sy1 = y1.getText().toString();
        String sx2 = x2.getText().toString();
        String sy2 = y2.getText().toString();

        if (sx1.length()==0 || sx2.length()==0 || sy1.length()==0 || sy2.length()==0)
        {
            // error.setText("Please Enter all fields");
            return;
        }

        Location loc1 = new Location("");
        loc1.setLatitude(Double.parseDouble(x1.getText().toString()));
        loc1.setLongitude(Double.parseDouble(y1.getText().toString()));


        Location loc2 = new Location("");
        loc2.setLatitude(Double.parseDouble(x2.getText().toString()));
        loc2.setLongitude(Double.parseDouble(y2.getText().toString()));
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);




        float distanceInMeters = (loc1.distanceTo(loc2) / 1000) ; //kms

        if (dmeasure.compareTo("Kilometers") != 0){

            distanceInMeters = distanceInMeters * Float.valueOf("1.6");
            Double d = Double.parseDouble(Float.toString(distanceInMeters));

            dist.setText( df.format(d)+" Miles");

        } else {
            Double d = Double.parseDouble(Float.toString(distanceInMeters));
            dist.setText(df.format(d)+" Kms");
        }


        float bearingbetween = (loc1.bearingTo(loc2)); //degress

        if (bmeasure.compareTo("Degrees") != 0){

            bearingbetween = bearingbetween * Float.valueOf("17.777");

            Double b = Double.parseDouble(Float.toString(bearingbetween));

            bear.setText(df.format(b)+"Mils");
        } else {
            Double b = Double.parseDouble(Float.toString(bearingbetween));
            bear.setText(df.format(b)+"Degrees");
        }

        HistoryContent.HistoryItem item = new
                HistoryContent.HistoryItem(this.x1.getText().toString(),
                this.y1.getText().toString(), this.x2.getText().toString(), this.y2.getText().toString(), DateTime.now());
        HistoryContent.addItem(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {

                case R.id.settings:
                    Intent intent1 =new Intent(this,Settings.class);
                    String sx1 = x1.getText().toString();
                    String sy1 = y1.getText().toString();
                    String sx2 = x2.getText().toString();
                    String sy2= y2.getText().toString();
                    String s =  sx1+","+sy1+","+sx2+","+sy2;
                    intent1.putExtra("dselected", dmeasure);
                    intent1.putExtra("bselected", bmeasure);
                    intent1.putExtra("coordindate", s);
                    this.startActivity(intent1);

                    return true;

                case R.id.action_history:
                    Intent intent = new Intent(HomePage.this, HistoryActivity.class);
                    startActivityForResult(intent, HISTORY_RESULT );
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SETTINGS_RESULT) {
            this.dmeasure = data.getStringExtra("bearingUnits");
            this.bmeasure = data.getStringExtra("distanceUnits");
            update();
        } else if (resultCode == HISTORY_RESULT) {
            String[] vals = data.getStringArrayExtra("item");
            this.x1.setText(vals[0]);
            this.y1.setText(vals[1]);
            this.x2.setText(vals[2]);
            this.y2.setText(vals[3]);
            this.update(); // code that updates the calcs.
        }
    }

    public void keyboardhide(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(x1.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(y1.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(x2.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(y2.getWindowToken(), 0);
    }

}
