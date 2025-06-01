package client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//import toysassembler.Catalogue;

public class ClientAgentGui extends JFrame {

	private ClientAgent cAgent;
	private JComboBox noc;
	private JButton okButton;

	public ClientAgentGui(ClientAgent clientAgent)
	throws HeadlessException {
		super("Client Agent Gui");
		this.cAgent = clientAgent;
		addWindowListener(new	WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ClientAgentGui.this.cAgent.doDelete();
			}
		} );
	}

	public void initGui(){
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new GridBagLayout());
		rootPanel.setMinimumSize(new Dimension(330, 125));
		rootPanel.setPreferredSize(new Dimension(330, 125));

		JLabel l = new JLabel("Number of Calls:");
		l.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
		rootPanel.add(l, gridBagConstraints);

		noc = new JComboBox();
		noc.setMinimumSize(new Dimension(222, 20));
		noc.setPreferredSize(new Dimension(222, 20));

//		Iterator it = Catalogue.getToyTypes();
//		while (it.hasNext()) {
//			noc.addItem((String) it.next());
//		}
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 3, 0, 3);
		rootPanel.add(noc, gridBagConstraints);

		okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
//				String type = noc.getSelectedItem().toString();
//				cAgent.invocation();
			cAgent.deployment();
			}
		} );
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
		rootPanel.add(okButton, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
		gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);

		this.add(rootPanel);
		pack();
	}
	
	public void showMessage(String message,String boxTitle, int dialogType) {
		JOptionPane.showMessageDialog(this,message,boxTitle,dialogType);
	}
}
