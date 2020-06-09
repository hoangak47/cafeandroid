package com.example.quanlyquancf.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.quanlyquancf.DoiTuong.Bill;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class Database extends SQLiteAssetHelper {
    private  static  final String DB_Name= "ca_phe.db";
    private  static  final int DB_Ver=1;
    public static SQLiteDatabase db;
    public  Database(Context context)
    {
        super(context,DB_Name,null,DB_Ver);
    }
    public ArrayList<Bill> getBill()
    {
        db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelct = {"ProductID","ProductName","Quantity","Price"};
        String sqlTable = "BillInfo";

        qb.setTables(sqlTable);
        Cursor c=  qb.query(db,sqlSelct,null,null,null,null,null);

        final  ArrayList<Bill> result= new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                    result.add(new Bill(c.getString(c.getColumnIndex("ProductID")),
                            c.getString(c.getColumnIndex("ProductName")),
                                    c.getString(c.getColumnIndex("Quantity")),
                                            c.getString(c.getColumnIndex("Price"))
                    ));
            }while (c.moveToNext());
        }
        return result;
    }
    public  void AddToCart(Bill bill)
    {
        db = getReadableDatabase();
        String query = String.format("INSERT INTO Bill(ProductID,ProductName,Quantity,Price) VALUES ('%s','%s','%s','%s');",

                bill.getProductID(),
                bill.getProductName(),
                "1",
             //   bill.getQuantity(),
                bill.getPrice()
                );
        db.execSQL(query);
    }
    public  void ClearBill()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Bill");
        db.execSQL(query);
    }
}
