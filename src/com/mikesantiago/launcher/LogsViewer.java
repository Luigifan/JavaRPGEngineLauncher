package com.mikesantiago.launcher;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JEditorPane;

import java.awt.Window.Type;
import java.awt.Toolkit;

import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Font;

public class LogsViewer extends JFrame {

	private JPanel contentPane;

	public LogsViewer(String serrLogs, String sinfoLogs) 
	{
		setTitle("Logs Viewer");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LogsViewer.class.getResource("/com/mikesantiago/launcher/res/Icon.png")));
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 468, 458);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Error Logs", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JEditorPane errLogs = new JEditorPane();
		errLogs.setForeground(Color.RED);
		errLogs.setFont(new Font("Monospaced", Font.BOLD, 13));
		errLogs.setEditable(false);
		errLogs.setText(serrLogs);
		//panel.add(errLogs, BorderLayout.CENTER);
		JScrollPane errLogsScroller = new JScrollPane(errLogs);
		panel.add(errLogsScroller, BorderLayout.CENTER);
		
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Info Logs", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JEditorPane infoLogs = new JEditorPane();
		infoLogs.setFont(new Font("Monospaced", Font.BOLD, 13));
		infoLogs.setText(sinfoLogs);
		JScrollPane infoLogsScroller = new JScrollPane(infoLogs);
		panel_1.add(infoLogsScroller, BorderLayout.CENTER);
	}

	
	
}
