package com.elitelodgit.eliteapp.ClientProfile;

/**
 * Created by SEBASTIAN on 2/1/2018.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elitelodgit.eliteapp.R;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.HolderItem>{
    List<ModelList> mListItem;
    Context context;
    public AdapterList(List<ModelList> mListItem, Context context) {
        this.mListItem = mListItem;
        this.context = context;}

    @Override
    public HolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rows,parent,false);
        HolderItem holder=new HolderItem(layout);
        return holder;
    }
    @Override
    public void onBindViewHolder(final HolderItem holder, final int position) {
        final ModelList mlist=mListItem.get(position);
        holder.name.setText(mlist.getName());
        holder.email.setText(mlist.getEmail());
        holder.phone.setText(mlist.getPhone());
        /*loading image*/
       // Glide.with(context).load(mlist.getImg()).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thubnail);

    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public class HolderItem extends RecyclerView.ViewHolder{
        ImageView thubnail;
        TextView name,email,phone;
        CardView cardView;
        public ImageView imgs;

        public HolderItem(View v) {
            super(v);
            //name=(ImageView)v.findViewById(R.id.profilename);
            name=(TextView) v.findViewById(R.id.profilename);
            email=(TextView) v.findViewById(R.id.profilemail);
            phone=(TextView) v.findViewById(R.id.profilephone);
            cardView=(CardView)v.findViewById(R.id.cardview);
        }
    }
}

