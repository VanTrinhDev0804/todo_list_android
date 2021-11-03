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
    // tạo task mơi
    public void addTask(int makh,String title , String description, String date , String time , int status ){
        String sql = "insert into Tasks (maKH, title, description , date , time , status)" +
                " values ('"+makh+"','"+title+"','"+description+"','"+date+"','"+time+"','"+status+"')";
        query_no_result(sql);
    }
    public void backupTaskDeleted(int makh,String title , String description, String date , String time){
        String sql = "insert into TasksDeleted (maKH, title, description , date , time )" +
                " values ('"+makh+"','"+title+"','"+description+"','"+date+"','"+time+"')";
        query_no_result(sql);
    }
    // tạo tài khoản mới
    public void addAccount(String Name , String email, String Pass , String BirthDay , String gender ){
        String sql = "insert into KhachHang (tenKH,email,passWord,birthDay, gender)" +
                " values ('"+Name+"','"+email+"','"+Pass+"','"+BirthDay+"','"+gender+"')";
        query_no_result(sql);
    }
//    update

    // status = 0 => false :status =1 => true
    public void upDateStatus(int id ){
        String sql="update Tasks set status = 1 where id = '"+id+"'";
        query_no_result(sql);
    }
    public void upDateTask(String title , String description, String date , String time , int status ,int id){
        String sql = "update Tasks set  title='"+title+"', description='"+description+"' ," +
                " date= '"+date+"', time ='"+time+"', status='"+status+"'  where id = '"+id+"' " ;
        query_no_result(sql);
    }
    //update thoong tin nguoi dung
    public void upDateInfoUser(int makh,String UserName , String email){
        String sql = "update KhachHang set  tenKH='"+UserName+"', email='"+email+"' where maKH = '"+makh+"' " ;
        query_no_result(sql);
    }
    public void upDatePassWord(int makh,String PassWord){
        String sql = "update KhachHang set  passWord='"+PassWord+"'where maKH = '"+makh+"' " ;
        query_no_result(sql);
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
