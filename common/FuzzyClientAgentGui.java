package common;


// Import required Java classes 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import jade.core.*;


/**

 */
public class FuzzyClientAgentGui extends JFrame implements ActionListener
{
  private FuzzyClientAgent myAgent;
  //private LocationTableModel visitedSiteListModel;
  //private JTable            visitedSiteList;
  //private LocationTableModel availableSiteListModel;
  //private JTable            availableSiteList;
  private JTextField executionMode; 
  private JTextField invocationRate;

  private static String START = "Start";

//  private JTextArea jTextArea1 = new JTextArea();

    
	// Constructor
  
  	public FuzzyClientAgentGui() {
      try {
          
//          System.out.println("In the first Constructor");
      } catch (Exception e) {
          e.printStackTrace();
      }
  	}
  	FuzzyClientAgentGui(FuzzyClientAgent fuzzyClientAgent){
		super();
		this.myAgent = fuzzyClientAgent;

		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FuzzyClientAgentGui.this.myAgent.doDelete();
			}
		} );
	}

  	public void initGui(){
  		
//  	   this.setSize(new Dimension(400, 107));

  		setTitle("GUI of "+myAgent.getLocalName());
        setSize(605,75);

		////////////////////////////////
		// Set GUI window layout manager
	
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));

		JPanel counterPanel = new JPanel();
		counterPanel.setLayout(new BoxLayout(counterPanel, BoxLayout.X_AXIS));

		JLabel counterLabel1 = new JLabel("Execution Mode");
		executionMode = new JTextField();
		
		JLabel counterLabel2 = new JLabel("Rate of Invocations ");
		invocationRate = new JTextField();
		
        JButton startButton = new JButton(START);
        startButton.addActionListener(this);

          main.add(counterPanel);

        //counterPanel.add(continueButton);
        counterPanel.add(counterLabel1);
        counterPanel.add(executionMode);
        counterPanel.add(counterLabel2);
        counterPanel.add(invocationRate);        
        counterPanel.add(startButton);
//        pack();
        // Add the list of visited sites to the CENTER part 
//        JPanel visitedPanel = new JPanel();
//        visitedPanel.setLayout(new BoxLayout(visitedPanel, BoxLayout.X_AXIS));
//        jTextArea1.setBounds(new Rectangle(180, 265, 280, 170));
//        jTextArea1.setAutoscrolls(true);
//        //jTextArea1.setEditable(false);
//        jTextArea1.setRows(35);
//        JScrollPane pane = new JScrollPane();
//        pane.getViewport().setView(jTextArea1);
//        visitedPanel.add(pane,BorderLayout.CENTER);
//        visitedPanel.setBorder(BorderFactory.createTitledBorder("All Results Received From Traveller Agent"));
//        visitedPanel.add(jTextArea1, null);
//        main.add(visitedPanel);
        
        getContentPane().add(main, BorderLayout.CENTER);
//        setExecutionMode(2);
        setInvocationRate(0);

  	}

//    public int getExecutionMode(){
//
//        return new Integer(executionMode.getText()).intValue();
//    //counterText.fireActionPerformed();
//    }
    
//    public void setExecutionMode(int em){
//    	executionMode.setText(new Integer(em).toString());
//    }
    
    public int getInvocationRate(){
        return new Integer(executionMode.getText()).intValue();
        //counterText.fireActionPerformed();
    }
    
    public void setInvocationRate(double roi){
    	invocationRate.setText(new Double(roi).toString());
    }
    
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
	
		if (command.equalsIgnoreCase(START)) {
//			myAgent.callback(getInvocationNumber(),getInvocationRate());
			myAgent.invocation();
	    }
	}
	
	void showCorrect()
	{
		///////////////////////////////////////////
		// Arrange and display GUI window correctly
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		show();
	}
	
	public void addVisitedSite(Location site)
	{
//		visitedSiteListModel.add(site);
//		visitedSiteListModel.fireTableDataChanged();

	}
//    public void setAllResults(String allResults){
//        this.jTextArea1.setText(allResults); 
//    
//    }
//    
//    public void clearTeaxtArea1(){
//        this.jTextArea1.setText(""); 
//    
//    }
}
