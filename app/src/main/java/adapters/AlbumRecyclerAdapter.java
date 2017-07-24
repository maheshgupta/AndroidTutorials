package adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.Album;

import java.util.List;

/**
 * Created by Dhiren Rachamallu on 07/24/17.
 */

public class AlbumRecyclerAdapter extends RecyclerView.Adapter<AlbumRecyclerAdapter.AlbumViewHolder> implements View.OnClickListener{

    private List<Album> albumList;
    private Context mContext;
    private Callback callback;

    public AlbumRecyclerAdapter(@NonNull Context mContext, @LayoutRes int resource, @NonNull List<Album> objects) {
        this.mContext = mContext;
        this.albumList = objects;
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
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.row_album, parent, false);
        AlbumViewHolder viewHolder = new AlbumViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        String albumName = album.getTitle();
        holder.setTag(position);
        holder.holderView.setOnClickListener(this);
        holder.mAlbumName.setText(albumName);
    }

    @Override
    public int getItemCount() {
        if (albumList == null) return 0;
        return albumList.size();
    }

    @Override
    public void onClick(View view) {
        Integer position = (Integer) view.getTag();
        if (position != null) {
            this.callback.onClick(position);
        }
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {

        TextView mAlbumName;
        View holderView;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            holderView = itemView;
            mAlbumName = itemView.findViewById(R.id.txt_view_album_name);
        }

        public void setTag(int position) {
            this.holderView.setTag(position);
        }
    }
}
