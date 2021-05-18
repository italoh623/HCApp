package me.aflak.heroicuidador.crud;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import me.aflak.heroicuidador.R;

public class cadastroCuidadorActivity2 extends AppCompatActivity {

    private EditText altura;
    private EditText peso;
    private EditText senha;

    private CuidadorDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

//        altura = findViewById(R.id.editAltura);
//        peso = findViewById(R.id.editPeso);

        dao = new CuidadorDAO(this);
    }

    public void salvar2(View view){
        Cuidador c = new Cuidador();

        c.setAltura(Double.parseDouble(altura.getText().toString()));
        c.setPeso(Double.parseDouble(peso.getText().toString()));
        c.setSenha(senha.getText().toString());


        long id = dao.inserir(c);
        Toast.makeText(this,"Cuidador inserido com id: "+ id,Toast.LENGTH_SHORT).show();
    }

}
