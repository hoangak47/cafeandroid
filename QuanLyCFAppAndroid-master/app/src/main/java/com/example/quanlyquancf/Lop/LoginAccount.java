package com.example.quanlyquancf.Lop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlyquancf.DoiTuong.TableBan;
import com.example.quanlyquancf.DoiTuong.User;
import com.example.quanlyquancf.MainActivity;
import com.example.quanlyquancf.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginAccount extends AppCompatActivity {

    Button btnDn;
    EditText txtUser,txtPass;
    DatabaseReference user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_login_account);
        AnhXa();
        btnDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(LoginAccount.this);
                progressDialog.setMessage("Xin chờ ...");
                progressDialog.show();

                user.child("User").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user_nv1  = dataSnapshot.child(txtUser.getText().toString()).getValue(User.class);

                            if(dataSnapshot.child(txtUser.getText().toString()).exists())
                            {
                                if(txtUser.getText().toString().equals(user_nv1.getLoginname()) && txtPass.getText().toString().equals(user_nv1.getPassword()) )
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Table.class);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(getApplicationContext(),"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_LONG).show();
                                   progressDialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Tài khoản không tồn tại",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    };

    private void AnhXa()
    {
        // Get database from firebase
        user = FirebaseDatabase.getInstance().getReference();

        txtUser = (EditText) findViewById(R.id.tass);
        txtPass = (EditText) findViewById(R.id.tass2);
        btnDn = (Button)findViewById(R.id.btnDN);

    }
}
