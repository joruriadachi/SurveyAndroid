package com.project.untag.survey1.Model;

import com.google.gson.annotations.SerializedName;

public class Lampu{

	@SerializedName("IDJenisLampu")
	private String iDJenisLampu;

	@SerializedName("JenisLampu")
	private JenisLampu jenisLampu;

	@SerializedName("LatitudeLampu")
	private String latitudeLampu;

	@SerializedName("WattLampu")
	private String wattLampu;

	@SerializedName("KondisiLampu")
	private String kondisiLampu;

	@SerializedName("IDTiang")
	private String iDTiang;

	@SerializedName("VALampu")
	private String vALampu;

	@SerializedName("IDLampu")
	private String iDLampu;

	@SerializedName("LongitudeLampu")
	private String longitudeLampu;

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

	public void setLatitudeLampu(String latitudeLampu){
		this.latitudeLampu = latitudeLampu;
	}

	public String getLatitudeLampu(){
		return latitudeLampu;
	}

	public void setWattLampu(String wattLampu){
		this.wattLampu = wattLampu;
	}

	public String getWattLampu(){
		return wattLampu;
	}

	public void setKondisiLampu(String kondisiLampu){
		this.kondisiLampu = kondisiLampu;
	}

	public String getKondisiLampu(){
		return kondisiLampu;
	}

	public void setIDTiang(String iDTiang){
		this.iDTiang = iDTiang;
	}

	public String getIDTiang(){
		return iDTiang;
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

	public void setLongitudeLampu(String longitudeLampu){
		this.longitudeLampu = longitudeLampu;
	}

	public String getLongitudeLampu(){
		return longitudeLampu;
	}

	@Override
 	public String toString(){
		return 
			"Lampu{" +
			"iDJenisLampu = '" + iDJenisLampu + '\'' + 
			",jenisLampu = '" + jenisLampu + '\'' + 
			",latitudeLampu = '" + latitudeLampu + '\'' + 
			",wattLampu = '" + wattLampu + '\'' + 
			",kondisiLampu = '" + kondisiLampu + '\'' + 
			",iDTiang = '" + iDTiang + '\'' + 
			",vALampu = '" + vALampu + '\'' + 
			",iDLampu = '" + iDLampu + '\'' + 
			",longitudeLampu = '" + longitudeLampu + '\'' + 
			"}";
		}
}