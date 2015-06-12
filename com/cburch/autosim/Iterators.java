/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

class Iterators {
    private Iterators() { }
    
    public static Iterator join(Iterator a, Iterator b) {
        return new Join(a, b);
    }
    
    public static Iterator reverse(LinkedList list) {
        return new Reverse(list);
    }
    
    private static class Join implements Iterator {
        private Iterator a;
        private Iterator b;

        public Join(Iterator a, Iterator b) {
            this.a = a;
            this.b = b;
        }
        public boolean hasNext() {
            if(a != null && a.hasNext()) return true;
            if(b != null && b.hasNext()) return true;
            return false;
        }
        public Object next() {
            if(a != null) {
                if(a.hasNext()) return a.next();
                a = null;
            }
            return b.next();
        }
        
        public void remove() {
            if(a != null) a.remove();
            b.remove();
        }
    }

    private static class Reverse implements Iterator {
        private ListIterator a;

        public Reverse(LinkedList a) {
            this.a = a.listIterator(a.size());
        }
        public boolean hasNext() {
            return a != null && a.hasPrevious();
        }
        public Object next() {
            return a.previous();
        }
        public void remove() {
            if(a == null) throw new NoSuchElementException();
            a.remove();
        }
    }

}
