package com.example.recordkeeper;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.RecordsViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    public RecordsAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }
    public class RecordsViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public RecordsViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textview_name_item);
        }
    }
    @Override
    public RecordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.database_records, parent, false);
        return new RecordsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecordsViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.RecordsEntry.COLUMN_NAME));
        long id = mCursor.getLong(mCursor.getColumnIndex(DatabaseContract.RecordsEntry._ID));

        holder.itemView.setTag(id);
        holder.nameText.setText(name);
    }
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}

