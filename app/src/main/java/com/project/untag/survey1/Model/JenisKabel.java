package com.project.untag.survey1.Model;

import com.google.gson.annotations.SerializedName;

public class JenisKabel{

	@SerializedName("NmJenisKabel")
	private String nmJenisKabel;

	@SerializedName("IDJenisKabel")
	private String iDJenisKabel;

	public void setNmJenisKabel(String nmJenisKabel){
		this.nmJenisKabel = nmJenisKabel;
	}

	public String getNmJenisKabel(){
		return nmJenisKabel;
	}

	public void setIDJenisKabel(String iDJenisKabel){
		this.iDJenisKabel = iDJenisKabel;
	}

	public String getIDJenisKabel(){
		return iDJenisKabel;
	}

	@Override
 	public String toString(){
		return 
			"JenisKabel{" + 
			"nmJenisKabel = '" + nmJenisKabel + '\'' + 
			",iDJenisKabel = '" + iDJenisKabel + '\'' + 
			"}";
		}
}