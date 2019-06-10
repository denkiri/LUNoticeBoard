package com.example.dennis.lunoticeboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;
import java.util.UUID;
public class NoticeApload extends AppCompatActivity implements View.OnClickListener {
    private Button logbtn;
    private PreferenceHelper preferenceHelper;
    private ParseContent parseContent;

    Button SelectButton, UploadButton;
    TextView Secname;
    EditText PdfNameEditText ;
    TextView depname;
    TextView schname;
    TextView pwd;

    Uri uri;

    public static final String PDF_UPLOAD_HTTP_URL = "http://192.168.43.237:80/laikipia/file_upload.php";
    private static final String KEY_EMPTY ="";

    public int PDF_REQ_CODE = 1;

    String PdfNameHolder,Notice, PdfPathHolder,Department ,School,PdfID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_apload);
        AllowRunTimePermission();
        preferenceHelper = new PreferenceHelper(this);
        parseContent = new ParseContent(this);
        Secname = (TextView) findViewById(R.id.secname);
        depname = (TextView) findViewById(R.id.depname);
        schname = (TextView) findViewById(R.id.school);
        logbtn = (Button) findViewById(R.id.logbtn);
        pwd =(TextView)findViewById(R.id.chage);


        SelectButton = (Button) findViewById(R.id.button);
        UploadButton = (Button) findViewById(R.id.button2);
        PdfNameEditText = (EditText) findViewById(R.id.editText);
        Secname.setText(preferenceHelper.getName());
        depname.setText(preferenceHelper.getdepartment());
        schname.setText(preferenceHelper.getschool());
        pwd.setOnClickListener(this);

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.putIsLogin(false);
                Intent intent = new Intent(NoticeApload.this,Sec.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                NoticeApload.this.finish();
            }
        });
        SelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // PDF selection code start from here .

                Intent intent = new Intent();

                intent.setType("application/pdf");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);

            }
        });

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PdfUploadFunction();




            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();

            SelectButton.setText("Notice Has Been Selected");
        }
    }

    public void PdfUploadFunction() {


        PdfNameHolder = PdfNameEditText.getText().toString().trim();
        Notice = Secname.getText().toString().trim();
        Department = depname.getText().toString().trim();
        School = schname.getText().toString().trim();
        PdfPathHolder = FilePath.getPath(this, uri);

        if (PdfPathHolder == null) {

            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();

 }
        if (KEY_EMPTY.equals(PdfNameHolder)) {

            Toast.makeText(this, "Pdf title required.", Toast.LENGTH_LONG).show();

        }


        else {

            try {

                PdfID = UUID.randomUUID().toString();

                new MultipartUploadRequest(this, PdfID, PDF_UPLOAD_HTTP_URL)
                        .addFileToUpload(PdfPathHolder, "pdf")
                        .addParameter("name", PdfNameHolder)
                        .addParameter("author",Notice)
                        .addParameter("department",Department)
                        .addParameter("school",School)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload();
                Toast.makeText(NoticeApload.this,"Notice Posted Successfully", Toast.LENGTH_LONG).show();

            } catch (Exception exception) {

                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void AllowRunTimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(NoticeApload.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            Toast.makeText(NoticeApload.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(NoticeApload.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(NoticeApload.this,"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(NoticeApload.this,"Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
    private void changePwd(){
        Intent i = new Intent(NoticeApload.this,RegisterActivity.class);
        startActivity(i);


    }

    @Override
    public void onClick(View v) {
        if(v==pwd){
            changePwd();

        }

    }
}
