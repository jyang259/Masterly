package com.example.masterly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditTextActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        Intent intent = getIntent();
        final String message = intent.getStringExtra(NoteActivity.EXTRA_MESSAGE);
    }
}
