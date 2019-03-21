package com.lyz.taglayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.lyz.taglayout.tag.OnTagClickListener;
import com.lyz.taglayout.tag.Tag;
import com.lyz.taglayout.tag.TagLayout;

import java.util.ArrayList;

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

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

    }

    private void init(){
        ArrayList<Tag> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
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
