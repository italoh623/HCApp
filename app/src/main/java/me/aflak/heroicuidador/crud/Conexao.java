package me.aflak.heroicuidador.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.database.Cursor;

public class Conexao  extends  SQLiteOpenHelper{
    private  static  final  String name = "banco.db";
    private static  final int version = 1;

    public Conexao(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cuidador(id integer primary key autoincrement, "+
                "nome varchar(50), email varchar(50), altura varchar(50),idade varchar(50), sexo varchar(50), peso varchar(50), senha varchar(50) )");
        db.execSQL("create table idoso(id integer primary key autoincrement, "+
                "nomeIdoso varchar(50), alturaIdoso varchar(50),idadeIdoso varchar(50), sexoIdoso varchar(50), pesoIdoso varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS cuidador");
        db.execSQL("DROP TABLE IF EXISTS idoso");
        onCreate(db);
    }

    public long CriarCuidador( Cuidador cuidador){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", cuidador.getNome());
        values.put("email", cuidador.getEmail());
        values.put("altura", cuidador.getAltura());
        values.put("idade", cuidador.getIdade());
        values.put("peso", cuidador.getPeso());
        values.put("senha", cuidador.getSenha());
        values.put("sexo", cuidador.getSexo());
        long result = db.insert("cuidador",null,values);
        return result;
    }

    public long CriarIdoso( Idoso idoso){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nomeIdoso", idoso.getNomeIdoso());
        values.put("alturaIdoso", idoso.getAlturaIdoso());
        values.put("idadeIdoso", idoso.getIdadeIdoso());
        values.put("pesoIdoso", idoso.getPesoIdoso());
        values.put("sexoIdoso", idoso.getSexoIdoso());
        long result = db.insert("idoso",null,values);
        return result;
    }


    public String ValidarLogin(String nome, String senha){
//        String nome = Cuidador.getNome();
//        String senha = Cuidador.getSenha();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM cuidador WHERE nome=? and senha=?", new String[]{nome, senha});
        if (c.getCount() > 0){
            return "ok";
        }
        return "Erro";
    }

    public Conexao(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
