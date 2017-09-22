package com.example.bargraph.watcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bargraph.R;

import java.util.Random;

public class WatcherActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mTextView;
    private String[] names = {"apple", "banana", "orange", "melon", "bread"};
    private int[] nums = {100, 20, 33, 66, 1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watcher);
        initView();
        setOnclick();
    }

    private void setOnclick() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Food food = new Food();
                Man man = new Man(new TransmitListener() {
                    @Override
                    public void transmit(Object object) {
                        if (object != null) {
                            Food f = (Food) object;
                            mTextView.append("Man: 水果降价啦  " + f.getPrice() + "还剩下" + food.getNum() + "个\n");
                        }
                    }
                });
                Woman woman = new Woman(new TransmitListener() {
                    @Override
                    public void transmit(Object object) {
                        if (object != null) {
                            Food f = (Food) object;
                            mTextView.append("Woman: 水果降价啦  " + f.getPrice() + "还剩下" + food.getNum() + "个，快来抢啊\n");
                        }
                    }
                });
                Random r = new Random();
                int i = r.nextInt(4);
                food.setNum(nums[i]);
                food.setPrice(names[i]);
                food.addWatcher(man);
                food.addWatcher(woman);

                food.notifyChanged();

            }
        });
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.btn_watch);
        mTextView = (TextView) findViewById(R.id.tv_watch);
    }
}
