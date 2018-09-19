package com.codesndata.androidapps.zalego;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codesndata.androidapps.prefs.UserInfo;
import com.codesndata.androidapps.prefs.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewDataActivity extends AppCompatActivity {
    private EditText uName, pass, fName, lName;
    private UserSession session;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        uName = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        fName = findViewById(R.id.firstname);
        lName = findViewById(R.id.lastname);
        session = new UserSession(this);
        userInfo = new UserInfo(this);

        if(!session.isUserLoggedin()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        String username = userInfo.getKeyUsername();
        String password = userInfo.getKeyPass();

        uName.setText(username);
        pass.setText(password);

        viewData();
    }

    public void viewData() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            OkHttpClient client = new OkHttpClient();

            String username = String.valueOf(uName.getText());
            String password = String.valueOf(pass.getText());

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.43.58/zalego_backend/view.php").newBuilder();
            urlBuilder.addQueryParameter("username", String.valueOf(username));
            urlBuilder.addQueryParameter("password", String.valueOf(password));

            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                //txtInfo.setText(response.body().string());

                                try {
                                    String data = response.body().string();

                                    //JSON data response
                                    JSONArray jsonArray = new JSONArray(data);
                                    JSONObject jsonObject;

                                    jsonObject = jsonArray.getJSONObject(0);

                                    fName.setText(jsonObject.getString("firstname"));
                                    lName.setText(jsonObject.getString("lastname"));
                                    uName.setText(jsonObject.getString("username"));
                                    pass.setText(jsonObject.getString("password"));

                                } catch (JSONException e) {
                                    toast(e.getMessage());
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onLogout(View view){
        session.setLoggedin(false);
        userInfo.clearUserInfo();
        Intent logout = new Intent(this, LoginActivity.class);
        startActivity(logout);
        finish();
    }

    private void toast(String x){
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
    }
}
