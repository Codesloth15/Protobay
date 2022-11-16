package codesloth.com.apivolleysingleton.ImagePackage;

import com.bumptech.glide.Glide;

public class ImageListConstructor {
    String imageUrl, tags;
    int like;

    public ImageListConstructor(String imageUrl, String tags, int like) {
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.like = like;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTags() {
        return tags;
    }

    public int getLike() {
        return like;
    }
}
