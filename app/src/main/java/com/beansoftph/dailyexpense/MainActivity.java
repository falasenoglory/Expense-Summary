package com.beansoftph.dailyexpense;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.beansoftph.models.ChartOfAccounts;
import com.beansoftph.models.ReceiptType;
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
    private EditText txtReceiptNo;
    private EditText txtAmount;
    private Spinner spnAmountDesignation;
    private Spinner spnDebitLabel;
    private Spinner spnCreditLabel;
    private ImageView imgCameraPreview;
    private ImageView imgCalendar;
    private ImageButton imbTakePhoto;
    private ImageButton imbUploadPhoto;
    private ImageButton imbSend;
    private int month, day, year;
    int selectedspinSupp;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    File file   = null;
    Bitmap thumbnail;
    File pic;
    private ArrayList<AmountDesignation>data= new ArrayList<>();
    List<String> Lad= new ArrayList<>();
    String COA_type;

    private ArrayList<ChartOfAccounts>COAL= new ArrayList<>();
    List<String> COA= new ArrayList<>();
    String ad_type;



    private ArrayList<Supplier>supp= new ArrayList<>();
    List<String> listSupp = new ArrayList<String>();
    private ArrayList<ReceiptType>rec= new ArrayList<>();
    List<String> listReceipt = new ArrayList<String>();
    String suppname,receipt_type;
    Context context;
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
        txtReceiptNo=(EditText)findViewById(R.id.txtReceiptNo);
        txtAmount = (EditText) findViewById(R.id.txtAmount);
        spnAmountDesignation = (Spinner) findViewById(R.id.spnAmountDesignation);
        spnDebitLabel = (Spinner) findViewById(R.id.spnDebitLabel);
        spnCreditLabel = (Spinner) findViewById(R.id.spnCreditLabel);
        imgCalendar = (ImageView) findViewById(R.id.imgcalendar);
        imgCameraPreview = (ImageView) findViewById(R.id.imgcampreview);
        imbTakePhoto = (ImageButton) findViewById(R.id.btnTake);
        imbUploadPhoto = (ImageButton) findViewById(R.id.btnUpload);
        imbSend = (ImageButton) findViewById(R.id.btnSend);
        context = this;
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

        //ParseAmountDesignation();

        /// START OF SUPPLIER FUNCTIONALITIES

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
                        supplier = new Supplier(obj.getString("Name"),
                                obj.getString("Tin"));
                        supp.add(supplier);
                        listSupp.add(obj.getString("Name"));
                        Log.d("MainActivity", "Hehe : " + obj.getString("Name"));
                        Toast.makeText(getApplicationContext(), obj.getString("Name") + "Parse", Toast.LENGTH_LONG).show();


                        Log.d("MainActivity", "Kasud sa if");
                    }
                    listSupp.add("Others");

                }//End if
                Log.d("MainActivity", "Wa kasud sa if : " + e);
            }
        });

        Toast.makeText(getApplicationContext(),"Gawas sa Parse", Toast.LENGTH_LONG).show();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listSupp);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        listSupp.add("...");

        dataAdapter.notifyDataSetChanged();


        spnSuppliersName.setSelected(false);
        spnSuppliersName.setAdapter(dataAdapter);

        spnSuppliersName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                suppname = String.valueOf(adapterView.getItemAtPosition(i));
                selectedspinSupp = i;
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
                } else {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    View promptsView = li.inflate(R.layout.prompts, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            MainActivity.this);

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
                                        public void onClick(DialogInterface dialog, int id) {
                                            ParseObject newsupplier = new ParseObject("Supplier_Name");
                                            if (SupplierName.getText() != null && SupplierTin.getText() != null)
                                            {
                                                String holder= listSupp.get(listSupp.size()-1);
                                                listSupp.set(listSupp.size()-1,SupplierName.getText().toString());
                                                listSupp.add("Others");
                                            }
                                            newsupplier.put("Name", SupplierName.getText().toString());
                                            newsupplier.put("Tin", SupplierTin.getText().toString());
                                            newsupplier.saveInBackground();
                                            txtSupplierTin.setText(SupplierTin.getText());
                                            spnSuppliersName.setSelection(listSupp.size() - 2);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                    if(spnSuppliersName.getSelectedItem()=="Others")
                    {
                        spnSuppliersName.setSelection(0);
                    }

                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing to do
            }
        });
        dataAdapter.notifyDataSetChanged();
        //END OF SUPPLIER FUNCTIONALITIES

        // START OF TYPE OF RECEIPT FUNCTIONALITIES
        Toast.makeText(getApplicationContext(), "Wa pa sa parse2", Toast.LENGTH_LONG).show();
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Type_of_receipt");
        query2.orderByAscending("createdAt");
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    ReceiptType receipt = null;
                    ParseObject obj;
                    for (int i = 0; i < objects.size(); i++) {

                        obj = objects.get(i);
                        receipt = new ReceiptType(obj.getString("Type"));

                        rec.add(receipt);
                        listReceipt.add(obj.getString("Type"));
                        Log.d("MainActivity", "Hehe : " + obj.getString("Type"));
                        Toast.makeText(getApplicationContext(), obj.getString("Type") + "Parse", Toast.LENGTH_LONG).show();


                        Log.d("MainActivity", "Kasud sa if2");
                    }
                    listReceipt.add("Others");

                }//End if
                Log.d("MainActivity", "Wa kasud sa if2 : " + e);
            }
        });


        Toast.makeText(getApplicationContext(),"Gawas sa Parse", Toast.LENGTH_LONG).show();

        ArrayAdapter<String> receiptAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listReceipt);
        receiptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        receiptAdapter.notifyDataSetChanged();

        receiptAdapter.notifyDataSetChanged();
        spnTypeOfReceipt.setAdapter(receiptAdapter);
        listReceipt.add("...");
        spnTypeOfReceipt.setSelected(false);
        spnTypeOfReceipt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                receipt_type = String.valueOf(adapterView.getItemAtPosition(i));
                selectedspinSupp = i;
                if (receipt_type != "Others") {

                    spnSuppliersName.setSelection(i);

                } else {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    View promptsView = li.inflate(R.layout.prompts_tor, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            MainActivity.this);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText etTOR = (EditText) promptsView
                            .findViewById(R.id.TypeofReceipt);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ParseObject newreceipt = new ParseObject("Type_of_receipt");
                                            if (etTOR.getText() != null)
                                            {
                                                String holder= listReceipt.get(listReceipt.size()-1);
                                                listReceipt.set(listReceipt.size()-1,etTOR.getText().toString());
                                                listReceipt.add("Others");
                                            }
                                            newreceipt.put("Type", etTOR.getText().toString());
                                            newreceipt.saveInBackground();
                                            spnTypeOfReceipt.setSelection(listReceipt.size() - 2);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog2 = alertDialogBuilder.create();

                    // show it
                    alertDialog2.show();
                    if(spnTypeOfReceipt.getSelectedItem()=="Others")
                    {
                        spnTypeOfReceipt.setSelection(0);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing to do
            }
        });
        receiptAdapter.notifyDataSetChanged();


        // END OF TYPE OF RECEIPT FUNCTIONALITIES

        // START AMOUNT DESIGNATION FUNCTIONALITIES
        Toast.makeText(getApplicationContext(), "Wa pa sa parse2", Toast.LENGTH_LONG).show();
        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Amount_designation");
        query3.orderByAscending("createdAt");
        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    AmountDesignation receipt = null;
                    ParseObject obj;
                    for (int i = 0; i < objects.size(); i++) {

                        obj = objects.get(i);
                        receipt = new AmountDesignation(obj.getString("Type"));

                        data.add(receipt);
                        Lad.add(obj.getString("Type"));
                        Log.d("MainActivity", "Hehe : " + obj.getString("Type"));
                        Toast.makeText(getApplicationContext(), obj.getString("Type") + "Parse", Toast.LENGTH_LONG).show();


                        Log.d("MainActivity", "Kasud sa if2");
                    }
                    Lad.add("Others");

                }//End if
                Log.d("MainActivity", "Wa kasud sa if2 : " + e);
            }
        });


        Toast.makeText(getApplicationContext(),"Gawas sa Parse", Toast.LENGTH_LONG).show();

        ArrayAdapter<String> ADAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Lad);
        ADAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ADAdapter.notifyDataSetChanged();
        spnAmountDesignation.setAdapter(ADAdapter);
        Lad.add("...");
        spnAmountDesignation.setSelected(false);
        spnAmountDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ad_type = String.valueOf(adapterView.getItemAtPosition(i));
                selectedspinSupp = i;
                if (ad_type != "Others") {

                    spnAmountDesignation.setSelection(i);

                } else {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    View promptsView = li.inflate(R.layout.prompts_amount_designation, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            MainActivity.this);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText etTOR = (EditText) promptsView
                            .findViewById(R.id.TypeAD);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ParseObject newreceipt = new ParseObject("Amount_designation");
                                            if (etTOR.getText() != null)
                                            {
                                                String holder= Lad.get(Lad.size()-1);
                                                Lad.set(Lad.size()-1,etTOR.getText().toString());
                                                Lad.add("Others");
                                            }
                                            newreceipt.put("Type", etTOR.getText().toString());
                                            newreceipt.saveInBackground();
                                            spnAmountDesignation.setSelection(Lad.size() - 2);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog2 = alertDialogBuilder.create();

                    // show it
                    alertDialog2.show();
                    if (spnAmountDesignation.getSelectedItem() == "Others") {
                        spnAmountDesignation.setSelection(0);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing to do
            }
        });
        ADAdapter.notifyDataSetChanged();
        // END OF AMOUNT DESIGNATION FUNCTIONALITY

        // START CHART OF ACCOUNTS FUNCTIONALITIES

        Toast.makeText(getApplicationContext(), "Wa pa sa parse2", Toast.LENGTH_LONG).show();
        ParseQuery<ParseObject> query4 = ParseQuery.getQuery("Chart_of_Accounts");
        query4.orderByAscending("createdAt");
        query4.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {
                    ChartOfAccounts receipt = null;
                    ParseObject obj;
                    for (int i = 0; i < objects.size(); i++) {

                        obj = objects.get(i);
                        receipt = new ChartOfAccounts(obj.getString("Name_of_Account")
                                );

                        COAL.add(receipt);
                        COA.add(obj.getString("Name_of_Account"));
                        Log.d("MainActivity", "Hehe : " + obj.getString("Name_of_Account"));
                        Toast.makeText(getApplicationContext(), obj.getString("Name_of_Account") + "Parse", Toast.LENGTH_LONG).show();


                        Log.d("MainActivity", "Kasud sa if2");
                    }
                    COA.add("Others");

                }//End if
                Log.d("MainActivity", "Wa kasud sa if2 : " + e);
            }
        });


        ArrayAdapter<String> COAAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, COA);
        COAAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        COAAdapter.notifyDataSetChanged();
        COA.add("...");
        spnDebitLabel.setAdapter(COAAdapter);
        spnCreditLabel.setAdapter(COAAdapter);

        spnDebitLabel.setSelected(false);
        spnCreditLabel.setSelected(false);
        spnDebitLabel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                COA_type = String.valueOf(adapterView.getItemAtPosition(i));
                selectedspinSupp = i;
                if (COA_type != "Others") {

                    spnDebitLabel.setSelection(i);

                } else {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    View promptsView = li.inflate(R.layout.prompts_coa, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            MainActivity.this);

                    // set prompts_coa.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText etTOR = (EditText) promptsView
                            .findViewById(R.id.txtCOA);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ParseObject newreceipt = new ParseObject("Chart_of_Accounts");
                                            if (etTOR.getText() != null) {
                                                String holder = COA.get(COA.size() - 1);
                                                COA.set(COA.size() - 1, etTOR.getText().toString());
                                                COA.add("Others");
                                            }
                                            newreceipt.put("Name_of_Account", etTOR.getText().toString());
                                            newreceipt.saveInBackground();
                                            spnDebitLabel.setSelection(COA.size() - 2);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog2 = alertDialogBuilder.create();

                    // show it
                    alertDialog2.show();
                    if (spnDebitLabel.getSelectedItem() == "Others") {
                        spnDebitLabel.setSelection(0);
                    }

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing to do
            }
        });

        spnCreditLabel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                COA_type = String.valueOf(adapterView.getItemAtPosition(i));
                selectedspinSupp = i;
                if (COA_type != "Others") {

                    spnCreditLabel.setSelection(i);

                } else {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    View promptsView = li.inflate(R.layout.prompts_coa, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            MainActivity.this);

                    // set prompts_coa.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText etTOR = (EditText) promptsView
                            .findViewById(R.id.txtCOA);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ParseObject newreceipt = new ParseObject("Chart_of_Accounts");
                                            if (etTOR.getText() != null) {
                                                String holder= COA.get(COA.size()-1);
                                                COA.set(COA.size()-1,etTOR.getText().toString());
                                                COA.add("Others");
                                            }
                                            newreceipt.put("Name_of_Account", etTOR.getText().toString());
                                            newreceipt.saveInBackground();
                                            spnCreditLabel.setSelection(COA.size() - 2);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog2 = alertDialogBuilder.create();

                    // show it
                    alertDialog2.show();
                    if (spnCreditLabel.getSelectedItem() == "Others") {
                        spnCreditLabel.setSelection(0);
                    }

                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing to do
            }
        });


        COAAdapter.notifyDataSetChanged();



        // END OF CHART OF ACCOUNTS FUNCTIONALITY
        // START OF SENDING EMAIL FUNCTIONALITY


        imbSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String suppliersName,typeRec,amountdes,debla,crela;

                    suppliersName=spnSuppliersName.getSelectedItem().toString();
                    typeRec=spnTypeOfReceipt.getSelectedItem().toString();
                    amountdes=spnAmountDesignation.getSelectedItem().toString();
                    debla=spnDebitLabel.getSelectedItem().toString();
                    crela=spnCreditLabel.getSelectedItem().toString();

                if(suppliersName.equals("..."))
                    suppliersName="";
                if(typeRec.equals("..."))
                    typeRec="";
                if(amountdes.equals("..."))
                    amountdes="";
                if(debla.equals("..."))
                    debla="";
                if(crela.equals("..."))
                    crela="";

                String columnString =   "\"Date\",\"Suppliers Name\",\"Suppliers Tin\",\"Type of Receipt\"" +
                        ",\"Receipt No.\",\"Amount\",\"AmountDesignation\",\"Debit\",\"Credit\"";
                String dataString   =   "\"" + txtDate.getText() +"\",\"" +
                                                suppliersName + "\",\"" +
                                                txtSupplierTin.getText() + "\",\"" +
                                                typeRec+ "\",\""+
                                                txtReceiptNo.getText()+ "\",\"" +
                                                txtAmount.getText()+ "\",\"" +
                                                amountdes+ "\",\"" +
                                                debla+ "\",\"" +
                                                crela + "\"";
                String combinedString = columnString + "\n" + dataString;


                File root   = Environment.getExternalStorageDirectory();
                if (root.canWrite()){
                    File dir    =   new File (root.getAbsolutePath() + "/PersonData");
                    dir.mkdirs();
                    file   =   new File(dir, "Data.csv");
                    FileOutputStream out   =   null;
                    try {
                        out = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.write(combinedString.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ArrayList<Uri> uris = new ArrayList<Uri>();
                Uri u1 = null;
                u1 = Uri.fromFile(file);
                uris.add(Uri.fromFile(file));
                if(pic!=null) {
                    uris.add(Uri.fromFile(pic));
                }
                Intent sendIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"chanferolino@gmail.com"});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, txtDate.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_STREAM, uris);
                sendIntent.setType("text/html");
                sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                context.startActivity(Intent.createChooser(sendIntent, "Send mail..."));

//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"chanferolino@gmail.com"});
//                i.putExtra(Intent.EXTRA_SUBJECT,"On The Job");
//                Log.d("URI@!@#!#!@##!", Uri.fromFile(pic).toString() + "   " + pic.exists());
//                i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(pic));
//
//                i.setType("image/jpeg");
//                startActivity(Intent.createChooser(i,"Share you on the jobing"));

            }
        });



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
                try {
                    File root = Environment.getExternalStorageDirectory();
                    if (root.canWrite()){
                        pic = new File(root, "pic.jpeg");
                        FileOutputStream out = new FileOutputStream(pic);

                        if (bitmap != null) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        }
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    Log.e("BROKEN", "Could not write file " + e.getMessage());
                }



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
                try {
                    File root = Environment.getExternalStorageDirectory();
                    if (root.canWrite()){
                        pic = new File(root, "pic.jpeg");
                        FileOutputStream out = new FileOutputStream(pic);

                        if (thumbnail != null) {
                          thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        }
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    Log.e("BROKEN", "Could not write file " + e.getMessage());
                }

            }

        }


    }
}
