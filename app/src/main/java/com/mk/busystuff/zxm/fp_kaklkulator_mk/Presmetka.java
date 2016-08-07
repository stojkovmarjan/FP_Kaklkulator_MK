package com.mk.busystuff.zxm.fp_kaklkulator_mk;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Created by marjan on 3.10.15.
 */
public class Presmetka {

    protected static int GetPushUpsCalc(String rezultat,int sgrupa,String fileSklek , Context ctx){

        int tmpRez=0;
        if (rezultat.equals("")) return 0; // ako e prazen string exit sub so 0 vred
            tmpRez=Integer.parseInt(rezultat);

        if (tmpRez<5) return 0;

        System.out.println("EEEEEEEEEEEEEE "+String.valueOf(tmpRez)+" "+fileSklek);

        if ((fileSklek.equals("msklek.csv"))&&(tmpRez>76)) return 100;
        if ((fileSklek.equals("zsklek.csv"))&&(tmpRez>49)) return 100;


        int poeniSklek=0;
        //String rez=String.valueOf(rezultat);

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(ctx.getAssets().open(fileSklek), "UTF-8"));
            String mLine = reader.readLine();

            while (mLine != null) {
                //process lin
                String [] vred;
                mLine = reader.readLine();
                vred=mLine.split(";");
                //System.out.println("XXXXXX "+mLine);
                if (vred[0].equals(rezultat)){// indeks nula e razultat za sporedba
                    poeniSklek=Integer.valueOf(vred[sgrupa]);//indeks starosna_starosna grupa se poenite
                    //Toast.makeText(MainActivity.this,vred[0],Toast.LENGTH_SHORT).show();
                    //lblPushUpMin.setText(vred[0]);
                    break;
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return poeniSklek;
    }

    protected static int GetSitUpsCalc(String rezultat,int sgrupa,String fileStom , Context ctx){

        int tmpRez=0;
        if (rezultat.equals("")) return 0; // ako e prazen string exit sub so 0 vred
        tmpRez=Integer.parseInt(rezultat);

        if (tmpRez<21) return 0;
        if (tmpRez>81) return 100;


        int poeniStom=0;
        //String rez=String.valueOf(rezultat);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(ctx.getAssets().open(fileStom), "UTF-8"));
            String mLine = reader.readLine();
            while (mLine != null) {
                //process lin
                String [] vred;
                mLine = reader.readLine();
                vred=mLine.split(";");
                //System.out.println("XXXXXX "+mLine);
                if (vred[0].equals(rezultat)){
                    poeniStom=Integer.valueOf(vred[sgrupa]);
                    break;
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return poeniStom;
    }


    protected static int GetRunCalc(String rezultat,int sgrupa,String fileTrc , Context ctx){

        int tmpRez=0;
        if (rezultat.equals("")) return 0; // ako e prazen string exit sub so 0 vred
        tmpRez=Integer.parseInt(rezultat);

        if ((fileTrc.equals("mtrcanje.csv"))&&(tmpRez>2455)) return 0;
        if ((fileTrc.equals("ztrcanje.csv"))&&(tmpRez>2455)) return 0;
        //if (tmpRez<=1300) return 100;
        int trcanjePoeni=0;
        String [] mLines=new String[123];

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(ctx.getAssets().open(fileTrc), "UTF-8"));
            String mLine = " ";

            int counter=0;
            while (mLine != null) {
                //process lin
                mLine = reader.readLine();
                mLines[counter]=mLine;
                //System.out.println("UUUUUU  "+String.valueOf(counter)+" "+mLines[counter]);
                counter++;
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        mLines[122]="9999";
        int rez=Integer.parseInt(rezultat);
        int rez1=0;
        for (int f=0;f<=121;f++){

            String [] temp=mLines[f].split(";");
            String [] temp1=mLines[f+1].split(";");

            int rezTemp=Integer.parseInt(temp[0]);
            int rezTemp1=Integer.parseInt(temp1[0]);

            if ((rez>=rezTemp)&&(rez<rezTemp1)) {
                trcanjePoeni=Integer.parseInt(temp[sgrupa]);
                break;
            }

        }

        return trcanjePoeni;
    }


    protected  static String GetGrade(float prosek){

        int tmpAverage=Math.round(prosek);

        if (tmpAverage < 60) return "1";
        if ((tmpAverage >= 60) && (tmpAverage <= 64)) return "2";
        if ((tmpAverage >= 65) && (tmpAverage <= 74)) return "3";
        if ((tmpAverage >= 75) && (tmpAverage <= 84)) return "4";
        //if ((tmpAverage >= 75) && (tmpAverage <= 84)) return "4";

        return "5";

    }

}
