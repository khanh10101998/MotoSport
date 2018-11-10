package hongkhanh.motosport.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hongkhanh.motosport.R;
import hongkhanh.motosport.adapter.MenuAdapter;
import hongkhanh.motosport.adapter.ProductAdapter;
import hongkhanh.motosport.model.Menu;
import hongkhanh.motosport.model.Product;
import hongkhanh.motosport.ultil.CheckConnection;
import hongkhanh.motosport.ultil.Server;

public class JacketsActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ArrayList<Menu> arrMenu;
    MenuAdapter productTypeAdapter;
    ListView listViewMenu;
    int page = 1;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    ListView listViewProduct;
    String idCategory = "jackets";
    View footerView;
    boolean isLoading = false;
    boolean limitData = false;
    mHanler mHanler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jacket);
        initData();
        initControl();
        initDisplay();
        initEvent();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetidProduct();
            GetData(page);
            LoadMoreData();
        }else {

        }


    }

    private void LoadMoreData() {
        listViewProduct.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData== false){
                    isLoading = true ;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.urlProduct + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String name ="";
                String price ="";
                String image ="";
                int count =0;
                if(response != null && response.length()>0){
                    listViewProduct.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            name = jsonObject.getString("name");
                            price = jsonObject.getString("price");
                            image = jsonObject.getString("image");
                            count = jsonObject.getInt("count");
                            productArrayList.add(new Product(id, name, price, image, count));
                            productAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitData = true;
                    listViewProduct.removeFooterView(footerView);
                    CheckConnection.showToast_short(getApplicationContext(), "da het du lieu");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("category", idCategory);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetidProduct() {
//        idCategory = getIntent().getStringExtra("idCategory");
    }

    private void initData() {
        arrMenu = new ArrayList<>();
        productArrayList = new ArrayList<>();
        getDataMenu();
    }

    private void initControl() {
        listViewMenu = findViewById(R.id.listViewMenu);
        toolbar = findViewById(R.id.tollbar_main_screen);
        drawerLayout = findViewById(R.id.drawer_layout);
        productTypeAdapter = new MenuAdapter(arrMenu, getApplicationContext());
        listViewProduct = findViewById(R.id.lv_product_jacket);
        productAdapter = new ProductAdapter(productArrayList, getApplicationContext());

        listViewMenu.setAdapter(productTypeAdapter);
        listViewProduct.setAdapter(productAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        mHanler = new mHanler();
    }

    private void initDisplay() {
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActonBar();
        } else {
            CheckConnection.showToast_short(getApplicationContext(), "No Internet");
            finish();
        }
    }

    private void initEvent() {
        CatchOnItemListView();
    }

    private void ActonBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void getDataMenu() {

        arrMenu.add(0, new Menu(0, "ĐỒ PHƯỢT", "http://chittagongit.com//images/icon-clothing/icon-clothing-4.jpg"));
        arrMenu.add(1, new Menu(1, "MŨ BẢO HIỂM", "https://cdn3.iconfinder.com/data/icons/helmet/154/auto-race-car-moto-helmet-512.png"));
        arrMenu.add(2, new Menu(2, "ÁO GIÁP - KHOÁC", "http://chittagongit.com//images/icon-clothing/icon-clothing-4.jpg"));
        arrMenu.add(3, new Menu(3, "GĂNG TAY", "http://chittagongit.com//images/icon-clothing/icon-clothing-4.jpg"));
        arrMenu.add(4, new Menu(4, "THÔNG TIN", "http://downloadicons.net/sites/default/files/red-button-info-icon-50695.png"));
        arrMenu.add(5, new Menu(5, "LIÊN HỆ", "http://icons.iconarchive.com/icons/pelfusion/long-shadow-media/72/Contact-icon.png"));
    }

    private void CatchOnItemListView() {
        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    switch (position) {
                        case 0:
                            Toast.makeText(JacketsActivity.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(JacketsActivity.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(JacketsActivity.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(JacketsActivity.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toast.makeText(JacketsActivity.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    CheckConnection.showToast_short(getApplicationContext(), "ban hay kiem tra lai ket noi");
                }
            }
        });
    }

    public class mHanler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listViewProduct.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;

            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHanler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHanler.obtainMessage(1);
            mHanler.sendMessage(message);
            super.run();
        }
    }
}
