/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import GUI.ContactEditorUI;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import GUI.Main;

public class MainFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	
    private File curFile = null;
    private JMenuBar menubar = null;
    private JTabbedPane tabbedPane=null;
    private JTabbedPane tabbedPaneMain=null;
    private Panel jPanel1 = null;
    private boolean enteredValidMDP = false;
    private static Canvas currentCanvas;// = new Canvas();
    private static ArrayList<Automaton> disTA = new ArrayList<Automaton>();
    private static Automaton automaton;
    ToolBox toolbox = new ToolBox();
    private char autoStateName = 's';
    private char autoActionName = 'a';
    
   
    public MainFrame(Automaton initial, JFrame parent) {
       
    	super(parent, true);
    	setResizable(true);
    	setSize(700, 600);
    	setLocation(200, 200);
    	//setExtendedState(JFrame.MAXIMIZED_BOTH);
    	
    	
        setTitle("MDP Editor");
        setBackground(Color.white);
        
        Listener l = new Listener();
         //addWindowListener(l);
        addComponentListener(l);
        
        //canvas.setToolBox(toolbox);

        menubar = new JMenuBar();
        menubar.add(new MenuFile());
        menubar.add(new MenuTools());   
        menubar.add(new MenuHelp());
        setJMenuBar(menubar);
        
        getContentPane().add(toolbox, BorderLayout.NORTH);
        
        Panel jPaneledit = new Panel();
        //jPanel1.add(newContentPane);
        jPaneledit.getCanvas().setToolBox(toolbox);
        enteredValidMDP = false;
        //Panel jPanelsim = new Panel();
        //jPanel1.add(newContentPane);
        //jPanelsim.getCanvas().setToolBox(toolbox);
        

        //Panel jPanelconsim = new Panel();
        //jPanel1.add(newContentPane);
        //jPanelconsim.getCanvas().setToolBox(toolbox);
        

        //Panel jPanelver = new Panel();
        //jPanel1.add(newContentPane);
        //jPanelver.getCanvas().setToolBox(toolbox);
        
        //tabbedPaneMain = new JTabbedPane();
        //tabbedPaneMain.addTab("Editor", jPaneledit );
        
        
        /*tabbedPaneMain.addTab("Simulator", jPanelsim );
        tabbedPaneMain.addTab("ConcreteSimulator", jPanelconsim );
        tabbedPaneMain.addTab("Verifier", jPanelver );*/
        
        
        /*ChangeListener changeListenerMain = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
              JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
              int index = sourceTabbedPane.getSelectedIndex();
              if(index != -1)
            	  setCurrentCanvas(((Panel)sourceTabbedPane.getComponentAt(index)).getCanvas());
            }
          };
        tabbedPaneMain.addChangeListener(changeListenerMain);*/
        
        //getContentPane().add(tabbedPaneMain, BorderLayout.CENTER);        
        //jPanel2.getCanvas().exposeAll();
        //jPanel2.getCanvas().commitTransaction(true);
        
        
        //FileBrowser filebrowser = new FileBrowser();
        //getContentPane().add(filebrowser.getGui());
        //filebrowser.showRootFile();
        
        //content panes must be opaque
        //frame.setContentPane(newContentPane);
        
        
        
        //getContentPane().add(newContentPane,BorderLayout.WEST);
        
        
        
        
        
        //Panel jPanel1 = new Panel();
        jPanel1 = new Panel();
        //jPanel1.add(newContentPane);
        //JPanel textboxes=new JPanel();
        
        
        /*
        JLabel jl=new JLabel();
        jl.setText("Name");
        JTextField jt1=new JTextField();
        jt1.setColumns(10);
        jt1.setText("");
        jPanel1.add(jl);
        jPanel1.add(jt1);
        jPanel1.setLayout(new FlowLayout());
        */
        
        jPanel1.getCanvas().setToolBox(toolbox);
        /*tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Process "+ Character.toString(autoActionName).toUpperCase(), jPanel1 );
        int an = tabbedPane.getTabCount();
        
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
              JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
              int index = sourceTabbedPane.getSelectedIndex();
              if(index != -1)
            	  setCurrentCanvas(((Panel)sourceTabbedPane.getComponentAt(index)).getCanvas());
            }
          };
        tabbedPane.addChangeListener(changeListener);*/
        
        //getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(jPanel1, BorderLayout.CENTER);
        
        
        
        //jPaneledit.add(textboxes,BorderLayout.NORTH);
        
        
        //jPaneledit.add(tabbedPane, BorderLayout.CENTER);

        // setting state tool as default tool, not setting will not set
        // any tool in default way
        setCurrentCanvas(jPanel1.getCanvas());
        //toolbox.selectButton(toolbox.getStateTool());
        
        if(initial != null){ 
        	jPanel1.getCanvas().setAutomaton(initial);
            automaton = initial;
        }
        else{
        	//Automaton a;
            try {
               // a = (Automaton) DisTA.class.newInstance();
            //	automaton = new Automaton();
            } catch(Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                return;
            }
            automaton.setAutoStateName(autoStateName++);
            automaton.setAutoActionName(autoActionName++);
            //initial.setAutoStateName(autoStateName++);
            //initial.setAutoActionName(autoActionName++);
          //  disTA.add(a);
            jPanel1.getCanvas().setAutomaton(automaton);
        }
        
        jPanel1.getCanvas().exposeAll();
        jPanel1.getCanvas().commitTransaction(true);
        computeTitle();
        
        /*
         * JPanel textboxes=new JPanel();
        JLabel jl=new JLabel();
        jl.setText("Name");
        JTextField jt1=new JTextField();
        jt1.setText("");
        textboxes.add(jl);
        textboxes.add(jt1);
        textboxes.setLayout(new FlowLayout());
         */
        
    }

    public boolean getEnteredValidMDP(){
    	return enteredValidMDP;
    }
    
    private void computeTitle() {
        String new_title = "MDP Editor";
        if(curFile != null) {
            new_title += ": " + curFile.getName();
        }
        setTitle(new_title);
    }

    public void openAutomaton(File f) throws IOException {
        FileReader fread = new FileReader(f);
        GroupedReader fin = new GroupedReader(fread);
        
        String what = fin.readLine();
        if(!what.equals("MDP Editor, " + Main.FILE_VERSION_NAME)) {
            throw new IOException("unrecognized file version");
        }

        String type = fin.readLine();
       // disTA = new ArrayList<Automaton>();

       // while(type != null){
        	type = type.trim();
        	
           // DisTA automaton;
        	//Automaton automaton;
			//if(type.startsWith("DisTA")) automaton = new DisTA();
        	if(type.startsWith("Automaton")){
        		// automaton = new Automaton();
        	}
            else throw new IOException("unknown automaton type");
            
        	Automaton.read(automaton,fin);
        	
        //	disTA.add(automaton);
        	type = fin.readLine();
       // }
        autoStateName = 's';
        autoActionName = 'a';
        //tabbedPane.removeAll();
        //for (Automaton automaton : disTA) {

        //	Panel jPanel1 = new Panel();
        //    jPanel1 = new Panel();
        //	int tabCount = tabbedPane.getTabCount();
        //    tabbedPane.addTab("Process "+ Character.toString(autoActionName).toUpperCase(), jPanel1 );
         //   tabCount = tabbedPane.getTabCount();
            //getContentPane().add(tabbedPane, BorderLayout.CENTER);
    		
    	//	int count = tabbedPane.getTabCount();
    	//	tabbedPane.setSelectedIndex(count-1);
            automaton.setAutoStateName(autoStateName++);
            automaton.setAutoActionName(autoActionName++);
            
            // setting state tool as default tool, not setting will not set
            // any tool in default way
            toolbox.selectButton(toolbox.getStateTool());
            jPanel1.getCanvas().setToolBox(toolbox);
            jPanel1.getCanvas().setAutomaton(automaton);
            jPanel1.getCanvas().exposeAll();
            jPanel1.getCanvas().commitTransaction(true );
            jPanel1.getCanvas().computeSize();
            //getContentPane().add(jPanel1, BorderLayout.CENTER);
        
	//	}
        computeTitle();
    }

    public void doQuit() {
    	System.out.println("Inside Do quit method");
        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
        	MainFrame.this.getContentPane().removeAll();
            MainFrame.this.dispose();
        }
        else{
        	setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        }
    }

    public static Canvas getCurrentCanvas() {
		return currentCanvas;
	}

	public void setCurrentCanvas(Canvas currentCanvas) {
		MainFrame.currentCanvas = currentCanvas;
	}

	private class PrintItem implements Pageable {
        public int getNumberOfPages() {
            return 1;
        }
        public PageFormat getPageFormat(int which)
                throws IndexOutOfBoundsException {
            if(which < 0 || which >= getNumberOfPages()) {
                throw new IndexOutOfBoundsException();
            }
            PageFormat format;
            format = new PageFormat();
            format.setOrientation(PageFormat.LANDSCAPE);
            return format;
        }
        public Printable getPrintable(int which)
                throws IndexOutOfBoundsException {
            if(which < 0 || which >= getNumberOfPages()) {
                throw new IndexOutOfBoundsException();
            }
            return (Printable) getCurrentCanvas();
        }
    }

    private class MenuFile extends JMenu implements ActionListener {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private JFileChooser chooser
            = new JFileChooser(System.getProperty("user.dir"));

        //private JMenuItem newProcess;
        private JMenuItem newProject;
        private JMenuItem open;
        private JMenuItem save;
        private JMenuItem print;
        private JMenuItem quit;

        public MenuFile() {
            super("File");
            setPopupMenuVisible(true);

            JMenu newMenu = new JMenu("New");
          //  newProcess = create(newMenu, "Process");
            newProject = create(newMenu, "Project");

            this.add(newMenu);
            open = create(this, "Open", KeyEvent.VK_O);
            save = create(this, "Save", KeyEvent.VK_S);
            print = create(this, "Print", KeyEvent.VK_P);
            quit = create(this, "Quit", KeyEvent.VK_Q);
        }

        private JMenuItem create(JMenu dest, String title) {
            JMenuItem ret = new JMenuItem(title);
            ret.addActionListener(this);
            dest.add(ret);
            return ret;
        }

        private JMenuItem create(JMenu dest, String title, int accel) {
            JMenuItem ret = create(dest, title);
            int mask = ret.getToolkit().getMenuShortcutKeyMask();
            ret.setAccelerator(KeyStroke.getKeyStroke(accel, mask));
            return ret;
        }

        public void actionPerformed(ActionEvent event) {
            Object src = event.getSource();
            //if(src == newProcess)       doNewTab(DisTA.class);
           // if(src == newProcess)       doNewTab(Automaton.class);
            if(src == newProject)  doNew(Automaton.class);
            else if(src == open)    doOpen();
            else if(src == save)    doSave();
            else if(src == print)   doPrint();
            else if(src == quit)    doQuit();
        }

        //private void doNewTab(Class<DisTA> source) {
        private void doNewTab(Class<Automaton> source) {

        	//Panel jPanel1 = new Panel();
        	jPanel1 = new Panel();
            tabbedPane.addTab("Process "+ Character.toString(autoActionName).toUpperCase(), jPanel1 );
            getContentPane().add(tabbedPane, BorderLayout.CENTER);
    		
    		int count = tabbedPane.getTabCount();
    		tabbedPane.setSelectedIndex(count-1);
    		//Automaton a;
            try {
                automaton = (Automaton) source.newInstance();
            } catch(Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                return;
            }
            automaton.setAutoStateName(autoStateName++);
            automaton.setAutoActionName(autoActionName++);
            //disTA.add(a);
           /* ToolBox toolbox = new ToolBox(canvas);
            canvas.setToolBox(toolbox);
            getContentPane().add(toolbox, BorderLayout.NORTH);
            toolbox.selectButton(toolbox.getStateTool());*/
        	
            // setting state tool as default tool, not setting will not set
            // any tool in default way
            toolbox.selectButton(toolbox.getStateTool());
            jPanel1.getCanvas().setToolBox(toolbox);
            jPanel1.getCanvas().setAutomaton(automaton);
            jPanel1.getCanvas().exposeAll();
            jPanel1.getCanvas().commitTransaction(true);
            jPanel1.getCanvas().computeSize();
        }

		private void doNew(Class<?> source) {

			int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to clear everything for a new project?",
                    "Confirm New",
                    JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                curFile = null;
               // Automaton a;
                try {
                  //  automaton = (Automaton) source.newInstance();
                } catch(Exception e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                    return;
                }
           //     disTA = new ArrayList<Automaton>();
            //    tabbedPane.removeAll();
         //       Panel jPanel1 = new Panel();
          //      jPanel1 = new Panel();
                autoStateName = 's';
                autoActionName = 'a';
           //     disTA.add(a);
           //     tabbedPane.add("Process "+ Character.toString(autoStateName).toUpperCase(), jPanel1 );
                automaton.setAutoStateName(autoStateName++);
                automaton.setAutoActionName(autoActionName++);
                /*ToolBox toolbox = new ToolBox(canvas);
                canvas.setToolBox(toolbox);
                getContentPane().add(toolbox, BorderLayout.NORTH);
                toolbox.selectButton(toolbox.getStateTool());*/
                
                // setting state tool as default tool, not setting will not set
                // any tool in default way
                toolbox.selectButton(toolbox.getStateTool());
                jPanel1.getCanvas().setToolBox(toolbox);
                jPanel1.getCanvas().setAutomaton(automaton);
                jPanel1.getCanvas().exposeAll();
                jPanel1.getCanvas().commitTransaction(true);
            }
        }

        private void doOpen() {
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                try {
                    openAutomaton(f);
                    getCurrentCanvas().commitTransaction(true);
                } catch(IOException e) {
                    JOptionPane.showMessageDialog(null,
                        "Could not open file" + f.toString()
                        + ": " + e.getMessage());
                }
            }
        }

        private void doSave() {
            int returnVal = chooser.showSaveDialog(null);
            if(returnVal != JFileChooser.APPROVE_OPTION) return;

            File f = chooser.getSelectedFile();
            if(f.exists()) {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "The file already exists. Do you want to overwrite it?",
                    "Confirm Overwrite",
                    JOptionPane.YES_NO_OPTION);
                if(confirm != JOptionPane.YES_OPTION) return;
            }
            saveFile(f);
            curFile = f;
            computeTitle();
            getCurrentCanvas().commitTransaction(true);
        }

        private void doPrint() {
            PrinterJob job = PrinterJob.getPrinterJob();
            if(job.printDialog() == false) return;
            job.setPageable(new PrintItem());
            try {
                job.print();
            } catch(PrinterException e) {
                JOptionPane.showMessageDialog(null,
                    "Error during printing: " + e.toString());
            }
        }

        public void saveFile(File f) {
            try {
                FileOutputStream fwrite = new FileOutputStream(f);
                GroupedWriter fout = new GroupedWriter(fwrite);
                fout.println("MDP Editor, " + Main.FILE_VERSION_NAME);
                /*for (Automaton automaton : disTA) {
					automaton.print(fout);
				} */
                automaton.print(fout);
                //getCurrentCanvas().getAutomaton().print(fout);
                fout.close();
            } catch(IOException e) {
                JOptionPane.showMessageDialog(null,
                    "Could not open file.");
            }
        }
    }

    private class MenuHelp extends JMenu implements ActionListener {
    	private static final long serialVersionUID = 1L;
		private JMenuItem help;
        private JMenuItem about;

        public MenuHelp() {
            super("Help");
            help = create(this, "Help");
            about = create(this, "About");
        }

        private JMenuItem create(JMenu dest, String title) {
            JMenuItem ret = new JMenuItem(title);
            ret.addActionListener(this);
            dest.add(ret);
            return ret;
        }

        public void actionPerformed(ActionEvent event) {
            Object src = event.getSource();
            if(src == help)       doHelp();
            else if(src == about) doAbout();
        }

        private void doHelp() {
            HelpFrame help = new HelpFrame("com/cburch/autosim/doc/index.html");
            URL index = help.getCurrent();
            help.setTitle("Help: MDP Editor");
            help.addContentsItem("Contents", index);
            try {
                help.addContentsItem("About", new URL(index, "about.html"));
            } catch(Exception e) { }
            help.setVisible(true);
        }

        private void doAbout() {
            JOptionPane.showMessageDialog(null,
                    "MDP Editor " + Main.VERSION_NAME + ". "
                    + "(c) 2015, Neeraj Pai.\n"
                    + "See Help for details.\n");
        }
    }
    
    
    private class MenuTools extends JMenu implements ActionListener {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JMenuItem finish;

        public MenuTools() {
            super("Run");
            finish = create(this, "Finish");
        }

        private JMenuItem create(JMenu dest, String title) {
            JMenuItem ret = new JMenuItem(title);
            ret.addActionListener(this);
            dest.add(ret);
            return ret;
        }

        public void actionPerformed(ActionEvent event) {
            Object src = event.getSource();
            if(src == finish)   doFinish();
        }

        private void doFinish() {
            // Write code to parse and validate MDP here.
        	String actionName = null;
        	String stateName = null;
        	Automaton automaton = MainFrame.automaton;
        	LinkedList<AutomatonComponent> states = automaton.states;
        	LinkedList<AutomatonComponent> sActions = automaton.sActions;
        	LinkedList<AutomatonComponent> transitions = automaton.transitions;
        	LinkedList<AutomatonComponent> stateActionLinks = automaton.stateActionLinks;
        	int noOfStates = states.size();
        	if(noOfStates == 0){
        		JOptionPane.showMessageDialog(MainFrame.getCurrentCanvas(), 
            			"There are no states in the MDP.\n " +
            			"An MDP should consist of atleast one state.\n"+ "Please add a state and try.");
        	}
        	else{
        	    int noOfActions = sActions.size();
        	    int noOfSALinks = stateActionLinks.size();
        	    int noOfTransitions = transitions.size();
        	    double eps = 0.0000000000000001;
        	    boolean isAttachedToState[]  =  new boolean [noOfActions];
        	    boolean hasActionOrTransition[] = new boolean [noOfStates];
        	    double totalProbability[] = new double [noOfActions];
        	    for(int i=0; i<noOfActions;i++){
        		    isAttachedToState[i] = false;
        		    totalProbability[i] = 0.0;
        	    }
        	    for(int i=0;i<noOfStates;i++)
        		    hasActionOrTransition[i] = false;
        	
        	    for(int i=0;i<noOfSALinks;i++){
        		    StateActionLink saLink = (StateActionLink) stateActionLinks.get(i);
        		    int j = sActions.indexOf(saLink.getDest());
        		    int k = states.indexOf(saLink.getSource());
        		    isAttachedToState[j] = true;
        		    hasActionOrTransition[k] = hasActionOrTransition[k] || true;
        	    }
        	
        	    for(int i=0;i<noOfTransitions;i++){
    			    Transition trans = (Transition) transitions.get(i);
        		    int j = sActions.indexOf(trans.getSource());
        		    totalProbability[j] += trans.getProbability();
        		    int k = states.indexOf(trans.getDest());
        		    hasActionOrTransition[k] = hasActionOrTransition[k] || true;
    		    }
        	
        	    for(int i=0; i<noOfStates;i++){
        		    if(hasActionOrTransition[i] == false){
        			    stateName = ((State)states.get(i)).getName();
        			    break;
        		    }
        	    }
        	    if(stateName != null){
        		    JOptionPane.showMessageDialog(MainFrame.getCurrentCanvas(), 
            			"The state " + stateName + " is not associated with any action in the MDP" +
            			".\n"+ "Nor does it have any incoming transition. Please remove it and try.");
        	    }
        	    else{     	
        	        for(int i=0; i<noOfActions;i++){
        		        if(isAttachedToState[i] == false){
        			        actionName = ((Action)sActions.get(i)).getName();
        			        break;
        		        }
        	        }
        	        if(actionName != null){
        		        JOptionPane.showMessageDialog(MainFrame.getCurrentCanvas(), 
            			"The action " + actionName + " is not associated with any state in the MDP" +
            			".\n"+ "Please add it to a state or remove it.");
        	        }
        	        else{
        		        for(int i=0; i<noOfActions;i++){
        		//	System.out.println("The total Probability of action at index 0 is " + totalProbability[i]);
            		        if(Math.abs(totalProbability[i] - 1.0) > eps){
            			        actionName = ((Action)sActions.get(i)).getName();
            			        break;
            		        }
            	        }
        		        if(actionName != null){
            		        JOptionPane.showMessageDialog(MainFrame.getCurrentCanvas(), 
                			"The sum total of transition probabilities of " + actionName + " is not equal to 1.0" +
                			".\n"+ "Please ensure that it is 1.0.");
            	        }
        		        else{
        			//this.removeAll();
        		    	    enteredValidMDP = true;
        		    	    
        		    	    JOptionPane.showMessageDialog(MainFrame.getCurrentCanvas(), 
        	            			"MDP successfully added.\n " +
        	            			"Close this window to exit\n");
        		    	    System.out.println("Entered valid MDP is now true");
        			        //MainFrame.this.getContentPane().removeAll();
        			        //MainFrame.this.dispose();
        		    	    
        			        //= null;
                  //  djhdfhd;
                    
        		        }
        	        }
        	    }
            }
        }
    }
    
    private class Listener implements ComponentListener {
       /* public void windowActivated(WindowEvent e) { }
        public void windowClosed(WindowEvent e) {
        	//doQuit();
            //System.exit(0);
        }
        public void windowClosing(WindowEvent e) {
            //doQuit();
        }
        public void windowDeactivated(WindowEvent e) { }
        public void windowDeiconified(WindowEvent e) { }
        public void windowIconified(WindowEvent e) { }
        public void windowOpened(WindowEvent e) { } */

        public void componentHidden(ComponentEvent e) { }
        public void componentMoved(ComponentEvent e) { }
        public void componentResized(ComponentEvent e) {
        	getCurrentCanvas().computeSize();
        }
        public void componentShown(ComponentEvent e) {
        	getCurrentCanvas().computeSize();
        }
    }

	public static ArrayList<Automaton> getDisTA() {
		return disTA;
	}
}
