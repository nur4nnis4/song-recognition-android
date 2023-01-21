package com.example.asus.songrecognition.model;


/**
 * @author nur4nnis4@gmail
 */
public class SongFingerprints  implements java.io.Serializable {


     private String no;
     private String hash;
     private String value;
     private int songId;

    public SongFingerprints() {
    }

    public SongFingerprints(String no, String hash, String value, int songId) {
       this.no = no;
       this.hash = hash;
       this.value = value;
       this.songId = songId;
    }
    public SongFingerprints( String hash, String value) {
       this.hash = hash;
       this.value = value;
    }
   
    public String getNo() {
        return this.no;
    }
    
    public void setNo(String no) {
        this.no = no;
    }
    public String getHash() {
        return this.hash;
    }
    
    public void setHash(String hash) {
        this.hash = hash;
    }
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    public int getSongId() {
        return this.songId;
    }
    
    public void setSongId(int songId) {
        this.songId = songId;
    }




}


