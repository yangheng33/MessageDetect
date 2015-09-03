package com.detect.amar.messagedetect.log;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.detect.amar.messagedetect.R;
import com.detect.amar.messagedetect.widget.SlideRecyclerView;
import com.detect.amar.messagedetect.widget.SlideView;

/**
 * Created by amar on 15/9/4.
 */
public class ErrorLogAdapter<ErrorLog> extends BaseSlideAdapter{

    public ErrorLogAdapter(Activity activity, SlideRecyclerView listView) {
        super(activity, listView);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {

        return new ErrorLogViewHolder(view);

    }

    public static class ErrorLogViewHolder extends RecyclerView.ViewHolder {

        TextView titleTxt;
        TextView dateTxt;
        CheckBox deleteChk;
        TextView detailTxt;
        SlideView slideView;


        public ErrorLogViewHolder(View itemView) {
            super(itemView);
            slideView = (SlideView) itemView;
            titleTxt = (TextView) itemView.findViewById(R.id.error_title);
            dateTxt = (TextView) itemView.findViewById(R.id.error_date);
            deleteChk = (CheckBox) itemView.findViewById(R.id.error_del);
            detailTxt = (TextView) itemView.findViewById(R.id.error_detail);
        }
    }
}
