package com.example.dennis.lunoticeboard;
import android.content.Context;
import android.content.SharedPreferences;

import static com.example.dennis.lunoticeboard.Constants.Params.ID;

public class PreferenceHelper {
    private final String INTRO = "intro";
    private final String NAME = "name";
    private final String SCHOOL= "school";
    private final String DEPARTMENT= "department";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }
    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public void putName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NAME, loginorout);
        edit.commit();
    }
    public String getName() {
        return app_prefs.getString(NAME, "");
    }

    public void putdepartment(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(DEPARTMENT, loginorout);
        edit.commit();
    }
    public String getdepartment()

    {
        return app_prefs.getString(DEPARTMENT, "");
    }
    public void putschool(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(SCHOOL, loginorout);
        edit.commit();
    }
    public String getschool()

    {
        return app_prefs.getString(SCHOOL, "");
    }
    public void putid(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ID, loginorout);
        edit.commit();
    }
    public String getid()

    {
        return app_prefs.getString(ID, "");
    }
}
