package com.example.myshop.ui.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myshop.R;

import com.example.myshop.ui.Goods.AcuActivity;
import com.example.myshop.ui.Goods.CabelActivity;
import com.example.myshop.ui.Goods.CustomActivity;
import com.example.myshop.ui.Goods.GameActivity;
import com.example.myshop.ui.Goods.MicActivity;
import com.example.myshop.ui.Goods.VinilActivity;

public class CategoryActivity extends AppCompatActivity {

    private ImageView acu,mic,game,vinil,custom,cabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        init();
        acu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, AcuActivity.class);
                startActivity(intent);
            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, MicActivity.class);
                startActivity(intent);
            }
        });

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        vinil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, VinilActivity.class);
                startActivity(intent);
            }
        });

        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CustomActivity.class);
                startActivity(intent);
            }
        });

        cabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CabelActivity.class);
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