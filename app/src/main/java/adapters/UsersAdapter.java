package adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.user.User;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<User> {

    private List<User> usersList;
    private Context mContext;

    static class ViewHolder {
        TextView txtViewUserName;
        TextView txtViewEmail;
    }

    public UsersAdapter(@NonNull Context context, @NonNull List<User> objects) {
        super(context, 0, objects);
        this.usersList = objects;
        this.mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
            holder = new ViewHolder();
            holder.txtViewUserName = view.findViewById(R.id.txt_view_user_name);
            holder.txtViewEmail = view.findViewById(R.id.txt_view_email_address);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        User user = this.usersList.get(position);
        holder.txtViewUserName.setText(user.getUsername());
        holder.txtViewEmail.setText(user.getEmail());


        return view;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }
}
