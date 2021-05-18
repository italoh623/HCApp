package me.aflak.heroicuidador.crud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.aflak.heroicuidador.R;


public class ListaCuidadorActivity extends AppCompatActivity {

    private ListView listView;
    private CuidadorDAO dao;
    private List<Cuidador> cuidadors;
    private List<Cuidador> cuidadorsFiltrados = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cuidador);

        listView = findViewById(R.id.lista_cuidador);
        dao = new CuidadorDAO(this);

        cuidadors = dao.obterTodos();
        cuidadorsFiltrados.addAll(cuidadors);
        ArrayAdapter<Cuidador>  adaptador = new ArrayAdapter<Cuidador>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adaptador);
    }

    public boolean onCreatOptionsMenu(Menu menu){
        MenuInflater i= getMenuInflater();
                i.inflate(R.menu.menu_main, menu);
                return true;
    }
    public  void  cadastrar(Menu menu){
        Intent it = new Intent(this, cadastroCuidadorActivity.class);
        startActivity(it);

    }
}