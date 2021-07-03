package com.db.satu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseManager dm;
    EditText nama,hobi;
    Button addBtn;
    TableLayout table4data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DatabaseManager(this);
        table4data =(TableLayout) findViewById(R.id.table_data);
        nama = (EditText) findViewById(R.id.inNama);
        hobi = (EditText) findViewById(R.id.inHobi);
        addBtn = (Button) findViewById(R.id.btnAdd);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpKamuta();
            }
        });
        updateTable();
    }
    protected void simpKamuta(){
        try {
            dm.addRow(nama.getText().toString(),hobi.getText().toString());
            Toast.makeText(getBaseContext(),nama.getText().toString()+", Berhasil disimpan", Toast.LENGTH_LONG).show();

            updateTable();
            kosongkanField();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getBaseContext(),"Gagal simpan, "+ e.toString(),Toast.LENGTH_LONG).show();
        }
    }
    protected void kosongkanField(){
        nama.setText("");
        hobi.setText("");
    }
    protected void updateTable(){
        while (table4data.getChildCount()>1){
            table4data.removeViewAt(1);
        }

        ArrayList<ArrayList<Object>> data =dm.ambilSemuaBaris();

        for (int posisi=0; posisi<data.size(); posisi++){
            TableRow tabelBaris = new TableRow(this);
            ArrayList<Object>baris = data.get(posisi);

            TextView idTxt = new TextView(this);
            idTxt.setText(baris.get(0).toString());
            tabelBaris.addView(idTxt);

            TextView namaTxt = new TextView(this);
            namaTxt.setText(baris.get(1).toString());
            tabelBaris.addView(namaTxt);

            TextView hobiTxt = new TextView(this);
            hobiTxt.setText(baris.get(2).toString());
            tabelBaris.addView(hobiTxt);

            table4data.addView(tabelBaris);
        }
    }
}

