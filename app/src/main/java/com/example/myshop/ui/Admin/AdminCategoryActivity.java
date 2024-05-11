package com.example.myshop.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myshop.R;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView acu,mic,game,vinil,custom,cabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        init();
        acu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "music");
                startActivity(intent);
            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "collection");
                startActivity(intent);
            }
        });

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "book");
                startActivity(intent);
            }
        });

        vinil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "sport");
                startActivity(intent);
            }
        });

        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "fridge");
                startActivity(intent);
            }
        });

        cabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "photo");
                startActivity(intent);
            }
        });



    }
    private void init(){
        acu = findViewById(R.id.acu);
        mic = findViewById(R.id.mic);
        game = findViewById(R.id.game);
        vinil = findViewById(R.id.vinil);

        custom = findViewById(R.id.custom);
        cabel = findViewById(R.id.cabel);

    }
}