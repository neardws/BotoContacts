package com.qq.singleangelvip.contact.qrcard.core;

/**
 * Created by singl on 2017/4/16.
 */

import java.sql.Date;
import java.util.regex.Pattern;

public class RegularExpression{


    public CallingCard regularEp(String cc)
    {
        CallingCard cToreturn = new CallingCard();
        String regex = "\n";
        Pattern p = Pattern.compile(regex);
        String[] s = p.split(cc);
        for(String x :s)
        {
            String regex2 = ":";
            Pattern xp = Pattern.compile(regex2);
            String[] xx = xp.split(x);
            if(xx[0].equals("NAME")){
                if(xx.length>1)
                    cToreturn.setName(xx[1]);
            }
            if(xx[0].equals("NICKNAME")){
                if(xx.length>1)
                    cToreturn.setNickName(xx[1]);
            }
            if(xx[0].equals("NOTE")){
                if(xx.length>1)
                    cToreturn.setNote(xx[1]);
            }
            if(xx[0].equals("EMAIL")){
                if(xx.length>1)
                    cToreturn.setEmail(xx[1]);
            }
            if(xx[0].equals("TEL")){
                if(xx.length>1)
                    cToreturn.setTel(xx[1]);
            }
            if(xx[0].equals("CELL")){
                if(xx.length>1)
                    cToreturn.setMobile(xx[1]);
            }
            if(xx[0].equals("ADR")){
                if(xx.length>1)
                    cToreturn.setAddress(xx[1]);
            }
            if(xx[0].equals("ORG")){
                if(xx.length>1)
                    cToreturn.setCompany(xx[1]);
            }
            if(xx[0].equals("X-QQ")){
                if(xx.length>1)
                    cToreturn.setIm(xx[1]);
            }
        }
        return cToreturn;
    }
}