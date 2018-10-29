package com.oose.group18.RecommenderController;
import java.io.*;
import java.util.*;
public class Recommender {
public static void main(String[] args) {
    try {
        //System.out.println();
        String uid = args[0];
        int topk = 12;
        ProcessBuilder pb = new ProcessBuilder("python","recommender.py", 
                    "--rating-data","./rating_sparse.data",
                    "--uid", uid, "--topk", ""+topk);
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