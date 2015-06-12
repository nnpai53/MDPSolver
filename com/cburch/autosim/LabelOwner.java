/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

interface LabelOwner {
    int getLabelX(Label which);
    int getLabelY(Label which);
    int getLabelHAlign(Label which);
    int getLabelVAlign(Label which);
}
