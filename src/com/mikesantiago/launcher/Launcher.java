package com.mikesantiago.launcher;

import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

public class Launcher extends JFrame 
{
	public Launcher()
	{
		setTitle("Java RPG Engine Launcher");
		getContentPane().setLayout(null);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setBounds(6, 6, 568, 254);
		try 
		{
			editorPane.setPage("http://mrmiketheripper.x10.mx/rpg");
		}
		catch (IOException e) 
		{
				editorPane.setContentType("text/html");
				editorPane.setText("<html>Could not load</html>");
		} 
		getContentPane().add(editorPane);
		
	}
}
