package com.example.basededatossql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MySQLiteHelper(context: Context): SQLiteOpenHelper(
    context, "amigos.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE amigos " +
                            "(id INTEGER PRIMARY KEY AUTOINCREMENT" +
                            ",nombre TEXT" +
                            ",email TEXT)"
        db!!.execSQL(ordenCreacion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS amigos"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }

    fun insertarDatos(nombre: String, email: String) {
        val datos = ContentValues()
        datos.put("nombre", nombre)
        datos.put("email", email)
        val db = this.writableDatabase /*Abrimos la base de datos en modo escritura*/
        db.insert("amigos", null, datos)
        db.close()
    }

    fun mostrarDatos(): Cursor? {
        val db : SQLiteDatabase = this.readableDatabase /*Abrimos la base de datos en modo lectura*/
        val cursor = db.rawQuery(
            "SELECT * FROM amigos",
            null)
        return cursor
    }

    fun editarDato(id: String, nombre: String, email: String) {
        val datos = ContentValues()
        if (nombre.isNotEmpty()) {
            datos.put("nombre", nombre)
        }
        if (email.isNotEmpty()) {
            datos.put("email",email)
        }
        if (datos.size()>0) {
            val db = this.writableDatabase
            db.update("amigos",datos,"id = ?", arrayOf(id))
            db.close()
        }
    }

    fun borrarDatos(id: String) {
        val db = this.writableDatabase
        db.delete("amigos","id = ?", arrayOf(id))
        db.close()
    }
}