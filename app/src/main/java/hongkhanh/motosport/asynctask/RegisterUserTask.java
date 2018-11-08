package hongkhanh.motosport.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import hongkhanh.motosport.activity.Login;

public class RegisterUserTask extends AsyncTask<String, String, String > {
        Context context;
public RegisterUserTask(Context context){
        this.context = context;
        }

@Override
protected void onPreExecute() {
        super.onPreExecute();
        }

@Override
protected String doInBackground(final String... strings) {
        Log.d("HongKhanh","doingbackgroun ne");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.6/project2/index.php", new Response.Listener<String>() {
@Override
public void onResponse(String response) {
        if(response.trim().equals("success")){
        Toast.makeText(context, "Update successfully", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(context,Login.class);
                ((Activity)context).startActivity(intent);
                ((Activity)context).finish();


        }else {
        Toast.makeText(context, "Update error", Toast.LENGTH_SHORT).show();
                Log.d("HongKhanh","create fail roi");
        }
        }
        }, new Response.ErrorListener() {
@Override
public void onErrorResponse(VolleyError error) {

        }
        }
        ){
@Override
protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("operation",strings[0]);
        params.put("name",strings[1]);
        params.put("phone",strings[2]);
        params.put("email",strings[3]);
        params.put("password",strings[4]);
        params.put("andress",strings[5]);
        return params;
        }
        };
        requestQueue.add(stringRequest);
        return null;
        }

@Override
protected void onPostExecute(String s) {
        super.onPostExecute(s);
        }
        }
