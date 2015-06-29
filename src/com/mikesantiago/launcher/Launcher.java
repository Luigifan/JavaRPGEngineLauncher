package com.mikesantiago.launcher;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mikesantiago.launcher.OSDetection.OSType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Launcher extends JFrame 
{
	private boolean NeedsDownload = false;
	public static Version latestVersion = new Version(0,0,0,0);
	public static Version currentVersion = new Version(0,0,0,0);
	JLabel curVerLabel;
	JLabel latestVerLabel;
	
	public Launcher()
	{
		setResizable(false);
		setTitle("Java RPG Engine Launcher");
		getContentPane().setLayout(null);
		setSize(640, 390);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 287, 640, 81);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		curVerLabel = new JLabel("CurVer");
		curVerLabel.setBounds(6, 14, 232, 16);
		panel.add(curVerLabel);
		
		JButton btnLaunch = new JButton("Launch");
		btnLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				RunGame();
			}
		});
		btnLaunch.setBounds(231, 29, 163, 29);
		panel.add(btnLaunch);
		
		latestVerLabel = new JLabel("LatestVer");
		latestVerLabel.setBounds(6, 42, 232, 16);
		panel.add(latestVerLabel);
		
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		editorPane.setEditable(false);
		editorPane.setBounds(0, 0, 640, 288);
		try 
		{
			editorPane.setPage("http://mrmiketheripper.x10.mx/rpg/update");
		} 
		catch (IOException e1) 
		{
			editorPane.setText("<html>Couldn't load page</html>");
			editorPane.setContentType("text/html");
		}
		editorPane.addHyperlinkListener(new HyperlinkListener() {
		    public void hyperlinkUpdate(HyperlinkEvent e) {
		        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		        	if(Desktop.isDesktopSupported()) {
		        	    try {
							Desktop.getDesktop().browse(e.getURL().toURI());
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (URISyntaxException e1) {
							e1.printStackTrace();
						}
		        	}
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(editorPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 640, 289);
		getContentPane().add(scrollPane);
		
		SetIcons();
		UpdateCurrentInternalVersion();
		CheckDirectories();
		
	}
	
	private void SetIcons()
	{
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Minecraft Skin Stealer");
        String test = getClass().getResource("res/Icon.png").toString();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("res/Icon.png")));
	}
	
	private void RunGame()
	{
		try {
			File dataFolder = new File("");
			if(MainProgram.CurrentOS == OSType.macosx)
				dataFolder =  new File(
						System.getProperty("user.home") + "/Library/Application Support/JavaRPGEngine/bin/jrpg.jar");
			else
				dataFolder = new File(System.getProperty("user.home") + "/.JavaRPGEngine/bin/jrpg.jar");
			this.setVisible(false);
			
			Process p = null;
			
			if(MainProgram.CurrentOS == OSType.macosx)
			{
				p = Runtime.getRuntime()
						.exec(new String[] { "java", "-Dapple.laf.useScreenMenuBar=true", 
								"-Dcom.apple.macos.use-file-dialog-packages=true", 
								"-Xdock:name=Java RPG Engine", 
								"-jar",
								dataFolder.getAbsolutePath() });
			}
			else
			{
				p = Runtime.getRuntime()
						.exec(new String[] { "java", "-jar", dataFolder.getAbsolutePath() });
			}
			
			p.waitFor();
			java.io.InputStream is = p.getInputStream();
			byte b[] = new byte[is.available()];
			is.read(b, 0, b.length);
			
			java.io.InputStream es = p.getErrorStream();
			b = new byte[es.available()];
			es.read(b, 0, b.length);
			System.out.println(new String(b));
			this.setVisible(true);
			this.requestFocus();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	private void UpdateCurrentInternalVersion()
	{
		try {
			URL url = new URL("http://mrmiketheripper.x10.mx/rpg/update/LatestVersion");
			Scanner s = new Scanner(url.openStream());
			String version = s.nextLine();
			latestVersion = new Version(version);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void UpdateLabels()
	{
		this.curVerLabel.setText("Current Version: " + currentVersion.toString());
		this.latestVerLabel.setText("Latest Version: " + latestVersion.toString());
	}
	
	private void CheckDirectories()
	{
		File applicationSupport = new File(System.getProperty("user.home") + "/Library/Application Support/JavaRPGEngine");
		File appData = new File(System.getProperty("user.home") + "/.JavaRPGEngine");
		File linux = new File(System.getProperty("user.home") + "/.JavaRPGEngine");
		
		if(MainProgram.CurrentOS == OSType.macosx)
		{
			if(!applicationSupport.exists())
			{
				System.out.println("creating storage folder at " + applicationSupport.getAbsolutePath());
				applicationSupport.mkdir(); //so if all this executes, that means the game has to be downloaded still
				System.out.println("making bin directory at " + applicationSupport.getAbsolutePath() + "/bin");
				new File(applicationSupport.getAbsolutePath() + "/bin").mkdir();
				
				NeedsDownload = true;
			}
			else
			{
				if(new File(applicationSupport.getAbsolutePath() + "/bin").exists())
				{
					if(new File(applicationSupport.getAbsolutePath() + "/bin/jrpg.jar").exists())
					{
						CheckForUpdates();
						UpdateLabels();
					}
					else
					{
						NeedsDownload = true;
					}
				}
				else
				{
					NeedsDownload = true;
				}
			}
		}
		else if(MainProgram.CurrentOS == OSType.windows)
		{
			if(!appData.exists())
			{
				System.out.println("creating storage folder at " + appData.getAbsolutePath());
				appData.mkdir();
				System.out.println("making bin folder at " + appData.getAbsolutePath() + "/bin");
				new File(appData.getAbsolutePath() + "/bin").mkdir();
				
				NeedsDownload = true;
			}
			else
			{
				if(new File(appData.getAbsolutePath() + "/bin").exists())
				{
					if(new File(appData.getAbsolutePath() + "/bin/jrpg.jar").exists())
					{
						CheckForUpdates();
						UpdateLabels();
					}
					else
					{
						NeedsDownload = true;
					}
				}
				else
				{
					NeedsDownload = true;
				}
			}
		}
		else if(MainProgram.CurrentOS == OSType.linux)
		{
			if(!linux.exists())
			{
				System.out.println("creating storage folder at " + linux.getAbsolutePath());
				linux.mkdir();
				System.out.println("making bin folder at " + linux.getAbsolutePath() + "/bin");
				new File(linux.getAbsolutePath() + "/bin").mkdir();
				
				NeedsDownload = true;
			}
			else
			{
				if(new File(linux.getAbsolutePath() + "/bin").exists())
				{
					if(new File(linux.getAbsolutePath() + "/bin/jrpg.jar").exists())
					{
						CheckForUpdates();
						UpdateLabels();
					}
					else
					{
						NeedsDownload = true;
					}
				}
				else
				{
					NeedsDownload = true;
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Your operating system is unsupported. Sorry!", "Unsupported Operating System", JOptionPane.ERROR_MESSAGE);
			System.exit(-5);
		}
		
		DownloadGame();
	}
	
	public void DownloadGame()
	{
		if(NeedsDownload)
		{
			int result = JOptionPane.showConfirmDialog(this, "Java RPG Engine was not found in your appdata directory, would you like to download it now?", "Question", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.NO_OPTION)
			{
				JOptionPane.showMessageDialog(null, "Launcher will exit");
				System.exit(0);
			}
			else if(result == JOptionPane.YES_OPTION)
			{
				try 
				{
					Downloader d = new Downloader(new URL("http://mrmiketheripper.x10.mx/rpg/update/latest.zip"));
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			        d.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
					d.setVisible(true);
					
					while(d.isVisible())
					{
						System.out.println("locked");
					}
					
					UpdateLabels();
				} 
				catch (MalformedURLException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void CheckForUpdates()
	{
		File applicationSupport = new File(System.getProperty("user.home") + "/Library/Application Support/JavaRPGEngine");
		File linwin = new File(System.getProperty("user.home") + "/.JavaRPGEngine");
		File currentVersionFile = new File("smileyfacelolredspin");
		
		if(MainProgram.CurrentOS == OSType.windows || MainProgram.CurrentOS == OSType.linux)
		{
			currentVersionFile = new File(linwin.getAbsolutePath() + "/bin/CurVer.txt");
		}
		else if(MainProgram.CurrentOS == OSType.macosx)
		{
			currentVersionFile = new File(applicationSupport.getAbsolutePath() + "/bin/CurVer.txt");
		}
		
		if(!currentVersionFile.exists())
		{
			NeedsDownload = true;
			return;
		}
		else
		{
			try 
			{
				@SuppressWarnings("deprecation")
				Scanner ss = new Scanner(new URL(currentVersionFile.toURI().toURL().toString()).openStream());
				String ver = ss.nextLine();
				currentVersion = new Version(ver);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		try 
		{
			URL url = new URL("http://mrmiketheripper.x10.mx/rpg/update/LatestVersion");
			Scanner s = new Scanner(url.openStream());
			String version = s.nextLine();
			latestVersion = new Version(version);
		} 
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this, "An error occurred while trying to check versions\n\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		
		if(currentVersion.CompareVersion(latestVersion))
		{
			Update();
		}
		else
		{
			System.out.println("update not needed!");
		}
	}
	
	public void Update()
	{
		int result = JOptionPane.showConfirmDialog(this, "Do you wish to update from " + currentVersion.toString() + " to " + latestVersion.toString(),
				"Update Confirmation", JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.CLOSED_OPTION || result == JOptionPane.NO_OPTION)
		{
			//update suppressed, do nothing
		}
		else if(result == JOptionPane.YES_OPTION)
		{
			try 
			{
				Downloader d = new Downloader(new URL("http://mrmiketheripper.x10.mx/rpg/update/latest.zip"));
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		        d.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
				d.setVisible(true);
				while(d.isVisible())
				{
					this.setEnabled(false);
					d.setAlwaysOnTop(true);
				}
				
				this.setEnabled(true);
				UpdateLabels();
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This differs from Update
	 */
	public void CheckIfDownloadNeeded()
	{
		
	}
	
	public void ReadLatestVersion()
	{
		
	}
	
	public void GetCurrentVersion()
	{
		
	}
}
