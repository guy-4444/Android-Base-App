package com.guy.baseapplication.room;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.guy.baseapplication.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class Activity_Room extends AppCompatActivity {

    private MaterialButton room_BTN_add;
    private MaterialButton room_BTN_all;
    private MaterialTextView room_LBL_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        findViews();
        initViews();
    }

    private void initViews() {
        room_BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinDataHelper.getInstance().addLog(
                        "MyTag",
                        "New Message",
                        System.currentTimeMillis()
                );
            }
        });

        room_BTN_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLogs();
            }
        });

        room_LBL_all.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void refreshLogs() {
        MinDataHelper.getInstance().getAllLogs(new MinDataHelper.CallBack_Logs() {
            @Override
            public void dataReady(List<MinLog> minLogs) {
                String data = "minLogs size = " + minLogs.size();
                for (MinLog minLog : minLogs) {
                    data += "\n" + new SimpleDateFormat("dd/MM HH:mm:ss  ").format(minLog.time) + minLog.message;
                }
                String finalData = data;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        room_LBL_all.setText(finalData);
                    }
                });
            }
        });
    }

    private void findViews() {
        room_BTN_add = findViewById(R.id.room_BTN_add);
        room_BTN_all = findViewById(R.id.room_BTN_all);
        room_LBL_all = findViewById(R.id.room_LBL_all);
    }
}