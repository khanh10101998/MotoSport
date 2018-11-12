package hongkhanh.motosport.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
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
import hongkhanh.motosport.adapter.MenuAdapter;
import hongkhanh.motosport.adapter.RecyclerViewAdapter;
import hongkhanh.motosport.model.DataModel;
import hongkhanh.motosport.model.Menu;
import hongkhanh.motosport.ultil.CheckConnection;
import hongkhanh.motosport.ultil.Server;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewMain;
    ListView listViewMenu;
    DrawerLayout drawerLayout;
    ArrayList<Menu> arrMenu;
    ArrayList<String> slideShow;
    MenuAdapter productTypeAdapter;
    RecyclerViewAdapter adapter;
    int id = 0;
    String nameProductType = "";
    String imageProductType = "";
    // hung
    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;

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

        arrMenu = new ArrayList<>();
        slideShow = new ArrayList<>();
        arrayList = new ArrayList<>();
        getDataBanner();
        getDataMenu();
        getDataNewProduct();
    }

    private void initControl() {
        toolbar = findViewById(R.id.tollbar_main_screen);
        viewFlipper = findViewById(R.id.viewliper);
        recyclerViewMain = findViewById(R.id.recyclerView);
        listViewMenu = findViewById(R.id.listViewMenu);
        drawerLayout = findViewById(R.id.drawer_layout);
        productTypeAdapter = new MenuAdapter(arrMenu, getApplicationContext());
        listViewMenu.setAdapter(productTypeAdapter);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(this, arrayList, this);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    private void initDisplay() {
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActonBar();
            ActionViewFlipper();

            //String a = arrMenu.get(2).nameType;
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

//        slideShow.add("https://image.freepik.com/free-photo/a-black-motorcycle-helmet-on-dark-background_2221-275.jpg");
//        slideShow.add("http://wallpaperlepi.com/wp-content/uploads/2015/05/Helmet-Arrai-Motorcycle-Wallpaper.jpg");

        Log.d("HongKhanh", "so luong slideshow: " + slideShow.size());
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

    private void getDataMenu() {
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
                            arrMenu.add(new Menu(id, nameProductType, imageProductType));
                            productTypeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
//                    arrMenu.add(2, new Menu(0, "Contact", "https://cdn2.iconfinder.com/data/icons/picons-basic-1/57/basic1-039_address_book-512.png"));
//                    arrMenu.add(3, new Menu(0, "Infomation", "https://cdn1.iconfinder.com/data/icons/education-set-4/512/information-512.png"));
//                    arrMenu.add(4, new Menu(0, "mu bao hiem", "https://cdn3.iconfinder.com/data/icons/helmet/154/auto-race-car-moto-helmet-512.png"));
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

    private void getDataBanner() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlBanner, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String image = jsonObject.getString("image");
                            Log.d("HongKhanh","hinh banner: "+ image);
                            slideShow.add(image);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ActionViewFlipper();

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
                            Toast.makeText(MainActivity.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            Intent intent0 = new Intent(MainActivity.this,Category.class);
                            intent0.putExtra("idCategory","jackets");
                            startActivity(intent0);
                            break;
                        case 1:
                            Toast.makeText(MainActivity.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(MainActivity.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,JacketsActivity.class);
                            intent.putExtra("idCategory","jackets");
                            startActivity(intent);
                            break;
                        case 3:
                            Toast.makeText(MainActivity.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toast.makeText(MainActivity.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    CheckConnection.showToast_short(getApplicationContext(), "ban hay kiem tra lai ket noi");
                }
            }
        });
    }

    @Override
    public void onItemClick(DataModel item) {
        Toast.makeText(getApplicationContext(), item.name+ " is clicked", Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("HongKhanh",query);
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }


    private void getDataNewProduct(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlNewProducts, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID = 0;
                    String name = "";
                    String price = "";
                    String image = "";
                    int IDproduct =0;

                    for (int i=0; i<response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            name = jsonObject.getString("name");
                            price = jsonObject.getString("price");
                            image = jsonObject.getString("image");
                            arrayList.add(new DataModel(name,image,price));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }


}
