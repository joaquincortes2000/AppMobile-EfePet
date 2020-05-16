package com.example.certamen1app.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.example.certamen1app.model.mascota;

public class BD extends SQLiteOpenHelper {

    public static class DatosTabla implements BaseColumns {

        public static final String NOMBRE_TABLA = "Mascotas";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_RAZA = "raza";
        public static final String COLUMNA_EDAD= "edad";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREAR_TABLA =
                "CREATE TABLE " + DatosTabla.NOMBRE_TABLA + "(" +
                        DatosTabla.COLUMNA_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DatosTabla.COLUMNA_NOMBRE + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_RAZA + TEXT_TYPE + COMMA_SEP +
                        DatosTabla.COLUMNA_EDAD + TEXT_TYPE + ")";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DatosTabla.NOMBRE_TABLA;
    }






    // Si se cambia el Chema de la Base de datos, hay que incrementar el numero de la Version
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "miBD.db";


    public BD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DatosTabla.SQL_CREAR_TABLA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void DeleteDatos(SQLiteDatabase db){
        db.delete(DatosTabla.SQL_DELETE_ENTRIES,null,null);
        db.execSQL(DatosTabla.SQL_DELETE_ENTRIES);
        db.execSQL(DatosTabla.SQL_CREAR_TABLA);
        db.close();
    }

    public void buscarMascota(mascota mascota, String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from Mascotas where id='"+id+"'", null);
        if (c.moveToFirst()){
            do{
                mascota.setNombre(c.getString(1));
                mascota.setRaza(c.getString(2));
                mascota.setEdad(c.getString(3));
            }while (c.moveToNext());
        }
    }

    public void editarMascota(String codigo, String nombre,String raza, String edad){
        SQLiteDatabase bd = getWritableDatabase();
        if(bd!=null){
            bd.execSQL("update Mascotas set nombre='"+nombre+"',raza='"+raza+"',edad='"+edad+"' where id='"+codigo+"'");
            bd.close();
        }
    }

    public void eliminarMascota(String codigo){
        SQLiteDatabase bd = getWritableDatabase();
        if(bd!=null){
            bd.execSQL("delete from Mascotas where id='"+codigo+"'");
            bd.close();
        }
    }

    public Cursor cursor(){
        String[] columnas={"id","nombre","raza","edad"};

        Cursor c=this.getReadableDatabase().query("Mascotas", columnas, null, null, null, null, null);

        return c;
    }


}
