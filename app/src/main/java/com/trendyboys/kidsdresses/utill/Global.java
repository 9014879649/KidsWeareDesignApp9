package com.trendyboys.kidsdresses.utill;

import java.util.ArrayList;

/**
 * Created by Nageswara Rao.CH on 5/7/2018.
 */

public class Global {
    public static String categories[] = {"Birthday Special","Casual Dresses","Formal Dresses","Home Made Dresses ","Party Dresses"};
    public static ArrayList<String> getDesign1Images(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        for (int i=1;i<21;i++){
            arrayList.add("b"+i);
        }

        return arrayList;
    }

    public static ArrayList<String> getDesign2Images(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        for (int i=1;i<21;i++){
            arrayList.add("c"+i);
        }

        return arrayList;
    }


    public static ArrayList<String> getDesign3Images(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        for (int i=1;i<21;i++){
            arrayList.add("f"+i);
        }


        return arrayList;
    }


    public static ArrayList<String> getDesign4Images(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        for (int i=1;i<21;i++){
            arrayList.add("h"+i);
        }


        return arrayList;
    }

    public static ArrayList<String> getDesign5Images(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        for (int i=1;i<21;i++){
            arrayList.add("p"+i);
        }


        return arrayList;
    }
}
