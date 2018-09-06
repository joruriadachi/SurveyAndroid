package com.project.untag.survey1.Model;

import com.google.gson.annotations.SerializedName;

public class JenisLampu{

	@SerializedName("IDJenisLampu")
	private String iDJenisLampu;

	@SerializedName("NmJenisLampuPDK")
	private String nmJenisLampuPDK;

	@SerializedName("FAktif")
	private Object fAktif;

	@SerializedName("NmJenisLampu")
	private String nmJenisLampu;

	public void setIDJenisLampu(String iDJenisLampu){
		this.iDJenisLampu = iDJenisLampu;
	}

	public String getIDJenisLampu(){
		return iDJenisLampu;
	}

	public void setNmJenisLampuPDK(String nmJenisLampuPDK){
		this.nmJenisLampuPDK = nmJenisLampuPDK;
	}

	public String getNmJenisLampuPDK(){
		return nmJenisLampuPDK;
	}

	public void setFAktif(Object fAktif){
		this.fAktif = fAktif;
	}

	public Object getFAktif(){
		return fAktif;
	}

	public void setNmJenisLampu(String nmJenisLampu){
		this.nmJenisLampu = nmJenisLampu;
	}

	public String getNmJenisLampu(){
		return nmJenisLampu;
	}

	@Override
 	public String toString(){
		return 
			"JenisLampu{" + 
			"iDJenisLampu = '" + iDJenisLampu + '\'' + 
			",nmJenisLampuPDK = '" + nmJenisLampuPDK + '\'' + 
			",fAktif = '" + fAktif + '\'' + 
			",nmJenisLampu = '" + nmJenisLampu + '\'' + 
			"}";
		}
}