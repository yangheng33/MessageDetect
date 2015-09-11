package com.detect.amar.messagedetect.log;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.detect.amar.common.DatetimeUtil;
import com.detect.amar.common.ResourcesUtil;
import com.detect.amar.messagedetect.R;
import com.detect.amar.messagedetect.db.DataBaseManager;
import com.detect.amar.messagedetect.widget.SlideRecyclerView;
import com.detect.amar.messagedetect.widget.SpacesItemDecoration;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ErrorLogActivity extends AppCompatActivity {

    @Bind(R.id.error_edit)
    TextView editTxt;
    @Bind(R.id.error_select_all)
    TextView selectAllTxt;
    @Bind(R.id.error_multi_delete)
    TextView multiDeleteTxt;
    @Bind(R.id.error_from)
    TextView fromDateTxt;
    @Bind(R.id.error_to)
    TextView toDateTxt;
    @Bind(R.id.error_query)
    TextView queryTxt;

    SlideRecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ErrorLogAdapter errorLogAdapter;
    List<ErrorLog> errorLogList = null;

    boolean isEdit = false;
    boolean isSelectAll = false;

    @OnClick(R.id.error_multi_delete)
    void deleteChecked() {
        if (errorLogList != null && errorLogList.size() > 0) {
            Iterator<ErrorLog> errorLogIterator = errorLogList.iterator();
            while (errorLogIterator.hasNext()) {
                ErrorLog errorLog = errorLogIterator.next();
                if (errorLog.isChecked()) {
                    try {
                        errorLogIterator.remove();
                        DataBaseManager.getHelper().getErrorLogDAO().delete(errorLog);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            errorLogAdapter.setData(errorLogList);
        }
    }

    @OnClick(R.id.error_edit)
    void clickEditTxt() {
        if (isEdit) {
            editTxt.setText(ResourcesUtil.getString(R.string.edit));
            isEdit = false;
            errorLogAdapter.setIsChooseable(false);
            selectAllTxt.setVisibility(View.GONE);
            multiDeleteTxt.setVisibility(View.GONE);
        } else {
            editTxt.setText(ResourcesUtil.getString(R.string.cancel));
            isEdit = true;
            errorLogAdapter.setIsChooseable(true);
            errorLogAdapter.shrink();
            selectAllTxt.setVisibility(View.VISIBLE);
            multiDeleteTxt.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.error_select_all)
    void clickSelectAll() {
        isSelectAll = !isSelectAll;
        setSelected(isSelectAll);
        errorLogAdapter.setData(errorLogList);
    }

    void setSelected(boolean selected) {
        if (errorLogList != null && errorLogList.size() > 0) {
            for (ErrorLog errorLog : errorLogList) {
                errorLog.setIsChecked(selected);
            }
        }
    }

    @OnClick(R.id.error_query)
    void clickQuery() {
        long startDate = DatetimeUtil.dateToLong(fromDateTxt.getText().toString() + " " + DatetimeUtil.DayStart);
        long endDate = DatetimeUtil.dateToLong(toDateTxt.getText().toString() + " " + DatetimeUtil.DayEnd);

        try {
            errorLogList = DataBaseManager.getHelper().getErrorLogDAO().queryBuilder().orderBy("id", false).where().between("date", startDate, endDate).query();
            errorLogAdapter.setData(errorLogList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_log);
        ButterKnife.bind(this);

        initRecyclerViewList();

        initDate();
    }

    public void initDate() {
        fromDateTxt.setText(DatetimeUtil.getDurrentDate());
        toDateTxt.setText(DatetimeUtil.getDurrentDate());
    }


    private void initRecyclerViewList() {

        recyclerView = (SlideRecyclerView) findViewById(R.id.slide_recycle_list);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        errorLogAdapter = new ErrorLogAdapter(this, recyclerView);
        errorLogAdapter.setDeleteItemOnclickListener(singleDeleteListener);
        recyclerView.setAdapter(errorLogAdapter);

        try {
            errorLogList = DataBaseManager.getHelper().getErrorLogDAO().queryBuilder().limit(100L).orderBy("id", false).query();
            errorLogAdapter.setData(errorLogList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener singleDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                int position = (int) v.getTag();
                DataBaseManager.getHelper().getErrorLogDAO().deleteById(errorLogList.get(position).getId());
                errorLogAdapter.singleDelete(position);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
