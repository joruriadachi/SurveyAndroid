package com.project.untag.survey1.Model;

import com.google.gson.annotations.SerializedName;

public class LampuPJU {

	@SerializedName("Lampu")
	private Lampu lampu;

	@SerializedName("KondisiLampu")
	private String kondisiLampu;

	@SerializedName("IDTiang")
	private String iDTiang;

	@SerializedName("Jumlah")
	private String jumlah;

	@SerializedName("IDLampuPJU")
	private String iDLampuPJU;

	@SerializedName("IDLampu")
	private String iDLampu;

	public void setLampu(Lampu lampu){
		this.lampu = lampu;
	}

	public Lampu getLampu(){
		return lampu;
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

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setIDLampuPJU(String iDLampuPJU){
		this.iDLampuPJU = iDLampuPJU;
	}

	public String getIDLampuPJU(){
		return iDLampuPJU;
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
			"LampuPJU{" +
			"lampu = '" + lampu + '\'' + 
			",kondisiLampu = '" + kondisiLampu + '\'' + 
			",iDTiang = '" + iDTiang + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",iDLampuPJU = '" + iDLampuPJU + '\'' + 
			",iDLampu = '" + iDLampu + '\'' + 
			"}";
		}
}