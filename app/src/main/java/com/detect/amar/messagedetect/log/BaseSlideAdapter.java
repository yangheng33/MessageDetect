package com.detect.amar.messagedetect.log;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.detect.amar.messagedetect.widget.SlideRecyclerView;
import com.detect.amar.messagedetect.widget.SlideView;

import java.util.List;

/**
 * Created by SAM on 2015/8/2.
 */
public abstract class BaseSlideAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<T> _data;
    private LayoutInflater _layoutInflater;
    private Activity _activity;
    private SlideRecyclerView _listView;


    public void delete()
    {

    }


    public BaseSlideAdapter(Activity activity, SlideRecyclerView listView) {
        _activity = activity;
        _layoutInflater = LayoutInflater.from(_activity);
        _listView = listView;
    }

    public void setData(List<T> data) {
        this._data = data;
        this.notifyDataSetChanged();
    }

    public List<T> getData() {
        return _data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        SlideView slideView = new SlideView(_activity,_listView.getSlideViewLayoutResId(),_listView.getSlideViewContentResId(),_listView.getSlideWidth());

        View itemView = _layoutInflater.inflate(_listView.getRowViewLayoutResId(), null);
        slideView.setContentView(itemView);
        //ContentViewHolder contentViewHolder = new ContentViewHolder(slideView);

        return getViewHolder(slideView);
    }

    public abstract RecyclerView.ViewHolder getViewHolder(View view);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

//        if (viewHolder instanceof ContentViewHolder) {
//            ContentViewHolder holder = (ContentViewHolder) viewHolder;
//
//            OddBetItem oddBetItem = getData().get(position);
//            holder.categoryTxt.setText(oddBetItem.getCategory());
//            holder.teamHomeTxt.setText(oddBetItem.getTeamHome());
//            holder.teamAwayTxt.setText(oddBetItem.getTeamAway());
//            holder.oddTxt.setText(oddBetItem.getOdd());
//            holder.oddTypeTxt.setText(oddBetItem.getOddType());
//            holder.betTeamTxt.setText(oddBetItem.getBetTeam());
//
//            if (_isChooseable) {
//                holder.deleteChk.setVisibility(View.VISIBLE);
//            } else {
//                holder.deleteChk.setVisibility(View.GONE);
//            }
//
//            holder.deleteChk.setTag(position);
//            holder.deleteChk.setChecked(oddBetItem.isSelected());
//            holder.deleteChk.setOnClickListener(this);
//
//            holder.deleteTxt.setOnClickListener(deleteItemListener);
//            holder.deleteTxt.setTag(position);
//            holder.slideView.setOnSlideListener((SlideView.OnSlideListener) _activity);
//        }
    }

    public View.OnClickListener deleteItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("deleteItemListener", "===>" + v.getTag().toString());
        }
    };

    @Override
    public void onClick(View view) {
        if (view instanceof CheckBox) {
//            CheckBox deleteChk = (CheckBox) view;
//            int clickedPos = (int) (deleteChk.getTag());
//            OddBetItem oddBetItem = getData().get(clickedPos);
//            boolean selected = !oddBetItem.isSelected();
//            oddBetItem.setIsSelected(selected);
        }
    }

    @Override
    public int getItemCount() {
        return _data == null ? 0 : _data.size();
    }

    private boolean _isChooseable = false;

    public boolean isChooseable() {
        return _isChooseable;
    }

    public void setIsChooseable(boolean isChooseable) {
        if (_isChooseable != isChooseable) {
            _isChooseable = isChooseable;
            notifyDataSetChanged();
        }
    }

//    public static class ContentViewHolder extends RecyclerView.ViewHolder {
//
//        TextView categoryTxt;
//        TextView teamHomeTxt;
//        TextView teamAwayTxt;
//        TextView oddTxt;
//        TextView oddTypeTxt;
//        TextView betTeamTxt;
//        CheckBox deleteChk;
//        TextView deleteTxt;
//        SlideView slideView;
//
//        public ContentViewHolder(View itemView) {
//            super(itemView);
//            slideView = (SlideView) itemView;
//            categoryTxt = (TextView) itemView.findViewById(R.id.parplay_item_team_category);
//            teamHomeTxt = (TextView) itemView.findViewById(R.id.parplay_item_team_home);
//            teamAwayTxt = (TextView) itemView.findViewById(R.id.parplay_item_team_away);
//            oddTxt = (TextView) itemView.findViewById(R.id.parplay_item_odd);
//            oddTypeTxt = (TextView) itemView.findViewById(R.id.parplay_item_odd_type);
//            betTeamTxt = (TextView) itemView.findViewById(R.id.parplay_item_bet_team);
//            deleteChk = (CheckBox) itemView.findViewById(R.id.parplay_item_choose);
//            deleteTxt = (TextView) itemView.findViewById(R.id.delete);
//        }
//    }
}
