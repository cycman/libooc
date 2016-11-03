package com.cyc.hibernate.domain;

 import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

  
@Entity(name="ebook_t")
@Table(name="ebook_t")
 public class EbookDomain {

	
	Integer ID;
	Integer eb_ID;//a
	String Title;//b
	String Volume;//c  none
	
	//String series ;//d
	

	
	private String series;
	
	
	//String E;
	String Authors;//f
	String Year;//G
	String Edition;//h
	String Publisher;//i
	//String J;
	String Pages;//k
	String Language;//L
	//String M;
	String Library;//n
	String LibraryIssue;//o
	String Isbn;//P
	String SizeByte;//AI
	String Extension;//AJ
	String MD5;//AK;
	String CRC32;//AL;
	String eDokey;//AM;
	String AICH;//AN;
	String SHA1;//AO;
	String TTH;//AP;
	//String AQ;
	String URL;//AR;
   //String AS;
	String TOPICANDORIGINFILENAME;//AT;
	//String AU;
	String TimeAdd;//Av
	String TimeModified;//AW
	String ImageUrl;//AX
//	String AY;
 	
	
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}

	
 
	public String Ed2k() {
 
		{
			String ed2k = "";
			String heard = "ed2k://|FILE|";
			String md5 = this.getMD5();
			ed2k += heard+md5;

			String extension = this.getExtension();
			ed2k += "." + extension + "|";

			String filesize = this.getSizeByte();
			ed2k += filesize + "|";

			String eDonkey = this.geteDokey();
			ed2k += eDonkey + "|";

			String Aich = "h=" + this.getAICH();
			ed2k += Aich + "|/";
			return ed2k;
		}
		
		
 
	}
   
	public void setSeries(String series) {
		this.series = series;
	}
	//key
		@Column(name="eb_ID")
		@Id
	public Integer getEb_ID() {
		return eb_ID;
	}
	public void setEb_ID(Integer eb_ID) {
		this.eb_ID = eb_ID;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getVolume() {
		return Volume;
	}
	public void setVolume(String volume) {
		Volume = volume;
	}
	
	
	
	public String getSeries() {
		return series;
	}
	public String getAuthors() {
		return Authors;
	}
	public void setAuthors(String authors) {
		Authors = authors;
	}
	public String getYear() {
		return Year;
	}
	public void setYear(String year) {
		Year = year;
	}
	public String getEdition() {
		return Edition;
	}
	public void setEdition(String edition) {
		Edition = edition;
	}
	public String getPublisher() {
		return Publisher;
	}
	public void setPublisher(String publisher) {
		Publisher = publisher;
	}
 
	public String getPages() {
		return Pages;
	}
	public void setPages(String pages) {
		Pages = pages;
	}
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
//	public String getM() {
//		return M;
//	}
//	public void setM(String m) {
//		M = m;
//	}
	public String getLibrary() {
		return Library;
	}
	public void setLibrary(String library) {
		Library = library;
	}
	public String getLibraryIssue() {
		return LibraryIssue;
	}
	public void setLibraryIssue(String libraryIssue) {
		LibraryIssue = libraryIssue;
	}
	public String getIsbn() {
		return Isbn;
	}
	public void setIsbn(String isbn) {
		Isbn = isbn;
	}
	 
	 
	public String getSizeByte() {
		return SizeByte;
	}
	public void setSizeByte(String sizeByte) {
		SizeByte = sizeByte;
	}
	public String getExtension() {
		return Extension;
	}
	public void setExtension(String extension) {
		Extension = extension;
	}
	public String getMD5() {
		return MD5;
	}
	public void setMD5(String mD5) {
		MD5 = mD5;
	}
	public String getCRC32() {
		return CRC32;
	}
	public void setCRC32(String cRC32) {
		CRC32 = cRC32;
	}
	public String geteDokey() {
		return eDokey;
	}
	public void seteDokey(String eDokey) {
		this.eDokey = eDokey;
	}
	public String getAICH() {
		return AICH;
	}
	public void setAICH(String aICH) {
		AICH = aICH;
	}
	public String getSHA1() {
		return SHA1;
	}
	public void setSHA1(String sHA1) {
		SHA1 = sHA1;
	}
	public String getTTH() {
		return TTH;
	}
	public void setTTH(String tTH) {
		TTH = tTH;
	}
	 
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	 
	public String getTOPICANDORIGINFILENAME() {
		return TOPICANDORIGINFILENAME;
	}
	public void setTOPICANDORIGINFILENAME(String tOPICANDORIGINFILENAME) {
		TOPICANDORIGINFILENAME = tOPICANDORIGINFILENAME;
	}
 
	public String getTimeAdd() {
		return TimeAdd;
	}
	public void setTimeAdd(String timeAdd) {
		TimeAdd = timeAdd;
	}
	public String getTimeModified() {
		return TimeModified;
	}
	public void setTimeModified(String timeModified) {
		TimeModified = timeModified;
	}
	public String getImageUrl() {
		return ImageUrl;
	}
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	 
	 
	
//	String Q;
//	String R;
//	String S;
//	String T;
//	String U;
//	String V;
//	String W;
//	String X;
//	String Y;
//	String Z;
//	String AA;
//	String AB;
//	String AC;
//	String AD;
//	String AE;
//	String AF;
//	String AG;
//	String AH;
	
	 
	
	
	
	
	
	
	
	
	
}
