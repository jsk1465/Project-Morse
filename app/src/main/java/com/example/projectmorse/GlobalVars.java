package com.example.projectmorse;

public enum GlobalVars {
    na,
    dot,
    dash,
    space,
    comm,
    set,
    home;
    final private static long Dot_dur = 500;
    final private static long Dash_dur = 1000;
    final private static long Space_dur = 2000;
    final private static long Comm_dur = 3000;
    final private static long Set_dur = 4000;
    final private static long Home_dur = 5000;
    public Boolean wasShaked = true;
    public Boolean getShook(){
        return wasShaked;
    }
    public void setShook(Boolean shook){
        wasShaked = shook;
    }
    public static GlobalVars getType(long div){
        if(div<=Dot_dur){
            return dot;
        } else if(div<=Dash_dur){
            return dash;
        } else if(div<=Space_dur){
            return space;
        } else if(div<=Comm_dur){
            return comm;
        } else if(div<=Set_dur){
            return set;
        } else if(div<=Home_dur){
            return home;
        } else{
            return na;
        }
    }
}