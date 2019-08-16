package com.example.a13162.activitytest;


import java.util.ArrayList;
import java.util.List;

public class Data{
    public static boolean active =false;
    //public static ArrayList<String> tag=new ArrayList<String>();
    /*public static ArrayList getTag(){
        return tag;
    }*/
    public static ArrayList<String> nfclist=new ArrayList<>();
    private static List<TagClass> TagList1=new ArrayList<>();
    private static List<TagClass> TagList=new ArrayList<>();
    public static List getTagList(){
        return TagList;
    }
    public static List getTagList1(){
        return TagList1;
    }
    public static void tagListAdd(TagClass item){
        TagList.add(item);
    }
    public static void tagListAdd1(TagClass item){
        TagList1.add(item);
    }
    public static ArrayList<String> getnfclist(){return nfclist;}
    //public static ArrayList<TagClass> tag2=new Arraylist<TagClass>();
   /* public static void addTag(String string){
        tag.add(string);
    }*/
    public static int getIsactive() {
        if(Data.active==false){
            return 0;
        }else{
            return 1;
        }
    }
    public static void change() {
        if(Data.active==false){
            Data.active=true;
        }else{
            Data.active=false;
        }
    }
}

