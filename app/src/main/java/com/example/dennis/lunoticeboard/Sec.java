package com.example.dennis.lunoticeboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sec extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;


    public static final String PDF_FETCH_URL = "http://192.168.43.237:80/laikipia/getnotices.php";



    //Image request code
    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;



    //Uri to store the image uri
    private Uri filePath;

    //ListView to show the fetched Pdfs from the server
    ListView listView;

    //button to fetch the intiate the fetching of pdfs.
    Button buttonFetch;
    Button buttonsch;
    Button buttoneducation;
    Button buttonhds;
    Button business;

    //Progress bar to check the progress of obtaining pdfs
    ProgressDialog progressDialog;

    //an array to hold the different pdf objects
    ArrayList<Pdf> pdfList= new ArrayList<Pdf>();

    //pdf adapter

    PdfAdapter pdfAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Initializing views




        //initializing ListView
        listView = (ListView) findViewById(R.id.listView);

        //initializing buttonFetch
        buttonFetch = (Button) findViewById(R.id.buttonFetchPdf);
        buttonsch = (Button) findViewById(R.id.buttonsch);
        buttoneducation = (Button) findViewById(R.id.education);
        buttonhds =(Button) findViewById(R.id.hds);
        business =(Button) findViewById(R.id.business);
        //initializing progressDialog

        progressDialog = new ProgressDialog(this);

        //Setting clicklistener

        buttonFetch.setOnClickListener(this);
        buttonsch.setOnClickListener(this);
        buttoneducation.setOnClickListener(this);
        buttonhds.setOnClickListener(this);
        business.setOnClickListener(this);


        //setting listView on item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Pdf pdf = (Pdf) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(pdf.getUrl()));
                startActivity(intent);

            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent home = new Intent(this, Sec.class);
            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(home);
            return true;
        }
        if (id == R.id.action_sec) {
            Intent upload = new Intent(this, Login2.class);
            startActivity(upload);
            return true;
        }
        if (id == R.id.help) {
            Intent upload = new Intent(this, help.class);
            startActivity(upload);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

        }
    }


    //Requesting permission


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if(v==buttonFetch){
            getHdsNotices();
        }
        if(v==buttonsch){
            getSatNotices();
        }
        if(v==buttoneducation){
            getEducationNotices();
        }
        if(v==buttonhds){

            getPdfs();
        }
        if(v==business){
            getBusinessNotices();
        }
    }
    private  void getBusinessNotices(){

        progressDialog.setMessage("Loading Notices... Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PDF_FETCH_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(Sec.this,obj.getString("message"), Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = obj.getJSONArray("business");

                            for(int i=0;i<jsonArray.length();i++){

                                //Declaring a json object corresponding to every pdf object in our json Array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //Declaring a Pdf object to add it to the ArrayList  pdfList
                                Pdf pdf  = new Pdf();
                                String pdfName = jsonObject.getString("noticeNAME");
                                String pdfUrl = jsonObject.getString("noticeURL");
                                String author = jsonObject.getString("author");
                                String department = jsonObject.getString("department");
                                String school = jsonObject.getString("school");
                                String time =  jsonObject.getString("TimeStamp");
                                pdf.setName(pdfName);
                                pdf.setUrl(pdfUrl);
                                pdf.setAuthor(author);
                                pdf.setDepartment(department);
                                pdf.setSchool(school);
                                pdf.setTime(time);
                                pdfList.add(pdf);


                            }

                            pdfAdapter=new PdfAdapter(Sec.this,R.layout.list_layout, pdfList);

                            listView.setAdapter(pdfAdapter);

                            pdfAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);

    }
    private  void getHdsNotices(){

        progressDialog.setMessage("Loading Notices... Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PDF_FETCH_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(Sec.this,obj.getString("message"), Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = obj.getJSONArray("hds");

                            for(int i=0;i<jsonArray.length();i++){

                                //Declaring a json object corresponding to every pdf object in our json Array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //Declaring a Pdf object to add it to the ArrayList  pdfList
                                Pdf pdf  = new Pdf();
                                String pdfName = jsonObject.getString("noticeNAME");
                                String pdfUrl = jsonObject.getString("noticeURL");
                                String author = jsonObject.getString("author");
                                String department = jsonObject.getString("department");
                                String school = jsonObject.getString("school");
                                String time =  jsonObject.getString("TimeStamp");
                                pdf.setName(pdfName);
                                pdf.setUrl(pdfUrl);
                                pdf.setAuthor(author);
                                pdf.setDepartment(department);
                                pdf.setSchool(school);
                                pdf.setTime(time);
                                pdfList.add(pdf);


                            }

                            pdfAdapter=new PdfAdapter(Sec.this,R.layout.list_layout, pdfList);

                            listView.setAdapter(pdfAdapter);

                            pdfAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);

    }
    private  void getEducationNotices(){

        progressDialog.setMessage("Loading Notices... Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PDF_FETCH_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(Sec.this,obj.getString("message"), Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = obj.getJSONArray("education");

                            for(int i=0;i<jsonArray.length();i++){

                                //Declaring a json object corresponding to every pdf object in our json Array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //Declaring a Pdf object to add it to the ArrayList  pdfList
                                Pdf pdf  = new Pdf();
                                String pdfName = jsonObject.getString("noticeNAME");
                                String pdfUrl = jsonObject.getString("noticeURL");
                                String author = jsonObject.getString("author");
                                String department = jsonObject.getString("department");
                                String school = jsonObject.getString("school");
                                String time =  jsonObject.getString("TimeStamp");
                                pdf.setName(pdfName);
                                pdf.setUrl(pdfUrl);
                                pdf.setAuthor(author);
                                pdf.setDepartment(department);
                                pdf.setSchool(school);
                                pdf.setTime(time);
                                pdfList.add(pdf);


                            }

                            pdfAdapter=new PdfAdapter(Sec.this,R.layout.list_layout, pdfList);

                            listView.setAdapter(pdfAdapter);

                            pdfAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);

    }
    private  void getSatNotices(){

        progressDialog.setMessage("Loading Notices... Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PDF_FETCH_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(Sec.this,obj.getString("message"), Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = obj.getJSONArray("sat");

                            for(int i=0;i<jsonArray.length();i++){

                                //Declaring a json object corresponding to every pdf object in our json Array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //Declaring a Pdf object to add it to the ArrayList  pdfList
                                Pdf pdf  = new Pdf();
                                String pdfName = jsonObject.getString("noticeNAME");
                                String pdfUrl = jsonObject.getString("noticeURL");
                                String author = jsonObject.getString("author");
                                String department = jsonObject.getString("department");
                                String school = jsonObject.getString("school");
                                String time =  jsonObject.getString("TimeStamp");
                                pdf.setName(pdfName);
                                pdf.setUrl(pdfUrl);
                                pdf.setAuthor(author);
                                pdf.setDepartment(department);
                                pdf.setSchool(school);
                                pdf.setTime(time);
                                pdfList.add(pdf);


                            }

                            pdfAdapter=new PdfAdapter(Sec.this,R.layout.list_layout, pdfList);

                            listView.setAdapter(pdfAdapter);

                            pdfAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);

    }
    private void getPdfs() {

        progressDialog.setMessage("Loading Notices... Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, PDF_FETCH_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(Sec.this,obj.getString("message"), Toast.LENGTH_SHORT).show();

                            JSONArray jsonArray = obj.getJSONArray("notices");

                            for(int i=0;i<jsonArray.length();i++){

                                //Declaring a json object corresponding to every pdf object in our json Array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //Declaring a Pdf object to add it to the ArrayList  pdfList
                                Pdf pdf  = new Pdf();
                                String pdfName = jsonObject.getString("noticeNAME");
                                String pdfUrl = jsonObject.getString("noticeURL");
                                String author = jsonObject.getString("author");
                                String department = jsonObject.getString("department");
                                String school = jsonObject.getString("school");
                                String time =  jsonObject.getString("TimeStamp");
                                pdf.setName(pdfName);
                                pdf.setUrl(pdfUrl);
                                pdf.setAuthor(author);
                                pdf.setDepartment(department);
                                pdf.setSchool(school);
                                pdf.setTime(time);
                                pdfList.add(pdf);


                            }

                            pdfAdapter=new PdfAdapter(Sec.this,R.layout.list_layout, pdfList);

                            listView.setAdapter(pdfAdapter);

                            pdfAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);
}}
