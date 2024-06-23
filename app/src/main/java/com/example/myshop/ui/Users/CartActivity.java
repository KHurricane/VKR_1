package com.example.myshop.ui.Users;

import static java.util.Locale.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.myshop.Model.Order;
import com.example.myshop.Prevalent.Prevalent;
import com.example.myshop.R;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.Model.Products;
import com.example.myshop.ViewHolder.ProductViewHolder;
import com.example.myshop.ui.ImageLoader;

import com.example.myshop.ui.LoginActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;


public class CartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
   // private AppBarConfiguration mAppBarConfiguration;
    DatabaseReference ProductsRef;
    DatabaseReference CartRef;
    private DatabaseReference ordersRef;
    private RecyclerView recyclerView;
    private Button btnPlaceOrder;
    RecyclerView.LayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        CartRef = FirebaseDatabase.getInstance().getReference().child("Cart");
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

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

        btnPlaceOrder = findViewById(R.id.btn_place_order);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderDetailsDialog();
            }
        });


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
                        .setQuery(CartRef, Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Стоимость = " + model.getPrice() + " рублей");
                        new ImageLoader(holder.imageView).execute(model.getImage());

                        holder.itemView.setTag(getRef(position).getKey());

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
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // Не требуется перемещение
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Получение уникального ключа товара и удаление его из корзины
                String productId = (String) viewHolder.itemView.getTag();
                removeFromCart(productId);
            }
        };

        // Применение ItemTouchHelper к RecyclerView
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private void removeFromCart(String productId) {
        CartRef.child(productId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(recyclerView, "Товар удален из корзины", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            Snackbar.make(recyclerView, "Ошибка при удалении товара из корзины", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });
    }

    private void placeOrder(String address) {
        Log.d("CartActivity", "placeOrder called");

        CartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("CartActivity", "onDataChange called");

                if (dataSnapshot.exists()) {
                    StringBuilder orderDescription = new StringBuilder();
                    double totalAmount = 0;

                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        Products product = productSnapshot.getValue(Products.class);
                        if (product != null) {
                            // Убедитесь, что строка цены не содержит пробелов или других символов, которые мешают преобразованию
                            String priceStr = product.getPrice().replaceAll("[^\\d.]", "");
                            double price = Double.parseDouble(priceStr);

                            orderDescription.append(product.getPname()).append(" - ").append(priceStr).append(" руб.\n");
                            totalAmount += price;
                        }
                    }

                    Log.d("CartActivity", "Order Description: " + orderDescription.toString());
                    Log.d("CartActivity", "Total Amount: " + totalAmount);

                    String orderId = ordersRef.push().getKey();
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                    Order order = new Order(orderId, currentTime, currentDate, orderDescription.toString(), String.valueOf(totalAmount),address);
                    ordersRef.child(orderId).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("CartActivity", "Order successfully placed");
                                Snackbar.make(recyclerView, "Заказ успешно оформлен", Snackbar.LENGTH_LONG).show();
                                Intent ordersIntent = new Intent(CartActivity.this, OrdersActivity.class);
                                startActivity(ordersIntent);
                            } else {
                                Log.d("CartActivity", "Error placing order");
                                Snackbar.make(recyclerView, "Ошибка при оформлении заказа", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Log.d("CartActivity", "No data found in CartRef");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("CartActivity", "onCancelled called with error: " + databaseError.getMessage());
                // Handle possible errors.
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_cart){

            Intent cartIntent = new Intent(CartActivity.this, CartActivity.class);
            startActivity(cartIntent);

        } else if(id == R.id.nav_orders){
            Intent ordersIntent = new Intent(CartActivity.this, OrdersActivity.class);
            startActivity(ordersIntent);

        } else if(id == R.id.nav_categories){
            Intent loginIntent = new Intent(CartActivity.this, CategoryActivity.class);
            startActivity(loginIntent);


        } else if(id == R.id.nav_orders){

        } else if(id == R.id.nav_categories){


        } else if(id == R.id.nav_settings){
            Intent loginIntent = new Intent(CartActivity.this, SettingsActivity.class);
            startActivity(loginIntent);


        } else if(id == R.id.nav_logout){
            Paper.book().destroy();
            Intent loginIntent = new Intent(CartActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void showOrderDetailsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_order_details, null);
        builder.setView(dialogView);

        EditText addressInput = dialogView.findViewById(R.id.address_input);
        EditText cardInput = dialogView.findViewById(R.id.card_input);

        builder.setTitle("Введите детали заказа")
                .setPositiveButton("Оформить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String address = addressInput.getText().toString().trim();
                        String card = cardInput.getText().toString().trim();
                        if (!address.isEmpty() && !card.isEmpty()) {
                            placeOrder(address);
                        } else {
                            Toast.makeText(CartActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}