package com.example.dennis.lunoticeboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.HashMap;

public class Login2 extends AppCompatActivity {
    private EditText secusername, secpassword;
    private Button seclogin;
    private ParseContent parseContent;
    private final int LoginTask = 1;
    private PreferenceHelper preferenceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        parseContent = new ParseContent(this);
        preferenceHelper = new PreferenceHelper(this);

        secusername = (EditText) findViewById(R.id.secusername);
        secpassword = (EditText) findViewById(R.id.secpassword);

        seclogin = (Button) findViewById(R.id.btn1);
        seclogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void login() throws IOException, JSONException {

        if (!Utils.isNetworkAvailable(Login2.this)) {
            Toast.makeText(Login2.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        Utils.showSimpleProgressDialog(Login2.this);
        final HashMap<String, String> map = new HashMap<>();
        map.put(Constants.Params.USERNAME, secusername.getText().toString());
        map.put(Constants.Params.PASSWORD, secpassword.getText().toString());
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    HttpRequest req = new HttpRequest(Constants.ServiceType.LOGINSEC);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss", result);
                onTaskCompleted(result,LoginTask);
            }
        }.execute();
    }

    private void onTaskCompleted(String response,int task) {
        Log.d("responsejson", response.toString());
        Utils.removeSimpleProgressDialog();  //will remove progress dialog
        switch (task) {
            case LoginTask:
                if (parseContent.isSuccess(response)) {
                    parseContent.saveInfo(response);
                    Toast.makeText(Login2.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login2.this,NoticeApload.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();
                }else {
                    Toast.makeText(Login2.this, parseContent.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
        }
    }
}
