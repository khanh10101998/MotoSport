package hongkhanh.motosport.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

import hongkhanh.motosport.R;
import hongkhanh.motosport.adapter.CartAdapter;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewCart;
    CartAdapter cartAdapter;
//    ArrayList<Product> productArrayList;
    ImageView iv_plus, iv_minus;
    Boolean update_set = true;
    static TextView tv_total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initData();
        initControl();
        CheckData();
        initDisplay();
        initEvent();
        ActionToolbar();
        EvenUltil();
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {


//        productArrayList = new ArrayList<>();
//        productArrayList.add(new Product(1,"AGV K3", "200.000 vnd", "https://www.revzilla.com/product_images/0157/1441/agvk3_sv_five_continents_helmets_blue_750x750.jpg",2));
//        productArrayList.add(new Product(1,"AGV K3", "200.000 vnd", "https://www.revzilla.com/product_images/0157/1441/agvk3_sv_five_continents_helmets_blue_750x750.jpg",2));
//        productArrayList.add(new Product(1,"AGV K3", "200.000 vnd", "https://www.revzilla.com/product_images/0157/1441/agvk3_sv_five_continents_helmets_blue_750x750.jpg",2));

    }

    private void initControl() {
        tv_total = findViewById(R.id.price_sum_cart);
        iv_minus = findViewById(R.id.iv_minus_cart);
        iv_plus = findViewById(R.id.iv_plus_cart);
        toolbar = findViewById(R.id.toolbar_cart);
        listViewCart=  findViewById(R.id.lv_cart_activity);

        cartAdapter = new CartAdapter(MainActivity.arrayListCartMain, CartActivity.this);
        listViewCart.setAdapter(cartAdapter);
    }

    private void initDisplay() {

    }

    private void initEvent() {

        listViewCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            }
        });
    }


    public static void EvenUltil(){
        int total = 0;
        for (int i=0; i < MainActivity.arrayListCartMain.size(); i++){
            total += Integer.valueOf(MainActivity.arrayListCartMain.get(i).getPrice());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tv_total.setText(decimalFormat.format(total) + "VND");

    }
    private void CheckData(){
        cartAdapter.notifyDataSetChanged();
//        if (MainActivity.arrayListCartMain.size() > 0) {
//            cartAdapter.notifyDataSetChanged();
//            listViewCart.setVisibility(View.INVISIBLE);
//        }else {
//            cartAdapter.notifyDataSetChanged();
//            listViewCart.setVisibility(View.VISIBLE);
//        }
    }
}
