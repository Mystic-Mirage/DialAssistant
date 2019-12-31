package net.m10e.dialassistant;

class Globals {
    private static Globals instance;

    private boolean defaultChosen = true;

    private Globals() {}

    boolean getDefaultChosen() {
        return defaultChosen;
    }

    void setDefaultChosen(boolean value) {
        defaultChosen = value;
    }

    static synchronized Globals getInstance() {
        if (instance == null) {
            instance = new Globals();
        }

        return instance;
    }
}
