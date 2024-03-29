package com.example.dennis.lunoticeboard;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PdfAdapter extends ArrayAdapter<Pdf>
{
    Activity activity;
    int layoutResourceId;
    ArrayList<Pdf> data=new ArrayList<Pdf>();
    Pdf pdf;

    public PdfAdapter(Activity activity, int layoutResourceId, ArrayList<Pdf> data) {
        super(activity, layoutResourceId, data);
        this.activity=activity;
        this.layoutResourceId=layoutResourceId;
        this.data=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        PdfHolder holder=null;
        if(row==null)
        {
            LayoutInflater inflater=LayoutInflater.from(activity);
            row=inflater.inflate(layoutResourceId,parent,false);
            holder=new PdfHolder();
            holder.textViewName= (TextView) row.findViewById(R.id.textViewName);
            holder.textViewUrl= (TextView) row.findViewById(R.id.textViewUrl);
            holder.Author =(TextView) row.findViewById(R.id.author);
            holder.Department =(TextView) row.findViewById(R.id.department);
            holder.School =(TextView) row.findViewById(R.id.schl);
            holder.Time =(TextView) row.findViewById(R.id.time);
            row.setTag(holder);
        }
        else
        {
            holder= (PdfHolder) row.getTag();
        }

        pdf = data.get(position);
        holder.textViewName.setText(pdf.getName());
        holder.textViewUrl.setText(pdf.getUrl());
        holder.Author.setText(pdf.getAuthor());
        holder.Department.setText(pdf.getDepartment());
        holder.School.setText(pdf.getSchool());
        holder.Time.setText(pdf.getTime());
        return row;
    }


    class PdfHolder
    {
        TextView textViewName,textViewUrl,Author,Department,School,Time;
    }

}
