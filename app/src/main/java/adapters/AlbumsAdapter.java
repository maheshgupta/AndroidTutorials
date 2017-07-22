package adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.Album;

import org.w3c.dom.Text;

import java.util.List;


public class AlbumsAdapter extends ArrayAdapter<Album> implements View.OnClickListener {

    private List<Album> albumList;
    private Context context;

    public AlbumsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Album> objects) {
        super(context, resource, objects);
        this.context = context;
        this.albumList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            // First time, when inflating and screen is empty
            view = LayoutInflater.from(this.context).inflate(R.layout.row_album, parent, false);
            TextView txtView = view.findViewById(R.id.txt_view_album_name);
            txtView.setText(this.albumList.get(position).getTitle() + " First ");
//            view.setOnClickListener(this);
        } else {
            // When Android, is recycling the views.
            TextView txtView = view.findViewById(R.id.txt_view_album_name);
            txtView.setText(this.albumList.get(position).getTitle() + " Recycled");
        }
        view.setTag(position);
        return view;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public void onClick(View view) {
        Log.i("", "onClick: " + view.getTag());
    }
}
