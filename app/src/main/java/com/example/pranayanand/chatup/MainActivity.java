package com.example.pranayanand.chatup;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.format.DateFormat;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Date;


public class MainActivity extends AppCompatActivity {

    FirebaseListAdapter<ChatMessage> adapter;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    ListView listView;
    ImageButton sendButton;
    EditText editText;
    ChatMessage model;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView username;
    TextView time;
    TextView message;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        listView = (ListView) findViewById(R.id.listview);
        sendButton = (ImageButton) findViewById(R.id.sendButton);
        editText = (EditText) findViewById(R.id.editText);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        username= (TextView) findViewById(R.id.message_user);
        time= (TextView) findViewById(R.id.message_time);
        message= (TextView) findViewById(R.id.message_text);
        model = new ChatMessage();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);
                }
            }
        };

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new ChatMessage(FirebaseAuth.getInstance().getCurrentUser().getEmail(), editText.getText().toString() ));
                editText.setText("");
            }
        });
        displayMessage();

    }




    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.signout){

            mAuth.signOut();
            Toast.makeText(MainActivity.this, "Successfully Signed Out.", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(MainActivity.this, Login.class);
            startActivity(intent2);





        }
        return true;
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void displayMessage(){


        adapter = new FirebaseListAdapter<ChatMessage>(MainActivity.this, ChatMessage.class, R.layout.list_item, databaseReference) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView text, time, username;
                text = v.findViewById(R.id.message_text);
                time = v.findViewById(R.id.message_time);
                username = v.findViewById(R.id.message_user);

                text.setText(model.getMessage());
                username.setText(model.getUsername());
                time.setText(android.text.format.DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getTime()));





            }
        };
        listView.setAdapter(adapter);

    };

}
