package com.detect.amar.messagedetect.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.detect.amar.messagedetect.Message;
import com.detect.amar.messagedetect.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageTransRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_trans_record);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.queryBtn)
    void clickQuery() {
        MessageDao messageDao = new MessageDao(this);

        List<Message> messageList = messageDao.findMessage(null);
        Toast.makeText(this, "query finish,find count:" + messageList.size(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.deleteBtn)
    void clickDeleteBtn() {

    }

    @OnClick(R.id.updateBtn)
    void clickUpdateBtn() {

    }

    @OnClick(R.id.insertBtn)
    void clickInsertBtn() {
//        Message message = new Message("0", "158", "136", "hello 中国", "2015-08-09 20:01:01", false, "2012-12-13 22:22:22", "", "2015-12-11 11:11:11");
//
//        MessageDao messageDao = new MessageDao(this);
//
//        messageDao.insert(message);
//        Toast.makeText(this, "insert finish", Toast.LENGTH_SHORT).show();
    }

}
