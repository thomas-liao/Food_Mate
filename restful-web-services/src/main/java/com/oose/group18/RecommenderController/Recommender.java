package com.oose.group18.RecommenderController;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Recommender {

    public static List<Integer> getRecommend(int id, int topk) {
        List<Integer> result = new ArrayList<>();
        try {
            Object var2 = true;
            ProcessBuilder argument = new ProcessBuilder(new String[]{"python", "recommender.py",
                    "--rating-data", "./rating_sparse.data", "--uid", ""+id, "--topk", ""+topk});
            Process process = argument.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            for(int i = 0; i < topk; ++i) {
                result.add(Integer.getInteger(bufferedReader.readLine()));
            }
            for(int i = 0; i < topk; ++i) {
                System.out.println(result.get(i));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}

//import io.swagger.models.auth.In;
//
//import java.io.*;
//import java.util.*;
//
//public class Recommender {
//
//    public static List<Integer> getRecommend(Integer uid, Integer topk) {
//        ProcessBuilder pb = new ProcessBuilder("python","recommender.py",
//                "--rating-data","./rating_sparse.data",
//                "--uid", ""+uid, "--topk", ""+topk);
//        try {
//            Process p = pb.start();
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//            List<Integer> res= new ArrayList<>();
//            for (int i = 0; i < topk; i++) {
//                res.add(Integer.getInteger(in.readLine()));
//                System.out.println(in.readLine());
//            }
//            return res;
//        }
//        catch (Exception e) {
//            System.out.println("cannot get recommend restaurant");
//            return null;
//        }
//    }
//public static void main(String[] args) {
//    try {
//        //System.out.println();
//        String uid = args[0];
//        int topk = 12;
//        ProcessBuilder pb = new ProcessBuilder("python","recommender.py",
//                    "--rating-data","./rating_sparse.data",
//                    "--uid", uid, "--topk", ""+topk);
//        Process p = pb.start();
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//        List<String> res= new ArrayList<String>();
//        for (int i = 0; i < 10; i++) {
//            res.add(in.readLine());
//        }
//        for (int i = 0; i < 10; i++) {
//            System.out.println(res.get(i));
//        }
//
//    }
//    catch(Exception e){System.out.println(e);}
//}
//}