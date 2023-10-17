package com.trafficx.game.menu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameData {
    private static final String PREFERENCES_NAME = "game_settings_hercules";
    private static final String NAME_0 = "name_0";
    private static final String NAME_1 = "name_1";
    private static final String NAME_2 = "name_2";


    private static Preferences preferences;

    public static void init() {
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
    }

    //name 0
    public static String getName0() {
        return preferences.getString(NAME_0, "");
    }

    public static void setName0(String name0) {
        preferences.putString(NAME_0, name0);
        preferences.flush();
    }

    //name 1
    public static String getName1() {
        return preferences.getString(NAME_1, "");
    }

    public static void setName1(String name1) {
        preferences.putString(NAME_1, name1);
        preferences.flush();
    }

    //name 2
    public static String getName2() {
        return preferences.getString(NAME_2, "");
    }

    public static void setName2(String name2) {
        preferences.putString(NAME_2, name2);
        preferences.flush();
    }


}

