package com.mikesantiago.launcher;

public class Version 
{
	private int MAJOR = 1;
	private int MINOR = 6;
	private int BUILD = 0;
	private int REVISION = 0;
	
	public Version(String versionToBeParsed)
	{
		String[] parts = versionToBeParsed.split("\\.");
		try
		{
			int major = Integer.parseInt(parts[0]);
			int minor = Integer.parseInt(parts[1]);
			int build = Integer.parseInt(parts[2]);
			int revision = Integer.parseInt(parts[3]);
			
			MAJOR = major;
			MINOR =  minor;
			BUILD = build;
			REVISION = revision;
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
		int newerMajor = versionToCompareTo.getMAJOR();
		int newerMinor = versionToCompareTo.getMINOR();
		int newerBuild = versionToCompareTo.getBUILD();
		int newerRevision = versionToCompareTo.getREVISION();
		
		if(newerMajor > MAJOR)
		{
			return true;
		}
		else if(newerMinor > MINOR)
		{
			return true;
		}
		else if(newerBuild > BUILD)
		{
			return true;
		}
		else if(newerRevision > REVISION)
		{
			return true;
		}
		else
			return false;
	}

	public int getMAJOR() {
		return MAJOR;
	}

	public void setMAJOR(int mAJOR) {
		MAJOR = mAJOR;
	}

	public int getMINOR() {
		return MINOR;
	}

	public void setMINOR(int mINOR) {
		MINOR = mINOR;
	}

	public int getBUILD() {
		return BUILD;
	}

	public void setBUILD(int bUILD) {
		BUILD = bUILD;
	}

	public int getREVISION() {
		return REVISION;
	}

	public void setREVISION(int rEVISION) {
		REVISION = rEVISION;
	}
	
	@Override
	public String toString()
	{
		String format = String.format("%s.%s.%s.%s", MAJOR, MINOR, BUILD,REVISION);
		return format;
	}
	
}
