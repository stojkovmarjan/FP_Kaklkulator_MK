package com.mk.busystuff.zxm.fp_kaklkulator_mk;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by marjan on 2.10.15.
 */
public class Minimum {

    protected static int GetMinPushUP(int sgrupa,String fileSklek , Context ctx){
        int minSklek=0;

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
                if (vred[sgrupa].equals("60")){
                    minSklek=Integer.valueOf(vred[0]);
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

        return minSklek;
    }


    protected static int GetMinSitUP(int sgrupa,String fileStom,Context ctx){
        int minStom=0;
        BufferedReader reader = null;
//-------- CITAJ STOMACNI MIN --------------
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
                if (vred[sgrupa].equals("60")){
                    minStom=Integer.valueOf(vred[0]);
                    //Toast.makeText(MainActivity.this,vred[0],Toast.LENGTH_SHORT).show();
                    //lblSitUpMin.setText(vred[0]);
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
        //-----------------------------
        return minStom;
    }

    protected static int GetMinRun(int sgrupa,String fileTrc,Context ctx){
        int minTrc=0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(ctx.getAssets().open(fileTrc), "UTF-8"));
            String mLine = reader.readLine();
            while (mLine != null) {
                //process lin
                String [] vred;
                mLine = reader.readLine();
                vred=mLine.split(";");
                //System.out.println("XXXXXX "+mLine);
                if (vred[sgrupa].equals("59")){
                    minTrc=Integer.valueOf(vred[0]);
                    minTrc--;

                    //Toast.makeText(MainActivity.this,timeSt,Toast.LENGTH_SHORT).show();
                    //lblRunMin.setText(timeSt);
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

        return minTrc;
    }

}
