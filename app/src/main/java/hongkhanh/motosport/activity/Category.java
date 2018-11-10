package hongkhanh.motosport.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import hongkhanh.motosport.R;
import hongkhanh.motosport.adapter.MenuAdapter;
import hongkhanh.motosport.model.Menu;
import hongkhanh.motosport.ultil.CheckConnection;

public class Category extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ArrayList<Menu> arrMenu;
    MenuAdapter productTypeAdapter;
    ListView listViewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initData();
        initControl();
        initDisplay();
        initEvent();


    }

    private void initData() {
        arrMenu = new ArrayList<>();
        getDataMenu();
    }

    private void initControl() {
        listViewMenu = findViewById(R.id.listViewMenu);
        toolbar = findViewById(R.id.tollbar_main_screen);
        drawerLayout = findViewById(R.id.drawer_layout);
        productTypeAdapter = new MenuAdapter(arrMenu, getApplicationContext());
        listViewMenu.setAdapter(productTypeAdapter);
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
                            Toast.makeText(Category.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(Category.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(Category.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(Category.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            Toast.makeText(Category.this, "click: " + arrMenu.get(position).nameType, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    CheckConnection.showToast_short(getApplicationContext(), "ban hay kiem tra lai ket noi");
                }
            }
        });
    }
}
