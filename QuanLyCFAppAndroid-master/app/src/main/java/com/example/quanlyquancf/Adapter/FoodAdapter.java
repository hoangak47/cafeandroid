package com.example.quanlyquancf.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancf.DoiTuong.Food;
import com.example.quanlyquancf.Lop.FoodActivity;
import com.example.quanlyquancf.Lop.LoginAccount;
import com.example.quanlyquancf.MainActivity;
import com.example.quanlyquancf.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    ArrayList<Food> datashops;
    Context context;
    int dem;

    public FoodAdapter(ArrayList<Food> datashops, Context context) {
        this.datashops = datashops;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row,parent,false);
        return new FoodAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.with(context).load(datashops.get(position).getImage()).into(holder.imageView);
        holder.textView.setText(datashops.get(position).getName());
        holder.textView2.setText(String.valueOf(datashops.get(position).getPrice()));
        holder.textView.setMaxLines(2);
        holder.textView.setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public int getItemCount() {
        return datashops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView2;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.hinh);
            textView= (TextView)itemView.findViewById(R.id.txtname);
            textView2= (TextView)itemView.findViewById(R.id.txtprice);


            final EditText sl=(EditText) itemView.findViewById(R.id.soluong);
            Button tang=(Button)itemView.findViewById(R.id.btntang);
            Button giam=(Button)itemView.findViewById(R.id.btngiam);
            sl.setText("1");
            tang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dem = Integer.parseInt(sl.getText() + "");
                    dem++;
                    sl.setText(dem+"");
                }
            });
            giam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dem > 1)
                    {
                        dem = Integer.parseInt(sl.getText() + "");
                        dem -=1 ;
                        sl.setText(dem + "");
                    }
                }
            });

//            final Dialog dialogbill=new Dialog(context,R.style.AppTheme);
//            Button bill = (Button)itemView.findViewById(R.id.bill);
//            bill.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogbill.setContentView(R.layout.bill);
//                    dialogbill.show();
//                }
//            });

        }
    }


}
