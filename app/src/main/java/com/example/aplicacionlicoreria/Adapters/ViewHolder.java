package com.example.aplicacionlicoreria.Adapters;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class ViewHolder extends RecyclerView.ViewHolder {
    private int mCurrentPosition;

    public ViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}
