package com.example.coffee

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?, name:String?, factory:SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context,name,factory,version) {

    //매개변수는 DB instance
    override fun onCreate(db: SQLiteDatabase?) {
        //쿼리문. 존재하지 않는다면 테이블을 생성하라는 말
        //시퀀스
        //컬럼명
        var sql : String = " CREATE TABLE IF NOT EXISTS daytime( " +
                "    SEQ INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "    TXT TEXT)  "
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var sql : String = " DROP TABLE IF EXISTS daytime "
        db?.execSQL(sql)
        onCreate(db)
    }

    fun delete(db: SQLiteDatabase, txt:Int){
        val sql = " DELETE FROM daytime" +
                " WHERE id=${txt} "

        db.execSQL(sql)
    }


}