package com.demo.newfeed.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class RandomColors {

    public int getColors() {
        Stack<Integer> colors = new Stack<>();
        Stack<Integer> recycle = new Stack<>();
        recycle.addAll(Arrays.asList(
                0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff673ab7,
                0xff3f51b5, 0xff2196f3, 0xff03a9f4, 0xff00bcd4,
                0xffffeb3b, 0xffffc107, 0xffff9800, 0xffff5722
                )
        );
        while (!recycle.isEmpty())
            colors.push(recycle.pop());
        Collections.shuffle(colors);
        Integer c = colors.pop();
        recycle.push(c);
        return c;
    }

    public int getColorsString(String getColor) {
        Stack<Integer> colors = new Stack<>();
        Stack<Integer> recycle = new Stack<>();
        recycle.addAll(Arrays.asList(
                0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff673ab7,
                0xff3f51b5, 0xff2196f3, 0xff03a9f4, 0xff00bcd4,
                0xffffeb3b, 0xffffc107, 0xffff9800, 0xffff5722
                )
        );
        while (!recycle.isEmpty())
            colors.push(recycle.pop());
        Collections.shuffle(colors);
        Integer c = colors.pop();
        recycle.push(c);
        return c;
    }
}
