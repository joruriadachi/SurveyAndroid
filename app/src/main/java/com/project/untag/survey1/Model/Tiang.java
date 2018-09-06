package com.project.untag.survey1.Model;

import com.google.gson.annotations.SerializedName;

public class Tiang{

	@SerializedName("JenisTiang")
	private JenisTiang jenisTiang;

	@SerializedName("LatitudeTiang")
	private String latitudeTiang;

	@SerializedName("Survey")
	private Survey survey;

	@SerializedName("LongitudeTiang")
	private String longitudeTiang;

	@SerializedName("JenisKabel")
	private JenisKabel jenisKabel;

	@SerializedName("FotoTiang")
	private String fotoTiang;

	@SerializedName("IDPel")
	private String iDPel;

	@SerializedName("KeteranganTiang")
	private String keteranganTiang;

	@SerializedName("StatusTiang")
	private String statusTiang;

	@SerializedName("IDTiang")
	private String iDTiang;

	@SerializedName("IDJenisTiang")
	private String iDJenisTiang;

	@SerializedName("IDJenisKabel")
	private String iDJenisKabel;

	public void setJenisTiang(JenisTiang jenisTiang){
		this.jenisTiang = jenisTiang;
	}

	public JenisTiang getJenisTiang(){
		return jenisTiang;
	}

	public void setLatitudeTiang(String latitudeTiang){
		this.latitudeTiang = latitudeTiang;
	}

	public String getLatitudeTiang(){
		return latitudeTiang;
	}

	public void setSurvey(Survey survey){
		this.survey = survey;
	}

	public Survey getSurvey(){
		return survey;
	}

	public void setLongitudeTiang(String longitudeTiang){
		this.longitudeTiang = longitudeTiang;
	}

	public String getLongitudeTiang(){
		return longitudeTiang;
	}

	public void setJenisKabel(JenisKabel jenisKabel){
		this.jenisKabel = jenisKabel;
	}

	public JenisKabel getJenisKabel(){
		return jenisKabel;
	}

	public void setFotoTiang(String fotoTiang){
		this.fotoTiang = fotoTiang;
	}

	public String getFotoTiang(){
		return fotoTiang;
	}

	public void setIDPel(String iDPel){
		this.iDPel = iDPel;
	}

	public String getIDPel(){
		return iDPel;
	}

	public void setKeteranganTiang(String keteranganTiang){
		this.keteranganTiang = keteranganTiang;
	}

	public String getKeteranganTiang(){
		return keteranganTiang;
	}

	public void setStatusTiang(String statusTiang){
		this.statusTiang = statusTiang;
	}

	public String getStatusTiang(){
		return statusTiang;
	}

	public void setIDTiang(String iDTiang){
		this.iDTiang = iDTiang;
	}

	public String getIDTiang(){
		return iDTiang;
	}

	public void setIDJenisTiang(String iDJenisTiang){
		this.iDJenisTiang = iDJenisTiang;
	}

	public String getIDJenisTiang(){
		return iDJenisTiang;
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
			"Tiang{" +
			"jenisTiang = '" + jenisTiang + '\'' + 
			",latitudeTiang = '" + latitudeTiang + '\'' + 
			",survey = '" + survey + '\'' + 
			",longitudeTiang = '" + longitudeTiang + '\'' + 
			",jenisKabel = '" + jenisKabel + '\'' + 
			",fotoTiang = '" + fotoTiang + '\'' + 
			",iDPel = '" + iDPel + '\'' + 
			",keteranganTiang = '" + keteranganTiang + '\'' + 
			",statusTiang = '" + statusTiang + '\'' + 
			",iDTiang = '" + iDTiang + '\'' + 
			",iDJenisTiang = '" + iDJenisTiang + '\'' + 
			",iDJenisKabel = '" + iDJenisKabel + '\'' + 
			"}";
		}
}