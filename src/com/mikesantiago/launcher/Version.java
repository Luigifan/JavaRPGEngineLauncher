package com.mikesantiago.launcher;

public class Version 
{
	private int MAJOR = 1;
	private int MINOR = 6;
	private int BUILD = 0;
	private int REVISION = 0;
	
	public Version(String versionToBeParsed)
	{
		String[] parts = versionToBeParsed.split(".");
		try
		{
			int major = Integer.parseInt(parts[0]);
			int minor = Integer.parseInt(parts[1]);
			int build = Integer.parseInt(parts[2]);
			int revision = Integer.parseInt(parts[3]);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public Version(int MAJOR, int MINOR, int BUILD, int REVISION)
	{
		this.MAJOR = MAJOR;
		this.MINOR = MINOR;
		this.BUILD = BUILD;
		this.REVISION = REVISION;
	}
	
	/**
	 * Compares self against another version
	 * @param versionToCompareTo
	 * @return true if compared version is greater
	 */
	public boolean CompareVersion(Version versionToCompareTo)
	{
		return false;
	}

}
