package com.oose.group18.RecommenderController;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.util.*;

import com.oose.group18.RecommenderController.RestaurantWithScore;

import jdk.jshell.spi.ExecutionControl.NotImplementedException;

import com.oose.group18.Entity.Post;
import com.oose.group18.Entity.Review;

public class Recommender {

    String rating_data_path = "./src/main/java/com/oose/group18/RecommenderController/rating_sparse.data";
    String py_program_path = "./src/main/java/com/oose/group18/RecommenderController/recommender.py";

    public Recommender() {
        init();
    }

    public void init() {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", py_program_path,
                    "--rating-data", rating_data_path, "--train"
            );
            Process p = pb.start();
            p.waitFor();
            System.out.println("Recommender system initialized!");
        }catch(Exception e){System.out.println(e);}
    }

    public void update(Review r) {
        writeToRatingData(r);
        init();
    }

    public void writeToRatingData (Review r) {
        BufferedWriter writer = new BufferedWriter(new FileWriter(rating_data_path));
        String review_string = r.getUserId() + " " + r.getRestaurantId() + " " 
                                + r.getScore() + " " + r.getTimeStep().toString() + '\n';
        writer.write(review_string);
        writer.close();
    }

    public static List<Integer> getRecommend(int id, int topk) {
        List<Integer> rec_list = new ArrayList<>();
        try {
            ProcessBuilder pb = new ProcessBuilder("python", py_program_path,
                    "--rating-data", rating_data_path,
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

}
