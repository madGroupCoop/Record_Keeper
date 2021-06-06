package com.example.recordkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import static com.example.recordkeeper.DatabaseHelper.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase rDatabase;
    private RecordsAdapter rAdapter;
    private EditText rEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //writes the contents of the recyclerview in the database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        rDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rAdapter = new RecordsAdapter(this, getAllItems());
        recyclerView.setAdapter(rAdapter);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        //Deletes an item in the recyclerview by swiping on it
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        //Initializing & Intenting the Buttons (Add Item & Back up to email)
        rEditTextName = findViewById(R.id.edittext_name);

        Button buttonAdd = findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(v -> addItem());


        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_EMAIL, new String[] {"example@domain.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "BackUp Of Personal Details");
            i.putExtra(Intent.EXTRA_TEXT, "Attached below is a back up of your personal details");
            i.setType("application/octet-stream");
            i.putExtra(Intent.EXTRA_STREAM,(getDatabasePath(DATABASE_NAME)));
            startActivity(Intent.createChooser(i, "Send e-mail"));

        });
    }

    private void addItem() {
        if (rEditTextName.getText().toString().trim().length() == 0) {
            return;
        }
        String name = rEditTextName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.RecordsEntry.COLUMN_NAME, name);
        rDatabase.insert(DatabaseContract.RecordsEntry.TABLE_NAME, null, cv);
        rAdapter.swapCursor(getAllItems());

        rEditTextName.getText().clear();
    }
    private void removeItem(long id) {
        rDatabase.delete(DatabaseContract.RecordsEntry.TABLE_NAME,
                DatabaseContract.RecordsEntry._ID + "=" + id, null);
        rAdapter.swapCursor(getAllItems());
    }


    private Cursor getAllItems() {
        return rDatabase.query(
                DatabaseContract.RecordsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.RecordsEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

}