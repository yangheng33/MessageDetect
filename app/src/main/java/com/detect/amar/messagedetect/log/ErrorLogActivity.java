package com.detect.amar.messagedetect.log;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.detect.amar.common.ResourcesUtil;
import com.detect.amar.messagedetect.R;
import com.detect.amar.messagedetect.db.DataBaseManager;
import com.detect.amar.messagedetect.widget.SlideRecyclerView;
import com.detect.amar.messagedetect.widget.SlideView;
import com.detect.amar.messagedetect.widget.SpacesItemDecoration;

import java.sql.SQLException;
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

    @OnClick(R.id.error_edit)
    void clickEditTxt() {
        if (isEdit) {
            editTxt.setText(ResourcesUtil.getString(R.string.edit));
            isEdit = false;
            errorLogAdapter.setIsChooseable(false);
        } else {
            editTxt.setText(ResourcesUtil.getString(R.string.cancel));
            isEdit = true;
            errorLogAdapter.setIsChooseable(true);
            errorLogAdapter.shrink();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_log);
        ButterKnife.bind(this);

        initRecyclerViewList();
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
            errorLogList = DataBaseManager.getHelper().getErrorLogDAO().queryBuilder().orderBy("id", false).query();
            errorLogAdapter.setData(errorLogList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener singleDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            try {
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
