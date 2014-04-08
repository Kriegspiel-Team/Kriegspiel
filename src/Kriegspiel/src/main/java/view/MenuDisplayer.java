package view;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.BoardController;

/**
 * The Menu Displayer.
 */
@SuppressWarnings("serial")
public class MenuDisplayer extends JPanel implements ItemListener, MouseListener {
	
	/** The controller. */
	private BoardController controller;

	/** The board displayer. */
	private BoardDisplayer boardDisplayer;
	
	/** The checkbox to choose whether to display com lines for player 0 or not. */
	private JCheckBox displayCom0;
	
	/** The checkbox to choose whether to display com lines for player 0 or not. */
	private JCheckBox displayCom1;
	
	/** The file chooser. */
	private JFileChooser fileChooser;
	
	/** The load board button. */
	private JButton loadBoardBtn;
	
	/** The display unit button. */
	private JButton displayUnitBtn;
	
	/** The display attack button. */
	private JButton displayAttackBtn;
	
	/** The display defence button. */
	private JButton displayDefenceBtn;
	
	/** The display defence minus attack button. */
	private JButton displayDefenceMinusAttackBtn;
	
	/** The display attackEvaluator0 button. */
	private JButton displayAttackEvaluator0Btn;
	
	/** The display defenceEvaluator0 button. */
	private JButton displayDefenceEvaluator0Btn;
	
	/** The display defenceEvaluatorMinusAttack0 button. */
	private JButton displayDefenceMinusAttackEvaluator0Btn;
	
	/** The display attackEvaluator1 button. */
	private JButton displayAttackEvaluator1Btn;
	
	/** The display defenceEvaluator1 button. */
	private JButton displayDefenceEvaluator1Btn;
	
	/** The display defenceEvaluatorMinusAttack1 button. */
	private JButton displayDefenceMinusAttackEvaluator1Btn;
	
	/**
	 * Instantiates a new menu displayer.
	 *
	 * @param b the BoardDisplayer to attach to
	 * @param controller the controller
	 */
	public MenuDisplayer(BoardDisplayer b, BoardController controller){
		this.controller = controller;
		boardDisplayer = b;
		
		initUI();
		initKeyBinding();		
	}

	/**
	 * Initializes the UI.
	 */
	private void initUI(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Maps and Units", "kmp", "ksv");
		fileChooser.setFileFilter(filter);
		
		loadBoardBtn = new JButton("Load a board...");
		loadBoardBtn.setFocusable(false);
		loadBoardBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadBoardBtn.addMouseListener(this);
		
		displayCom0 = new JCheckBox("Communication blue player", true);
		displayCom0.setFocusable(false);
		displayCom1 = new JCheckBox("Communication red player", true);
		displayCom1.setFocusable(false);
		displayCom0.setAlignmentX(Component.CENTER_ALIGNMENT);
		displayCom1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		displayCom0.addItemListener(this);
		displayCom1.addItemListener(this);
		
		JPanel displayModePanel = new JPanel();

		displayModePanel.setLayout(new GridLayout(15, 1));
		displayModePanel.setBorder(BorderFactory.createTitledBorder("Display mode"));
		
		displayUnitBtn = new JButton("Units");
		displayUnitBtn.setFocusable(false);
		displayAttackBtn = new JButton("Attack");
		displayAttackBtn.setFocusable(false);
		displayDefenceBtn = new JButton("Defence");
		displayDefenceBtn.setFocusable(false);
		displayDefenceMinusAttackBtn = new JButton("Fighting score");
		displayDefenceMinusAttackBtn.setFocusable(false);
		displayAttackEvaluator0Btn = new JButton("Red Dangerous zone");
		displayAttackEvaluator0Btn.setFocusable(false);
		displayAttackEvaluator1Btn = new JButton("Blue Dangerous zone");
		displayAttackEvaluator1Btn.setFocusable(false);
		
		displayDefenceEvaluator0Btn = new JButton("Blue safe zone");
		displayDefenceEvaluator0Btn.setFocusable(false);
		displayDefenceMinusAttackEvaluator0Btn = new JButton("Blue Fighting Score modifiers");
		displayDefenceMinusAttackEvaluator0Btn.setFocusable(false);
		displayDefenceEvaluator1Btn = new JButton("Red safe zone");
		displayDefenceEvaluator1Btn.setFocusable(false);
		displayDefenceMinusAttackEvaluator1Btn = new JButton("Red FIghting Score modifiers");
		displayDefenceMinusAttackEvaluator1Btn.setFocusable(false);
				
		displayUnitBtn.addMouseListener(this);
		displayAttackBtn.addMouseListener(this);
		displayDefenceBtn.addMouseListener(this);
		displayDefenceMinusAttackBtn.addMouseListener(this);
		displayAttackEvaluator0Btn.addMouseListener(this);
		displayAttackEvaluator1Btn.addMouseListener(this);
		displayDefenceEvaluator0Btn.addMouseListener(this);
		displayDefenceMinusAttackEvaluator0Btn.addMouseListener(this);
		displayDefenceEvaluator1Btn.addMouseListener(this);
		displayDefenceMinusAttackEvaluator1Btn.addMouseListener(this);
		
		displayModePanel.add(displayUnitBtn);
		displayModePanel.add(displayAttackBtn);
		displayModePanel.add(displayDefenceBtn);
		displayModePanel.add(displayDefenceMinusAttackBtn);
		displayModePanel.add(displayAttackEvaluator0Btn);
		displayModePanel.add(displayDefenceEvaluator1Btn);
		displayModePanel.add(displayAttackEvaluator1Btn);
		displayModePanel.add(displayDefenceEvaluator0Btn);
		displayModePanel.add(displayDefenceMinusAttackEvaluator0Btn);
		displayModePanel.add(displayDefenceMinusAttackEvaluator1Btn);
		
		this.add(loadBoardBtn);
		this.add(displayCom0);
		this.add(displayCom1);
		this.add(displayModePanel);
	}
	
	/**
	 * Inits the key bindings.
	 */
	private void initKeyBinding(){
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Quit");
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, 0), "DisplayCom0");
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0), "DisplayCom1");
		this.getInputMap().put(KeyStroke.getKeyStroke("U"), "DisplayUnits");
		this.getInputMap().put(KeyStroke.getKeyStroke("A"), "DisplayAttack");
		this.getInputMap().put(KeyStroke.getKeyStroke("D"), "DisplayDefence");
		
		Action doQuit = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
		    }
		};
		
		Action doDisplayCom0 = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(displayCom0.isEnabled())
				{
					boardDisplayer.switchPOComs();
					displayCom0.setSelected(boardDisplayer.getP0Coms());
					boardDisplayer.displayGUI();
				}
		    }
		};
		
		Action doDisplayCom1 = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(displayCom1.isEnabled())
				{
					boardDisplayer.switchP1Coms();
					displayCom1.setSelected(boardDisplayer.getP1Coms());
					boardDisplayer.displayGUI();
				}
		    }
		};
		
		Action doDisplayUnits = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_UNITS);
				boardDisplayer.displayGUI();
		    }
		};
		
		Action doDisplayAttack = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_ATTACK);
				boardDisplayer.displayGUI();
		    }
		};
		
		Action doDisplayDefence = new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_DEFENCE);
				boardDisplayer.displayGUI();
		    }
		};
		
		this.getActionMap().put("Quit", doQuit);
		this.getActionMap().put("DisplayCom0", doDisplayCom0);
		this.getActionMap().put("DisplayCom1", doDisplayCom1);
		this.getActionMap().put("DisplayUnits", doDisplayUnits);
		this.getActionMap().put("DisplayAttack", doDisplayAttack);
		this.getActionMap().put("DisplayDefence", doDisplayDefence);
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		Object source = e.getItemSelectable();
		int newState = e.getStateChange();
		boolean selected = false;
		if (newState == ItemEvent.DESELECTED)
			selected = false;
		else if (newState == ItemEvent.SELECTED)
			selected = true;
				
		if (source == displayCom0){
			boardDisplayer.setP0Coms(selected);
		}else if(source == displayCom1){
			boardDisplayer.setP1Coms(selected);
		}
		
		boardDisplayer.displayGUI();
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		
		if (source == displayUnitBtn) {
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_UNITS);
			enableCheckboxes();
			boardDisplayer.displayGUI();
		}else if (source == displayAttackBtn) {
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_ATTACK);
			enableCheckboxes();
			boardDisplayer.displayGUI();
		}else if (source == displayDefenceBtn) {
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_DEFENCE);
			enableCheckboxes();
			boardDisplayer.displayGUI();
		}else if (source == displayDefenceMinusAttackBtn) {
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_DEFENCE_MINUS_ATTACK);
			disableCheckboxes();
			boardDisplayer.displayGUI();
		}else if (source == loadBoardBtn) {
			int returnVal = fileChooser.showOpenDialog(MenuDisplayer.this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String extension = "";
                String fileName = file.getPath();
                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    extension = fileName.substring(i+1);
                }
                if(extension.equals("ksv"))
                	controller.loadNewBoard(file.getAbsolutePath());
                else
                	if(extension.equals("kmp"))
                		controller.loadNewMap(file.getAbsolutePath());
            }
		}else if (source == displayAttackEvaluator0Btn || source == displayAttackEvaluator1Btn 
				|| source == displayDefenceEvaluator0Btn || source == displayDefenceMinusAttackEvaluator0Btn 
				|| source == displayDefenceEvaluator1Btn || source == displayDefenceMinusAttackEvaluator1Btn) {
			disableCheckboxes();
			if (source == displayAttackEvaluator0Btn)
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_ATTACK_EVAL_TEAM0);
				
			else if (source == displayAttackEvaluator1Btn) 
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_ATTACK_EVAL_TEAM1);
				
			else if (source == displayDefenceEvaluator0Btn) 
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_DEFENCE_EVAL_TEAM0);
				
			else if (source == displayDefenceMinusAttackEvaluator0Btn) 
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_DEFENCE_MINUS_ATTACK_EVAL_TEAM0);
				
			else if (source == displayDefenceEvaluator1Btn) 
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_DEFENCE_EVAL_TEAM1);
				
			else if (source == displayDefenceMinusAttackEvaluator1Btn) 
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_DEFENCE_MINUS_ATTACK_EVAL_TEAM1);
				
			if(boardDisplayer.getP0Coms())
				boardDisplayer.switchPOComs();
			
			if(boardDisplayer.getP1Coms())
				boardDisplayer.switchP1Coms();
			
			displayCom0.setSelected(false);
			displayCom1.setSelected(false);
			boardDisplayer.displayGUI();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e){}
	
	private void disableCheckboxes()
	{
		displayCom0.setSelected(false);
		displayCom0.setEnabled(false);
		displayCom1.setSelected(false);
		displayCom1.setEnabled(false);
	}
	
	private void enableCheckboxes()
	{
		displayCom0.setEnabled(true);
		displayCom1.setEnabled(true);
	}
}
