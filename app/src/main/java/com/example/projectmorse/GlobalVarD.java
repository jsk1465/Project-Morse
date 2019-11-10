package com.example.projectmorse;

public class GlobalVarD {
    private static final GlobalVarD ourInstance = new GlobalVarD();
    public boolean wasShook = true;
    public static GlobalVarD getInstance() {
        return ourInstance;
    }

    private GlobalVarD() {
    }
}
