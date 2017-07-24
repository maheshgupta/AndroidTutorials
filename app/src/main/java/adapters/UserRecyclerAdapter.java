package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.user.User;

import java.util.List;

/**
 * Created by Dhiren Rachamallu on 07/24/17.
 */

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder> implements View.OnClickListener{

    private List<User> usersList;
    private Context mContext;
    private Callback callback;

    public UserRecyclerAdapter(@NonNull Context context, @NonNull List<User> objects) {
        this.usersList = objects;
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
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.row_user, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = usersList.get(position);
        String userName = user.getName();
        String userEmail = user.getEmail();
        holder.setTag(position);
        holder.holderView.setOnClickListener(this);
        holder.mUserName.setText(userName);
        holder.mEmailAddress.setText(userEmail);
    }

    @Override
    public int getItemCount() {
        if (usersList == null) return 0;
        return usersList.size();
    }

    @Override
    public void onClick(View view) {
        Integer position = (Integer) view.getTag();
        if (position != null) {
            this.callback.onClick(position);
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        View holderView;
        TextView mUserName;
        TextView mEmailAddress;

        public UserViewHolder(View itemView) {
            super(itemView);
            holderView = itemView;
            mUserName = itemView.findViewById(R.id.txt_view_user_name);
            mEmailAddress = itemView.findViewById(R.id.txt_view_email_address);
        }

        public void setTag(int position) {
            this.holderView.setTag(position);
        }
    }
}
