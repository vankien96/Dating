package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vankien.dating.Controllers.MessageController;
import com.example.vankien.dating.Controllers.MessageControllerCallback;
import com.example.vankien.dating.Models.FriendChatModel;
import com.example.vankien.dating.Models.MessageModel;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Adapter.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements MessageControllerCallback {
    TextView txtName;
    String name;
    String idFriend;
    String idUser;
    String avatar;
    MessageController controller;

    ListView lvChat;
    EditText txtMessage;
    ImageButton btnSend,btnBack,btnMenu;

    ArrayList<MessageModel> arrMessage;
    MessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        FriendChatModel model = (FriendChatModel) b.getSerializable("FriendData");
        name = model.getName();
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        idFriend = model.getId();
        avatar = model.getUrlAvatar();
        controller = MessageController.getInstance();
        controller.callback = this;

        addControls();
        addEvents();
        requestData();
    }

    private void requestData() {
        MessageController.getInstance().requestMessage(ChatActivity.this, idFriend,idUser);
    }

    private void addEvents() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = txtMessage.getText().toString().trim();
                if (!message.equals("")){
                    adapter.notifyDataSetChanged();
                    txtMessage.setText("");
                    lvChat.setAdapter(adapter);
                    MessageController.getInstance().sendMessage(idUser,idFriend,message);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this,v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_chat_screen, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuProfile:{
                        showProfile();
                        return true;
                    }
                    case R.id.menuBlock:{
                        blockUser();
                        return true;
                    }
                    default:{
                        return false;
                    }
                }
            }
        });
        popup.show();
    }

    private void blockUser() {

    }

    private void showProfile() {
        Intent intent = new Intent(ChatActivity.this,DetailActivity.class);
        startActivity(intent);
    }

    private void addControls() {
        txtName = findViewById(R.id.txtName);
        txtName.setText(name);
        lvChat = findViewById(R.id.lvChat);
        txtMessage = findViewById(R.id.txtMessage);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);
        btnMenu = findViewById(R.id.btnMenu);

        arrMessage = new ArrayList<>();
        adapter = new MessageAdapter(ChatActivity.this,R.layout.cell_my_message,arrMessage,avatar);
        lvChat.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getAllMessageSuccess(ArrayList<MessageModel> messageDatas) {
        Log.e("Message screen","callback");
        this.arrMessage.clear();
        this.arrMessage.addAll(messageDatas);
        if (adapter != null){
            lvChat.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            adapter = new MessageAdapter(this,R.layout.cell_my_message,arrMessage,avatar);
            lvChat.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void newMessageAdded(MessageModel message) {
        if (message.getMessage() != null){
            Log.e("MessageScreen","new message come");
            arrMessage.add(message);
            lvChat.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        controller.callback = null;
        controller.removeListener();
    }
}
