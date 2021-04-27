package me.aflak.heroicuidador.crud;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import me.aflak.heroicuidador.R;

public class cadastroCuidadorActivity  extends AppCompatActivity {

    private EditText id;
    private EditText nome;
    private EditText email;
    private EditText altura;
    private EditText idade;
    private EditText peso;
    private EditText sexo;

    private CuidadorDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        altura = findViewById(R.id.editAltura);
        idade = findViewById(R.id.editIdade);
        peso = findViewById(R.id.editPesp);
        sexo = findViewById(R.id.editSexo);

        dao = new CuidadorDAO(this);
    }

    public void salvar(View view){
        Cuidador c = new Cuidador();
        c.setNome(nome.getText().toString());
        c.setEmail(email.getText().toString());
        c.setAltura(Double.parseDouble(altura.getText().toString()));
        c.setIdade(Integer.parseInt(idade.getText().toString()));
        c.setPeso(Double.parseDouble(peso.getText().toString()));
        c.setSexo(sexo.getText().toString());
        c.setIdade(Integer.parseInt(idade.getText().toString()));

        long id = dao.inserir(c);
        Toast.makeText(this,"Aluno inserido com id:"+ id,Toast.LENGTH_SHORT).show();
    }

}
