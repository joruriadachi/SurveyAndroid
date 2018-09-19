package com.project.untag.survey1.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.untag.survey1.Model.Lampu;
import com.project.untag.survey1.Model.LampuPJU;
import com.project.untag.survey1.Model.Tiang;
import com.project.untag.survey1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LampuAdapter extends RecyclerView.Adapter<LampuAdapter.LampuViewHolder> {

    private final Context context;
    private List<LampuPJU> dataList;

    LayoutInflater inflater;
    public LampuAdapter(List<LampuPJU> dataList, Context context) {
        this.dataList = dataList;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public LampuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.item_lampu, parent, false);

        LampuViewHolder viewHolder=new LampuViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LampuViewHolder holder, int position) {
        holder.tvKondisi.setText(dataList.get(position).getLampu().getJenisLampu().getNmJenisLampu());
        holder.tvWattVA.setText("Watt :"+dataList.get(position).getLampu().getWattLampu()+"VA : "+dataList.get(position).getLampu().getVALampu());

        Picasso.get()
                .load(dataList.get(position).getKondisiLampu())
                .placeholder(R.drawable.placeholder_square)
                .error(R.drawable.placeholder_square)
                // To fit image into imageView
                .fit()
                // To prevent fade animation
                .noFade()
                .into(holder.imgKondisi);

    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount: "+dataList.size());
        return (dataList != null) ? dataList.size() : 0;
    }

    public class LampuViewHolder extends RecyclerView.ViewHolder{
        private TextView tvKondisi, tvWattVA;
        private ImageView imgKondisi;

        public LampuViewHolder(View itemView) {
            super(itemView);
            tvKondisi = (TextView) itemView.findViewById(R.id.tvKondisi);
            tvWattVA = (TextView) itemView.findViewById(R.id.tvWattVA);
            imgKondisi = (ImageView) itemView.findViewById(R.id.imgKondisi);

        }
    }
}
