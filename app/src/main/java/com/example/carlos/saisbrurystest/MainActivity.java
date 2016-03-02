package com.example.carlos.saisbrurystest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements IMainView {

    MainPresenter mPresenter;
    ListView mListView;
    TextView mTotal;
    MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Give to the Presenter a reference to the View
        mPresenter = new MainPresenter(this,getApplicationContext());

        mListView = (ListView) findViewById(R.id.listView);
        mTotal = (TextView) findViewById(R.id.textView_total);

        mAdapter = new MyAdapter(getApplicationContext(), mPresenter.getData());
        mListView.setAdapter(mAdapter);
        mTotal.setText(mPresenter.getTotalItems());

    }
}
