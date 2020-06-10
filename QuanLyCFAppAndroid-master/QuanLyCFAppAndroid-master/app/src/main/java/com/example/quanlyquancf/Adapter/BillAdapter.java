package com.example.quanlyquancf.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyquancf.DoiTuong.Bill;

import java.util.ArrayList;
import com.example.quanlyquancf.R;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    ArrayList<Bill> datashops;
    Context context;

    public BillAdapter(ArrayList<Bill> datashops, Context context) {
        this.datashops = datashops;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView =layoutInflater.inflate(R.layout.item_bill,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mon.setText(datashops.get(position).getProductName());
        holder.soluong.setText(datashops.get(position).getQuantity());
        holder.gia.setText(datashops.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return datashops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mon,soluong,gia,tongtien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            mon=(TextView)itemView.findViewById(R.id.txtbillname);
            soluong=(TextView)itemView.findViewById(R.id.txtbillsoluong);
            gia=(TextView)itemView.findViewById(R.id.txtbillgia);

        }

    }
}
