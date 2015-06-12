/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import UtilityFunctions.FileMerger;
import UtilityFunctions.ParseDrawnFigure;
import UtilityFunctions.UploadFile;
import gnuplotutils.Gnu_Script_Writer;
import gnuplotutils.OsUtils;
import input.MDPData;
import input.RandomizeMDP;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.imageio.*;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.cburch.autosim.*;
import valueiteration.ValueIterationImpl;

/**
 *
 * @author vinit
 */
public class ContactEditorUI extends javax.swing.JFrame {

    public JTextField getNoofactiononeachstate() {
        return noofactiononeachstate;
    }

    public void setNoofactiononeachstate(JTextField noofactiononeachstate) {
        this.noofactiononeachstate = noofactiononeachstate;
    }

    public JTextField getNoofactions() {
        return noofactions;
    }

    public void setNoofactions(JTextField noofactions) {
        this.noofactions = noofactions;
    }

    public JTextField getNoofstate() {
        return noofstate;
    }

    public void setNoofstate(JTextField noofstate) {
        this.noofstate = noofstate;
    }

    public JTextField getRewards() {
        return rewards;
    }

    public void setRewards(JTextField rewards) {
        this.rewards = rewards;
    }

    public JTextArea getTransitiontable() {
        return transitiontable;
    }

    public void setTransitiontable(JTextArea transitiontable) {
        this.transitiontable = transitiontable;
    }

    /**
     * Creates new form ContactEditorUI
     */
    
    private MDPData mdp=null;
    
    private static int policyCount=0;
    
    private static int valuecount=0;
    
    private static int comparecount=0;
    
    private String path=null;
    
    private String path1=null, path2= null;
    
    private JImagePanel ipanel = new JImagePanel();
    
    public ContactEditorUI()throws Exception {
        initComponents();
        SelectedAlgo2_panel.setVisible(false);
        Value_Whole_panel.setVisible(false);
        //Compute_Optimal_panel.setVisible(false);
        //jPanel19.add(add_Image("E:"+File.separator+"Docements"+File.separator+"vini.jpg"));
       // jPanel19.
        Action_Combobox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
               //System.out.println((String)func.getSelectedItem());
               if(((String)Action_Combobox.getSelectedItem()).equals("Compute and Compare")){
                   SelectedAlgo2_panel.setVisible(true);
                   jTabbedPane1.setEnabledAt(1, false);
                   jTabbedPane1.setEnabledAt(2, false);
               }
               else{
                   SelectedAlgo2_panel.setVisible(false);
                   jTabbedPane1.setEnabledAt(1, false);
                   jTabbedPane1.setEnabledAt(2, false);
               }
            }
        });
        ipanel.setSize(300,300);
        Action_sub_panel1.add(ipanel);
    }
    public void add_Image(int index, JLabel label, int mode){
        String fileExtension = OsUtils.getGnuPlotFileExtension();
        String imageFileExtension = OsUtils.getImageFileExtension();
        String pathSeparator = OsUtils.getPathSeparator();
        String totalPath = "";
        totalPath = path+pathSeparator;
        if(mode == 0){
            
            label.setIcon(new javax.swing.ImageIcon(totalPath+"value_state_"+index+"_Plot." +imageFileExtension));
        }
        else if(mode == 1){
            label.setIcon(new javax.swing.ImageIcon(totalPath+"S"+index+"." + imageFileExtension));
        }
        
        else{
            label.setIcon(new javax.swing.ImageIcon(totalPath+"value_Plot."+imageFileExtension));
        
        }
    }
    
    public void addComparePolicyImage(String path1, String path2){
    	String fileExtension = OsUtils.getGnuPlotFileExtension();
        String imageFileExtension = OsUtils.getImageFileExtension();
        String pathSeparator = OsUtils.getPathSeparator();
        String totalPath1 = path1+pathSeparator;
        String totalPath2 = path2+pathSeparator;
        if(comparecount<0)
            comparecount=0;
        System.out.println(totalPath1+"S"+(comparecount+1)+"."+imageFileExtension);
        System.out.println(totalPath2+"S"+(comparecount+1)+"."+imageFileExtension);
        
         image8.setIcon(new javax.swing.ImageIcon(totalPath1+"S"+(comparecount+1)+"." +imageFileExtension));
         
         image9.setIcon(new javax.swing.ImageIcon(totalPath2+"S"+(comparecount+1)+"." + imageFileExtension));
         
    }
    
    public void addCompareImage(String path1, String path2){
    	    String fileExtension = OsUtils.getGnuPlotFileExtension();
            String imageFileExtension = OsUtils.getImageFileExtension();
            String pathSeparator = OsUtils.getPathSeparator();
            String totalPath = "compare Algorithm"+pathSeparator;
            image7.setIcon(new javax.swing.ImageIcon(totalPath + "compareval." +imageFileExtension));
            this.addComparePolicyImage(path1, path2);
            //this.addCompareImage(path1, path2);
            //comparecount++;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() throws Exception{

        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Action_Combobox = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Algo1_ComboBox = new javax.swing.JComboBox();
        SelectedAlgo2_panel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Algo2_Combobox = new javax.swing.JComboBox();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        MDP_Input_panel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        noofactions = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        noofstate = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        noofactiononeachstate = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        rewards = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transitiontable = new javax.swing.JTextArea();
        jPanel14 = new javax.swing.JPanel();
        Upload_Button = new javax.swing.JButton();
        Calculate_Button = new javax.swing.JButton();
        randomMDP_button = new javax.swing.JButton();
        DrawMDP_button = new javax.swing.JButton();
        Compute_Optimal_panel = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        Policy_whole_panel = new javax.swing.JPanel();
        policy_Pane_Button = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        algoLabel1 = new javax.swing.JLabel();
        algoLabel2 = new javax.swing.JLabel();
        policy_panel = new javax.swing.JPanel();
        Action_sub_panel1 = new javax.swing.JPanel();
        image1 = new javax.swing.JLabel();
        Action_sub_panel2 = new javax.swing.JPanel();
        image2 = new javax.swing.JLabel();
        Action_sub_panel3 = new javax.swing.JPanel();
        image3 = new javax.swing.JLabel();
        refresh = new javax.swing.JButton();
        Value_Whole_panel = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        policy_panel1 = new javax.swing.JPanel();
        Action_sub_panel4 = new javax.swing.JPanel();
        image4 = new javax.swing.JLabel();
        Action_sub_panel5 = new javax.swing.JPanel();
        image5 = new javax.swing.JLabel();
        Action_sub_panel6 = new javax.swing.JPanel();
        image6 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        Compare_panel = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        Policy_whole_panel1 = new javax.swing.JPanel();
        policy_panel2 = new javax.swing.JPanel();
        valvsiterarion = new javax.swing.JPanel();
        image7 = new javax.swing.JLabel();
        Action_sub_panel8 = new javax.swing.JPanel();
        image8 = new javax.swing.JLabel();
        Action_sub_panel9 = new javax.swing.JPanel();
        image9 = new javax.swing.JLabel();
        refresh1 = new javax.swing.JButton();
        prev_compare = new javax.swing.JButton();
        Next_compare = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(100, 600));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Function", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setText("  Actions");

        Action_Combobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compute Optimal Strategy", "Compute and Compare" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Action_Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(Action_Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Algorithm"));
        jPanel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setText("Algorithm1");

        Algo1_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Value Iteration", "Policy Iteration", "Modified policy iteration", "Simplex on dual", "Bland simplex on dual", "Primal Affine", "Mixed Algorithm", "Single pivot policy iteration", "Single pivot modified policy iteration", "Least index policy iteration", "Least index modified policy iteration" }));
        Algo1_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Algo1_ComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Algo1_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(Algo1_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        SelectedAlgo2_panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Algorithm"));
        SelectedAlgo2_panel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setText("Algorithm 2");

        Algo2_Combobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Value Iteration", "Policy Iteration", "Modified policy iteration", "Simplex on dual", "Bland simplex on dual", "Primal Affine", "Mixed Algorithm", "Single pivot policy iteration", "Single pivot modified policy iteration", "Least index policy iteration", "Least index modified policy iteration" }));
        Algo2_Combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Algo2_ComboboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SelectedAlgo2_panelLayout = new javax.swing.GroupLayout(SelectedAlgo2_panel);
        SelectedAlgo2_panel.setLayout(SelectedAlgo2_panelLayout);
        SelectedAlgo2_panelLayout.setHorizontalGroup(
            SelectedAlgo2_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectedAlgo2_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Algo2_Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        SelectedAlgo2_panelLayout.setVerticalGroup(
            SelectedAlgo2_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectedAlgo2_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(Algo2_Combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel6.setText("No of Actions");

        noofactions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noofactionsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(noofactions, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(noofactions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel7.setText("No of States");

        noofstate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noofstateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(noofstate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(noofstate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel4.setText("No of Actions on Each State ( w.r.t. each state)");

        noofactiononeachstate.setMaximumSize(new java.awt.Dimension(400, 400));
        noofactiononeachstate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noofactiononeachstateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(noofactiononeachstate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(noofactiononeachstate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel5.setText("Reward For each Action respectively");

        rewards.setMaximumSize(new java.awt.Dimension(400, 400));
        rewards.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rewardsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rewards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rewards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel8.setText("Transition Table w.r.t Actions respectively");

        transitiontable.setColumns(20);
        transitiontable.setRows(5);
        jScrollPane1.setViewportView(transitiontable);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Upload_Button.setText("Upload MDP");
        Upload_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Upload_ButtonActionPerformed(evt);
            }
        });

        Calculate_Button.setText("Calculate");
        Calculate_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt){
                try {
					Calculate_ButtonActionPerformed(evt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        randomMDP_button.setText("Random MDP");
        randomMDP_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomMDP_buttonActionPerformed(evt);
            }
        });
        
        DrawMDP_button.setText("Draw MDP");
        DrawMDP_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DrawMDP_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Upload_Button, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(Calculate_Button, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(randomMDP_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DrawMDP_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Upload_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Calculate_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(randomMDP_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DrawMDP_button)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(178, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MDP_Input_panelLayout = new javax.swing.GroupLayout(MDP_Input_panel);
        MDP_Input_panel.setLayout(MDP_Input_panelLayout);
        MDP_Input_panelLayout.setHorizontalGroup(
            MDP_Input_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MDP_Input_panelLayout.setVerticalGroup(
            MDP_Input_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("MDP", MDP_Input_panel);

        jLayeredPane1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jLayeredPane1.setPreferredSize(new java.awt.Dimension(100, 500));

        Policy_whole_panel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Policy_whole_panelFocusGained(evt);
            }
        });

        policy_Pane_Button.setText("Policy Table");
        policy_Pane_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                policy_Pane_ButtonActionPerformed(evt);
            }
        });

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jButton6.setText("Next");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jButton6)
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jButton6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jButton7.setText("Prev");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jButton7)
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jButton7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        policy_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        policy_panel.setPreferredSize(new java.awt.Dimension(1310, 365));

        Action_sub_panel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Action_sub_panel1Layout = new javax.swing.GroupLayout(Action_sub_panel1);
        Action_sub_panel1.setLayout(Action_sub_panel1Layout);
        Action_sub_panel1Layout.setHorizontalGroup(
            Action_sub_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addContainerGap())
        );
        Action_sub_panel1Layout.setVerticalGroup(
            Action_sub_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Action_sub_panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addContainerGap())
        );

        Action_sub_panel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Action_sub_panel2Layout = new javax.swing.GroupLayout(Action_sub_panel2);
        Action_sub_panel2.setLayout(Action_sub_panel2Layout);
        Action_sub_panel2Layout.setHorizontalGroup(
            Action_sub_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image2, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );
        Action_sub_panel2Layout.setVerticalGroup(
            Action_sub_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Action_sub_panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Action_sub_panel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Action_sub_panel3Layout = new javax.swing.GroupLayout(Action_sub_panel3);
        Action_sub_panel3.setLayout(Action_sub_panel3Layout);
        Action_sub_panel3Layout.setHorizontalGroup(
            Action_sub_panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image3, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );
        Action_sub_panel3Layout.setVerticalGroup(
            Action_sub_panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout policy_panelLayout = new javax.swing.GroupLayout(policy_panel);
        policy_panel.setLayout(policy_panelLayout);
        policy_panelLayout.setHorizontalGroup(
            policy_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(policy_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Action_sub_panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(Action_sub_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(Action_sub_panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );
        policy_panelLayout.setVerticalGroup(
            policy_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, policy_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(policy_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Action_sub_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Action_sub_panel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Action_sub_panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Policy_whole_panelLayout = new javax.swing.GroupLayout(Policy_whole_panel);
        Policy_whole_panel.setLayout(Policy_whole_panelLayout);
        Policy_whole_panelLayout.setHorizontalGroup(
            Policy_whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Policy_whole_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(324, 324, 324)
                .addComponent(policy_Pane_Button)
                .addGap(18, 18, 18)
                .addComponent(refresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Policy_whole_panelLayout.createSequentialGroup()
                .addComponent(policy_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Policy_whole_panelLayout.setVerticalGroup(
            Policy_whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Policy_whole_panelLayout.createSequentialGroup()
                .addComponent(policy_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Policy_whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Policy_whole_panelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Policy_whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(Policy_whole_panelLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(Policy_whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(policy_Pane_Button)
                            .addComponent(refresh))))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        Policy_whole_panel.setBounds(0, 0, 1320, 530);
        jLayeredPane1.add(Policy_whole_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jButton5.setText("Value On States");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        policy_panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        policy_panel1.setPreferredSize(new java.awt.Dimension(1310, 365));

        Action_sub_panel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Action_sub_panel4Layout = new javax.swing.GroupLayout(Action_sub_panel4);
        Action_sub_panel4.setLayout(Action_sub_panel4Layout);
        Action_sub_panel4Layout.setHorizontalGroup(
            Action_sub_panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image4, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addContainerGap())
        );
        Action_sub_panel4Layout.setVerticalGroup(
            Action_sub_panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Action_sub_panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image4, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addContainerGap())
        );

        Action_sub_panel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Action_sub_panel5Layout = new javax.swing.GroupLayout(Action_sub_panel5);
        Action_sub_panel5.setLayout(Action_sub_panel5Layout);
        Action_sub_panel5Layout.setHorizontalGroup(
            Action_sub_panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image5, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );
        Action_sub_panel5Layout.setVerticalGroup(
            Action_sub_panel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Action_sub_panel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Action_sub_panel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Action_sub_panel6Layout = new javax.swing.GroupLayout(Action_sub_panel6);
        Action_sub_panel6.setLayout(Action_sub_panel6Layout);
        Action_sub_panel6Layout.setHorizontalGroup(
            Action_sub_panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image6, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );
        Action_sub_panel6Layout.setVerticalGroup(
            Action_sub_panel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout policy_panel1Layout = new javax.swing.GroupLayout(policy_panel1);
        policy_panel1.setLayout(policy_panel1Layout);
        policy_panel1Layout.setHorizontalGroup(
            policy_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(policy_panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Action_sub_panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(Action_sub_panel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(Action_sub_panel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );
        policy_panel1Layout.setVerticalGroup(
            policy_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, policy_panel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(policy_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Action_sub_panel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Action_sub_panel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Action_sub_panel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jButton10.setText("Prev");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jButton10)
                .addGap(34, 34, 34))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(jButton10))
        );

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton2.setText("Next");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jButton2)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        javax.swing.GroupLayout Value_Whole_panelLayout = new javax.swing.GroupLayout(Value_Whole_panel);
        Value_Whole_panel.setLayout(Value_Whole_panelLayout);
        Value_Whole_panelLayout.setHorizontalGroup(
            Value_Whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Value_Whole_panelLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(328, 328, 328)
                .addComponent(jButton5)
                .addGap(46, 46, 46)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 263, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158))
            .addGroup(Value_Whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Value_Whole_panelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(policy_panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        Value_Whole_panelLayout.setVerticalGroup(
            Value_Whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Value_Whole_panelLayout.createSequentialGroup()
                .addContainerGap(386, Short.MAX_VALUE)
                .addGroup(Value_Whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Value_Whole_panelLayout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Value_Whole_panelLayout.createSequentialGroup()
                        .addGroup(Value_Whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Value_Whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton5)
                                .addComponent(jButton1)))
                        .addGap(85, 85, 85))))
            .addGroup(Value_Whole_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Value_Whole_panelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(policy_panel1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(156, Short.MAX_VALUE)))
        );

        Value_Whole_panel.setBounds(0, 0, 1330, 530);
        jLayeredPane1.add(Value_Whole_panel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout Compute_Optimal_panelLayout = new javax.swing.GroupLayout(Compute_Optimal_panel);
        Compute_Optimal_panel.setLayout(Compute_Optimal_panelLayout);
        Compute_Optimal_panelLayout.setHorizontalGroup(
            Compute_Optimal_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
        );
        Compute_Optimal_panelLayout.setVerticalGroup(
            Compute_Optimal_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Compute_Optimal_panelLayout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Optimal Strategy", Compute_Optimal_panel);

        jLayeredPane2.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jLayeredPane2.setPreferredSize(new java.awt.Dimension(100, 500));

        Policy_whole_panel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Policy_whole_panel1FocusGained(evt);
            }
        });

        policy_panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        policy_panel2.setPreferredSize(new java.awt.Dimension(1310, 365));

        valvsiterarion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout valvsiterarionLayout = new javax.swing.GroupLayout(valvsiterarion);
        valvsiterarion.setLayout(valvsiterarionLayout);
        valvsiterarionLayout.setHorizontalGroup(
            valvsiterarionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(valvsiterarionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image7, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addContainerGap())
        );
        valvsiterarionLayout.setVerticalGroup(
            valvsiterarionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, valvsiterarionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image7, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                .addContainerGap())
        );

        Action_sub_panel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Action_sub_panel8Layout = new javax.swing.GroupLayout(Action_sub_panel8);
        Action_sub_panel8.setLayout(Action_sub_panel8Layout);
        Action_sub_panel8Layout.setHorizontalGroup(
            Action_sub_panel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image8, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );
        Action_sub_panel8Layout.setVerticalGroup(
            Action_sub_panel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Action_sub_panel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Action_sub_panel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout Action_sub_panel9Layout = new javax.swing.GroupLayout(Action_sub_panel9);
        Action_sub_panel9.setLayout(Action_sub_panel9Layout);
        Action_sub_panel9Layout.setHorizontalGroup(
            Action_sub_panel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image9, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );
        Action_sub_panel9Layout.setVerticalGroup(
            Action_sub_panel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Action_sub_panel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout policy_panel2Layout = new javax.swing.GroupLayout(policy_panel2);
        policy_panel2.setLayout(policy_panel2Layout);
        policy_panel2Layout.setHorizontalGroup(
            policy_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(policy_panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(valvsiterarion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124)
                .addComponent(Action_sub_panel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(Action_sub_panel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );
        policy_panel2Layout.setVerticalGroup(
            policy_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, policy_panel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(policy_panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(valvsiterarion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Action_sub_panel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Action_sub_panel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        refresh1.setText("Refresh");
        refresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refresh1ActionPerformed(evt);
            }
        });

        prev_compare.setText("Prev");
        prev_compare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prev_compareActionPerformed(evt);
            }
        });

        Next_compare.setText("Next");
        Next_compare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Next_compareActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Policy_whole_panel1Layout = new javax.swing.GroupLayout(Policy_whole_panel1);
        Policy_whole_panel1.setLayout(Policy_whole_panel1Layout);
        Policy_whole_panel1Layout.setHorizontalGroup(
            Policy_whole_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Policy_whole_panel1Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addComponent(prev_compare)
                .addGap(139, 139, 139)
                .addComponent(Next_compare)
                .addGap(291, 291, 291)
                .addComponent(refresh1)
                .addGap(0, 0, Short.MAX_VALUE))
             .addGroup(Policy_whole_panel1Layout.createSequentialGroup()
                .addGap(575, 575, 575)
                .addComponent(algoLabel1)
                .addGap(350, 350, 350)
                .addComponent(algoLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Policy_whole_panel1Layout.createSequentialGroup()
                .addComponent(policy_panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Policy_whole_panel1Layout.setVerticalGroup(
            Policy_whole_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Policy_whole_panel1Layout.createSequentialGroup()
                .addComponent(policy_panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Policy_whole_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Policy_whole_panel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(Policy_whole_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(algoLabel1)
                        .addComponent(algoLabel2)))
                    .addGroup(Policy_whole_panel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(Policy_whole_panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(prev_compare)
                            .addComponent(Next_compare)
                            .addComponent(refresh1))))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        Policy_whole_panel1.setBounds(0, 0, 1320, 530);
        jLayeredPane2.add(Policy_whole_panel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout Compare_panelLayout = new javax.swing.GroupLayout(Compare_panel);
        Compare_panel.setLayout(Compare_panelLayout);
        Compare_panelLayout.setHorizontalGroup(
            Compare_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
        );
        Compare_panelLayout.setVerticalGroup(
            Compare_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Compare_panelLayout.createSequentialGroup()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Compare Strategy", Compare_panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SelectedAlgo2_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SelectedAlgo2_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("MDP Input");
        SelectedAlgo2_panel.setVisible(false);
        jTabbedPane1.setEnabledAt(1, false);
        jTabbedPane1.setEnabledAt(2, false);
        SelectedAlgo2_panel.setVisible(false);
        jTabbedPane1.setEnabledAt(1, false);
        jTabbedPane1.setEnabledAt(2, false);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Algo1_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Algo1_ComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Algo1_ComboBoxActionPerformed

    private void Algo2_ComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Algo2_ComboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Algo2_ComboboxActionPerformed

    private void noofactionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noofactionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noofactionsActionPerformed

    private void noofstateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noofstateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noofstateActionPerformed

    private void noofactiononeachstateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noofactiononeachstateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noofactiononeachstateActionPerformed

    private void rewardsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rewardsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rewardsActionPerformed

    private void policy_Pane_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_policy_Pane_ButtonActionPerformed
        // TODO add your handling code here:
        Policy_whole_panel.setVisible(false);
        Value_Whole_panel.setVisible(true);
            ContactEditorUI.policyCount=0;
            this.addImageOnValuePanel(1);
        
    }//GEN-LAST:event_policy_Pane_ButtonActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
            Policy_whole_panel.setVisible(true);
            Value_Whole_panel.setVisible(false);
            ContactEditorUI.valuecount=0;
            this.addImageOnValuePanel(0);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
            addImageOnValuePanel(0);    //Code for next button
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
                System.out.println("Vinit Is Here====="+(ContactEditorUI.valuecount)); //code for prev button
                ContactEditorUI.valuecount-=2;
                addImageOnValuePanel(0);

    }//GEN-LAST:event_jButton7ActionPerformed
    
    
    private void Upload_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Upload_ButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser(System.getProperty("user.home"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println("File Picker == "+file.getPath());
            new UploadFile().upoloadFile(file, this);
        }
        
    }//GEN-LAST:event_Upload_ButtonActionPerformed


    
    private void generateMDPFromRandom(){
        policyCount=0;
        
        RandomizeMDP randomizer = new RandomizeMDP(60,5,20,this);
        
        int noofstate = randomizer.getNoOfStates();
        
        this.noofstate.setText(""+noofstate);
        
        int noofaction = randomizer.getNoOfActions();
        
        this.noofactions.setText(""+noofaction);
                        
        String str="";
        
        for(int i=0; i<randomizer.getAction_Per_State().size()-1;i++){
             str +=randomizer.getAction_Per_State().get(i)+" ";
        }
        str += randomizer.getAction_Per_State().get(randomizer.getAction_Per_State().size()-1);
        
        this.noofactiononeachstate.setText(str);
        
        str="";
        
        for(int i=0; i<randomizer.getNoOfActions()-1;i++){
             str +=randomizer.getRewards().get(i)+" ";
        }
        str += randomizer.getRewards().get(randomizer.getNoOfActions()-1);
        
        this.rewards.setText(str);
        
        
        str="";
        
        ArrayList<ArrayList<Double>> transition = randomizer.getTransition_Table();
        
        for(int i=0; i < randomizer.getNoOfActions();i++){
            for(int j=0;j<randomizer.getNoOfStates();j++){
                str += transition.get(i).get(j)+" ";
            }
            str =  str.substring(0,str.length()-1);
            str+="\n";
        }
        str =  str.substring(0,str.length()-1);
        
        this.transitiontable.setText(str);
        
    }
    
    
    private void DrawMDP(){
    	//System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MDP Editor");
    	MainFrameCaller mfc = new MainFrameCaller(this);
        int value = mfc.showMainFrame(this);
        System.out.println("Value now received is:" + value);
        if (value == 0) {
            Automaton automaton = mfc.getAutomaton();
            //System.out.println("File Picker == "+file.getPath());
            new ParseDrawnFigure().parseFigure(automaton, this);
        }
        
        //MainFrame win = new MainFrame(automaton);
        //win.setVisible(true);
    }
    
    
    
    
    private MDPData generateMDPFromGUI(){
        int noofstate=0;
        
        double gamma=0.0;
        
        int noofaction=0;
        
        try{
        	noofstate =  (Integer.parseInt(this.noofstate.getText()));
        }
        catch(NumberFormatException ex)
        {
        }
        
        gamma = .5;
        
        noofaction=(Integer.parseInt(this.noofactions.getText()));
        
        String []arr = rewards.getText().split(" ");
        
        int actionOnEachState[] = new int[noofstate];
                
        double reward[] = new double[noofaction];
        
        for(int i=0;i<noofaction;i++){
            reward[i] = Double.parseDouble(arr[i]);
        }
        
        String []brr = noofactiononeachstate.getText().split(" ");
        
        for(int i=0;i<noofstate;i++){
        	actionOnEachState[i] = Integer.parseInt(brr[i]);
        }

        double transitionMatrix[][]=new double[noofaction][noofstate];
        
        String [] lines = this.transitiontable.getText().split("\\n");
        
        for(int i=0;i<noofaction;i++){
            String crr[] = lines[i].split(" ");
            for(int j=0;j<noofstate;j++){
                transitionMatrix[i][j]=(Double.parseDouble(crr[j]));
            }
	}
        MDPData mdp = new MDPData(noofstate, noofaction, reward, transitionMatrix, actionOnEachState, gamma);
        return mdp;
    }
    
    public void addImageOnValuePanel(int mode){
        
       if((((((ContactEditorUI.valuecount+1))*3)<=mdp.getNoOfStates())&& mode ==0)||(((((ContactEditorUI.policyCount+1))*3)<=mdp.getNoOfStates())&& mode ==1)){
            if(mode==0){
                this.add_Image((ContactEditorUI.valuecount*3)+1,this.image1,mode);
                this.add_Image((ContactEditorUI.valuecount*3)+2,this.image2,mode);
                this.add_Image((ContactEditorUI.valuecount*3)+3,this.image3,mode);
                Action_sub_panel1.setVisible(true);
                Action_sub_panel2.setVisible(true);
                Action_sub_panel3.setVisible(true);
            }
            else{
                this.add_Image((ContactEditorUI.policyCount*3)+1,this.image4,mode);
                this.add_Image((ContactEditorUI.policyCount*3)+2,this.image5,mode);
                this.add_Image((ContactEditorUI.policyCount*3)+3,this.image6,mode);
                Action_sub_panel4.setVisible(true);
                Action_sub_panel5.setVisible(true);
                Action_sub_panel6.setVisible(true);
                
            }
        }
        
        else if (((((((ContactEditorUI.valuecount+1))*3)-mdp.getNoOfStates())==1)&& mode ==0)||((((((ContactEditorUI.policyCount+1))*3)-mdp.getNoOfStates())==1)&& mode ==1)){
            if(mode==0){
                this.add_Image((ContactEditorUI.valuecount*3)+1,this.image1,mode);
                this.add_Image((ContactEditorUI.valuecount*3)+2,this.image2,mode);
                this.add_Image((ContactEditorUI.valuecount)+3,this.image3,2);
                Action_sub_panel3.setVisible(true);
                Action_sub_panel1.setVisible(true);
                Action_sub_panel2.setVisible(true);

            }
            else{
                this.add_Image((ContactEditorUI.policyCount*3)+1,this.image4,mode);
                this.add_Image((ContactEditorUI.policyCount*3)+2,this.image5,mode);
                Action_sub_panel4.setVisible(true);
                Action_sub_panel5.setVisible(true);
                Action_sub_panel6.setVisible(false);
            }
        }
        else if (((((((ContactEditorUI.valuecount+1))*3)-mdp.getNoOfStates())==2)&& mode ==0)||((((((ContactEditorUI.policyCount+1))*3)-mdp.getNoOfStates())==2)&& mode ==1)){
           if(mode==0){
                this.add_Image((ContactEditorUI.valuecount*3)+1,this.image1,0);
                this.add_Image((ContactEditorUI.valuecount)+3,this.image2,2);
                Action_sub_panel2.setVisible(true);
                Action_sub_panel1.setVisible(true);
                Action_sub_panel3.setVisible(false);
            }
            else{
                this.add_Image((ContactEditorUI.policyCount*3)+1,this.image4,0);
                Action_sub_panel4.setVisible(true);
                Action_sub_panel5.setVisible(false);
                Action_sub_panel6.setVisible(false);

            }
        }
        
        else{
            
           if(mode==0){
                this.add_Image((ContactEditorUI.valuecount)+3,this.image1,2);
                Action_sub_panel1.setVisible(true);
                Action_sub_panel2.setVisible(false);
                Action_sub_panel3.setVisible(false);
                
            }
            else{
                Action_sub_panel4.setVisible(true);
                Action_sub_panel5.setVisible(false);
                Action_sub_panel6.setVisible(false);
            }
             //Action_sub_panel1.setVisible(true);
        }
        if(mode==0)
            ContactEditorUI.valuecount++;
        else
            ContactEditorUI.policyCount++;
            
    }
       
    private void Calculate_ButtonActionPerformed(java.awt.event.ActionEvent evt)throws Exception {
        // TODO add your handling code here:
        mdp= generateMDPFromGUI();
        
        GUI_Algo_Connector gui_con =  new GUI_Algo_Connector();
        
        System.out.println("IN CALCULATE");
        if(((String)Action_Combobox.getSelectedItem()).equals("Compute and Compare")){
        	jTabbedPane1.setEnabledAt(1, false);
            Compute_Optimal_panel.setVisible(false);
            jLayeredPane1.setEnabled(false);
            jTabbedPane1.setEnabledAt(2, true);
            Compare_panel.setVisible(true);
            jLayeredPane2.setEnabled(true);
            String algo1 = ((String)Algo1_ComboBox.getSelectedItem());
            String algo2 = ((String)Algo2_Combobox.getSelectedItem());
            algoLabel1.setText(algo1);
            algoLabel2.setText(algo2);
            //String path1="";
            //String path2="";
            System.out.println("Algo1=  "+algo1);
            System.out.println("Algo2=  "+algo2);
            if(algo1.equals("Simplex on dual")){
                path="Simplex";
                path1="Simplex";
                gui_con.connector(new MDPData(mdp),"simplex");
            }
            if(algo1.equals("Primal Affine")){
                path="Primal_Affine";
                path1 = "Primal_Affine";
                gui_con.connector(new MDPData(mdp),"primal affine");
            }
            if(algo1.equals("Mixed Algorithm")){
                path="MixStrategy";
                path1="MixStrategy";
                gui_con.connector(new MDPData(mdp),"cute algo");
            }
           if(algo1.equals("Value Iteration")){
                path="ValueIteration";
                path1="ValueIteration";
                gui_con.connector(new MDPData(mdp),"value iteration");

            }
            if(algo1.equals("Policy Iteration")){
                path="PolicyIteration";
                path1="PolicyIteration";
                gui_con.connector(new MDPData(mdp),"policy iteration");
            }
            
            if(algo2.equals("Simplex on dual"))
            {
                path="Simplex";
                path2="Simplex";
                gui_con.connector(new MDPData(mdp),"simplex");
            }
            
            if(algo2.equals("Primal Affine"))
            {
                path="Primal_Affine";
                path2 ="Primal_Affine";
                gui_con.connector(new MDPData(mdp),"primal affine");
            }
            if(algo2.equals("Mixed Algorithm"))
            {
                path="MixStrategy";
                path2="MixStrategy";
                gui_con.connector(new MDPData(mdp),"cute algo");
            }
           if(algo2.equals("Value Iteration"))
            {
                path="ValueIteration";
                path2="ValueIteration";
                gui_con.connector(new MDPData(mdp),"value iteration");

            }
           if(algo2.equals("Policy Iteration"))
            {
                path="PolicyIteration";
                path2="PolicyIteration";
                gui_con.connector(new MDPData(mdp),"policy iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Modified policy iteration"))
            {
                path="ModifiedPolicyIteration";
                path1="ModifiedPolicyIteration";                
                gui_con.connector(mdp,"Modified policy iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Bland simplex on dual"))
            {
                path = "Simplex_Bland";
                path1 = "Simplex_Bland";
                gui_con.connector(mdp,"Bland simplex on dual");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Single pivot policy iteration"))
            {
                path="SinglePivotPolicyIteration";
                path1="SinglePivotPolicyIteration";
                gui_con.connector(mdp,"Single pivot policy iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Single pivot modified policy iteration"))
            {
                path="SinglePivotModifiedPolicyIteration";
                path1="SinglePivotModifiedPolicyIteration";
                gui_con.connector(mdp,"Single pivot modified policy iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Least index policy iteration"))
            {
                path="LastIndexPivotPolicyIteration";
                path1="LastIndexPivotPolicyIteration";
                gui_con.connector(mdp,"Least index policy iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Least index modified policy iteration"))
            {
                path="LastIndexPivotModifiedPolicyIteration";
                path1="LastIndexPivotModifiedPolicyIteration";
                gui_con.connector(mdp,"Least index modified policy iteration");

            }
           
                   
           if(((String)Algo2_Combobox.getSelectedItem()).equals("Modified policy iteration"))
            {
                path="ModifiedPolicyIteration";
                path2="ModifiedPolicyIteration";
                gui_con.connector(mdp,"Modified policy iteration");

            }
           if(((String)Algo2_Combobox.getSelectedItem()).equals("Bland simplex on dual"))
            {
                path = "Simplex_Bland";
                path2 = "Simplex_Bland";
                gui_con.connector(mdp,"Bland simplex on dual");

            }
           if(((String)Algo2_Combobox.getSelectedItem()).equals("Single pivot policy iteration"))
            {
                path="SinglePivotPolicyIteration";
                path2="SinglePivotPolicyIteration";
                gui_con.connector(mdp,"Single pivot policy iteration");

            }
           if(((String)Algo2_Combobox.getSelectedItem()).equals("Single pivot modified policy iteration"))
            {
                path="SinglePivotModifiedPolicyIteration";
                path2="SinglePivotModifiedPolicyIteration";
                gui_con.connector(mdp,"Single pivot modified policy iteration");

            }
           if(((String)Algo2_Combobox.getSelectedItem()).equals("Least index policy iteration"))
            {
                path="LastIndexPivotPolicyIteration";
                path2="LastIndexPivotPolicyIteration";
                gui_con.connector(mdp,"Least index policy iteration");

            }
           if(((String)Algo2_Combobox.getSelectedItem()).equals("Least index modified policy iteration"))
            {
                path="LastIndexPivotModifiedPolicyIteration";
                path2="LastIndexPivotModifiedPolicyIteration";
                gui_con.connector(mdp,"Least index modified policy iteration");

            }
           
                             
            new FileMerger(path1,path2).merger();
            new Gnu_Script_Writer().compareScript(path1, path2);
            String fileExtension = OsUtils.getGnuPlotFileExtension();
            String pathSeparator = OsUtils.getPathSeparator();
            String totalPath = "";
            
            new Gnu_Script_Writer().generate_Images("compare Algorithm" +pathSeparator+"compare_Script." + fileExtension);
            //this.addCompareImage(path1,path2);
            
        }
        else
        {
        	jTabbedPane1.setEnabledAt(2, false);
            Compare_panel.setVisible(false);
            jLayeredPane2.setEnabled(false);
            jTabbedPane1.setEnabledAt(1, true);
            Compute_Optimal_panel.setVisible(true);
            jLayeredPane1.setEnabled(true);
            if(((String)Algo1_ComboBox.getSelectedItem()).equals("Simplex on dual"))
            {
                path="Simplex";
                gui_con.connector(mdp,"simplex");
            }
            if(((String)Algo1_ComboBox.getSelectedItem()).equals("Primal Affine"))
            {
                path="Primal_Affine";
                gui_con.connector(mdp,"primal affine");
            }
            if(((String)Algo1_ComboBox.getSelectedItem()).equals("Mixed Algorithm"))
            {
                path="MixStrategy";
                gui_con.connector(mdp,"cute algo");
            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Value Iteration"))
            {
                path="ValueIteration";
                gui_con.connector(mdp,"value iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Policy Iteration"))
            {
                path="PolicyIteration";
                gui_con.connector(mdp,"policy iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Modified policy iteration"))
            {
                path="ModifiedPolicyIteration";
                gui_con.connector(mdp,"Modified policy iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Bland simplex on dual"))
            {
                path = "Simplex_Bland";
                gui_con.connector(mdp,"Bland simplex on dual");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Single pivot policy iteration"))
            {
                path="SinglePivotPolicyIteration";
                gui_con.connector(mdp,"Single pivot policy iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Single pivot modified policy iteration"))
            {
                path="SinglePivotModifiedPolicyIteration";
                gui_con.connector(mdp,"Single pivot modified policy iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Least index policy iteration"))
            {
                path="LastIndexPivotPolicyIteration";
                gui_con.connector(mdp,"Least index policy iteration");

            }
           if(((String)Algo1_ComboBox.getSelectedItem()).equals("Least index modified policy iteration"))
            {
                path="LastIndexPivotModifiedPolicyIteration";
                gui_con.connector(mdp,"Least index modified policy iteration");

            }
           
                   
        }
        
        
    }//GEN-LAST:event_Calculate_ButtonActionPerformed

    private void randomMDP_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomMDP_buttonActionPerformed
        // TODO add your handling code here:
        generateMDPFromRandom();
    }//GEN-LAST:event_randomMDP_buttonActionPerformed

    private void DrawMDP_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_randomMDP_buttonActionPerformed
        // TODO add your handling code here:
        DrawMDP();
    }
    
    private void Policy_whole_panelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Policy_whole_panelFocusGained
        // TODO add your handling code here:
       // this.addImageOnValuePanel();
        
    }//GEN-LAST:event_Policy_whole_panelFocusGained

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        // TODO add your handling code here:
       this.addImageOnValuePanel(0);
        
    }//GEN-LAST:event_refreshActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
                System.out.println("Vinit Is Here====="+(ContactEditorUI.valuecount)); //code for prev button policy
                ContactEditorUI.policyCount-=2;
                addImageOnValuePanel(1);

        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                // TODO add your handling code here:
        
        this.addImageOnValuePanel(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
                ContactEditorUI.policyCount=0;
                addImageOnValuePanel(1);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void refresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refresh1ActionPerformed
        // TODO add your handling code here:
         //image7.setIcon(new javax.swing.ImageIcon("compare Algorithm\\compareval.jpeg"));
        comparecount=0;
        this.addCompareImage(path1,path2);
        comparecount++;
    }//GEN-LAST:event_refresh1ActionPerformed

    private void Policy_whole_panel1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Policy_whole_panel1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_Policy_whole_panel1FocusGained

    private void Next_compareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Next_compareActionPerformed
        // TODO add your handling code here:
        this.addCompareImage(path1,path2);
        comparecount++;        
    }//GEN-LAST:event_Next_compareActionPerformed

    private void prev_compareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prev_compareActionPerformed
        // TODO add your handling code here:
                comparecount--;
                this.addCompareImage(path1,path2);

    }//GEN-LAST:event_prev_compareActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ContactEditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ContactEditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ContactEditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ContactEditorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
					new ContactEditorUI().setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox Action_Combobox;
    private javax.swing.JPanel Action_sub_panel1;
    private javax.swing.JPanel Action_sub_panel2;
    private javax.swing.JPanel Action_sub_panel3;
    private javax.swing.JPanel Action_sub_panel4;
    private javax.swing.JPanel Action_sub_panel5;
    private javax.swing.JPanel Action_sub_panel6;
    private javax.swing.JPanel Action_sub_panel8;
    private javax.swing.JPanel Action_sub_panel9;
    private javax.swing.JComboBox Algo1_ComboBox;
    private javax.swing.JComboBox Algo2_Combobox;
    private javax.swing.JLabel algoLabel1;
    private javax.swing.JLabel algoLabel2;
    private javax.swing.JButton Calculate_Button;
    private javax.swing.JPanel Compare_panel;
    private javax.swing.JPanel Compute_Optimal_panel;
    private javax.swing.JPanel MDP_Input_panel;
    private javax.swing.JButton Next_compare;
    private javax.swing.JPanel Policy_whole_panel;
    private javax.swing.JPanel Policy_whole_panel1;
    private javax.swing.JPanel SelectedAlgo2_panel;
    private javax.swing.JButton Upload_Button;
    private javax.swing.JPanel Value_Whole_panel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel image1;
    private javax.swing.JLabel image2;
    private javax.swing.JLabel image3;
    private javax.swing.JLabel image4;
    private javax.swing.JLabel image5;
    private javax.swing.JLabel image6;
    private javax.swing.JLabel image7;
    private javax.swing.JLabel image8;
    private javax.swing.JLabel image9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField noofactiononeachstate;
    private javax.swing.JTextField noofactions;
    private javax.swing.JTextField noofstate;
    private javax.swing.JButton policy_Pane_Button;
    private javax.swing.JPanel policy_panel;
    private javax.swing.JPanel policy_panel1;
    private javax.swing.JPanel policy_panel2;
    private javax.swing.JButton prev_compare;
    private javax.swing.JButton randomMDP_button;
    private javax.swing.JButton DrawMDP_button;
    private javax.swing.JButton refresh;
    private javax.swing.JButton refresh1;
    private javax.swing.JTextField rewards;
    private javax.swing.JTextArea transitiontable;
    private javax.swing.JPanel valvsiterarion;
    
    // End of variables declaration//GEN-END:variables
}
