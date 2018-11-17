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
    private List<Message> list;
    CustomItemClickListener listener;

    public MyRecyclerViewAdapter(Context context, List<Message> list, CustomItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_row, parent, false);
        final ViewHolder mViewHoder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, mViewHoder.getAdapterPosition());
            }
        });
        return mViewHoder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = list.get(position);

        holder.testName.setText(message.getName());
        holder.textCategory.setText(message.getCategory());
        holder.textPic.setImageResource(message.getPic());
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

