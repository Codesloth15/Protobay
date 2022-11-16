package codesloth.com.apivolleysingleton.ImagePackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import codesloth.com.apivolleysingleton.R;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ItemViewHolder> {
    Context context;
    List<ImageListConstructor> itemList;
    boolean counter;

    public ImageListAdapter(Context context, List<ImageListConstructor> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tView = LayoutInflater.from(context).inflate(R.layout.cardview_for_image, parent, false);
        return new ItemViewHolder(tView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ImageListConstructor itemList_Data_Position = itemList.get(position);
        holder.setImage(itemList_Data_Position.getImageUrl());
        holder.setTags(itemList_Data_Position.getTags());
        holder.setLikes(itemList_Data_Position.getLike());
        TextView textView = holder.textView;
        counter = false;
        holder.Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewImageActivity.class);
                intent.putExtra("Images",  itemList_Data_Position.getImageUrl());
                intent.putExtra("Likes", itemList_Data_Position.getLike());
                context.startActivity(intent);
            }
        });

        holder.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!counter) {
                    textView.setVisibility(View.VISIBLE);
                    counter = true;
                } else {
                    textView.setVisibility(View.GONE);
                    counter = false;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tags, likes;
        View view;
        ImageView Image;
        TextView textView;
        CardView cardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            Image = itemView.findViewById(R.id.Image);
            textView = itemView.findViewById(R.id.Tags);
            cardView = view.findViewById(R.id.cardView_in_ViewImage);
        }

        public void setImage(String url) {
            image = view.findViewById(R.id.Image);
            Glide.with(context).load(url).into(image);
        }

        public void setTags(String Tags) {
            tags = view.findViewById(R.id.Tags);
            tags.setText(Tags);
        }

        @SuppressLint("SetTextI18n")
        public void setLikes(int Likes) {
            likes = view.findViewById(R.id.Likes);
            likes.setText("likes:" + Likes);
        }
    }

}
