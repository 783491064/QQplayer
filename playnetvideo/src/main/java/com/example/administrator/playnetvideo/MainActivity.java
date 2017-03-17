package com.example.administrator.playnetvideo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @butterknife.Bind(R.id.bt)
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);
    }

    @butterknife.OnClick(R.id.bt)
    public void onClick() {
        Intent intent =new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.parse("http://192.168.78.23:8080/oppo.mp4"),"video/*");
        startActivity(intent);




    }
}
