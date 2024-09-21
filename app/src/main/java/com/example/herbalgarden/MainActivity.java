package com.example.herbalgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.herbalgarden.adapter.Adapter;
import com.example.herbalgarden.helper.DbHelper;
import com.example.herbalgarden.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> itemList = new ArrayList<>();
    Adapter adapter;
    DbHelper SQLite;

    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_MANFAAT = "manfaat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLite = new DbHelper(getApplicationContext());

        FloatingActionButton fab = findViewById(R.id.fab);
        listView = findViewById(R.id.list_view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                startActivity(intent);
            }
        });

        DbHelper dbHelper = new DbHelper(this);

        int idToUpdate = 1;
        String newNama = "Updated Nama";
        String newManfaat = "Updated Manfaat";
        dbHelper.update(idToUpdate, newNama, newManfaat);

        int idToDelete = 1;
        dbHelper.delete(idToDelete);

        adapter = new Adapter(this, itemList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final String nama = itemList.get(position).getNama();
                final String manfaat = itemList.get(position).getManfaat();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, (dialogInterface, which) -> {
                    switch (which) {
                        case 0:
                            Intent intent = new Intent(MainActivity.this, AddEdit.class);
                            intent.putExtra(TAG_ID, idx);
                            intent.putExtra(TAG_NAMA, nama);
                            intent.putExtra(TAG_MANFAAT, manfaat);
                            startActivity(intent);
                            break;
                        case 1:
                            SQLite.delete(Integer.parseInt(idx));
                            itemList.clear();
                            getAllData();
                            break;
                    }
                }).show();
                return true;
            }
        });
        getAllData();
    }

    private void getAllData() {
        itemList.clear(); // Ensure the list is clear before adding new items
        ArrayList<HashMap<String, String>> row = SQLite.getAllData();
        for (HashMap<String, String> map : row) {
            String id = map.get(TAG_ID);
            String nama = map.get(TAG_NAMA);
            String manfaat = map.get(TAG_MANFAAT);

            Data data = new Data();
            data.setId(id);
            data.setNama(nama);
            data.setManfaat(manfaat);

            itemList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllData();
    }
}
