package com.detect.amar.messagedetect.log;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.detect.amar.messagedetect.widget.SlideRecyclerView;
import com.detect.amar.messagedetect.widget.SlideView;

import java.util.List;

/**
 * Created by SAM on 2015/8/2.
 */
public abstract class BaseSlideAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements  SlideView.OnSlideListener {
    private List<T> _data;
    private LayoutInflater _layoutInflater;
    private Activity _activity;
    private SlideRecyclerView _listView;

    private SlideView mLastSlideViewWithStatusOn;

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();
        }

        if (status == SLIDE_STATUS_ON) {
            mLastSlideViewWithStatusOn = (SlideView) view;
        }
    }

    public void shrink() {
        if (mLastSlideViewWithStatusOn != null) {
            mLastSlideViewWithStatusOn.shrink();
        }
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
        SlideView slideView = new SlideView(_activity, _listView.getSlideViewLayoutResId(), _listView.getSlideWidth());
        View itemView = _layoutInflater.inflate(_listView.getRowViewLayoutResId(), null);
        slideView.setContentView(itemView);
        return getViewHolder(slideView);
    }

    public abstract RecyclerView.ViewHolder getViewHolder(View view);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
    }

    @Override
    public int getItemCount() {
        return _data == null ? 0 : _data.size();
    }

    protected boolean _isChooseable = false;

    public boolean isChooseable() {
        return _isChooseable;
    }

    public void setIsChooseable(boolean isChooseable) {
        if (_isChooseable != isChooseable) {
            _isChooseable = isChooseable;
            notifyDataSetChanged();
        }
    }
}
