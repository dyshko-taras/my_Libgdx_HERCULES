package com.trafficx.game.g1.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LabelN {
    public static int getNum(Label label) {
        return Integer.parseInt(String.valueOf(label.getText()));
    }

    public static void setNum(Label label, int num) {
        if (num < 0) num = 0;
        label.setText(num);
    }

    public static void subtractNum(Label label, int num) {
        int temp = Integer.parseInt(String.valueOf(label.getText())) - num;
        if (temp < 0) temp = 0;
        label.setText(temp);
    }

    public static void addNum(Label label, int num) {
        int temp = Integer.parseInt(String.valueOf(label.getText())) + num;
        label.setText(temp);
    }
}
