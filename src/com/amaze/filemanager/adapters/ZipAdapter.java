/*
 * Copyright (C) 2014 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>
 *
 * This file is part of Amaze File Manager.
 *
 * Amaze File Manager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.amaze.filemanager.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amaze.filemanager.R;
import com.amaze.filemanager.fragments.ZipViewer;
import com.amaze.filemanager.services.asynctasks.ZipExtractTask;
import com.amaze.filemanager.services.asynctasks.ZipHelperTask;
import com.amaze.filemanager.utils.Futils;
import com.pkmmte.view.CircularImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipAdapter extends ArrayAdapter<ZipEntry> {
    Context c;
    Drawable folder, unknown;
    ArrayList<ZipEntry> enter;
    ZipViewer zipViewer;
    StringBuilder stringBuilder1;

    public ZipAdapter(Context c, int id, ArrayList<ZipEntry> enter, ZipViewer zipViewer) {
        super(c, id, enter);
        this.enter = enter;
        this.c = c;
        folder = c.getResources().getDrawable(R.drawable.ic_grid_folder_new);
        unknown = c.getResources().getDrawable(R.drawable.ic_doc_generic_am);
        this.zipViewer = zipViewer;
    }
    private class ViewHolder {
        CircularImageView viewmageV;
        ImageView imageView,apk;
        ImageView imageView1;
        TextView txtTitle;
        TextView txtDesc;
        TextView date;
        TextView perm;
        View rl;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ZipEntry rowItem = enter.get(position);

        View view = convertView;
        final int p = position;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) c
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.rowlayout, parent, false);
            final ViewHolder vholder = new ViewHolder();

            vholder.txtTitle = (TextView) view.findViewById(R.id.firstline);
            vholder.viewmageV = (CircularImageView) view.findViewById(R.id.cicon);
            vholder.imageView = (ImageView) view.findViewById(R.id.icon);
            vholder.rl = view.findViewById(R.id.second);
            vholder.perm = (TextView) view.findViewById(R.id.permis);
            vholder.date = (TextView) view.findViewById(R.id.date);
            vholder.txtDesc = (TextView) view.findViewById(R.id.secondLine);
            vholder.apk=(ImageView)view.findViewById(R.id.bicon);
            view.setTag(vholder);

        }
        final ViewHolder holder = (ViewHolder) view.getTag();


        final StringBuilder stringBuilder = new StringBuilder(rowItem.getName());
        if (rowItem.isDirectory()) {
            stringBuilder.deleteCharAt(rowItem.getName().length() - 1);
        try {
            holder.txtTitle.setText(stringBuilder.toString().substring(stringBuilder.toString().lastIndexOf("/") + 1));
        }catch (Exception e)
        {
            holder.txtTitle.setText(rowItem.getName().substring(0, rowItem.getName().lastIndexOf("/")));
        }} else {
                holder.txtTitle.setText(rowItem.getName().substring(rowItem.getName().lastIndexOf("/")+1));
            holder.txtDesc.setText(new Futils().readableFileSize(rowItem.getSize()));
        }
        GradientDrawable gradientDrawable = (GradientDrawable) holder.imageView.getBackground();
        if(zipViewer.coloriseIcons)gradientDrawable.setColor(Color.parseColor("#757575"));
        else gradientDrawable.setColor(Color.parseColor(zipViewer.skin));
        holder.date.setText(new Futils().getdate(rowItem.getTime(),"MMM dd, yyyy",zipViewer.year));
        if (rowItem.isDirectory()) {
            holder.imageView.setImageDrawable(folder);
        } else {
            holder.imageView.setImageDrawable(unknown);
        }
        holder.rl.setBackgroundResource(R.drawable.listitem1);
        holder.rl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View p1) {

                if (rowItem.isDirectory()) {

                    new ZipHelperTask(zipViewer, 1, stringBuilder.toString()).execute(zipViewer.f);

                } else {
                    String parentLength = new File(rowItem.getName()).getParent();
                    stringBuilder1 = new StringBuilder(rowItem.getName());
                    stringBuilder1.delete(0, parentLength.length()+1);
                    File file = new File(zipViewer.f.getParent() + "/" + stringBuilder1.toString());
                    zipViewer.files.clear();
                    zipViewer.files.add(0, file);

                    try {
                        ZipFile zipFile = new ZipFile(zipViewer.f);
                        new ZipExtractTask(zipFile, zipViewer.f.getParent(), zipViewer, stringBuilder1.toString()).execute(rowItem);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }
}
