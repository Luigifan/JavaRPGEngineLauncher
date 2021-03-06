package com.mikesantiago.launcher;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mikesantiago.launcher.OSDetection.OSType;


public class MainProgram 
{
	private static String systemLookAndFeel = "";
	public static OSType CurrentOS = OSDetection.GetCurrentOSName();
	
	public static void main(String[] args)
	{
		SetLookAndFeel();
		
		System.out.println("spying on user (not really)");
		if(CurrentOS == OSType.macosx)
			System.out.println("game is running on " + OSDetection.GetCurrentOSName() + ", nice choice");
		else
			System.out.println("game is running on " + OSDetection.GetCurrentOSName());
		System.out.println("os is " + System.getProperty("os.arch") + " bit");
		System.out.println("java version is " + System.getProperty("java.version") + " from " + System.getProperty("java.vendor"));
		System.out.println("end spy (again, not really)\n\n");
		//launch the GUI
		Launcher l = new Launcher();
		l.setVisible(true);
		//VersionTest();
	}

	private static void VersionTest()
	{
		try 
		{
			Downloader d = new Downloader(new URL("http://mrmiketheripper.x10.mx/rpg/update/latest.zip"));
			d.setVisible(true);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	
	public static void SetLookAndFeel()
	{
		switch(CurrentOS)
		{
		case windows:
			try
            {
                systemLookAndFeel = UIManager.getSystemLookAndFeelClassName();
            }
            catch(Exception ex)
            {
                systemLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }
			break;
		case macosx:
			try
            {
                systemLookAndFeel = UIManager.getSystemLookAndFeelClassName();
            }
            catch(Exception ex)
            {
                systemLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }
			break;
		case linux:
			try
            {
                systemLookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            }
            catch(Exception ex)
            {
                systemLookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }
			break;
		case other:
			JOptionPane.showMessageDialog(null, "Not supported", "Not supported on your current operaing system.", JOptionPane.ERROR_MESSAGE);
			break;
		}
		try {
			UIManager.setLookAndFeel(systemLookAndFeel);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
