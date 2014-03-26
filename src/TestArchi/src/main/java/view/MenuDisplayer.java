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

import evaluator.Potentials;
import main.Board;
import main.Engine;

@SuppressWarnings("serial")
public class MenuDisplayer extends JPanel implements ItemListener, MouseListener {

	private BoardDisplayer boardDisplayer;
	
	private JCheckBox displayCom0;
	private JCheckBox displayCom1;
	
	private JFileChooser fileChooser;
	private JButton loadBoardBtn;
	
	private JButton displayUnitBtn;
	private JButton displayAttackBtn;
	private JButton displayDefenceBtn;
	private JButton displayPrevailing1Btn;
	private JButton displayPrevailing2Btn;
	
	public MenuDisplayer(BoardDisplayer b){
		boardDisplayer = b;
		
		initUI();
		initKeyBinding();		
	}

	private void initUI(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
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
		
		displayUnitBtn = new JButton("Unit");
		displayUnitBtn.setFocusable(false);
		displayAttackBtn = new JButton("Attack");
		displayAttackBtn.setFocusable(false);
		displayDefenceBtn = new JButton("Defence");
		displayDefenceBtn.setFocusable(false);
		displayPrevailing1Btn = new JButton("Prevailing team blue");
		displayPrevailing1Btn.setFocusable(false);
		displayPrevailing2Btn = new JButton("Prevailing team red");
		displayPrevailing2Btn.setFocusable(false);
				
		displayUnitBtn.addMouseListener(this);
		displayAttackBtn.addMouseListener(this);
		displayDefenceBtn.addMouseListener(this);
		displayPrevailing1Btn.addMouseListener(this);
		displayPrevailing2Btn.addMouseListener(this);
		
		displayModePanel.add(displayUnitBtn);
		displayModePanel.add(displayAttackBtn);
		displayModePanel.add(displayDefenceBtn);
		displayModePanel.add(displayPrevailing1Btn);
		displayModePanel.add(displayPrevailing2Btn);
		
		this.add(loadBoardBtn);
		this.add(displayCom0);
		this.add(displayCom1);
		this.add(displayModePanel);
	}
	
	private void initKeyBinding(){
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Quit");
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, 0), "DisplayCom0");
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0), "DisplayCom1");
		this.getInputMap().put(KeyStroke.getKeyStroke("U"), "DisplayUnits");
		this.getInputMap().put(KeyStroke.getKeyStroke("A"), "DisplayAttack");
		this.getInputMap().put(KeyStroke.getKeyStroke("D"), "DisplayDefence");
		
		Action doQuit = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
		    }
		};
		
		Action doDisplayCom0 = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				boardDisplayer.switchPOComs();
				displayCom0.setSelected(boardDisplayer.getP0Coms());
				boardDisplayer.displayGUI();
		    }
		};
		
		Action doDisplayCom1 = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				boardDisplayer.switchP1Coms();
				displayCom1.setSelected(boardDisplayer.getP1Coms());
				boardDisplayer.displayGUI();
		    }
		};
		
		Action doDisplayUnits = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_UNITS);
				boardDisplayer.displayGUI();
		    }
		};
		
		Action doDisplayAttack = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_ATTACK);
				boardDisplayer.displayGUI();
		    }
		};
		
		Action doDisplayDefence = new AbstractAction(){
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

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		
		if (source == displayUnitBtn) {
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_UNITS);
			boardDisplayer.displayGUI();
		}else if (source == displayAttackBtn) {
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_ATTACK);
			boardDisplayer.displayGUI();
		}else if (source == displayDefenceBtn) {
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_DEFENCE);
			boardDisplayer.displayGUI();
		}else if (source == displayPrevailing1Btn) {
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_PREVAILING1);
			boardDisplayer.displayGUI();
		}else if (source == displayPrevailing2Btn) {
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_PREVAILING2);
			boardDisplayer.displayGUI();
		}else if (source == loadBoardBtn) {
			int returnVal = fileChooser.showOpenDialog(MenuDisplayer.this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                Board b = boardDisplayer.getBoard();
                b.resetBoard();
                
                Engine engine = new Engine(b);
                
            	engine.placeFixedEntities();
            	b.loadBoardWithFile(file.getAbsolutePath());
            	engine.computeCommunications();
              	engine.computePossibleMoves();
              	engine.computeAttackDefence();
              	
              	Potentials p = new Potentials(b);
              	p.computePotentials();
              	engine.computeDeath();
              	
              	boardDisplayer.setPotential(p);
              	
              	boardDisplayer.drawEntities();
              	
              	boardDisplayer.displayGUI();
            }
			 
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e){}
}
