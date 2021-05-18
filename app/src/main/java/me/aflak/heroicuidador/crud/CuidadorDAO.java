package me.aflak.heroicuidador.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class CuidadorDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public CuidadorDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir( Cuidador cuidador){
        ContentValues values = new ContentValues();
        values.put("nome", cuidador.getNome());
        values.put("email", cuidador.getEmail());
        values.put("altura", cuidador.getAltura());
        values.put("idade", cuidador.getIdade());
        values.put("peso", cuidador.getPeso());
        values.put("senha", cuidador.getSenha());
        values.put("sexo", cuidador.getSexo());
        return banco.insert("cuidador", null, values);
    }


    public List<Cuidador>obterTodos(){
        List<Cuidador> cuidador = new ArrayList<>();
        Cursor cursor = banco.query("cuidador", new String[]{"id","nome"},
                null, null,null,null, null,null);
        while (cursor.moveToNext()){
            Cuidador a = new Cuidador();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
        }
        return cuidador;
    }
}
