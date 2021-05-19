package me.aflak.heroicuidador.crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.aflak.heroicuidador.R;

public class cadastroIdosoActivity extends AppCompatActivity {

    private EditText id;
    private EditText nomeIdoso;
    private EditText alturaIdoso;
    private EditText idadeIdoso;
    private EditText pesoIdoso;
    private EditText sexoIdoso;

    Conexao db;

    Button bt_registrar2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_idoso);

        db = new Conexao(this);

        nomeIdoso = findViewById(R.id.editNomeIdoso);
        alturaIdoso = findViewById(R.id.editAlturaIdoso);
        idadeIdoso = findViewById(R.id.editIdadeIdoso);
        pesoIdoso = findViewById(R.id.editPesoIdoso);
        sexoIdoso = findViewById(R.id.editSexoIdoso);


        bt_registrar2= (Button)findViewById(R.id.bt_registrar_novo2);

        bt_registrar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Idoso i = new Idoso();
                i.setNomeIdoso(nomeIdoso.getText().toString());
                i.setIdadeIdoso(Integer.parseInt(idadeIdoso.getText().toString()));
                i.setAlturaIdoso(Double.parseDouble(alturaIdoso.getText().toString()));
                i.setPesoIdoso(Double.parseDouble(pesoIdoso.getText().toString()));
                i.setSexoIdoso(sexoIdoso.getText().toString());


                long res=db.CriarIdoso(i);
                if(res>0){
                    Toast.makeText(cadastroIdosoActivity.this,"Cadastro efetuado", Toast.LENGTH_SHORT).show();
                        Intent tela = new Intent(cadastroIdosoActivity.this, LoginActivity.class);
                        startActivity(tela);
                } else {
                    Toast.makeText(cadastroIdosoActivity.this,"Registro inválido, tente novamente", Toast.LENGTH_SHORT).show();
                }
            }
//            }
        });

    }

//    public void salvar(View view){
//        Cuidador c = new Cuidador();
//        c.setNome(nome.getText().toString());
//        c.setEmail(email.getText().toString());
//        c.setIdade(Integer.parseInt(idade.getText().toString()));
//        c.setSexo(sexo.getText().toString());
//        c.setSexo(senha.getText().toString());
//        c.setSexo(senha2.getText().toString());
//
//        long id = dao.inserir(c);
//        Toast.makeText(this,"Cuidador inserido com id: "+ id,Toast.LENGTH_SHORT).show();
//
//        if(!senha.equals(senha2)) {
//            Toast.makeText(cadastroCuidadorActivity.this, "As duas senhas não correspondem, tente novamente", Toast.LENGTH_SHORT).show();
//        }else{
//
//        }
//
//    }

//    public void salvar(View view){
//        String nome = nomegetText().toString();
//        String email = email.getText().toString();
//        String idade = Integer.parseInt(idade.getText().toString());
//        String sexo = sexo.getText().toString();
//        String senha = senha.getText().toString();
//        String senha2 = senha2.getText().toString();
//
//        if (senha.equals(senha2) && )
//    }
}
