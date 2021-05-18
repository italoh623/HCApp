package me.aflak.heroicuidador.crud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.view.View;

import me.aflak.heroicuidador.R;

public class PrincipalActivity extends AppCompatActivity {
    Button bt_entrar, bt_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bt_entrar = (Button) findViewById(R.id.bt_entrar);
        bt_registrar = (Button) findViewById(R.id.bt_registrar);

        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrincipalActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        bt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PrincipalActivity.this, cadastroCuidadorActivity.class);
                startActivity(i);
            }
        });
    }
}
