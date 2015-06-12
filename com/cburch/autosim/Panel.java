package com.cburch.autosim;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Panel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private JScrollPane jScrollPane;
	
	Panel(){
		super(new BorderLayout());
		canvas = new Canvas();
		jScrollPane = new JScrollPane(canvas,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(jScrollPane, BorderLayout.CENTER);
		canvas.setScrollPane(jScrollPane);
	}

	Canvas getCanvas(){
		return canvas;
	}
}
