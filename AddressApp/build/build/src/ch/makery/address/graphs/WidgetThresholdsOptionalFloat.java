package ch.makery.address.graphs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A widget that lets the user make minimum and maximum values be automatic or specified. 
 */
@SuppressWarnings("serial")
public class WidgetThresholdsOptionalFloat extends JPanel {
	
	JLabel levelOneLabel;
	JLabel levelTwoLabel;
	JLabel levelThreeLabel; 
	
	JCheckBox useDefaultCheckbox;
	JCheckBox minCheckbox;
	
	JTextField levelOneTextfield;
	JTextField levelTwoTextfield;
	JTextField levelThreeTextfield; 
	
	float upperLimit;
	float lowerLimit;
	
	float defaultLevelOne;
	float defaultLevelTwo;
	float defaultLevelThree;
	
	/*
	JLabel levelOneLabel;
	JLabel levelTwoLabel;
	JCheckBox useDefaultCheckbox;
	//JCheckBox minCheckbox;
	JTextField levelOneTextfield;
	JTextField levelTwoTextfield;
//??	float upperLimit;
//??	float lowerLimit;
	float defaultLevelOne;
	float defaultLevelTwo;*/
	
	/**
	 * @param labelPrefix       Text to show before "Maximum" or "Minimum"
	 * @param defaultLevelOne    Default value for level one
	 * @param defaultLevelTwo    Default value for level two
	 * @param defaultLevelThree  Default value for level three
	 * @param lowerLimit        Minimum allowed value.
	 * @param upperLimit        Maximum allowed value.
	 */
	public WidgetThresholdsOptionalFloat(float defaultLevelOne, float defaultLevelTwo, float defaultLevelThree, float lowerLimit, float upperLimit) {
		
		super();
		
		levelOneLabel = new JLabel(" Level One: ");
		levelTwoLabel = new JLabel(" Level Two: ");
		levelThreeLabel = new JLabel( " Level Three: "); 
		
		useDefaultCheckbox = new JCheckBox("Use Defaults");
		//minCheckbox = new JCheckBox("Automatic");
		useDefaultCheckbox.setSelected(true);
		//minCheckbox.setSelected(true);
		
		levelOneTextfield = new JTextField(Float.toString(defaultLevelTwo));
		levelTwoTextfield = new JTextField(Float.toString(defaultLevelOne));
		levelThreeTextfield  = new JTextField(Float.toString(defaultLevelThree));
		
		levelOneTextfield.setEnabled(false);
		levelTwoTextfield.setEnabled(false);
		levelThreeTextfield.setEnabled(false); 
		
		this.upperLimit = upperLimit;
		this.lowerLimit = lowerLimit;
		
		this.defaultLevelOne = defaultLevelTwo;
		this.defaultLevelTwo = defaultLevelOne;
		this.defaultLevelThree = defaultLevelThree; 
		
		
		useDefaultCheckbox.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent ae) {
				levelOneTextfield.setEnabled(!useDefaultCheckbox.isSelected());
				levelTwoTextfield.setEnabled(!useDefaultCheckbox.isSelected());
				levelThreeTextfield.setEnabled(!useDefaultCheckbox.isSelected());
			}
		});
		
		/*minCheckbox.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent ae) {
				levelTwoTextfield.setEnabled(!minCheckbox.isSelected());
			}
		});*/
		
		levelOneTextfield.addFocusListener(new FocusListener() {
			@Override public void focusLost(FocusEvent fe) {
				sanityCheckLevelHierarchy();
			}
			
			@Override public void focusGained(FocusEvent e) {
				levelOneTextfield.selectAll();
			}
		});
		
		levelTwoTextfield.addFocusListener(new FocusListener() {
			@Override public void focusLost(FocusEvent fe) {
				sanityCheckLevelHierarchy();
			}
			
			@Override public void focusGained(FocusEvent e) {
				levelTwoTextfield.selectAll();
			}
		});
		
		levelThreeTextfield.addFocusListener(new FocusListener() {
			@Override public void focusLost(FocusEvent fe) {
				sanityCheckLevelHierarchy();
			}
			
			@Override public void focusGained(FocusEvent e) {
				levelThreeTextfield.selectAll();
			}
		});
		
		setLayout(new GridLayout(3, 3, 10, 10));
		
		JPanel levelOnePanel = new JPanel();
		levelOnePanel.setLayout(new BoxLayout(levelOnePanel, BoxLayout.X_AXIS));
		levelOnePanel.add(useDefaultCheckbox);
		levelOnePanel.add(Box.createHorizontalStrut(10));
		levelOnePanel.add(levelOneTextfield);
		add(levelOneLabel);
		add(levelOnePanel);
		
		JPanel levelTwoPanel = new JPanel();
		levelTwoPanel.setLayout(new BoxLayout(levelTwoPanel, BoxLayout.X_AXIS));
	//	levelTwoPanel.add(minCheckbox);
		levelTwoPanel.add(Box.createHorizontalStrut(10));
		levelTwoPanel.add(levelTwoTextfield);
		add(levelTwoLabel);
		add(levelTwoPanel);
		
		JPanel levelThreePanel = new JPanel();
		levelThreePanel.setLayout(new BoxLayout(levelThreePanel, BoxLayout.X_AXIS));
	//	levelTwoPanel.add(minCheckbox);
		levelThreePanel.add(Box.createHorizontalStrut(10));
		levelThreePanel.add(levelThreeTextfield);
		add(levelThreeLabel);
		add(levelThreePanel);
		
	}
	
	/**
	 * Ensures that levelOne < levelTwo.
	 */
	private void sanityCheckLevelHierarchy() {
		
		try {
			
			levelOneTextfield.setText(levelOneTextfield.getText().trim());
			levelTwoTextfield.setText(levelTwoTextfield.getText().trim());
			levelThreeTextfield.setText(levelThreeTextfield.getText().trim()); 
			
			float levelOne = Float.parseFloat(levelOneTextfield.getText());
			float levelTwo = Float.parseFloat(levelTwoTextfield.getText());
			float levelThree = Float.parseFloat(levelThreeTextfield.getText()); 
			
			//TODO change this check to show proper errors when level entered does not make sense.(low priority) 
			
			/*// clip to limits
			if(levelOne > upperLimit) levelOne = defaultLevelOne;
			if(levelOne < lowerLimit) levelOne = lowerLimit;
			
			if(levelTwo > upperLimit || levelTwo < lowerLimit ) levelTwo = defaultLevelTwo;
			//if(levelTwo < lowerLimit) levelTwo = defaultLevelTwo;
			
			if(levelThree > upperLimit) levelThree = upperLimit;
			if(levelThree < lowerLimit) levelThree = defaultLevelThree;
			
			// ensure level one < level two 
			if(levelTwo == levelOne) {
				levelOne = defaultLevelOne; 
				levelTwo = defaultLevelTwo;
			} else if(levelOne > levelTwo) {
				float temp = levelOne;
				levelOne = levelTwo;
				levelTwo = temp;
			}
			*/ 
			// update textfields
			levelOneTextfield.setText(Float.toString(levelOne));
			levelTwoTextfield.setText(Float.toString(levelTwo));
			levelThreeTextfield.setText(Float.toString(levelThree));
			
		} catch(Exception e) {
			
			// one of the textfields doesn't contain a valid number, so reset both to defaults
			levelOneTextfield.setText(Float.toString(defaultLevelOne));
			levelTwoTextfield.setText(Float.toString(defaultLevelTwo));
			levelThreeTextfield.setText(Float.toString(defaultLevelThree));
		}
		
	}
	
	/**
	 * @return    True if the maximum should be automatic.
	 */
	public boolean useDefault() {
		
		return useDefaultCheckbox.isSelected();
		
	}
	
	
	 /** 
	 * @return    The level one value.
	 */
	public float getLevelOneValue() {
		
		return Float.parseFloat(levelOneTextfield.getText());
		
	}
	
	 /** 
	 * @return    The level two value.
	 */
	public float getLevelTwoValue() {
		
		return Float.parseFloat(levelTwoTextfield.getText());
		
	}
	
	 /** 
	 * @return    The level three value.
	 */
	public float getLevelThreeValue() {
			
		return Float.parseFloat(levelThreeTextfield.getText());
	
	}

}
