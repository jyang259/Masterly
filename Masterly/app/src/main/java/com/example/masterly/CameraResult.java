package com.example.masterly;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class CameraResult extends AppCompatActivity {

    TextToSpeech t1;
    Button b1;
    Button edit;
    Button save;
    TextView content;
    public String message;

    public static final String THE_MESSAGAE = "";

    private DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {

            mUserId = mFirebaseUser.getUid();


            b1 = (Button) findViewById(R.id.button4);
            edit = (Button) findViewById(R.id.button10);
            content = (TextView) findViewById(R.id.textView3);
            content.setMovementMethod(ScrollingMovementMethod.getInstance());

            Intent intent = getIntent();
            message = intent.getStringExtra(NoteActivity.EXTRA_MESSAGE);
            content.setText(message);

            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        t1.setLanguage(Locale.UK);
                    }
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    final EditText taskEditText = new EditText(CameraResult.this);
                    taskEditText.setText(message);
                    AlertDialog alertDialog = new AlertDialog.Builder(CameraResult.this)
                            .setTitle("Edit Notes")
                            .setView(taskEditText)
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    message = String.valueOf(taskEditText.getText());
                                    content.setText(message);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    alertDialog.show();
                }
            });


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t1.speak(message, TextToSpeech.QUEUE_FLUSH, null);
                }
            });

            save = (Button) findViewById(R.id.button11);
            save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mDatabase.child("users").child(mUserId).child("Notes").push().setValue(message);
                }
            });


        }
    }

    @Override
    public void onBackPressed() {
        onPause();
        super.onBackPressed();
    }

    public void editIt(){
    }

    @Override
    public void onStop(){
        onPause();
        super.onStop();
    }

    public void onPause(){

        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
