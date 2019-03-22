package com.lyz.taglayoutlib;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liyanze
 * @create 2019/03/21
 * @Describe
 */
public class Line {
    private List<Child> list = new ArrayList<>();

    public void addChild(Child child) {
        list.add(child);
    }

    public void clear() {
        list.clear();
    }

    public List<Child> getChildren() {
        return list;
    }

    static class Child {
        View view;
        int left;
        int top;
        int right;
        int bottom;

        @Override
        public String toString() {
            return "Child{" +
                    "view=" + view +
                    ", left=" + left +
                    ", top=" + top +
                    ", right=" + right +
                    ", bottom=" + bottom +
                    '}';
        }
    }
}
