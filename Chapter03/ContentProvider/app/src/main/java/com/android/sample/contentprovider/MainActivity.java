package com.android.sample.contentprovider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Cursor cursor = getImage();
            if (cursor.moveToFirst()) {

                // get index of column
                int idColumnNumber = cursor.getColumnIndexOrThrow(ImageColumns._ID);
                int titleColumnNumber = cursor.getColumnIndexOrThrow(ImageColumns.TITLE);
                int dateTakenColumnNumber = cursor.getColumnIndexOrThrow(ImageColumns.DATE_TAKEN);

                //get data of index
                long id = cursor.getLong(idColumnNumber);
                String title = cursor.getString(titleColumnNumber);
                long dateTaken = cursor.getLong(dateTakenColumnNumber);
                Uri imageUri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id);

                TextView textView = (TextView) findViewById(R.id.text_view);
                ImageView imageView = (ImageView) findViewById(R.id.image_view);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(dateTaken);
                String text = DateFormat.format("yyyy/MM/dd(E) kk:mm:ss", calendar).toString();

                textView.setText(text);
                imageView.setImageURI(imageUri);
            }
            cursor.close();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "Please grant storage permission.",
                    Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        com.android.sample.contentprovider.onmemory.WordActivity.class));
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        com.android.sample.contentprovider.sqlite.WordActivity.class));
            }
        });
    }

    private Cursor getImage() {
        ContentResolver contentResolver = getContentResolver();
        Uri queryUri = Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                ImageColumns._ID,
                ImageColumns.TITLE,
                ImageColumns.DATE_TAKEN,
        };

        String sortOrder = ImageColumns.DATE_TAKEN + " DESC";

        queryUri = queryUri.buildUpon().appendQueryParameter("limit", "1").build();

        return contentResolver.query(queryUri, projection, null, null, sortOrder);
    }
}
