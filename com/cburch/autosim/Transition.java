/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class Transition extends AutomatonComponent
        implements LabelOwner {
    public static final double ARROW_LEN = 15.0;
    public static final double ARROW_THETA = Math.PI / 6.0;
    public static final double DEFAULT_OFFSET_THETA = Math.PI / 6.0;
    public static final double DEFAULT_SELFLOOP_THETA = 0.75 * Math.PI;
    public static final int CURSOR_R = State.RADIUS / 2;

    /*private class TransitMenuItem extends JCheckBoxMenuItem
            implements ActionListener {
        
		private static final long serialVersionUID = 1L;
		private char c;

        public TransitMenuItem(char c) {
            super(Alphabet.toString(c));
            this.c = c;
            setState(transitsOn(c));
            setEnabled(transitsOn(c) || canBeTransit(c));
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            if(getState()) addTransit(c);
            else removeTransit(c);
        }
    }*/
   
    private Action src = null;
    private State dst = null;
    
    private double probability;
    
    public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	private double offset_theta = DEFAULT_OFFSET_THETA;
    private Label label = new Label(this);

    private Rectangle bounds; // bounding box

    private int start_x, start_y, end_x, end_y; // this is for memory
    private double old_theta;

    private double cx; // information about arc
    private double cy;
    private double r;
    private double astart;
    private double alength;

    private int arcx; // information about arc
    private int arcy;
    private int arcwidth;   // 0 when line should be drawn
    private int arcstart;
    private int arclength;
    private boolean clockwise;

    private int line_x0; // information about line (when applicable)
    private int line_x1;
    private int line_y0;
    private int line_y1;

    private int arrowx;  // information about arrow coordinates
    private int arrowy;
    private int arrowxl;
    private int arrowyl;
    private int arrowxr;
    private int arrowyr;

    private int textx;   // information about label location
    private int texty;
    private int halign;
    private int valign;

    private boolean cursor_exists = false; // info about green dot
    private double cursor_progress = 0.0;
    private int cursor_x;
    private int cursor_y;

    public Transition(Automaton automaton, Action src, State dst) {
        super(automaton);
        this.src = src;
        this.dst = dst;
        //if(src == dst) offset_theta = DEFAULT_SELFLOOP_THETA;
        computeCircle();
        setLabelText();
    }

    /*protected JMenuItem createTransitItem(char c) {
        return new TransitMenuItem(c);
    }*/

    public void remove() { getAutomaton().removeTransition(this); }

    public Action getSource() { return src; }
    public State getDest() { return dst; }

    public void setCursorExists(boolean flag) {
        if(cursor_exists != flag) {
            cursor_exists = flag;
            setCursorProgress(cursor_progress);
        }
    }
    public void setCursorProgress(double fraction) {
        cursor_progress = fraction;

        int old_x = cursor_x;
        int old_y = cursor_y;
        double cursor_angle;
        if(clockwise) {
            cursor_angle = astart + fraction * alength;
        } else {
            cursor_angle = astart + (1.0 - fraction) * alength;
        }
        cursor_x = (int) (cx + r * Math.cos(cursor_angle) - CURSOR_R);
        cursor_y = (int) (cy - r * Math.sin(cursor_angle) - CURSOR_R);
        int w = 2 * CURSOR_R + 4;
        getAutomaton().getCanvas().expose(old_x - 2, old_y - 2, w, w);
        if(cursor_exists) {
            getAutomaton().getCanvas().expose(cursor_x - 2, cursor_y - 2, w, w);
        }
    }

    public int getLabelX(Label which) { return textx; }
    public int getLabelY(Label which) { return texty; }
    public int getLabelHAlign(Label which) { return halign; }
    public int getLabelVAlign(Label which) { return valign; }
    protected void setLabelText() {
        Canvas canvas = getAutomaton().getCanvas();
        if(canvas != null) label.expose(canvas, canvas.getGraphics());
        label.setText(determineLabelText());
        if(canvas != null) label.expose(canvas, canvas.getGraphics());
    }
    
    public String determineLabelText() {
    	if(probability == 0.0)
    		return "0.0";
    	StringBuffer ret = new StringBuffer("");
    	ret.append(Double.toString(probability));
        /*if(transits == null || transits.equals("")) {
            return "none";
        } else {
            StringBuffer ret = new StringBuffer("");
            for(int i = 0; i < transits.length(); i++) {
                if(i > 0) ret.append(",");
                char c = transits.charAt(i);
                if(c == Alphabet.BLANK) ret.append("_");
                else ret.append(Alphabet.toString(c));
            }
            
        }*/
    	return ret.toString();
    }

    //public abstract boolean canBeTransit(String what);
    /*private void addTransit(char c) {
        if(!transitsOn(c)) {
            transits = transits + new Character(c).toString();
            setLabelText();
        }
    }
    private void removeTransit(char c) {
        int pos = transits.indexOf(c);
        if(pos >= 0) {
            transits = transits.substring(0, pos)
                + transits.substring(pos + 1);
            setLabelText();
        }
    }*/
    
    public void showMenu(int x, int y) {
    	CustomDialog customDialog = new CustomDialog(MainFrameCaller.getMainFrame(),this);
        customDialog.pack();
        
        customDialog.setLocationRelativeTo(MainFrameCaller.getMainFrame());
        customDialog.setVisible(true);

        String[] s = customDialog.getValidatedText();
        if(s[0] != null)
        	probability = Double.parseDouble(s[0]);
        setLabelText();
    }
    
    /*public void createMenu(JPopupMenu menu) {
        JCheckBoxMenuItem item;
        String dict = getAutomaton().getAlphabet().toString();
        for(int i = 0; i < dict.length(); i++) {
            item = new TransitMenuItem(dict.charAt(i));
            menu.add(item);
        }

        menu.addSeparator();
        super.createMenu(menu);
    }*/

    public Rectangle getBounds(Rectangle rect, Graphics g) {
        computeCircle();
        rect.setBounds(bounds);
        label.addToBounds(rect, g);
        return rect;
    }
    public boolean isIn(int qx, int qy, Graphics g) {
        computeCircle();
        Rectangle labrect = label.getBounds(g);
        if(labrect.contains(qx, qy)) return true;

        double distr = (qx - cx) * (qx - cx) + (qy - cy) * (qy - cy);
        double dist = Math.abs(Math.sqrt(distr) - r);
        if(dist > 3.0) return false;

        qy = -qy;
        cy = -cy;
        double thet = normalizeRadians(Math.atan2(qy - cy, qx - cx)
            - astart);
        qy = -qy;
        cy = -cy;

        return thet < alength;
    }

    public void move(int x, int y) {
        // this routine just finds the correct value of offset_theta
        // to use and then passing control onto computeCircle() to
        // compute the actual parameters
        getAutomaton().invalidateBounds();

        /*if(src == dst) {
            double x0 = (double) src.getX();
            double y0 = (double) -src.getY();
            offset_theta = Math.atan2(-y - y0, x - x0);
            computeCircle();
            return;
        } */

        // find arc passing through these three points
        double x0 = (double) src.getX();
        double y0 = (double) -src.getY();
        double x1 = (double) x;
        double y1 = (double) -y;
        double x2 = (double) dst.getX();
        double y2 = (double) -dst.getY();

        // first find coordinate of arc's circle's center
        double cx;
        double cy;
        {
            /* Correct but divides by zero unnecessarily
             * if(Math.abs(y0 - y1) < 1e-4
             *      || Math.abs(y0 - y1) < 1e-4) return;
             * double ma = -(x0 - x1) / (y0 - y1);
             * double mb = -(x2 - x1) / (y2 - y1);
             * if(Math.abs(mb - ma) < 1e-4) return;
             * double xa = (x0 + x1) / 2.0;
             * double xb = (x1 + x2) / 2.0;
             * double ya = (y0 + y1) / 2.0;
             * double yb = (y1 + y2) / 2.0;
             * cx = (ya - yb + mb * xb - ma * xa) / (mb - ma);
             * cy = ya + ma * (cx - xa);
             * System.err.println("correct: " + cx + "," + cy);
             */

            double denom = (x1-x2) * (y0-y1) - (x1-x0) * (y2-y1);
            if(Math.abs(denom) < 1e-2) {
                offset_theta = 0.0;
                computeCircle();
                return;
            }
            double numer = (y0-y2) * (y0-y1) * (y2-y1)
                + (x1-x2) * (x1+x2) * (y0-y1)
                - (x1-x0) * (x1+x0) * (y2-y1);
            cx = numer / 2.0 / denom;
            if(Math.abs(y0 - y1) > 1e-4) {
                cy = (y0+y1) / 2.0 + (x1-x0) * (cx-(x0+x1)/2.0) / (y0-y1);
            } else {
                cy = (y2+y1) / 2.0 + (x1-x2) * (cx-(x2+x1)/2.0) / (y2-y1);
            }
        }

        // compute angle of tangent at (x0,y0) w.r.t. angle to (x2,y2)
        double phic = Math.atan2(cy - y0, cx - x0);
        double phi1 = Math.atan2(y1 - y0, x1 - x0);
        double phi2 = Math.atan2(y2 - y0, x2 - x0);
        double tan;
        if(normalizeRadians(phi1 - phi2) < Math.PI) {
            tan = phic + Math.PI / 2;
        } else {
            tan = phic - Math.PI / 2;
        }
        offset_theta = tan - phi2;

        computeCircle();
    }

    private void computeCircle() {
        if(src == null || dst == null) return;

        if(src.getX() == start_x
                && src.getY() == start_y
                && dst.getX() == end_x
                && dst.getY() == end_y
                && old_theta == offset_theta) {
            return;
        }
        start_x = src.getX();
        start_y = src.getY();
        end_x = dst.getX();
        end_y = dst.getY();
        old_theta = offset_theta;

        boolean draw_arc = true;
        double x0 = (double) src.getX();
        double y0 = (double) -src.getY();
        double x1 = (double) dst.getX();
        double y1 = (double) -dst.getY();
        double theta0;
        double theta1 = 0.0;
        double thetaa = 0.0;

        /*if(src == dst) {
            // compute center and radius
            r = State.RADIUS;
            double dist = r + (Math.sqrt(2.0) - 1.0) * State.RADIUS;
            cx = x0 + dist * Math.cos(offset_theta);
            cy = y0 + dist * Math.sin(offset_theta);

            // compute arc parameters
            double stateth = 2.0 * Math.asin(3.0 / 2.0 / r);
            theta0 = offset_theta - 0.75 * Math.PI + stateth;
            theta1 = offset_theta + 0.75 * Math.PI - stateth;
            clockwise = false;
            astart = theta1;
            alength = -(1.5 * Math.PI - 2.0 * stateth);
            thetaa = theta1 - 0.7 * Math.PI;
        } else */ if(Math.abs(offset_theta) < 1e-4) {
            // arc is just a straight line
            arcwidth = 0;
            draw_arc = false;

            double theta = Math.atan2(y1 - y0, x1 - x0);
            double dy = (3.0 + State.RADIUS) * Math.sin(theta);
            double dx = (3.0 + State.RADIUS) * Math.cos(theta);
            x0 += dx;
            y0 += dy;
            x1 -= dx;
            y1 -= dy;
            line_x0 = (int) Math.round(x0);
            line_y0 = (int) Math.round(-y0);
            line_x1 = (int) Math.round(x1);
            line_y1 = (int) Math.round(-y1);

            double ax = x1;
            double ay = y1;
            double ath = theta + Math.PI;
            arrowx = (int) Math.round(ax);
            arrowy = (int) Math.round(ay);
            arrowxl = (int) Math.round(ax
                + ARROW_LEN * Math.cos(ath + ARROW_THETA));
            arrowyl = (int) Math.round(ay
                + ARROW_LEN * Math.sin(ath + ARROW_THETA));
            arrowxr = (int) Math.round(ax
                + ARROW_LEN * Math.cos(ath - ARROW_THETA));
            arrowyr = (int) Math.round(ay
                + ARROW_LEN * Math.sin(ath - ARROW_THETA));
        } else {
            // compute center and radius
            double phi = Math.atan2(y1 - y0, x1 - x0);
            double m0 = Math.tan(phi + offset_theta - Math.PI / 2.0);
            double m1 = Math.tan(phi + Math.PI - offset_theta + Math.PI / 2.0);
            cx = (y1 - y0 + m0 * x0 - m1 * x1) / (m0 - m1);
            cy = m0 * (cx - x0) + y0;
            r = Math.sqrt(ddistSq(cx, cy, x0, y0));

            // compute arc parameters
            theta0 = Math.atan2(y0 - cy, x0 - cx);
            theta1 = Math.atan2(y1 - cy, x1 - cx);
            double stateth = 2.0 * Math.asin((3.0 + State.RADIUS) / 2.0 / r);
            if(normalizeRadians((phi + offset_theta) - theta0) < Math.PI) {
                theta0 += stateth;
                theta1 -= stateth;
                clockwise = true;
                thetaa = theta1 - Math.PI / 2.0;
                astart = normalizeRadians(theta0);
                alength = normalizeRadians(theta1 - theta0);
            } else {
                theta0 -= stateth;
                theta1 += stateth;
                clockwise = false;
                thetaa = theta1 + Math.PI / 2.0;
                astart = normalizeRadians(theta1);
                alength = normalizeRadians(theta0 - theta1);
            }
        }

        // compute integer parameters to drawArc()
        if(draw_arc) {
            arcx = (int) Math.round(cx - r);
            arcy = (int) Math.round(cy + r);
            arcwidth = (int) Math.round(2.0 * r);
            arcstart = (int) Math.round(180.0 * astart / Math.PI);
            arclength = (int) Math.round(180.0 * alength / Math.PI);
            if(arclength < 0) {
                arcstart += arclength;
                arclength = -arclength;
            }
        }

        // compute arrow information
        if(draw_arc) {
            double ax = cx + r * Math.cos(theta1);
            double ay = cy + r * Math.sin(theta1);
            double ath = thetaa;
            ath += 0.75 * Math.asin(ARROW_LEN / 2.0 / r);
            arrowx = (int) Math.round(ax);
            arrowy = (int) Math.round(ay);
            arrowxl = (int) Math.round(ax
                + ARROW_LEN * Math.cos(ath + ARROW_THETA));
            arrowyl = (int) Math.round(ay
                + ARROW_LEN * Math.sin(ath + ARROW_THETA));
            arrowxr = (int) Math.round(ax
                + ARROW_LEN * Math.cos(ath - ARROW_THETA));
            arrowyr = (int) Math.round(ay
                + ARROW_LEN * Math.sin(ath - ARROW_THETA));
        }

        // put y-coordinates back into actual space
        cy = -cy;
        arcy = -arcy;
        arrowy = -arrowy;
        arrowyl = -arrowyl;
        arrowyr = -arrowyr;

        // compute label information
        {
            double textth = normalizeRadians(astart + alength / 2.0);
            textx = (int) Math.round(cx + r * Math.cos(textth));
            texty = (int) Math.round(cy - r * Math.sin(textth));
            textth = 180.0 * textth / Math.PI; // into degrees
            halign = GraphicsUtil.H_CENTER;
            valign = GraphicsUtil.V_CENTER;

            if(textth > 202.5 && textth < 357.5) {
                texty += 2; valign = GraphicsUtil.V_TOP;
            } else if(textth > 22.5 && textth < 157.5) {
                texty -= 2; valign = GraphicsUtil.V_BOTTOM;
            }

            if(textth > 292.5 || textth < 67.5) {
                textx += 2; halign = GraphicsUtil.H_LEFT;
            } else if(textth > 112.5 && textth < 247.5) {
                textx -= 2; halign = GraphicsUtil.H_RIGHT;
            }
        }

        // compute bounding box
        {
            bounds = new Rectangle(arrowx, arrowy, 1, 1);
            bounds.add(arrowxl, arrowyl);
            bounds.add(arrowxr, arrowyr);
            bounds.add(
                (int) Math.round(cx + r * Math.cos(astart)),
                (int) Math.round(cy - r * Math.sin(astart)));
            bounds.add(
                (int) Math.round(cx + r * Math.cos(astart + alength)),
                (int) Math.round(cy - r * Math.sin(astart + alength)));

            if(normalizeDegrees(0 - arcstart) <= arclength) {
                bounds.add(arcx + arcwidth, arcy + arcwidth / 2);
            }
            if(normalizeDegrees(90 - arcstart) <= arclength) {
                bounds.add(arcx + arcwidth / 2, arcy);
            }
            if(normalizeDegrees(180 - arcstart) <= arclength) {
                bounds.add(arcx, arcy + arcwidth / 2);
            }
            if(normalizeDegrees(270 - arcstart) <= arclength) {
                bounds.add(arcx + arcwidth / 2, arcy + arcwidth);
            }

            bounds.grow(3, 3);
        }
    }
    private double normalizeRadians(double ang) {
        double twopi = 2.0 * Math.PI;
        while(ang >= twopi) ang -= twopi;
        while(ang < 0) ang += twopi;
        return ang;
    }
    private double normalizeDegrees(double ang) {
        while(ang >= 360.0) ang -= 360.0;
        while(ang < 0) ang += 360.0;
        return ang;
    }

    private double ddistSq(double x0, double y0, double x1, double y1) {
        return (x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1);
    }

    public void draw(Graphics g) {
        label.setText(determineLabelText());
        computeCircle();
        GraphicsUtil.switchToWidth(g, 3);
        g.setColor(Color.blue);
        if(arcwidth > 0) {
            g.drawArc(arcx, arcy, arcwidth, arcwidth, arcstart, arclength);
        } else {
            g.drawLine(line_x0, line_y0, line_x1, line_y1);
        }

        int[] xp = { (int) arrowxl, (int) arrowx, (int) arrowxr };
        int[] yp = { (int) arrowyl, (int) arrowy, (int) arrowyr };
        g.drawPolyline(xp, yp, 3);

        g.setColor(Color.black);
        label.draw(g);

        if(cursor_exists) {
            g.setColor(Color.green);
            g.fillOval(cursor_x, cursor_y, 2 * CURSOR_R, 2 * CURSOR_R);
            g.setColor(Color.black);
            g.drawOval(cursor_x, cursor_y, 2 * CURSOR_R, 2 * CURSOR_R);
        }
    }


    public void print(GroupedWriter fout) {
        super.print(fout);
        fout.print("probability "); fout.printlnGroup(Double.toString(probability));
        fout.print("offset "); fout.printlnGroup(Double.toString(offset_theta));
    }
    
    public boolean setKey(String key, GroupedReader fin) throws IOException {
        if(key.equals("probability")) {
            probability = Double.parseDouble(fin.readGroup());
            setLabelText();
            return true;
        }
        else if(key.equals("offset")) {
            String what = fin.readGroup();
            offset_theta = Double.parseDouble(what);
            return true;
        } else {
            return super.setKey(key, fin);
        }
    }

	@Override
	public String toString() {
		return "Transition [src=" + src + ", dst=" + dst + ", probability=" + probability 
				+ "]\n";
	}
	
	/*public boolean canBeTransit(String what) {
        //if(what == Alphabet.EPSILON) return false;

        for(Iterator it = getTransitions(); it.hasNext(); ) {
            Transition transition = (Transition) it.next();
            if(this != transition
                    && transition.getSource() == this.getSource()
                    && transition.transitsOn(what)) {
                return false;
            }
        }
        return true;
    } */

}
