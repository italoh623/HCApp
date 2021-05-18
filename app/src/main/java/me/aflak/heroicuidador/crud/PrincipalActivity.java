package me.aflak.heroicuidador.crud;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import me.aflak.heroicuidador.R;

public class MainActivity extends AppCompatActivity {
    Button bt_entrar, bt_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bt_entrar = (Button) findViewById(R.id.bt_entrar);
        bt_registrar = (Button) findViewById(R.id.bt_registrar);

//        bt_entrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(i);
//            }
//        });
//
//        bt_registrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, cadastroCuidadorActivity.class);
//                startActivity(i);
//            }
//        });
    }
}
