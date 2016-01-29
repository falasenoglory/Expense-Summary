package com.beansoftph.dailyexpense;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText txtDate;
    private Spinner spnSuppliersName;
    private EditText txtSupplierTin; // dependent to Supplier Name
    private Spinner spnTypeOfReceipt;
    private EditText txtAmount;
    private Spinner spnAmountDesignation;
    private Spinner spnDebitLabel;
    private Spinner spnCreditLabel;
    private ImageView imgCameraPreview;
    private ImageView imgCalendar;
    private ImageButton imbTakePhoto;
    private ImageButton imbUploadPhoto;
    private int month,day,year;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtDate = (EditText) findViewById(R.id.txtDate);
        spnSuppliersName = (Spinner) findViewById(R.id.spnSupplierName);
        txtSupplierTin = (EditText) findViewById(R.id.txtSuppliersTIN);
        spnTypeOfReceipt = (Spinner) findViewById(R.id.spnTypeofReceipt);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        spnAmountDesignation = (Spinner) findViewById(R.id.spnAmountDesignation);
        spnDebitLabel = (Spinner) findViewById(R.id.spnDebitLabel);
        spnCreditLabel = (Spinner) findViewById(R.id.spnCreditLabel);
        imgCalendar = (ImageView) findViewById(R.id.imgcalendar);


        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(999);


            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub

        if (id == 999) {
            return new DatePickerDialog(this, myDateListener,year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day

            String date= (arg2+1)+"-"+arg3+"-"+arg1;
            txtDate.setText(date);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
