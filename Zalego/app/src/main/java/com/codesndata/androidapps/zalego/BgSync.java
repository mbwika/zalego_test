package com.codesndata.androidapps.zalego;


        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AlertDialog;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.io.OutputStreamWriter;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.URLEncoder;

/**
 *
 */

public class BgSync extends AsyncTask<String,Void,String>{

    public static final String LOGIN_URL  = "http://192.168.43.58/zalego_backend/login.php";
    Context ctx, context;
    AlertDialog alertDialog;


    @Override
    protected void onPreExecute() {
//        alertDialog = new AlertDialog.Builder(ctx).create();
//        alertDialog.setTitle("Server Response");

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
//        if(result.equals("Registration Successful!"))
//        {
//            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
//        } else if(result.contains("Data")){
//            alertDialog.setMessage(result);
//            alertDialog.show();
//
//        }  else {
           // popUp();
//        }
    }

    @Override
    protected String doInBackground(String... params) {

        //Private (Phone) IP: 192.168.43.58
        //Local (Emulator) IP: 10.0.2.2
        String reg_url = "http://192.168.43.58/zalego_backend/register.php";
        String login_url = "http://192.168.43.58/zalego_backend/login.php";
        String method = params[0];

        if(method.equals("register")){

            //passing parameters
            String firstname = params[1];
            String lastname = params[2];
            String username = params[3];
            String password = params[4];

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("firstname", "UTF-8") +"="+URLEncoder.encode(firstname, "UTF-8")+"&"+
                        URLEncoder.encode("lastname", "UTF-8") +"="+URLEncoder.encode(lastname, "UTF-8")+"&"+
                        URLEncoder.encode("username", "UTF-8") +"="+URLEncoder.encode(username, "UTF-8")+"&"+
                        URLEncoder.encode("password", "UTF-8") +"="+URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registration Successful!";
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(method.equals("login")) {
            String username = params[1];
            String password = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") +"="+URLEncoder.encode(username, "UTF-8")+"&"+
                        URLEncoder.encode("password", "UTF-8") +"="+URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null)
                {
                    response += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void popUp(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setMessage("Logged in. Proceed?");
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                try {

                    try{
                        Toast.makeText(ctx, "Redirecting...", Toast.LENGTH_LONG).show();
                        Intent land = new Intent(context, ViewDataActivity.class);
                        context.startActivity(land);
                    } catch (Exception x){
                        x.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ctx, "Logged out!", Toast.LENGTH_LONG).show();
                Intent land = new Intent(context, LoginActivity.class);
                context.startActivity(land);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }


}

