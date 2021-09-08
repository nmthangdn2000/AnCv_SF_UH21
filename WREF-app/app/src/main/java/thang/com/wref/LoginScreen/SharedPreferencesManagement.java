package thang.com.wref.LoginScreen;

import android.content.Context;
import android.content.SharedPreferences;

import thang.com.wref.StoriesScreen.Models.UsersModel;

public class SharedPreferencesManagement {
    private static final String TAG = "SharedPreferencesManagement";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String SHARED_PREF_NAME = "userlogin", ID = "id", USER_NAME = "userName",
            EMAIL = "email", AVATA = "avata", TOKEN = "token", LONGTI = "longti", LATI ="lati", ADDRESS="address";

    public SharedPreferencesManagement (Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    // save data của user sau khi login
    public void saveData(UsersModel usersModel){
        editor.putString(ID, usersModel.getId());
        editor.putString(USER_NAME, usersModel.getUsername());
        editor.putString(EMAIL, usersModel.getEmail());
        editor.putString(AVATA, usersModel.getAvata());
        editor.putString(TOKEN, "Bearer "+usersModel.getToken());
        editor.apply();
    }
    // set location application current
    public void setLocation(float lati, float longti, String address){
        String[] strings = address.split(", ");
        String val = strings[1]+" - "+strings[2]+" - "+strings[3];
        editor.putFloat(LATI, lati);
        editor.putFloat(LONGTI, longti);
        editor.putString(ADDRESS, val);
        editor.apply();
    }

    public String getID() {
        return sharedPreferences.getString(ID, null);
    }

    public String getUSER_NAME() {
        return sharedPreferences.getString(USER_NAME, null);
    }

    public String getEMAIL() {
        return sharedPreferences.getString(EMAIL, null);
    }

    public String getAVATA() {
        return sharedPreferences.getString(AVATA, null);
    }

    public String getTOKEN() { return sharedPreferences.getString(TOKEN, null); }

    public String getADDRESS() { return sharedPreferences.getString(ADDRESS, null); }

    public float getLAT() { return sharedPreferences.getFloat(LATI, 0f); }

    public float getLONG() { return sharedPreferences.getFloat(LONGTI, 0f); }

    public void clearData(){
        editor.clear().apply();
    }
}
