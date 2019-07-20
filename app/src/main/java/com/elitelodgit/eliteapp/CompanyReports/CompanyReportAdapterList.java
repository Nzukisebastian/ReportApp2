package com.elitelodgit.eliteapp.CompanyReports;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elitelodgit.eliteapp.ClientProfile.AdapterList;
import com.elitelodgit.eliteapp.ClientProfile.ModelList;
import com.elitelodgit.eliteapp.R;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CompanyReportAdapterList extends RecyclerView.Adapter<CompanyReportAdapterList.HolderItem>{

    List<CompanyReportModelList> mListItem;
    Context context;
    public CompanyReportAdapterList(List<CompanyReportModelList> mListItem, Context context) {
        this.mListItem = mListItem;
        this.context = context;}

    @Override
    public HolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.company_reports_layout,parent,false);
        HolderItem holder=new HolderItem(layout);
        return holder;
    }
    @Override
    public void onBindViewHolder(final HolderItem holder, final int position) {
        final CompanyReportModelList mlist=mListItem.get(position);
        holder.scene.setText(mlist.getScene());
        holder.executive.setText(mlist.getExecutive());
        holder.evidence.setText(mlist.getEvidence());
        holder.findings.setText(mlist.getFindings());
        holder.document.setText(mlist.getDocument());
        holder.subject.setText(mlist.getSubject());
        holder.observation.setText(mlist.getObservation());
        holder.conclusion.setText(mlist.getConclusion());
        holder.recommendation.setText(mlist.getRecommendation());
        /*loading image*/
        Glide.with(context).load(mlist.getImage()).transition(withCrossFade()).into(holder.thubnail);
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public class HolderItem extends RecyclerView.ViewHolder{
        ImageView thubnail;
        TextView scene,executive,evidence,findings,document,subject,observation,conclusion,recommendation;
        CardView cardView;
        public ImageView imgs;

        public HolderItem(View v) {
            super(v);
            thubnail=(ImageView)v.findViewById(R.id.companyreport_image);
            scene=(TextView) v.findViewById(R.id.sceneanlysis);
            executive=(TextView) v.findViewById(R.id.executivesummary);
            evidence=(TextView) v.findViewById(R.id.summaryevidence);
            findings=(TextView) v.findViewById(R.id.findings);
            document=(TextView) v.findViewById(R.id.documentanalysis);
            subject=(TextView) v.findViewById(R.id.subjectmatter);
            observation=(TextView) v.findViewById(R.id.analyticalobservation);
            conclusion=(TextView) v.findViewById(R.id.conclusionreability);
            recommendation=(TextView) v.findViewById(R.id.riskrecommendation);
            cardView=(CardView)v.findViewById(R.id.cardview);
        }
    }
}
