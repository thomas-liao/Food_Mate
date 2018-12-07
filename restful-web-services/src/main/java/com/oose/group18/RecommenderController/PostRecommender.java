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

import com.oose.group18.Entity.Post;
import com.oose.group18.RecommenderController.Recommender;
import com.oose.group18.RecommenderController.RestaurantWithScore;

public class PostRecommender extends Recommender {
    //final int nUserMax;
    int nUser;
    private float[][] userSim; // = new float[nUserMax][nUserMax];

    public PostRecommender(int n_user) {
        
        init(n_user);
    }
    
    public void init (int n_user) {
        nUser = n_user;
        userSim = new float[nUser][nUser];
        String fileName = "./src/main/java/com/oose/group18/RecommenderController/user_sim.txt";
        // Load User similarity matrix from file
        Scanner sc;
        try {
            sc = new Scanner(new File(fileName));
            int row = 0;
            
            while (sc.hasNextLine() && row < n_user) {
                int column = 0;
                Scanner s2 = new Scanner(sc.nextLine());
                while (s2.hasNext() && column < n_user) {
                    String s = s2.next();
                    userSim[row][column] = Float.parseFloat(s);
                    // System.out.println(s);
                    column += 1;
                }
                s2.close();
                row += 1;
            }
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
    }

    public void update (int n_user) {
        init(n_user);
    }
    
    public List<Post> getRecommendPost (List<Post> posts, int id, int topk ) {
        int NUM_RECOMMEND_RESTAURANTS = 1000;
        List<RestaurantWithScore> recomm_rst_list;
        Map<Integer, Float> rstScoreMap = new HashMap<>();
        List<Post> rec_posts = new ArrayList<>();
        recomm_rst_list = getRecommendWithScore(id, NUM_RECOMMEND_RESTAURANTS);
        for (int i = 0; i < recomm_rst_list.size(); i++) {
            rstScoreMap.put(recomm_rst_list.get(i).rid, recomm_rst_list.get(i).score);
        }
        
        // filter posts that are not in recomm_rst_list
        List<Post> posts_filtered = new ArrayList<>();
        posts.forEach((post) -> {
            if (rstScoreMap.containsKey(post.getRestaurant().getId()) &&
                post.getUser().getId() != id) {
                posts_filtered.add(post);
            }
        });
        if (posts_filtered.isEmpty()) {
            return rec_posts;
        }
        
        PostComparer comparator = new PostComparer(id, userSim, rstScoreMap);
        Collections.sort(posts_filtered, comparator);
        Collections.reverse(posts_filtered); // Collections.sort() sort the list in ascending order

        for (int i = 0; i < topk && i < posts_filtered.size(); i++) {
            rec_posts.add(posts_filtered.get(i));
        }
        
        return rec_posts;
    }
}


class PostComparer implements Comparator<Post> {
    private int _uid;
    private float[][] _user_sim;
    private Map<Integer, Float> _rst_score_map;

    public PostComparer (int uID, float[][] userSim, Map<Integer, Float> rstScoreMap) {
        _uid = uID;
        _user_sim = userSim;
        _rst_score_map = rstScoreMap;
    }
    @Override
    public int compare(Post x, Post y) {
        int xuid = x.getId();
        int yuid = y.getId();
        int xrid = x.getRestaurant().getId();
        int yrid = y.getRestaurant().getId();

        float x_user_sim = _user_sim[xuid][_uid];
        float y_user_sim = _user_sim[yuid][_uid];

        float w_rst = (float)1.0;
        float w_sim = (float)0.1;

        float x_rst_score = _rst_score_map.get(xrid);
        float y_rst_score = _rst_score_map.get(yrid);
        
        float x_value = w_rst * x_rst_score + w_sim * x_user_sim;
        float y_value = w_rst * y_rst_score + w_sim * y_user_sim;
        return x_value < y_value ? -1 : x_value > y_value ? 1 : 0;
    }
}