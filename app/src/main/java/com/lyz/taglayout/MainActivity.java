package com.lyz.taglayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.lyz.taglayoutlib.OnTagClickListener;
import com.lyz.taglayoutlib.Tag;
import com.lyz.taglayoutlib.TagLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author liyanze
 */
public class MainActivity extends AppCompatActivity {

    private TagLayout tagLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagLayout = findViewById(R.id.tagLayout);

        init();

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

    }

    private void init() {
        ArrayList<Tag> list = new ArrayList<>();
        Random random = new Random();
//        int size = random.nextInt(200) + 1;
        int size = 200;
        System.out.println("size:" + size);
        for (int i = 0; i < size; i++) {
            Tag tag = Tag.newTag(this, i);
            tag.setText("aaa" + String.valueOf(i * 13));
            list.add(tag);
        }

        tagLayout.initTag(list);
        tagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onClick(Tag tag) {
                Toast.makeText(MainActivity.this, "position:" + tag.position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
