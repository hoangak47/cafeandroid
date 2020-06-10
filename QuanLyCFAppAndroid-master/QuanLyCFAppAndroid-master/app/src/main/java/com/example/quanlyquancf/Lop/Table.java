package com.example.quanlyquancf.Lop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlyquancf.DoiTuong.TableBan;
import com.example.quanlyquancf.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Table extends AppCompatActivity {

    TableLayout tb;
    private DrawerLayout drawer;
    DatabaseReference table;
    ArrayList<TableBan> banList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_table);

        Toolbar toolbar = findViewById(R.id.tlb1);
        table = FirebaseDatabase.getInstance().getReference();

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_table, null);
        tb = (TableLayout) findViewById(R.id.tb1);
        GetAllTable(new FirebaseCallBack() {

            @Override
            public void onCallBack(ArrayList<TableBan> list) {
                banList = list;
                SetTable();
            }
        });
     //   SetTable();

    }
    private void GetAllTable(final FirebaseCallBack firebaseCallBack)
    {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    TableBan tam = ds.getValue(TableBan.class);
                    banList.add(tam);
                }
                firebaseCallBack.onCallBack(banList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Lỗi kết nối mạng",Toast.LENGTH_LONG).show();
            }
        };
        table.child("Table").addListenerForSingleValueEvent(valueEventListener);
    }
    private void SetTable()
    {

        for(int i = 0;i<banList.size();i++) {
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    200, 1f);
            params.setMargins(10, 10, 10, 10);
            final int idTable = i;
            if (i % 2 == 0) {

                final Button myButton = new Button(this);
                myButton.setText(banList.get(i).getName());
                myButton.setHeight(80);
                myButton.setId(Integer.parseInt(banList.get(i).getID()));
                myButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bo_tron_nut_2, 0, 0, 0);
                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), "Bàn " + myButton.getId(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("id",idTable);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });

                final Button myButton2 = new Button(this);
                if (i + 1 < banList.size()) {

                    myButton2.setText(banList.get(i + 1).getName());
                    myButton2.setHeight(80);
                    myButton2.setId(Integer.parseInt(banList.get(i + 1).getID()));
                    myButton2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bo_tron_nut_2, 0, 0, 0);
                    myButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
                            Bundle b = new Bundle();
                            b.putInt("idTable",idTable);
                            intent.putExtra("table",b);
                            startActivity(intent);
                        }
                    });
                    myButton2.setLayoutParams(params);
                }
                myButton.setLayoutParams(params);

                TableRow tr1 = new TableRow(this);
                tr1.addView(myButton, params);
                if (i + 1 < banList.size()){ tr1.addView(myButton2, params);}
                tb.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            }
        }
    }

    private interface  FirebaseCallBack{

        void onCallBack(ArrayList<TableBan> list);
    }
}
