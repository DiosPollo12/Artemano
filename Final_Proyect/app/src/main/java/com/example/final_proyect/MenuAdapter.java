package com.example.final_proyect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuOption> menuList;
    private Context context;
    private OnMenuOptionClickListener listener;

    public interface OnMenuOptionClickListener {
        void onMenuOptionClick(MenuOption option);
    }

    public MenuAdapter(List<MenuOption> menuList, Context context, OnMenuOptionClickListener listener) {
        this.menuList = menuList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_option, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuOption option = menuList.get(position);
        holder.title.setText(option.getTitle());
        holder.icon.setImageResource(option.getIconResId());

        holder.itemView.setOnClickListener(v -> listener.onMenuOptionClick(option));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_option_title);
            icon = itemView.findViewById(R.id.menu_option_icon);
        }
    }
}
