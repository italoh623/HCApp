package me.aflak.heroicuidador.crud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.aflak.heroicuidador.R;

public class LoginActivity extends AppCompatActivity {

    private EditText loginNome;
    private EditText loginSenha;

    Button bt_login;

    private EditText nome;
    private EditText senha;

    Conexao db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nome = findViewById(R.id.editNome);
        senha = findViewById(R.id.editSenha);

        db = new Conexao(this);

        loginNome = (EditText)findViewById(R.id.editNome);
        loginSenha = (EditText)findViewById(R.id.loginSenha);

        bt_login = (Button) findViewById(R.id.bt_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cuidador c = new Cuidador();
                c.setNome(nome.getText().toString());
                c.setSenha(senha.getText().toString());

                if(!nome.equals("")) {
                    Toast.makeText(LoginActivity.this, "Username em branco", Toast.LENGTH_SHORT).show();
                }else{
                    String res=db.ValidarLogin(c);
                    if(res.equals("ok")){
                        Toast.makeText(LoginActivity.this,"Login efetuado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this,"Login inválido, tente novamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}