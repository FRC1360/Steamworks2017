package org.usfirst.frc.team1360.robot.util;

import java.util.ArrayList;

public class Debugger {
	
    private static ArrayList<String> currFlags; 
    private static boolean defaultOn;
    private static int currLevel;
    
    static {
        currFlags = new ArrayList<>();
        currLevel = 0;
        defaultOn = false;
    }
    
    public static void println(String msg) {
        if(defaultOn) {
            System.out.println("[DEBUG] " + msg);
        }
    }
    
    public static void println(String msg, String flag, int level) {
        if(meetsCurrRequirements(flag, level)) {
            System.out.println("[" + flag + "] " + msg);
        }
    }
    
    public static void println(String msg, String flag) {
        println(msg, flag, 0);
    }
    
    public static void println(int msg) {
        println("" + msg);
    }
    
    public static void println(int msg, String flag) {
        println("" + msg, flag);
    }
    
    public static void println(int msg, String flag, int level) {
        println("" + msg, flag, level);
    }
    
    public static void println(double msg) {
        println("" + msg);
    }
    
    public static void println(double msg, String flag) {
        println("" + msg, flag);
    }
    
    public static void println(double msg, String flag, int level) {
        println("" + msg, flag, level);
    }
    
    public static void println(float msg) {
        println("" + msg);
    }
    
    public static void println(float msg, String flag) {
        println("" + msg, flag);
    }
    
    public static void println(float msg, String flag, int level) {
        println("" + msg, flag, level);
    }
    
    public static void println(long msg) {
        println("" + msg);
    }
    
    public static void println(long msg, String flag) {
        println("" + msg, flag);
    }
    
    public static void println(long msg, String flag, int level) {
        println("" + msg, flag, level);
    }
    
    public static void println(boolean msg) {
        println("" + msg);
    }
    
    public static void println(boolean msg, String flag) {
        println("" + msg, flag);
    }
    
    public static void println(boolean msg, String flag, int level) {
        println("" + msg, flag, level);
    }
    
    public static void println(Object msg) {
        println(msg.toString());
    }
    
    public static void println(Object msg, String flag) {
        println(msg.toString(), flag);
    }
    
    public static void println(Object msg, String flag, int level) {
        println(msg.toString(), flag, level);
    }
    
    public static void println(byte msg) {
        println("" + msg);
    }
    
    public static void println(byte msg, String flag) {
        println("" + msg, flag);
    }
    
    public static void println(byte msg, String flag, int level) {
        println("" + msg, flag, level);
    }
    
    public static void println(char msg) {
        println("" + msg);
    }
    
    public static void println(char msg, String flag) {
        println("" + msg, flag);
    }
    
    public static void println(char msg, String flag, int level) {
        println("" + msg, flag, level);
    }
    
    public static void println(char[] msg) {
        println(new String(msg));
    }
    
    public static void println(char[] msg, String flag) {
        println(new String(msg), flag);
    }
    
    public static void println(char[] msg, String flag, int level) {
        println(new String(msg), flag, level);
    }
    
    public static void flagOn(String flag) {
        if(!currFlags.contains(flag)) {
            currFlags.add(flag);
        }
    }
    
    public static void flagOff(String flag) {
        currFlags.remove(flag);
    }
    
    public static void allFlagsOff() {
        currFlags.clear();
    }
    
    public static void defaultOn() {
        defaultOn = true;
    }
    
    public static void defaultOff() {
        defaultOn = false;
    }
    
    public static void setLevel(int level) {
        currLevel = level;
    }  
    
    private static boolean meetsCurrRequirements(String flag, int level) {
        for(int i = 0; i < currFlags.size(); i++) {
            if(((String) currFlags.get(i)).equals(flag) && level >= currLevel) {
                return true;
            }
        }
        return false;
}
}
