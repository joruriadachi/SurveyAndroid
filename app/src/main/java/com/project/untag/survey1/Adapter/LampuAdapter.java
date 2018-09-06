package com.project.untag.survey1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.untag.survey1.Model.Lampu;
import com.project.untag.survey1.Model.Tiang;
import com.project.untag.survey1.R;

import java.util.List;

public class LampuAdapter extends RecyclerView.Adapter<LampuAdapter.LampuViewHolder> {

    private final Context context;
    private List<Lampu> dataList;

    LayoutInflater inflater;
    public LampuAdapter(List<Lampu> dataList, Context context) {
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
        holder.tvKondisi.setText(dataList.get(position).getKondisiLampu()+"("+dataList.get(position).getJenisLampu().getNmJenisLampu()+")");
        holder.tvWattVA.setText("Watt :"+dataList.get(position).getWattLampu()+"VA : "+dataList.get(position).getVALampu());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class LampuViewHolder extends RecyclerView.ViewHolder{
        private TextView tvKondisi, tvWattVA;

        public LampuViewHolder(View itemView) {
            super(itemView);
            tvKondisi = (TextView) itemView.findViewById(R.id.tvKondisi);
            tvWattVA = (TextView) itemView.findViewById(R.id.tvWattVA);

        }
    }
}
