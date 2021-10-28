package com.example.listtodo.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public  Database(@Nullable Context context){
        super(context,"/data/data/com.example.listtodo/database/TodoList.db", null,1);
    }
    public void query_no_result(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    public Cursor query_with_result(String sql){
        SQLiteDatabase db = getWritableDatabase();
        return db.rawQuery(sql, null);
    }
//    insert
    public void addTask(int makh,String title , String description, String date , String time , int status ){
        String sql = "insert into Tasks (maKH, title, description , date , time , status)" +
                " values ('"+makh+"','"+title+"','"+description+"','"+date+"','"+time+"','"+status+"')";
        query_no_result(sql);
    }
//    update
    public void upDateStatus(int id ){
        String sql="update Tasks set status = 1 where id = '"+id+"'";
        query_no_result(sql);
    }
    public void upDateTask(String title , String description, String date , String time , int status ,int id){
        String sql = "update Tasks set  title='"+title+"', description='"+description+"' ," +
                " date= '"+date+"', time ='"+time+"', status='"+status+"'  where id = '"+id+"' " ;
        query_no_result(sql);
    }
    public void upDateInfoUser(int makh,String UserName , String email){
        String sql = "update KhachHang set  tenKH='"+UserName+"', email='"+email+"' where maKH = '"+makh+"' " ;
        query_no_result(sql);
    }
    public void upDatePassWord(int makh,String PassWord){
        String sql = "update KhachHang set  passWord='"+PassWord+"'where maKH = '"+makh+"' " ;
        query_no_result(sql);
    }

//    get
    public Integer getMaKHfromEmail(String email){
        int makh = 0;
        String sql = "select maKH from KhachHang where email ='"+email+"'";
        Cursor cursor = query_with_result(sql);
        while (cursor.moveToNext()){
            makh = cursor.getInt(0);
        }
        return makh;
    }
    public String getPassFromMaKH(int maKH){
        String password= "";
        String sql = "select passWord from KhachHang where maKH = '"+maKH+"'";
        Cursor cursor = query_with_result(sql);
        while (cursor.moveToNext()){
            password= cursor.getString(0);
        }
        return  password;
    }
// delete
    public void deleteTask(int id){
        String sql = "delete from Tasks where id ='"+id+"' ";
        query_no_result(sql);
}

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
