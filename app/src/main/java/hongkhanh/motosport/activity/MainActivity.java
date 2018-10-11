package hongkhanh.motosport.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hongkhanh.motosport.R;
import hongkhanh.motosport.adapter.ProductTypeAdapter;
import hongkhanh.motosport.model.ProductType;
import hongkhanh.motosport.ultil.CheckConnection;
import hongkhanh.motosport.ultil.Server;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMain;
    ListView listViewMenu;
    DrawerLayout drawerLayout;
    ArrayList<ProductType> arrProductType;
    ProductTypeAdapter productTypeAdapter;
    int id = 0;
    String nameProductType = "";
    String imageProductType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initControl();
        initDisplay();
        initEvent();

    }

    private void initData() {
        arrProductType = new ArrayList<>();
        Log.d("HongKhanh", "Get DaTa 1");
        getDataProductType();
    }

    private void initControl() {
        toolbar = findViewById(R.id.tollbar_main_screen);
        viewFlipper = findViewById(R.id.viewliper);
        recyclerViewMain = findViewById(R.id.recyclerview);
        listViewMenu = findViewById(R.id.listViewMenu);
        drawerLayout = findViewById(R.id.drawer_layout);
        productTypeAdapter = new ProductTypeAdapter(arrProductType, getApplicationContext());
        listViewMenu.setAdapter(productTypeAdapter);
    }

    private void initDisplay() {
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActonBar();
            ActionViewFlipper();

            //String a = arrProductType.get(2).nameType;
            // Log.d("HongKhanh", a);
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

    private void ActionViewFlipper() {
        ArrayList<String> slideShow = new ArrayList<>();
        slideShow.add("https://znews-photo-td.zadn.vn/w1024/Uploaded/tmuitg/2018_03_21/5.jpg");
        slideShow.add("http://vtimes.com.au/up/images/09-17/2764158-nhung-mon-an-nhat-dinh-ph-0.jpg");
        slideShow.add("https://znews-photo-td.zadn.vn/w1024/Uploaded/tmuitg/2018_03_21/7.jpg");
        slideShow.add("https://znews-photo-td.zadn.vn/w1024/Uploaded/tmuitg/2018_03_21/9.jpg");
        slideShow.add("https://wiki-travel.com.vn/uploads/post/NguyenBinhVTV-153518083503-Phovietnam.jpg");
        for (int i = 0; i < slideShow.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(slideShow.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation anim_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation anim_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(anim_slide_in);
        viewFlipper.setOutAnimation(anim_slide_out);
    }

    private void getDataProductType() {
        Log.d("HongKhanh", "Get DaTa");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlProductType, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Log.d("HongKhanh", "response ! null");
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            nameProductType = jsonObject.getString("name");
                            imageProductType = jsonObject.getString("image");
                            Log.d("HongKhanh", "id" + id + ", name: " + nameProductType + " image: " + imageProductType);
                            arrProductType.add(new ProductType(id, nameProductType, imageProductType));
                            productTypeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
//                    arrProductType.add(2, new ProductType(0, "Contact", "https://cdn2.iconfinder.com/data/icons/picons-basic-1/57/basic1-039_address_book-512.png"));
//                    arrProductType.add(3, new ProductType(0, "Infomation", "https://cdn1.iconfinder.com/data/icons/education-set-4/512/information-512.png"));
//                    arrProductType.add(4, new ProductType(0, "mu bao hiem", "https://cdn3.iconfinder.com/data/icons/helmet/154/auto-race-car-moto-helmet-512.png"));
                } else {
                    Log.d("HongKhanh", "response is null");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void CatchOnItemListView() {
        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    switch (position) {
                        case 0:
                            Toast.makeText(MainActivity.this, "click: " + arrProductType.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(MainActivity.this, "click: " + arrProductType.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(MainActivity.this, "click: " + arrProductType.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(MainActivity.this, "click: " + arrProductType.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toast.makeText(MainActivity.this, "click: " + arrProductType.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                else {
                    CheckConnection.showToast_short(getApplicationContext(), "ban hay kiem tra lai ket noi");
                }
            }
        });
    }
}
