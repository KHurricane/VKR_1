package com.example.myshop.ui.Goods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myshop.Model.Products;
import com.example.myshop.Prevalent.Prevalent;
import com.example.myshop.R;
import com.example.myshop.ViewHolder.ProductViewHolder;
import com.example.myshop.ui.LoginActivity;
import com.example.myshop.ui.Users.CartActivity;
import com.example.myshop.ui.ImageLoader;
import com.example.myshop.ui.Users.CategoryActivity;
import com.example.myshop.ui.Users.OrdersActivity;
import com.example.myshop.ui.Users.SettingsActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;


public class AcuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference AcuRef;
    DatabaseReference CartRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        CartRef = FirebaseDatabase.getInstance().getReference().child("Cart");
        AcuRef = FirebaseDatabase.getInstance().getReference().child("Acu");



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(AcuActivity.this, CartActivity.class);
                startActivity(cartIntent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Меню");
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


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(AcuRef, Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText(model.getPrice() + " рублей");
                        new ImageLoader(holder.imageView).execute(model.getImage());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addToCart(model);
                            }
                        });



                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        return new ProductViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    private void addToCart(Products product) {
        // Получение уникального ключа для нового элемента в корзине
        String cartItemId = CartRef.push().getKey();
        if (cartItemId != null) {
            // Копирование данных товара в таблицу Cart по уникальному ключу
            CartRef.child(cartItemId).setValue(product)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Успешно добавлено в корзину
                                Snackbar.make(recyclerView, "Товар добавлен в корзину", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                // Обработка ошибки при добавлении в корзину
                                Snackbar.make(recyclerView, "Ошибка при добавлении в корзину", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_cart){

            Intent cartIntent = new Intent(AcuActivity.this, CartActivity.class);
            startActivity(cartIntent);

        } else if(id == R.id.nav_orders){
            Intent ordersIntent = new Intent(AcuActivity.this, OrdersActivity.class);
            startActivity(ordersIntent);

        } else if(id == R.id.nav_categories){
            Intent loginIntent = new Intent(AcuActivity.this, CategoryActivity.class);
            startActivity(loginIntent);


        } else if(id == R.id.nav_orders){

        } else if(id == R.id.nav_categories){


        } else if(id == R.id.nav_settings){
            Intent loginIntent = new Intent(AcuActivity.this, SettingsActivity.class);
            startActivity(loginIntent);


        } else if(id == R.id.nav_logout){
            Paper.book().destroy();
            Intent loginIntent = new Intent(AcuActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
