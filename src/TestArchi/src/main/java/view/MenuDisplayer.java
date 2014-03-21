package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuDisplayer extends JPanel implements ItemListener, KeyListener {

	private BoardDisplayer boardDisplayer;
	
	private JCheckBox displayCom0;
	private JCheckBox displayCom1;
	
	public MenuDisplayer(BoardDisplayer b){
		boardDisplayer = b;
		
		initUI();
	}

	
	private void initUI(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.addKeyListener(this);
		this.setFocusable(true);
		
		displayCom0 = new JCheckBox("Communication blue player", true);
		displayCom1 = new JCheckBox("Communication red player", true);
		
		displayCom0.addItemListener(this);
		displayCom1.addItemListener(this);
		
		this.add(displayCom0);
		this.add(displayCom1);
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
				boardDisplayer.displayGUI();
				break;
			case KeyEvent.VK_NUMPAD1:
				boardDisplayer.switchP1Coms();
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
}
