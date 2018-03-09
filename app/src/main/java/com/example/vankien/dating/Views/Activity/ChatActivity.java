package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vankien.dating.Controllers.MessageController;
import com.example.vankien.dating.Models.MessageModel;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Utils.KeyboardUtils;
import com.example.vankien.dating.Views.Adapter.MessageAdapter;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    TextView txtName;
    String name;
    ListView lvChat;
    EditText txtMessage;
    ImageButton btnSend;

    ArrayList<MessageModel> arrMessage;
    MessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = txtMessage.getText().toString().trim();
                if (!message.equals("")){
                    MessageModel model = new MessageModel(true,message);
                    arrMessage.add(model);
                    adapter.notifyDataSetChanged();
                    txtMessage.setText("");
                    Log.e("Error message",model.getMessage());
                    lvChat.setAdapter(adapter);
                }
            }
        });
    }

    private void addControls() {
        txtName = findViewById(R.id.txtName);
        txtName.setText(name);
        lvChat = findViewById(R.id.lvChat);
        txtMessage = findViewById(R.id.txtMessage);
        btnSend = findViewById(R.id.btnSend);

        arrMessage = MessageController.getInstance().getExampleData();
        adapter = new MessageAdapter(this,R.layout.cell_my_message,arrMessage);
        lvChat.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
