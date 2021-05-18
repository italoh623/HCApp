package me.aflak.heroicuidador.crud;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import me.aflak.heroicuidador.R;

public class cadastroCuidadorActivity  extends AppCompatActivity {

    private EditText id;
    private EditText nome;
    private EditText email;
    private EditText idade;
    private EditText sexo;
    private EditText senha;
    private EditText senha2;
    private EditText altura;
    private EditText peso;

    Conexao db;

    Button bt_registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        db = new Conexao(this);

        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        idade = findViewById(R.id.editIdade);
        sexo = findViewById(R.id.editSexo);
        altura = findViewById(R.id.editAltura);
        peso = findViewById(R.id.editPeso);
        senha = findViewById(R.id.editSenha);
        senha2 = findViewById(R.id.editSenha2);

        bt_registrar= (Button)findViewById(R.id.bt_registrar_novo);

        bt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cuidador c = new Cuidador();
                c.setNome(nome.getText().toString());
                c.setEmail(email.getText().toString());
                c.setIdade(Integer.parseInt(idade.getText().toString()));
                c.setAltura(Double.parseDouble(altura.getText().toString()));
                c.setPeso(Double.parseDouble(peso.getText().toString()));
                c.setSexo(sexo.getText().toString());
                c.setSenha(senha.getText().toString());
                c.setSenha2(senha2.getText().toString());


//                if(!senha.equals(senha2)) {
//                    Toast.makeText(cadastroCuidadorActivity.this, "As duas senhas não correspondem, tente novamente", Toast.LENGTH_SHORT).show();
//                }else{
                    long res=db.CriarCuidador(c);
                    if(res>0){
                        Toast.makeText(cadastroCuidadorActivity.this,"Cadastro efetuado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(cadastroCuidadorActivity.this,"Registro inválido, tente novamente", Toast.LENGTH_SHORT).show();
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
