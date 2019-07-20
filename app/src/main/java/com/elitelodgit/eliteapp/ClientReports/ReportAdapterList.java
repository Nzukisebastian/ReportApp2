package com.elitelodgit.eliteapp.ClientReports;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import com.bumptech.glide.request.RequestOptions;
import com.elitelodgit.eliteapp.R;

import java.util.List;

public class ReportAdapterList extends RecyclerView.Adapter<ReportAdapterList.HolderItem> {

    List<ReportModelList> mListItem;
    Context context;
    public ReportAdapterList(List<ReportModelList> mListItem, Context context) {
        this.mListItem = mListItem;
        this.context = context;}

    @Override
    public HolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.report_layout,parent,false);
        HolderItem holder=new HolderItem(layout);
        return holder;
    }
    @Override
    public void onBindViewHolder(final HolderItem holder, final int position) {
        final ReportModelList mlist=mListItem.get(position);
        holder.nature.setText(mlist.getNature());
        holder.date.setText(mlist.getDate());
        holder.time.setText(mlist.getTime());
        holder.place.setText(mlist.getPlace());
        //holder.parties.setText(mlist.getParties());
        //holder.insurance.setText(mlist.getInsurance());
        //holder.policy.setText(mlist.getPolicy());
        //holder.reference.setText(mlist.getReference());
        /*loading image*/
       //Glide.with(context).load(mlist.getImage()).transition(withCrossFade()).into(holder.thubnail);
        /*loading image*/
        //Glide.with(context).load(mlist.getImage()).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thubnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Full_Details.class);
                intent.putExtra("nature",mlist.getNature());
                intent.putExtra("date",mlist.getDate());
                intent.putExtra("time",mlist.getTime());
                intent.putExtra("place",mlist.getPlace());
                intent.putExtra("parties",mlist.getParties());
                intent.putExtra("insurance",mlist.getInsurance());
                intent.putExtra("policy",mlist.getPolicy());
                intent.putExtra("reference",mlist.getReference());
                intent.putExtra("image",mlist.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public class HolderItem extends RecyclerView.ViewHolder{
        ImageView thubnail;
        TextView nature,date,time,place;
        CardView cardView;
        public ImageView imgs;

        public HolderItem(View v) {
            super(v);
            nature=(TextView) v.findViewById(R.id.report_nature);
            time=(TextView) v.findViewById(R.id.report_time);
            place=(TextView) v.findViewById(R.id.report_place);
            cardView=(CardView)v.findViewById(R.id.cardview);
        }
    }
}
