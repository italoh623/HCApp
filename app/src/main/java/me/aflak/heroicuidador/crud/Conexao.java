package me.aflak.heroicuidador.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Conexao  extends  SQLiteOpenHelper{
    private  static  final  String name = "banco.db";
    private static  final int version = 1;

    public Conexao(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cuidador(id integer primary key autoincrement, "+
                "nome varchar(50), email varchar(50), altura varchar(50),idade varchar(50), sexo varchar(50), peso varchar(50) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Conexao(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
