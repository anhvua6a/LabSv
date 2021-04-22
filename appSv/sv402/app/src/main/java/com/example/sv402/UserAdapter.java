package com.example.sv402;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sv402.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private Context context;
    private List<User> userList;
    private onClickDelete delete;

    public UserAdapter(Context context, List<User> userList, onClickDelete delete) {
        this.context = context;
        this.userList = userList;
        this.delete = delete;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.tvName.setText(userList.get(position).name);
        holder.tvAddress.setText(userList.get(position).address);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete.onClickXoa(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                delete.onClickUpdate(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }



    public static class UserHolder extends RecyclerView.ViewHolder {
        private ImageView imgDelete;
        private TextView tvName;
        private TextView tvAddress;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);

        }
    }

    public interface onClickDelete {
        void onClickXoa(int position);
        void onClickUpdate(int position);
    }
}
