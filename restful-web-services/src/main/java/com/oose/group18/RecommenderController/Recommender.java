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

    public Recommender() {
        ProcessBuilder pb = new ProcessBuilder("python","./src/main/java/com/oose/group18/RecommenderController/recommender.py",
                    "--rating-data","./src/main/java/com/oose/group18/RecommenderController/rating_sparse.data", "--train"
                    );
        //ProcessBuilder pb = new ProcessBuilder("python", "./src/main/java/com/oose/group18/RecommenderController/test.py");
        Process p = pb.start();
    }


    public static List<Integer> getRecommend(int id, int topk) {
        List<Integer> result = new ArrayList<>();
        try {
            //System.out.println();
        /*
        ProcessBuilder pb = new ProcessBuilder("python","recommender.py",
                    "--rating-data","./rating_sparse.data",
                    "--uid", uid, "--topk", ""+topk);
        */
            ProcessBuilder pb = new ProcessBuilder("python","./src/main/java/com/oose/group18/RecommenderController/recommender.py",
                    "--rating-data","./src/main/java/com/oose/group18/RecommenderController/rating_sparse.data",
                    "--uid", ""+id, "--topk", ""+topk);
            //ProcessBuilder pb = new ProcessBuilder("python", "./src/main/java/com/oose/group18/RecommenderController/test.py");
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            for (int i = 0; i < 10; i++) {
                result.add(Integer.parseInt(in.readLine()));
            }

        }
        catch(Exception e){System.out.println(e);}
        return result;
    }
    public static void main(String[] args) {
    try {
        //System.out.println();
        String uid = "1";
        int topk = 12;
        /*
        ProcessBuilder pb = new ProcessBuilder("python","recommender.py",
                    "--rating-data","./rating_sparse.data",
                    "--uid", uid, "--topk", ""+topk);
        */
        ProcessBuilder pb = new ProcessBuilder("python","./src/main/java/com/oose/group18/RecommenderController/recommender.py",
                "--rating-data","./src/main/java/com/oose/group18/RecommenderController/rating_sparse.data",
                "--uid", ""+uid, "--topk", ""+topk);

        Process p = pb.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        List<String> res= new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            res.add(in.readLine());
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(res.get(i));
        }

    }
    catch(Exception e){System.out.println(e);}
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