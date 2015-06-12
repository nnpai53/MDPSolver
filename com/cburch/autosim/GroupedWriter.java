/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

class GroupedWriter {
    private int depth = 0;
    private PrintWriter writer;
    private boolean begin_line = true;

    public GroupedWriter(Writer writer) {
        this.writer = new PrintWriter(writer);
    }
    public GroupedWriter(OutputStream stream) {
        this.writer = new PrintWriter(stream);
    }

    public void close() {
        writer.close();
    }

    public void print(String s) {
        doPrint(s);
        indent();
    }
    public void println(String s) {
        doPrint(s);
        doPrint("\n");
    }
    public void println() {
        doPrint("\n");
    }

    public void beginGroup() {
        ++depth;
        indent();
        writer.print("{");
    }
    public void startGroup() { beginGroup(); }
    public void endGroup() {
        --depth;
        indent();
        writer.print("}");
    }

    public void printGroup(String s) {
        beginGroup();
        print(s);
        endGroup();
    }
    public void printlnGroup(String s) {
        beginGroup();
        print(s);
        endGroup();
        println();
    }

    private void doPrint(String s) {
        for(int newline = s.indexOf('\n'); newline >= 0;
                newline = s.indexOf('\n')) {
            if(newline > 0) {
                indent();
                writer.println(protect(s.substring(0, newline)));
            } else {
                writer.println();
            }
            begin_line = true;
            s = s.substring(newline + 1);
        }
        if(s.length() > 0) {
            indent();
            writer.print(protect(s));
        }
    }
    private void indent() {
        if(begin_line) {
            for(int i = 0; i < depth; i++) writer.print("\t");
            begin_line = false;
        }
    }
    private String protect(String s) {
        int cur_pos = 0;
        while(true) {
            cur_pos = findFirstIndex(s, cur_pos, "{}\\");
            if(cur_pos < 0) return s;
            s = s.substring(0, cur_pos) + "\\" + s.substring(cur_pos);
            cur_pos += 2;
        }
    }
    private int findFirstIndex(String s, int pos, String t) {
        int ret = -1;
        for(int i = 0; i < t.length(); i++) {
            int next = s.indexOf(t.charAt(i), pos);
            if(next >= 0 && (ret == -1 || next < ret)) ret = next;
        }
        return ret;
    }
}
