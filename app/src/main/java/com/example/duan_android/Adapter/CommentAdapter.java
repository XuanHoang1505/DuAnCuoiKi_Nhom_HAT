package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan_android.Model.Comment;
import com.example.duan_android.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context mContext;
    private List<Comment> mListComment;

    // Constructor nhận context
    public CommentAdapter(Context mContext, List<Comment> mListComment) {
        this.mContext = mContext;
        this.mListComment = mListComment;
    }

    // Cập nhật danh sách comment
    public void setData(List<Comment> list) {
        this.mListComment = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho item comment
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // Lấy comment tại vị trí tương ứng
        Comment comment = mListComment.get(position);
        if (comment != null) {
            // Set hình ảnh và nội dung bình luận
            holder.imgComment.setImageResource(comment.getResourceImage());
            holder.tvComment.setText(comment.getPreview());
        }
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng comment
        return mListComment == null ? 0 : mListComment.size();
    }

    // ViewHolder cho comment
    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgComment;  // Hình ảnh
        private TextView tvComment;   // Nội dung bình luận

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgComment = itemView.findViewById(R.id.img_comment); // ID của ImageView trong item_comment.xml
            tvComment = itemView.findViewById(R.id.tv_comment);   // ID của TextView trong item_comment.xml
        }
    }
}
