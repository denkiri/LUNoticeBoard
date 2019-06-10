package com.example.dennis.lunoticeboard;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.io.IOException;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText etusername, etpassword;
    private Button btnregister;
    private TextView etdepartment,etschool,id,etname ;
    private ParseContent parseContent;
    private PreferenceHelper preferenceHelper;
    private final int RegTask = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferenceHelper = new PreferenceHelper(this);
        parseContent = new ParseContent(this);
        id = (TextView) findViewById(R.id.unique);
        etname = (TextView) findViewById(R.id.name);
        etdepartment = (TextView) findViewById(R.id.etdepartment);
        etschool = (TextView) findViewById(R.id.etshc);
        etusername = (EditText) findViewById(R.id.etusername);
        etpassword = (EditText) findViewById(R.id.etpassword);
        id.setText(preferenceHelper.getid());
        etname.setText(preferenceHelper.getName());
        etschool.setText(preferenceHelper.getschool());
        etdepartment.setText(preferenceHelper.getdepartment());
        btnregister = (Button) findViewById(R.id.btn);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    register();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void register() throws IOException, JSONException {

        if (!Utils.isNetworkAvailable(RegisterActivity.this)) {
            Toast.makeText(RegisterActivity.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        Utils.showSimpleProgressDialog(RegisterActivity.this);
        final HashMap<String, String> map = new HashMap<>();
        map.put(Constants.Params.ID, id.getText().toString());
        map.put(Constants.Params.NAME, etname.getText().toString());
        map.put(Constants.Params.DEPARTMENT, etdepartment.getText().toString());
        map.put(Constants.Params.SCHOOL, etschool.getText().toString());
        map.put(Constants.Params.USERNAME, etusername.getText().toString());
        map.put(Constants.Params.PASSWORD, etpassword.getText().toString());
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    HttpRequest req = new HttpRequest(Constants.ServiceType.REGISTER);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss", result);
                onTaskCompleted(result, RegTask);
            }
        }.execute();
    }

    private void onTaskCompleted(String response,int task) {
        Log.d("responsejson", response.toString());
        Utils.removeSimpleProgressDialog();  //will remove progress dialog
        switch (task) {
            case RegTask:

                if (parseContent.isSuccess(response)) {

                    parseContent.saveInfo(response);
                    Toast.makeText(RegisterActivity.this, "Password and Username changed successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,NoticeApload.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    this.finish();

                }else {
                    Toast.makeText(RegisterActivity.this, parseContent.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
        }
    }
}


