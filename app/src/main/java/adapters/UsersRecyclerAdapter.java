package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.user.User;

import org.w3c.dom.Text;

import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<User> userList;
    private Callback callback;

    public Callback getCallback() {
        if (callback == null) {
            callback = new Callback() {
                @Override
                public void onItemClick(int position) {

                }
            };
        }
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onItemClick(int position);
    }


    public UsersRecyclerAdapter(@NonNull Context context, @NonNull List<User> userList) {
        this.mContext = context;
        this.userList = userList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.row_user, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = this.userList.get(position);
        holder.txtViewUserName.setText(user.getUsername());
        holder.txtViewEmail.setText(user.getEmail());
        holder.setTagPosition(position);
    }

    @Override
    public int getItemCount() {
        if (this.userList != null) return this.userList.size();
        return 0;
    }

    @Override
    public void onClick(View view) {
        Integer position = (Integer) view.getTag();
        if (position != null) {
            getCallback().onItemClick(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView txtViewUserName;
        TextView txtViewEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            txtViewEmail = itemView.findViewById(R.id.txt_view_email_address);
            txtViewUserName = itemView.findViewById(R.id.txt_view_user_name);
        }

        private void setTagPosition(int position) {
            this.view.setTag(position);
        }
    }


}
