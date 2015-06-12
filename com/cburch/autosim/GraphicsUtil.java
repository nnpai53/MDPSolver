/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

class GraphicsUtil {
    public static final int H_LEFT = -1;
    public static final int H_CENTER = 0;
    public static final int H_RIGHT = 1;
    public static final int V_TOP = -1;
    public static final int V_CENTER = 0;
    public static final int V_BASELINE = 1;
    public static final int V_BOTTOM = 2;

    static public void switchToWidth(Graphics g, int width) {
        if(g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke((float) width));
        }
    }

    static public void drawCenteredArc(Graphics g, int x, int y,
            int r, int start, int dist) {
        g.drawArc(x - r, y - r, 2 * r, 2 * r, start, dist);
    }

    static public Rectangle getTextBounds(Graphics g, Font font,
            String text, int x, int y, int halign, int valign) {
        if(g == null) return new Rectangle(x, y, 0, 0);
        Font oldfont = g.getFont();
        if(font != null) g.setFont(font);
        Rectangle ret = getTextBounds(g, text, x, y, halign, valign);
        if(font != null) g.setFont(oldfont);
        return ret;
    }
    static public Rectangle getTextBounds(Graphics g, String text,
            int x, int y, int halign, int valign) {
        if(g == null) return new Rectangle(x, y, 0, 0);
        FontMetrics mets = g.getFontMetrics();
        Rectangle2D size = mets.getStringBounds(text, g);
        int width = (new Double(Math.ceil(size.getWidth()))).intValue();
        int ascent = mets.getAscent();
        int height = ascent + mets.getDescent();
        Rectangle ret = new Rectangle(x, y, width, height);

        switch(halign) {
            case H_CENTER: ret.translate(-(width / 2), 0); break;
            case H_RIGHT:  ret.translate(-width, 0); break;
            default: ;
        }
        switch(valign) {
            case V_TOP:      break;
            case V_CENTER:   ret.translate(0, -(ascent / 2)); break;
            case V_BASELINE: ret.translate(0, -ascent); break;
            case V_BOTTOM:   ret.translate(0, -height); break;
            default: ;
        }
        return ret;
    }

    static public void drawText(Graphics g, Font font,
            String text, int x, int y, int halign, int valign) {
        Font oldfont = g.getFont();
        if(font != null) g.setFont(font);
        drawText(g, text, x, y, halign, valign);
        if(font != null) g.setFont(oldfont);
    }
    static public void drawText(Graphics g, String text,
            int x, int y, int halign, int valign) {
        if(text.length() == 0) return;
        Rectangle bd = getTextBounds(g, text, x, y, halign, valign);
        g.drawString(text, bd.x, bd.y + g.getFontMetrics().getAscent());
    }
    static public void drawCenteredText(Graphics g, String text,
            int x, int y) {
        drawText(g, text, x, y, H_CENTER, V_CENTER);
    }
}
