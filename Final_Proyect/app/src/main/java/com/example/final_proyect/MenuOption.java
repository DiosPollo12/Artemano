package com.example.final_proyect;

public class MenuOption {
    private String title;
    private int iconResId;

    public MenuOption(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResId() {
        return iconResId;
    }
}
