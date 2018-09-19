package com.project.untag.survey1.Model;

import com.google.gson.annotations.SerializedName;

public class Lampu{

	@SerializedName("IDJenisLampu")
	private String iDJenisLampu;

	@SerializedName("JenisLampu")
	private JenisLampu jenisLampu;

	@SerializedName("WattLampu")
	private String wattLampu;

	@SerializedName("FAktif")
	private String fAktif;

	@SerializedName("VALampu")
	private String vALampu;

	@SerializedName("IDLampu")
	private String iDLampu;

	public void setIDJenisLampu(String iDJenisLampu){
		this.iDJenisLampu = iDJenisLampu;
	}

	public String getIDJenisLampu(){
		return iDJenisLampu;
	}

	public void setJenisLampu(JenisLampu jenisLampu){
		this.jenisLampu = jenisLampu;
	}

	public JenisLampu getJenisLampu(){
		return jenisLampu;
	}

	public void setWattLampu(String wattLampu){
		this.wattLampu = wattLampu;
	}

	public String getWattLampu(){
		return wattLampu;
	}

	public void setFAktif(String fAktif){
		this.fAktif = fAktif;
	}

	public String getFAktif(){
		return fAktif;
	}

	public void setVALampu(String vALampu){
		this.vALampu = vALampu;
	}

	public String getVALampu(){
		return vALampu;
	}

	public void setIDLampu(String iDLampu){
		this.iDLampu = iDLampu;
	}

	public String getIDLampu(){
		return iDLampu;
	}

	@Override
 	public String toString(){
		return 
			"Lampu{" + 
			"iDJenisLampu = '" + iDJenisLampu + '\'' + 
			",jenisLampu = '" + jenisLampu + '\'' + 
			",wattLampu = '" + wattLampu + '\'' + 
			",fAktif = '" + fAktif + '\'' + 
			",vALampu = '" + vALampu + '\'' + 
			",iDLampu = '" + iDLampu + '\'' + 
			"}";
		}
}