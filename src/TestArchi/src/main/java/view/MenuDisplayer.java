package view;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuDisplayer extends JPanel implements ItemListener, KeyListener, MouseListener {

	private BoardDisplayer boardDisplayer;
	
	private JCheckBox displayCom0;
	private JCheckBox displayCom1;
	
	private JButton displayUnitBtn;
	private JButton displayAttackBtn;
	private JButton displayDefenceBtn;
	
	public MenuDisplayer(BoardDisplayer b){
		boardDisplayer = b;
		
		initUI();
	}

	
	/*
	 * TODO
	 * Center element in this JPanel
	 * Fix a correct size to the 3 buttons
	 */
	private void initUI(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.addKeyListener(this);
		this.setFocusable(true);
		
		displayCom0 = new JCheckBox("Communication blue player", true);
		displayCom1 = new JCheckBox("Communication red player", true);
		
		displayCom0.addItemListener(this);
		displayCom1.addItemListener(this);
		
		JPanel displayModePanel = new JPanel();
		displayModePanel.setLayout(new GridLayout(3, 1));
		displayModePanel.setBorder(BorderFactory.createTitledBorder("Display mode"));
		
		displayUnitBtn = new JButton("Unit");
		displayAttackBtn = new JButton("Attack");
		displayDefenceBtn = new JButton("Defence");
		
		displayUnitBtn.addMouseListener(this);
		displayAttackBtn.addMouseListener(this);
		displayDefenceBtn.addMouseListener(this);
		
		displayModePanel.add(displayUnitBtn);
		displayModePanel.add(displayAttackBtn);
		displayModePanel.add(displayDefenceBtn);
		
		
		
		
		this.add(displayCom0);
		this.add(displayCom1);
		this.add(displayModePanel);
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

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}


	@Override
	public void keyReleased(KeyEvent e) {
				
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_NUMPAD0:
				boardDisplayer.switchPOComs();
				displayCom0.setSelected(boardDisplayer.getP0Coms());
				boardDisplayer.displayGUI();
				break;
			case KeyEvent.VK_NUMPAD1:
				boardDisplayer.switchP1Coms();
				displayCom1.setSelected(boardDisplayer.getP1Coms());
				boardDisplayer.displayGUI();
				break;
			case KeyEvent.VK_U:
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_UNITS);
				boardDisplayer.displayGUI();
				break;
			case KeyEvent.VK_A:
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_ATTACK);
				boardDisplayer.displayGUI();
				break;
			case KeyEvent.VK_D:
				boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_DEFENCE);
				boardDisplayer.displayGUI();
				break;
		}
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		
		if (source == displayUnitBtn){
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_UNITS);
			boardDisplayer.displayGUI();
		}else if (source == displayAttackBtn){
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_ATTACK);
			boardDisplayer.displayGUI();
		}else if (source == displayDefenceBtn){
			boardDisplayer.setDisplayMode(BoardDisplayer.DISPLAY_DEFENCE);
			boardDisplayer.displayGUI();
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e){}
}
