package me.aflak.heroicuidador.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;



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
        values.put("sexo", cuidador.getSexo());
        return banco.insert("cuidador", null, values);
    }
}
