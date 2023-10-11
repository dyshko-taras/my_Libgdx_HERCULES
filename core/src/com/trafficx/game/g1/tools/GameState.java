package com.trafficx.game.g1.tools;

import com.badlogic.gdx.utils.Timer;

public class GameState {

    public static final int MENU = 0;
    public static final int WAIT = 1;
    public static final int SPINNING = 2;
    public static final int AUTO_SPIN = 3;

    private static int currentState;

    public static void setState(int newState) {
        currentState = newState;
    }

    public static int getState() {
        return currentState;
    }

    private static int numPrint = 0;

    public static void printState() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (currentState == 0) System.out.println(String.format( "%d MENU", numPrint));
                else if (currentState == 1) System.out.println(String.format( "%d WAIT", numPrint));
                else if (currentState == 2) System.out.println(String.format( "%d SPIN", numPrint));
                else if (currentState == 3) System.out.println(String.format( "%d AUTO_SPIN", numPrint));
                numPrint++;
            }
        }, 0, 0.5f);
    }
}
