package jhu.oose.group18.foodmate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Restaurant> list;

    public MyRecyclerViewAdapter(Context context, List<Restaurant> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Restaurant restaurant = list.get(position);

        holder.testName.setText(restaurant.getName());
        holder.textCategory.setText(restaurant.getCategory());
        holder.textPic.setImageResource(restaurant.getPic());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView textPic;
        public TextView testName, textCategory;
        public ViewHolder(View itemView) {
            super(itemView);

            testName = itemView.findViewById(R.id.main_name);
            textCategory = itemView.findViewById(R.id.main_category);
            textPic = itemView.findViewById(R.id.restaurant_logo);
        }
    }
}

