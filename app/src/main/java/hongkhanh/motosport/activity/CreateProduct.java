package hongkhanh.motosport.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

import hongkhanh.motosport.R;
import hongkhanh.motosport.asynctask.CreateNewCartTask;

public class CreateProduct extends AppCompatActivity {
    Button btn_addProduct;
    CreateNewCartTask createNewCartTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        btn_addProduct = findViewById(R.id.btn_add_cart);
        createNewCartTask = new CreateNewCartTask(this);
        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewCartTask.execute("9","9","9");
//                updateProduct("http://192.168.254.2/project2/create_cart.php");

            }
        });
    }

    private void updateProduct(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")){
                    Toast.makeText(CreateProduct.this, "Update successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CreateProduct.this, "Update error", Toast.LENGTH_SHORT).show();
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
                params.put("customer_id",String.valueOf(88));
                params.put("product_id",String.valueOf(88));
                params.put("count",String.valueOf(88));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
