package com.oose.group18.RecommenderController;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.*;

import com.oose.group18.RecommenderController.RestaurantWithScore;
import com.oose.group18.Entity.Post;

public class Recommender {


    public Recommender() {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "./src/main/java/com/oose/group18/RecommenderController/recommender.py",
                    "--rating-data", "./src/main/java/com/oose/group18/RecommenderController/rating_sparse.data", "--train"
            );
            Process p = pb.start();

            System.out.println("Recommender system initialized!");
        }catch(Exception e){System.out.println(e);}
    }


    public static List<Integer> getRecommend(int id, int topk) {
        List<Integer> rec_list = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("python","./src/main/java/com/oose/group18/RecommenderController/recommender.py",
                    "--rating-data","./src/main/java/com/oose/group18/RecommenderController/rating_sparse.data",
                    "--uid", ""+id, "--topk", ""+topk);
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            for (int i = 0; i < topk; i++) {
                String[] contents = in.readLine().split(" ");
                rec_list.add(Integer.parseInt(contents[0]));
            }

        }
        catch(Exception e){System.out.println(e);}
        return rec_list;
    }

    public static List<RestaurantWithScore> getRecommendWithScore(int id, int topk) {
        List<RestaurantWithScore> rec_list = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("python","./src/main/java/com/oose/group18/RecommenderController/recommender.py",
                    "--rating-data","./src/main/java/com/oose/group18/RecommenderController/rating_sparse.data",
                    "--uid", ""+id, "--topk", ""+topk);
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            for (int i = 0; i < topk; i++) {
                RestaurantWithScore res = new RestaurantWithScore();
                String[] contents = in.readLine().split(" ");
                res.rid = Integer.parseInt(contents[0]);
                res.score = Float.parseFloat(contents[1]);
                rec_list.add(res);
            }

        }
        catch(Exception e){System.out.println(e);}
        return rec_list;
    }



    public static void main(String[] args) {
    try {
        String uid = "1";
        int topk = 12;

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
