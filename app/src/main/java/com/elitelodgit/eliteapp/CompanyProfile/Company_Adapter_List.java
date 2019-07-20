package com.elitelodgit.eliteapp.CompanyProfile;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elitelodgit.eliteapp.ClientProfile.AdapterList;
import com.elitelodgit.eliteapp.ClientProfile.ModelList;
import com.elitelodgit.eliteapp.R;

import java.util.List;

public class Company_Adapter_List extends RecyclerView.Adapter<Company_Adapter_List.HolderItem> {

    List<Company_Model_List> mListItem;
    Context context;
    public Company_Adapter_List(List<Company_Model_List> mListItem, Context context) {
        this.mListItem = mListItem;
        this.context = context;}

    @Override
    public HolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.company_profile_layout,parent,false);
       HolderItem holder=new HolderItem(layout);
        return holder;
    }
    @Override
    public void onBindViewHolder(final HolderItem holder, final int position) {
        final Company_Model_List mlist=mListItem.get(position);
        holder.cname.setText(mlist.getCompanyname());
        holder.email.setText(mlist.getEmail());
        holder.phone.setText(mlist.getTelno());
        holder.reg.setText(mlist.getRegno());
        /*loading image*/
        // Glide.with(context).load(mlist.getImg()).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thubnail);

    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public class HolderItem extends RecyclerView.ViewHolder{
        ImageView thubnail;
        TextView cname,email,phone,reg;
        CardView cardView;
        public ImageView imgs;

        public HolderItem(View v) {
            super(v);
            //name=(ImageView)v.findViewById(R.id.profilename);
            cname=(TextView) v.findViewById(R.id.companyname);
            email=(TextView) v.findViewById(R.id.companyemail);
            phone=(TextView) v.findViewById(R.id.companytelno);
            reg=(TextView) v.findViewById(R.id.companyreg);
            cardView=(CardView)v.findViewById(R.id.cardview);
        }
    }

}
