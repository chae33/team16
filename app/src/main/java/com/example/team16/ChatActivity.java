package com.example.team16;

import  androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.CharacterData;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private String nick = "nick1";

    private EditText EditText_chat;
    private Button Button_send;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button_send = findViewById(R.id.Button_send);
        EditText_chat = findViewById(R.id.EditText_chat);

        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = EditText_chat.getText().toString();

                if(msg != null) {
                    ChatData chat = new ChatData();
                    chat.setNickname(nick);
                    chat.setMsg("hi");
                    myRef.setValue(chat);
                }
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView = findViewById(R.id.my_recycler_view);

        chatList = new ArrayList<>();
        mAdapter = new ChatAdapter(chatList, ChatActivity.this, nick);
        mRecyclerView.setAdapter(mAdapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        // ???????????? ????????? ???????????? ?????? ???

        ChatData chat = new ChatData();
        chat.setNickname(nick);
        chat.setMsg("hi");
        myRef.setValue(chat);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {
                ChatData chat = datasnapshot.getValue(ChatData.class);
                ((ChatAdapter) mAdapter).addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot datasnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // setValue : message ?????? ????????? ?????????.
        // 1. recyclerView = ??????, ???????????? ????????? ?????????
        // 2. DB ????????? ?????????.3
        // 3. ????????? ?????? ?????? ????????? ??????

        // 1-1. recyclerview - chat data
        // 1. message, nickname, isMine(??? ???????????? ?????????) - Data Transfer Object
        //(DTO : ???????????? ???????????? ?????? ??????)
    }
}