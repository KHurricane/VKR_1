package com.example.myshop.ui.Users;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.view.Gravity;
import android.widget.TextView;

import com.example.myshop.Prevalent.Prevalent;
import com.example.myshop.R;
import com.example.myshop.ui.Goods.AcuActivity;
import com.example.myshop.ui.Goods.CabelActivity;
import com.example.myshop.ui.Goods.CustomActivity;
import com.example.myshop.ui.Goods.GameActivity;
import com.example.myshop.ui.Goods.MicActivity;
import com.example.myshop.ui.Goods.VinilActivity;
import com.example.myshop.ui.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class CategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView acu, mic, game, vinil, custom, cabel;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Меню");
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(Prevalent.currentOnlineUser.getName());
        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setClickListeners();
    }

    private void init() {
        acu = findViewById(R.id.acu);
        mic = findViewById(R.id.mic);
        game = findViewById(R.id.game);
        vinil = findViewById(R.id.vinil);
        custom = findViewById(R.id.custom);
        cabel = findViewById(R.id.cabel);
    }

    private void setClickListeners() {
        acu.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, AcuActivity.class);
            startActivity(intent);
        });

        mic.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, MicActivity.class);
            startActivity(intent);
        });

        game.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, GameActivity.class);
            startActivity(intent);
        });

        vinil.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, VinilActivity.class);
            startActivity(intent);
        });

        custom.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, CustomActivity.class);
            startActivity(intent);
        });

        cabel.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, CabelActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_cart){

            Intent cartIntent = new Intent(CategoryActivity.this, CartActivity.class);
            startActivity(cartIntent);

        } else if(id == R.id.nav_orders){
            Intent ordersIntent = new Intent(CategoryActivity.this, OrdersActivity.class);
            startActivity(ordersIntent);

        } else if(id == R.id.nav_categories){
            Intent loginIntent = new Intent(CategoryActivity.this, CategoryActivity.class);
            startActivity(loginIntent);


        } else if(id == R.id.nav_orders){

        } else if(id == R.id.nav_categories){


        } else if(id == R.id.nav_settings){
            Intent loginIntent = new Intent(CategoryActivity.this, SettingsActivity.class);
            startActivity(loginIntent);


        } else if(id == R.id.nav_logout){
            Paper.book().destroy();
            Intent loginIntent = new Intent(CategoryActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
