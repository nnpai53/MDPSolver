/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

class Alphabet {
    public static final char EPSILON = '@';
    public static final char ELSE    = '*';
    public static final char BLANK   = '_';

    private String data = "";

    public static String toString(char what) {
        switch(what) {
        case EPSILON:   return "eps";
        case ELSE:      return "else";
        case BLANK:     return "blank";
        default:        return "" + what;
        }
    }

    public Alphabet(String what) {
        set(what);
    }

    public void set(String what) {
        data = "";
        for(int i = 0; i < what.length(); i++) {
            add(what.charAt(i));
        }
    }
    public String toString() {
        return data;
    }
    public void add(char what) {
        if(data.indexOf(what) < 0) data = data + what;
    }
    public void remove(char what) {
        int i = data.indexOf(what);
        if(i >= 0) {
            data = data.substring(0, i)
                + data.substring(i + 1);
        }
    }
    public boolean includes(char what) {
        return data.indexOf(what) >= 0;
    }
}
