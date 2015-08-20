package com.detect.amar.messagedetect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.title)
    TextView titleTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        titleTxt.setText("动态设置参数啦");
    }

    @OnClick(R.id.title)
    void clickTitle()
    {
        Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
    }

}
