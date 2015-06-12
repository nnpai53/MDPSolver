/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

class GroupedReader {
    private int depth = 0;
    private BufferedReader reader;
    private String buffer;

    public GroupedReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }
    public GroupedReader(BufferedReader reader) {
        this.reader = reader;
    }

    public void close() throws IOException {
        reader.close();
    }

    public String readLine() throws IOException {
        getBuffer();
        if(buffer == null)
        	return null;
        // find end of buffer (if any)
        int pos_lb = findFirstUnescaped(buffer, '{');
        int pos_rb = findFirstUnescaped(buffer, '}');
        int pos = pos_lb;
        if(pos_rb >= 0 && (pos == -1 || pos_rb < pos)) pos = pos_rb;

        // compute ret; trim buffer
        String ret;
        if(pos < 0) {
            ret = buffer;
            buffer = null;
        } else {
            ret = buffer.substring(0, pos);
            buffer = buffer.substring(pos);
        }

        ret = unprotect(ret);
        return ret;
    }

    public void beginGroup() throws IOException {
        getBuffer();
        if(buffer.charAt(0) != '{') {
            throw new IOException("Not at beginning of group");
        }
        ++depth;
        buffer = buffer.substring(1);
    }
    public void startGroup() throws IOException {
        beginGroup();
    }
    public void endGroup() throws IOException {
        getBuffer();
        if(buffer.charAt(0) != '}') {
            throw new IOException("Not at end of group");
        }
        --depth;
        buffer = buffer.substring(1);
    }
    public boolean atFileEnd() throws IOException {
        getBuffer();
        return buffer == null;
    }
    public boolean atGroupEnd() throws IOException {
        getBuffer();
        return buffer.charAt(0) == '}';
    }

    public String readGroup() throws IOException {
        beginGroup();
        StringBuffer ret = new StringBuffer(readLine());
        getBuffer();
        while(buffer.charAt(0) != '}') {
            ret.append('\n');
            ret.append(readLine());
            getBuffer();
        }
        endGroup();
        return ret.toString();
    }

    private void getBuffer() throws IOException {
        if(buffer != null && buffer.length() > 0) return;
        
        buffer = reader.readLine();
        if(buffer == null) return;

        int i = 0;
        while(i < depth && buffer.length() > i
                && buffer.charAt(i) == '\t') {
            i++;
        }
        buffer = buffer.substring(i);
    }
    private int findFirstUnescaped(String search, char find) {
        int pos = 0;
        while(true) {
            pos = search.indexOf(find, pos);
            if(pos < 0) return -1;
            if(pos == 0 || search.charAt(pos - 1) != '\\') return pos;
            ++pos;
        }
    }
    private String unprotect(String what) {
        int pos = 0;
        StringBuffer ret = new StringBuffer();
        while(true) {
            int newpos = what.indexOf('\\', pos);
            if(newpos < 0) break;

            ret.append(what.substring(pos, newpos));
            ret.append(what.charAt(newpos + 1));
            pos = newpos + 2;
        }
        ret.append(what.substring(pos));
        return ret.toString();
    }
}
