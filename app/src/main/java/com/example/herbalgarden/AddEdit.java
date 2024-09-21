package com.example.herbalgarden;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.herbalgarden.helper.DbHelper;

public class AddEdit extends AppCompatActivity {
    EditText etidHG, etnamaHG, etmanfaatHG;
    Button btsubmitHG, btcancelHG;
    DbHelper SQLite = new DbHelper(this);
    String id, nama, manfaat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etidHG = findViewById(R.id.etidHG);
        etnamaHG = findViewById(R.id.etnamaHG);
        etmanfaatHG = findViewById(R.id.etmanfaatHG);
        btsubmitHG = findViewById(R.id.btsubmitHG);
        btcancelHG = findViewById(R.id.btcancelHG);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        nama = getIntent().getStringExtra(MainActivity.TAG_NAMA);
        manfaat = getIntent().getStringExtra(MainActivity.TAG_MANFAAT);

        if (id == null || id.equals("")) {
            setTitle("Tambah Baru");
        } else {
            setTitle("Edit Data");
            etidHG.setText(id);
            etnamaHG.setText(nama);
            etmanfaatHG.setText(manfaat);
        }

        btsubmitHG.setOnClickListener(v -> {
            try {
                if (etidHG.getText().toString().equals("")) {
                    save();
                } else {
                    edit();
                }
            } catch (Exception e) {
                Log.e("submit", e.toString());
            }
        });

        btcancelHG.setOnClickListener(v -> {
            blank();
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            blank();
            this.finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void blank() {
        etnamaHG.requestFocus();
        etidHG.setText(null);
        etnamaHG.setText(null);
        etmanfaatHG.setText(null);
    }

    private void save() {
        if (String.valueOf(etnamaHG.getText()).equals("") ||
                String.valueOf(etmanfaatHG.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Silakan masukkan nama atau manfaat...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(etnamaHG.getText().toString().trim(), etmanfaatHG.getText().toString().trim());
            blank();
            finish();
        }
    }

    private void edit() {
        if (String.valueOf(etnamaHG.getText()).equals("") ||
                String.valueOf(etmanfaatHG.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Silakan masukkan nama atau manfaat...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(etidHG.getText().toString().trim()),
                    etnamaHG.getText().toString().trim(),
                    etmanfaatHG.getText().toString().trim());
            blank();
            finish();
        }
    }
}
