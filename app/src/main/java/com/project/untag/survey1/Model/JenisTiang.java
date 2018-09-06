package com.project.untag.survey1.Model;

import com.google.gson.annotations.SerializedName;
public class JenisTiang{

	@SerializedName("NmJenisTiangPDK")
	private String nmJenisTiangPDK;

	@SerializedName("FAktif")
	private Object fAktif;

	@SerializedName("IDJenisTiang")
	private String iDJenisTiang;

	@SerializedName("NmJenisTiang")
	private String nmJenisTiang;

	public void setNmJenisTiangPDK(String nmJenisTiangPDK){
		this.nmJenisTiangPDK = nmJenisTiangPDK;
	}

	public String getNmJenisTiangPDK(){
		return nmJenisTiangPDK;
	}

	public void setFAktif(Object fAktif){
		this.fAktif = fAktif;
	}

	public Object getFAktif(){
		return fAktif;
	}

	public void setIDJenisTiang(String iDJenisTiang){
		this.iDJenisTiang = iDJenisTiang;
	}

	public String getIDJenisTiang(){
		return iDJenisTiang;
	}

	public void setNmJenisTiang(String nmJenisTiang){
		this.nmJenisTiang = nmJenisTiang;
	}

	public String getNmJenisTiang(){
		return nmJenisTiang;
	}

	@Override
 	public String toString(){
		return 
			"JenisTiang{" + 
			"nmJenisTiangPDK = '" + nmJenisTiangPDK + '\'' + 
			",fAktif = '" + fAktif + '\'' + 
			",iDJenisTiang = '" + iDJenisTiang + '\'' + 
			",nmJenisTiang = '" + nmJenisTiang + '\'' + 
			"}";
		}
}