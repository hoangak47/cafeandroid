package com.example.quanlyquancf.Lop;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancf.Adapter.FoodAdapter;
import com.example.quanlyquancf.Database.Database;
import com.example.quanlyquancf.DoiTuong.Bill;
import com.example.quanlyquancf.DoiTuong.Food;
import com.example.quanlyquancf.DoiTuong.TableBan;
import com.example.quanlyquancf.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity implements FoodAdapter.OnItemReClickListener {

    DatabaseReference food;
    ArrayList<Food> arrFood=new ArrayList<>();
    EditText txtSL;
    int idTable;
    RecyclerView recyclerView;
    ArrayList<String> arrSL = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_food);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen


        txtSL = (EditText)findViewById(R.id.soluong);
        food = FirebaseDatabase.getInstance().getReference();

        GetAllFood(new FirebaseCallBack() {
            @Override
            public void onCallBack(ArrayList<Food> list) {
                arrFood = list;
                //     SetTable();
                init();
            }
        });

        final Dialog Bill=new Dialog(this,R.style.AppTheme);
        Button bill= (Button)findViewById(R.id.bill);
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bill.setContentView(R.layout.bill);
                Bill.show();

            }
        });

    }
    private void init()
    {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


        FoodAdapter shopAdapter= new FoodAdapter(arrFood,getApplicationContext(),this);
        recyclerView.setAdapter(shopAdapter);
    }

    private void GetIDTable()
    {
        Intent it = getIntent();
        Bundle bundle = it.getBundleExtra("table");
        idTable = bundle.getInt("idTable");
    }
//    private void KhoiTaoDatabase()
//    {
//        food.child("Food").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Food f =dataSnapshot.getValue(Food.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
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

    @Override
    public void OnNoteClick(int position) {
    //    arrSL = GetQuantity();
        Database db = new Database(getBaseContext());
        db.AddToCart(new Bill(arrFood.get(position).getID(),
                arrFood.get(position).getName(),
                "1",
                arrFood.get(position).getPrice().toString()
        ));
        Toast.makeText(getApplicationContext(),"Thêm bill thành công",Toast.LENGTH_LONG).show();
    }

    public ArrayList<String> GetQuantity()
    {
        ArrayList<String> temp = new ArrayList<>();
        temp.clear();
        for (int i = 0; i<arrFood.size();i++)
        {
            View view = recyclerView.getChildAt(i);
            EditText tam = (EditText)view.findViewById(R.id.soluong);
            temp.add(tam.getText().toString());
        }
        return  temp;
    }
    private interface  FirebaseCallBack{

        void onCallBack(ArrayList<Food> list);
    }
}