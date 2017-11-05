package com.example.jaremylongley.planttracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jaremylongley on 10/8/17.
 */

public class ProgressTracker extends AppCompatActivity {
    List<Plant> userPlants;
    DatabaseHelper db;
    Plant displayingPlant;
    String date;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        this.db = new DatabaseHelper(this);
        this.userPlants = db.getAllPlants();
        initTable();
    }

    void addProgressButtonClicked(View view) {
        // Alert the user to add text to this progress report
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Report Your Plant's Progress Since Last Report");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String progressText = input.getText().toString();
                addRow(progressText);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    // OnClick event for checklist to be shown
    public void viewChecklistButtonClicked(View view) {
        Intent intent = new Intent(this, Checklist.class);
        startActivity(intent);
    }

    void addRow(String progressText) {
        TableLayout layout = (TableLayout) findViewById(R.id.ProgressTable);
        TableRow dummyRow = new TableRow(this);
        TextView date = new TextView(this);
        TextView notes = new TextView(this);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        date.setText(currentDateTimeString);
        notes.setText(progressText);
        dummyRow.addView(date);
        dummyRow.addView(notes);
        layout.addView(dummyRow, 2);
    }

    void initTable() {
        //This part defines the layout to be used for creating new rows
        TableLayout layout = (TableLayout) findViewById(R.id.ProgressTable);
        TableRow  titleRow = new TableRow(this);
        TableRow layoutRow = new TableRow(this);
        TextView title = new TextView(this);
        TextView dateColumn = new TextView(this);
        TextView notesColumn = new TextView(this);
        title.setPadding(3,3,3,3);
        title.setText("Your Plant's Progress");
        title.setTextSize(30);
        dateColumn.setText("Date");
        dateColumn.setTextSize(25);
        notesColumn.setText("Notes");
        notesColumn.setTextSize(25);
        titleRow.addView(title);
        layoutRow.addView(dateColumn);
        layoutRow.addView(notesColumn);
        layout.addView(titleRow,0);
        layout.addView(layoutRow,1);
    }
}
