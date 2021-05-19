package me.aflak.heroicuidador.crud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.aflak.heroicuidador.R;
import me.aflak.heroicuidador.activity.Select;

public class LoginActivity extends AppCompatActivity {

    private EditText loginNome;
    private EditText loginSenha;
    private TextView backCadastro;

    Button bt_login;

    Conexao db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new Conexao(this);

        loginNome = (EditText) findViewById(R.id.loginNome);
        loginSenha = (EditText) findViewById(R.id.loginSenha);
        backCadastro = (TextView) findViewById(R.id.backCadastro);

        bt_login = (Button) findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = loginNome.getText().toString();
                String senha = loginSenha.getText().toString();

                if(nome.equals("")) {
                    Toast.makeText(LoginActivity.this, "Username em branco", Toast.LENGTH_SHORT).show();
                }else{
                    String res=db.ValidarLogin(nome, senha);
                    if(res.equals("ok")){
                        Toast.makeText(LoginActivity.this,"Login efetuado", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Select.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this,"Login inválido, tente novamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        backCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), cadastroCuidadorActivity.class);
                startActivity(intent);
            }
        });
    }
}