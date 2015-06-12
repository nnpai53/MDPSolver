/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;

class Label {
    private LabelOwner owner;
    private String text = "";
    private Font font = new Font("SansSerif", 0, 12);

    public Label(LabelOwner owner) {
        this.owner = owner;
    }
    public void setText(String text) {
        this.text = text;
    }

    public void print(GroupedWriter fout) {
        fout.print("text "); fout.printlnGroup(text);

        fout.print("font ");
        fout.beginGroup();
        String style = "";
        if(font.isBold()) style += "b";
        if(font.isItalic()) style += "i";
        if(style.equals("")) style = "-";
        fout.print(font.getName() + " " + font.getSize() + " " + style);
        fout.endGroup();
        fout.println();
    }
    public boolean setKey(String key, GroupedReader fin)
            throws IOException {
        if(key.equals("text")) {
            text = fin.readGroup();
        } else if(key.equals("font")) {
            String desc = fin.readGroup();
            StringTokenizer toks = new StringTokenizer(desc);
            String name = toks.nextToken();
            int size = Integer.parseInt(toks.nextToken());
            String style_str = toks.nextToken();
            int style = 0;
            for(int i = 0; i < style_str.length(); i++) {
                switch(style_str.charAt(i)) {
                case 'b': style |= Font.BOLD; break;
                case 'i': style |= Font.ITALIC; break;
                case '-': break;
                }
            }

            font = new Font(name, style, size);
        } else {
            return false;
        }
        return true;
    }

    public Rectangle getBounds(Graphics g) {
        Rectangle ret = GraphicsUtil.getTextBounds(g, font, text,
            owner.getLabelX(this), owner.getLabelY(this),
            owner.getLabelHAlign(this), owner.getLabelVAlign(this));
        return ret;
    }
    public Rectangle getBounds(Rectangle bounds, Graphics g) {
        bounds.setBounds(getBounds(g));
        return bounds;
    }
    public Rectangle addToBounds(Rectangle bounds, Graphics g) {
        if(text.length() > 0) bounds.add(getBounds(g));
        return bounds;
    }

    public void draw(Graphics g) {
        if(text.length() == 0) return;
        g.setColor(Color.black);
        GraphicsUtil.drawText(g, font, text,
            owner.getLabelX(this), owner.getLabelY(this),
            owner.getLabelHAlign(this), owner.getLabelVAlign(this));
    }
    public void expose(Canvas canvas, Graphics g) {
        Rectangle rect = getBounds(g);
        canvas.expose(rect);
    }
    public void exposeCursor(Canvas canvas, Graphics g) {
        Rectangle rect = getBounds(g);
        canvas.expose(rect.x + rect.width, rect.y, 2, rect.height);
    }
    public void drawCursor(Graphics g) {
        Rectangle rect = getBounds(g);
        int x_pos = rect.x + rect.width + 1;
        int y_pos = rect.y;
        GraphicsUtil.switchToWidth(g, 1);
        g.setColor(Color.black);
        g.drawLine(x_pos, y_pos, x_pos, y_pos + g.getFontMetrics().getAscent());
    }

    public boolean addLetter(char what) {
        if(what == 0x08 || what == 0x7F) {
            if(text.length() <= 0) return false;
            text = text.substring(0, text.length() - 1);
            return true;
        } else if(!Character.isISOControl(what)) {
            text += what;
            return true;
        } else {
            return false;
        }
    }

    private class FontSelectMenuItem extends JMenuItem
            implements ActionListener {
        Canvas canvas;
        Label label;
        public FontSelectMenuItem(Canvas canvas, Label label) {
            super("Font...");
            this.canvas = canvas;
            this.label = label;
            addActionListener(this);
        }
        public void actionPerformed(ActionEvent evt) {
            Graphics g = canvas.getGraphics();
            Rectangle old_rect = label.getBounds(g);
            label.exposeCursor(canvas, g);
            Font newval = showFontDialog(label.font);
            if(newval != null) {
                label.font = newval;
                canvas.expose(old_rect);
                label.exposeCursor(canvas, g);
                canvas.expose(label.getBounds(g));
                canvas.commitTransaction(false);
            }
        }
    }
    public JMenuItem getFontSelectMenuItem(Canvas canvas) {
        return new FontSelectMenuItem(canvas, this);
    }
    public void setFontByDialog() {
        Font newval = showFontDialog(font);
        if(newval != null) font = newval;
    }

    private static class FontDialog extends JDialog
            implements ActionListener {
        String[] std_fonts = { "Monospaced", "Serif", "SansSerif" };
        JList font_list = new JList(std_fonts);
        JCheckBox check_italic = new JCheckBox("Italic");
        JCheckBox check_bold = new JCheckBox("Bold");
        JSlider size_slider = new JSlider(6, 24, 6);
        JButton button_ok = new JButton("OK");
        JButton button_cancel = new JButton("Cancel");
        Font font = null;

        public FontDialog(Font dflt) {
            super((Frame) null, "Select Font", true);

            font_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            font_list.setBorder(BorderFactory.createLineBorder(Color.black, 1));

            button_ok.addActionListener(this);
            button_cancel.addActionListener(this);

            size_slider.setMajorTickSpacing(3);
            size_slider.setMinorTickSpacing(1);
            size_slider.setSnapToTicks(true);
            size_slider.setPaintTicks(true);
            size_slider.setPaintLabels(true);
            size_slider.setValue(12);

            if(dflt != null) {
                font_list.setSelectedValue(dflt.getName(), true);
                if(dflt.isItalic()) check_italic.setSelected(true);
                if(dflt.isBold()) check_bold.setSelected(true);
                size_slider.setValue(dflt.getSize());
            }

            Container pane = getContentPane();
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            pane.add(panel);
            GridBagLayout gridbag = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            panel.setLayout(gridbag);
            JLabel lab;

            c.anchor = GridBagConstraints.NORTHWEST;
            c.fill = GridBagConstraints.NONE;
            c.ipadx = 10;
            c.ipady = 5;
            c.weighty = 1.0;

            c.gridx = 0;

            lab = new JLabel("Font:");
            c.gridy = 0;
            gridbag.setConstraints(lab, c);
            panel.add(lab);

            lab = new JLabel("Style:");
            c.gridy = 1;
            gridbag.setConstraints(lab, c);
            panel.add(lab);

            lab = new JLabel("Size:");
            c.gridy = 2;
            gridbag.setConstraints(lab, c);
            panel.add(lab);

            c.gridx = 1;

            c.gridy = 0;
            gridbag.setConstraints(font_list, c);
            panel.add(font_list);

            c.gridy = 1;
            Box styles = Box.createVerticalBox();
            styles.add(check_italic);
            styles.add(check_bold);
            gridbag.setConstraints(styles, c);
            panel.add(styles);

            c.gridy = 2;
            gridbag.setConstraints(size_slider, c);
            panel.add(size_slider);

            // make button row
            Box buttons = Box.createHorizontalBox();
            buttons.add(button_ok);
            buttons.add(Box.createHorizontalStrut(10));
            buttons.add(button_cancel);
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 3;
            c.anchor = GridBagConstraints.CENTER;
            gridbag.setConstraints(buttons, c);
            panel.add(buttons);

            pack();
        }

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == button_ok) {
                Object val = font_list.getSelectedValue();
                String name = val == null ? null : val.toString();
                int size = size_slider.getValue();
                int style = 0;
                if(check_bold.isSelected()) style |= Font.BOLD;
                if(check_italic.isSelected()) style |= Font.ITALIC;
                font = new Font(name, style, size);
                dispose();
            } else {
                dispose();
            }
        }

        public Font getFont() {
            return font;
        }
    }
    public static Font showFontDialog(Font dflt) {
        FontDialog dlog = new FontDialog(dflt);
        dlog.setVisible(true);
        return dlog.getFont();
    }
}
