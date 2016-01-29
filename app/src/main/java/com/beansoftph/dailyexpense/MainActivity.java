package com.beansoftph.dailyexpense;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.beansoftph.models.AmountDesignation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private int month, day, year;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ArrayList<AmountDesignation>data= new ArrayList<>();
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
        imgCameraPreview = (ImageView) findViewById(R.id.imgcampreview);
        imbTakePhoto = (ImageButton) findViewById(R.id.btnTake);
        imbUploadPhoto = (ImageButton) findViewById(R.id.btnUpload);

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
        imbUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoGallery(v);
            }
        });
        imbTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto(v);
            }
        });

        ParseAmountDesignation();


    }

    public void ParseAmountDesignation()
    {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Amount_designation");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    AmountDesignation ad = null;
                    ParseObject obj;
                    for (int i = 0; i < objects.size(); i++) {

                        obj = objects.get(i);
                        ad = new AmountDesignation(obj.getString("Type"),
                                obj.getObjectId());

                        data.add(ad);


                    }//End if
                }}});
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub

        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day

            String date = (arg2 + 1) + "-" + arg3 + "-" + arg1;
            txtDate.setText(date);
        }
    };

    public void showPhotoGallery(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_FILE);

    }

    public void takephoto(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Bitmap bitmap = null;
                Log.d("AddMemorabiliaActivity", "Intent data has " + data.getDataString());
                Uri selectedImageUri = data.getData();


                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d("AddMemorabiliaActivity", "bitmap value : " + bitmap);
                imgCameraPreview.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 1000, 1000, false));


            } else if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG,90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgCameraPreview.setImageBitmap(Bitmap.createScaledBitmap(thumbnail, 1000, 1000, false));

            }

        }


    }
}
