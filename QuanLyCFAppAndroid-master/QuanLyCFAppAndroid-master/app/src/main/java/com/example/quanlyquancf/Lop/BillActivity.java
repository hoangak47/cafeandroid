package com.example.quanlyquancf.Lop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quanlyquancf.R;
import android.os.Bundle;

import com.example.quanlyquancf.Adapter.BillAdapter;
import com.example.quanlyquancf.DoiTuong.Bill;

import java.util.ArrayList;

public class BillActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        init();
    }
    private void init()
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.lstbill);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ArrayList<Bill> list = new ArrayList<>();
        list.add(new Bill("1","as","2","2"));

        BillAdapter billAdapter= new BillAdapter(list,getApplicationContext());
        recyclerView.setAdapter(billAdapter);
    }
}