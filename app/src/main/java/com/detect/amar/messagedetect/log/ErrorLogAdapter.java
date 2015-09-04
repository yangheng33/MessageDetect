package com.detect.amar.messagedetect.log;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.detect.amar.common.DatetimeUtil;
import com.detect.amar.messagedetect.R;
import com.detect.amar.messagedetect.widget.SlideRecyclerView;
import com.detect.amar.messagedetect.widget.SlideView;

/**
 * Created by amar on 15/9/4.
 */
public class ErrorLogAdapter extends BaseSlideAdapter<ErrorLog> {

    public ErrorLogAdapter(Activity activity, SlideRecyclerView listView) {
        super(activity, listView);
    }

    private View.OnClickListener deleteItemOnclickListener;

    public void setDeleteItemOnclickListener(View.OnClickListener deleteItemOnclickListener) {
        this.deleteItemOnclickListener = deleteItemOnclickListener;
    }

    public void singleDelete(int position) {
        this.getData().remove(position);
        this.notifyItemRemoved(position);
        notifyItemRangeChanged(position, getData().size());
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new ErrorLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ErrorLogViewHolder) {
            ErrorLogViewHolder holder = (ErrorLogViewHolder) viewHolder;
            ErrorLog item = getData().get(position);

            holder.titleTxt.setText(item.getTitle());
            holder.detailTxt.setText(item.getLog());
            holder.dateTxt.setText(DatetimeUtil.longToDatetime(item.getDate()));
            holder.idTxt.setText("id:" + item.getId());
            holder.singleDeleteBtn.setTag(position);
            holder.slideView.setOnSlideListener(this);
        }
    }

    public class ErrorLogViewHolder extends RecyclerView.ViewHolder {

        TextView titleTxt;
        TextView dateTxt;
        CheckBox deleteChk;
        TextView detailTxt;
        SlideView slideView;
        TextView idTxt;
        Button singleDeleteBtn;

        public ErrorLogViewHolder(View itemView) {
            super(itemView);
            slideView = (SlideView) itemView;
            titleTxt = (TextView) itemView.findViewById(R.id.error_title);
            dateTxt = (TextView) itemView.findViewById(R.id.error_date);
            deleteChk = (CheckBox) itemView.findViewById(R.id.error_del);
            detailTxt = (TextView) itemView.findViewById(R.id.error_detail);
            idTxt = (TextView) itemView.findViewById(R.id.error_id);
            singleDeleteBtn = (Button) itemView.findViewById(R.id.delete_single);

            if (deleteItemOnclickListener != null) {
                singleDeleteBtn.setOnClickListener(deleteItemOnclickListener);
            }
        }
    }
}
