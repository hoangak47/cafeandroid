package com.example.quanlyquancf.Lop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancf.Adapter.FoodAdapter;
import com.example.quanlyquancf.DoiTuong.Food;
import com.example.quanlyquancf.DoiTuong.TableBan;
import com.example.quanlyquancf.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    DatabaseReference food;
    ArrayList<Food> arrFood=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_food);


        food = FirebaseDatabase.getInstance().getReference();

        GetAllFood(new FirebaseCallBack() {
            @Override
            public void onCallBack(ArrayList<Food> list) {
                arrFood = list;
                //     SetTable();
                init();
            }
        });

    }
    private void init()
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        FoodAdapter shopAdapter= new FoodAdapter(arrFood,getApplicationContext());
        recyclerView.setAdapter(shopAdapter);
    }

    private void KhoiTaoDatabase()
    {
        food.child("Food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Food f =dataSnapshot.getValue(Food.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void GetAllFood(final FoodActivity.FirebaseCallBack firebaseCallBack)
    {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    Food tam = ds.getValue(Food.class);
                    arrFood.add(tam);
                }
                firebaseCallBack.onCallBack(arrFood);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Lỗi kết nối mạng",Toast.LENGTH_LONG).show();
            }
        };
        food.child("Food").addListenerForSingleValueEvent(valueEventListener);
    }
    private interface  FirebaseCallBack{

        void onCallBack(ArrayList<Food> list);
    }
}