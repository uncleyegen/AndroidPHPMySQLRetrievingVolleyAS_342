package com.example.androidphpmysqlretrievingvolleyas_342;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // Variable declarations
    private String userEmail;
    private TextView textView;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter mAdapter;
    private List<Product> products;
    private ProgressBar progressBar;
    private static  final String BASE_URL = "http://192.168.43.209/AndroidPHPMySQLRetrievingVolley__TechWithWalter_API/getProducts.php";

/*
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        if (item.getItemId() == R.id.action_settings){

            intent = new Intent(HomeActivity.this,SettingsActivity.class);
            startActivity(intent);
            Toast.makeText(HomeActivity.this,"Settings clicked!",Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.action_notifications){

            intent = new Intent(HomeActivity.this,NotificationsActivity.class);
            startActivity(intent);
            Toast.makeText(HomeActivity.this,"Notifications clicked!",Toast.LENGTH_SHORT).show();
        }

        return true;
    }
    */


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_menu,menu);

        return true;
    }
  */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = findViewById(R.id.dashboard_toolbar);
        progressBar = findViewById(R.id.progressbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();

        recyclerView = findViewById(R.id.products_recyclerView);
        manager = new GridLayoutManager(HomeActivity.this, 2);
        recyclerView.setLayoutManager(manager);
        products = new ArrayList<>();

        getProducts();

    }

    private void getProducts (){
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String title = object.getString("title");
                                double price = object.getDouble("price");
                                double rating = object.getDouble("rating");
                                String image = object.getString("image");

                                String rate = String.valueOf(rating);
                                float newRate = Float.valueOf(rate);

                                Product product = new Product(title,price, newRate,image);
                                products.add(product);
                            }

                        }catch (Exception e){

                        }

                        mAdapter = new RecyclerAdapter(HomeActivity.this,products);
                        recyclerView.setAdapter(mAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(HomeActivity.this).add(stringRequest);

    }

}
