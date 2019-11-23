package com.lisn.aspectjdemo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView normal, single;
    Button button;
    int nornalSum = 0;
    int singleSum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        normal = findViewById(R.id.normal);
        single = findViewById(R.id.single);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                normal();
                single();
            }
        });
    }

    /**
     * 跳转到待收货页面
     */
    @CheckNet
    public void jumpWaitReceiving(View view) {
        Log.e("MainActivity", "jumpWaitReceiving: ");
    }

    /**
     * 跳转到我的钱包页面
     */
    @CheckNet
    public void jumpMineWallet(View view) {
        Log.e("MainActivity", "jumpMineWallet: ");
    }


    public void normal() {
        normal.setText("点击次数:" + nornalSum++ + "次");
    }

    @SingleClick
    public void single() {
        single.setText("防止多次点击:" + singleSum++ + "次");
    }

}
