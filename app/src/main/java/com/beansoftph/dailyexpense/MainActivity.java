package com.beansoftph.dailyexpense;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.beansoftph.models.AmountDesignation;
import com.beansoftph.models.Supplier;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.Parse;
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
    int selectedspinSupp;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ArrayList<AmountDesignation>data= new ArrayList<>();
    private ArrayList<Supplier>supp= new ArrayList<>();
    List<String> listSupp = new ArrayList<String>();
    String suppname;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "OSkPxyGfWfMw9ykWVKWhSn8TXva7jrcsUi4DhNjr", "azqj87cioZ5bIwqZYK0sA8RPjTYABjsmkjsZg46r");
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

        Toast.makeText(getApplicationContext(), "Wa pa sa parse", Toast.LENGTH_LONG).show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Supplier_Name");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    Supplier supplier = null;
                    ParseObject obj;
                    for (int i = 0; i < objects.size(); i++) {

                        obj = objects.get(i);
                        supplier = new Supplier(obj.getObjectId(),
                                obj.getString("Name"),
                                obj.getString("Tin"));
                        supp.add(supplier);
                        listSupp.add(obj.getString("Name"));
                        Log.d("MainActivity", "Hehe : " + obj.getString("Name"));
                        Toast.makeText(getApplicationContext(), obj.getString("Name") + "Parse", Toast.LENGTH_LONG).show();


                        Log.d("MainActivity", "Kasud sa if");
                    }//End if

                }
                Log.d("MainActivity", "Wa kasud sa if : " + e);
            }
        });


        Toast.makeText(getApplicationContext(),"Gawas sa Parse", Toast.LENGTH_LONG).show();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listSupp);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        listSupp.add("..");
        listSupp.add("Others");
        dataAdapter.notifyDataSetChanged();
        spnSuppliersName.setAdapter(dataAdapter);
        spnSuppliersName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                suppname =  String.valueOf(adapterView.getItemAtPosition(i));
                selectedspinSupp=i;
                if (suppname != "Others") {


                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Supplier_Name");
                    query2.whereEqualTo("Name", suppname);
                    query2.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (e == null) {

                                for (ParseObject obj : list) {

                                    txtSupplierTin.setText(obj.getString("Tin"));
                                    Toast.makeText(MainActivity.this, "edited cardview", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(view.getContext());
                    View promptsView = li.inflate(R.layout.prompts, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            view.getContext());

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText SupplierName = (EditText) promptsView
                            .findViewById(R.id.SupplierName);
                    final EditText SupplierTin = (EditText) promptsView
                            .findViewById(R.id.SupplierTin);
                            SupplierTin.setInputType(InputType.TYPE_CLASS_NUMBER);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            ParseObject newsupplier = new ParseObject("Supplier_Name");
                                            if(SupplierName.getText()!=null&&SupplierTin.getText()!=null)
                                            listSupp.add(SupplierName.getText().toString());
                                            newsupplier.put("Name",SupplierName.getText().toString());
                                            newsupplier.put("Tin", SupplierTin.getText().toString());
                                            newsupplier.saveInBackground();
                                            txtSupplierTin.setText(SupplierTin.getText());
                                            spnSuppliersName.setSelection(listSupp.size()-1);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            //nothing to do
            }
        });
        dataAdapter.notifyDataSetChanged();
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
                Log.d("MainActivity", "Intent data has " + data.getDataString());
                Uri selectedImageUri = data.getData();


                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d("MainActivity", "bitmap value : " + bitmap);
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
