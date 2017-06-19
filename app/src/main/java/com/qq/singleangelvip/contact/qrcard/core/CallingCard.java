package com.qq.singleangelvip.contact.qrcard.core;

/**
 * Created by singl on 2017/4/16.
 */
import java.net.URLEncoder;
import java.util.Date;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CallingCard {
    private String name;
    private String nickName;
    private String im;
    private String note;
    private String company;
    private String tel;
    private String mobile;
    private String email;
    private String address;



    @Override
    public String toString() {
        String string = "BEGIN:VCARD\nVERSION:3.0\n"+
                "NAME:" +name+ "\nNICKNAME:"+nickName+ "\nNOTE:"+note+"\nEMAIL:"+email+"\nTEL:"+tel+"\nCELL:"+mobile+"\nADR:"+address+
                "\nX-QQ:"+im+
                "\nORG:"+company+"\nEND:VCARD\n";
        return string;
    }

    /**
     * @param name
     * @param tel
     * @param mobile
     * @param email
     * @param company
     * @param address
     */
    public CallingCard(String name, String tel, String mobile, String nickName,
                       String email, String company, String note, String address) {
        super();
        this.name = name;
        this.tel = tel;
        this.mobile = mobile;
        this.email = email;
        this.company = company;
        this.address = address;
        this.nickName = nickName;
        this.note = note;
    }



    /**
     *
     */
    public CallingCard() {
        super();
        this.address="";
        this.company="";
        this.email="";
        this.mobile="";
        this.name="";
        this.tel="";
        this.im = "";
        this.note = "";
        this.nickName = "";
    }

    //creat a qrcode bitmap from a string
    public Bitmap Creat2DCode(String s) throws WriterException{
        BitMatrix matrix = new MultiFormatWriter().encode(s, BarcodeFormat.QR_CODE, 400, 400);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width*height];
        for(int y = 0;y<height;y++){
            for(int x = 0;x<width;x++)
            {
                if(matrix.get(x, y)){
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }



    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }


    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }


    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }


    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName(){
        return nickName;
    }
    public void setNickName(String nickName){
        this.nickName = nickName;
    }


    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }


    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }


    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }


    public String getNote(){
        return note;
    }
    public void setNote(String note){
        this.note = note;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }


    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }



    /**
     * @return the im
     */
    public String getIm() {
        return im;
    }

    /**
     * @param im the im to set
     */
    public void setIm(String im) {
        this.im = im;
    }

}
