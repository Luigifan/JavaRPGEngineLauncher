package com.mikesantiago.launcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import org.apache.commons.io.IOUtils;

import com.mikesantiago.launcher.OSDetection.OSType;

import net.lingala.zip4j.core.ZipFile;
import java.awt.Dialog.ModalExclusionType;

public class Downloader extends JFrame 
{
	private URL fileToDownload;
	private JProgressBar progressBar;
	private JLabel lblDownloading;
	private long totalBytes;
	
	public Downloader(URL fileToDownload) 
	{
		setAlwaysOnTop(true);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		this.fileToDownload = fileToDownload;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Downloading...");
		setSize(452,108);
		getContentPane().setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(6, 48, 437, 20);
		getContentPane().add(progressBar);
		
		lblDownloading = new JLabel("Downloading...");
		lblDownloading.setBounds(19, 20, 410, 16);
		getContentPane().add(lblDownloading);
		
		//
		
		t.start();
	}
	
	Thread t = new Thread("downloader")
	{
		public void run()
		{
			try {
				OutputStream os = new FileOutputStream(new File("latest.zip"));
				InputStream is = fileToDownload.openStream();
				IOUtils.copy(is, os);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("download complete!");
			
			lblDownloading.setText("Extracting...");
			
			File applicationSupport = new File(System.getProperty("user.home") + "/Library/Application Support/JavaRPGEngine");
			File linwin = new File(System.getProperty("user.home") + "/.JavaRPGEngine");
			File binFolder = new File("BLERGH");
			
			if(MainProgram.CurrentOS == OSType.macosx)
			{
				binFolder = new File(applicationSupport.getAbsolutePath() + "/bin");
			}
			else if(MainProgram.CurrentOS == OSType.linux || MainProgram.CurrentOS == OSType.windows)
			{
				binFolder = new File(linwin.getAbsolutePath() + "/bin");
			}
			
			try
			{
				System.out.println("extracting to " + binFolder.getAbsolutePath());
				ZipFile zip = new ZipFile("latest.zip");
				zip.extractAll(binFolder.getAbsolutePath());
				
				FileWriter writer = new FileWriter(binFolder.getAbsolutePath() + "/CurVer.txt");
				writer.write(Launcher.latestVersion.toString());
				writer.flush();
				
				Launcher.currentVersion = Launcher.latestVersion;
				
				JOptionPane.showMessageDialog(null, "Downloaded!", "Information", JOptionPane.INFORMATION_MESSAGE);
				
				new File("latest.zip").delete();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			dispose();
		}
	
	};
}
