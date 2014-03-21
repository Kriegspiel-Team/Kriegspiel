package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuDisplayer extends JPanel implements ItemListener {

	private BoardDisplayer boardDisplayer;
	
	private JCheckBox displayCom0;
	private JCheckBox displayCom1;
	
	public MenuDisplayer(BoardDisplayer b){
		boardDisplayer = b;
		
		initUI();
	}

	
	private void initUI(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
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
}
