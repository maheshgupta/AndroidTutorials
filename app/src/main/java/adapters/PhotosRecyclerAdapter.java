package adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.Photo;

import java.util.List;

public class PhotosRecyclerAdapter extends RecyclerView.Adapter<PhotosRecyclerAdapter.PhotosViewHolder> implements View.OnClickListener {

    private List<Photo> mPhotoList;

    private Context mContext;

    private Callback callback;

    public PhotosRecyclerAdapter(@NonNull Context context, @NonNull List<Photo> photos) {
        this.mPhotoList = photos;
        this.mContext = context;
    }

    public Callback getCallback() {
        if (callback==null){
            callback = new Callback() {
                @Override
                public void onClick(int position) {

                }
            };
        }
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    public interface Callback {
        void onClick(int position);
    }


    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.row_photo, parent, false);
        PhotosViewHolder photosViewHolder = new PhotosViewHolder(view);
        return photosViewHolder;
    }

    @Override
    public void onBindViewHolder(PhotosViewHolder holder, int position) {
        Photo photo = this.mPhotoList.get(position);
        holder.setTag(position);
        holder.holderView.setOnClickListener(this);
        holder.txtViewPhotoTitle.setText(photo.getTitle());
        Glide.with(this.mContext).load(photo.getThumbnailUrl()).into(holder.photoImageView);

    }

    @Override
    public int getItemCount() {
        if (this.mPhotoList == null) return 0;
        return this.mPhotoList.size();
    }

    @Override
    public void onClick(View view) {
        Integer position = (Integer) view.getTag();
        if (position != null) {
            this.callback.onClick(position);
        }
    }

    public static class PhotosViewHolder extends RecyclerView.ViewHolder {

        View holderView;
        ImageView photoImageView;
        TextView txtViewPhotoTitle;


        public PhotosViewHolder(View itemView) {
            super(itemView);
            holderView = itemView;
            photoImageView = itemView.findViewById(R.id.image_view_photo);
            txtViewPhotoTitle = itemView.findViewById(R.id.text_view_photo_title);
        }


        public void setTag(int position) {
            this.holderView.setTag(position);
        }

    }


}
