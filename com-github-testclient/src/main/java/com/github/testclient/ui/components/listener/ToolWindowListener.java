package com.github.testclient.ui.components.listener;

import java.awt.event.WindowAdapter;

import javax.swing.JOptionPane;

public class ToolWindowListener extends WindowAdapter{

	public ToolWindowListener()
	{
	}

	@Override
	public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		int result = JOptionPane.showConfirmDialog(null, 
				"Are you sure to close this window?\n"
						+ "Click YES to exit.\n"
						+ "", "Really Closing?", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

		if (result == JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}
		else if(result == JOptionPane.NO_OPTION)
		{
//			System.exit(0);
		}

	}
}
