package com.example.dennis.lunoticeboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    private EditText etname, ethobby, etusername, etpassword;
    private Button btnregister;
    private TextView tvlogin;
    private ParseContent parseContent;
    private PreferenceHelper preferenceHelper;
    private final int RegTask = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceHelper = new PreferenceHelper(this);
        parseContent = new ParseContent(this);
        Intent Intent=new Intent(MainActivity.this,Sec.class);
        startActivity(Intent);
        if(preferenceHelper.getIsLogin()){
            Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }

    }
}
