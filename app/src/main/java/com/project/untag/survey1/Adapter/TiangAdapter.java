package com.project.untag.survey1.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.untag.survey1.FormTiang;
import com.project.untag.survey1.HomeActivity;
import com.project.untag.survey1.LampuActivity;
import com.project.untag.survey1.MainActivity;
import com.project.untag.survey1.Model.Lampu;
import com.project.untag.survey1.Model.Tiang;
import com.project.untag.survey1.R;
import com.project.untag.survey1.TiangActivity;

import java.util.ArrayList;
import java.util.List;

public class TiangAdapter extends RecyclerView.Adapter<TiangAdapter.TiangViewHolder>{

    private final Context context;
    private List<Tiang> dataList;
    public static int position;

    LayoutInflater inflater;
    public TiangAdapter(List<Tiang> dataList,Context context) {
        this.dataList = dataList;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public TiangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.item_tiang, parent, false);



        TiangViewHolder viewHolder=new TiangViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TiangViewHolder holder, int position) {
        holder.txtNmTiang.setText(dataList.get(position).getIDTiang());
        holder.txtKeteranganTiang.setText(dataList.get(position).getStatusTiang());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class TiangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNmTiang, txtKeteranganTiang;

        public TiangViewHolder(View itemView) {
            super(itemView);
            txtNmTiang = (TextView) itemView.findViewById(R.id.txtNmTiang);
            txtKeteranganTiang = (TextView) itemView.findViewById(R.id.txtKeteranganTiang);
            position = getAdapterPosition();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "asdasd", Toast.LENGTH_SHORT).show();
            final Context context = view.getContext();



            final int clickPosition = getAdapterPosition();

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_tiang);
            dialog.setTitle("Dialog Box");
            Button btnEdit = (Button) dialog.findViewById(R.id.btnEditTiang);
            Button btnShowLampu  = (Button) dialog.findViewById(R.id.btnShowLampu);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final Intent intent = new Intent(context, FormTiang.class);
                    Tiang objTiang = dataList.get(clickPosition);   // get clicked new object from news(news is an ArrayList)
                    Gson gson = new Gson();
                    intent.putExtra("objTiang", gson.toJson(objTiang).toString());
                    context.startActivity(intent);
                }
            });

            btnShowLampu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final Intent intent = new Intent(context, LampuActivity.class);
                    Tiang objTiang = dataList.get(clickPosition);   // get clicked new object from news(news is an ArrayList)
                    Gson gson = new Gson();
                    intent.putExtra("objTiang", gson.toJson(objTiang).toString());
                    context.startActivity(intent);
                }
            });
            dialog.show();





        }
    }
}
