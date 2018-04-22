package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.example.vankien.dating.Controllers.BlockController;
import com.example.vankien.dating.Controllers.MessageController;
import com.example.vankien.dating.Interface.MessageDelegate;
import com.example.vankien.dating.Interface.UploadImageDelegate;
import com.example.vankien.dating.Models.Constant;
import com.example.vankien.dating.Models.FriendChatModel;
import com.example.vankien.dating.Models.MessageModel;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Utils.FirebaseUtils;
import com.example.vankien.dating.Views.Adapter.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class ChatActivity extends AppCompatActivity implements MessageDelegate, UploadImageDelegate{
    TextView txtName;
    String name;
    String idFriend;
    String idUser;
    String avatar;
    MessageController controller;
    LinearLayout progressLayout;

    private static final int REQUEST_IMAGE = 69;

    ListView lvChat;
    EmojiconEditText txtMessage;
    ImageButton btnSend,btnBack,btnMenu,btnEmoji,btnImage;
    private EmojIconActions emojIcon;
    View contentRoot;
    FirebaseUtils utils;
    boolean isBlock;

    ArrayList<MessageModel> arrMessage;
    MessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        isBlock = b.getBoolean("isBlock",false);
        FriendChatModel model = (FriendChatModel) b.getSerializable("FriendData");
        name = model.getName();
        idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        idFriend = model.getId();
        avatar = model.getUrlAvatar();
        controller = MessageController.getInstance();
        controller.delegate = this;
        utils = FirebaseUtils.getShareInstance();
        utils.delegate = this;

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
                if (!isBlock) {
                    String message = txtMessage.getText().toString().trim();
                    if (!message.equals("")){
                        adapter.notifyDataSetChanged();
                        txtMessage.setText("");
                        lvChat.setAdapter(adapter);
                        controller.sendMessage(idUser,idFriend,message, Constant.typeText);
                    }
                } else {
                    Toast.makeText(ChatActivity.this,"You can send message while you are blocking this user",Toast.LENGTH_LONG).show();
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

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBlock) {
                    Toast.makeText(ChatActivity.this,"You can send message while you are blocking this user",Toast.LENGTH_LONG).show();
                } else {
                    openImagePicker();
                }
            }
        });

        lvChat.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int VisibleItemCount, int totalItem) {

            }
        });
    }

    private void openImagePicker() {
        ImagePicker.create(this)
                .returnMode(ReturnMode.GALLERY_ONLY)
                .single()
                .start();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this,v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_chat_screen, popup.getMenu());
        MenuItem liveitem = popup.getMenu().getItem(1);
        if (isBlock) {
            liveitem.setTitle("Unblock");
        } else {
            liveitem.setTitle("Block");
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuProfile:{
                        showProfile();
                        return true;
                    }
                    case R.id.menuBlock:{
                        if (isBlock) {
                            unblockUser();
                        } else {
                            blockUser();
                        }
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

    private void unblockUser() {
        BlockController.getShareInstance().unblock(idFriend);
        this.isBlock = false;
        Toast.makeText(this,"Unblock successfully",Toast.LENGTH_LONG).show();
    }

    private void blockUser() {
        BlockController.getShareInstance().blockPeople(idFriend);
        finish();
    }

    private void showProfile() {
        Intent intent = new Intent(ChatActivity.this,DetailActivity.class);
        intent.putExtra("UserID",idFriend);
        intent.putExtra("FromChat",true);
        startActivity(intent);
    }

    private void addControls() {
        contentRoot = findViewById(R.id.contentRoot);
        txtName = findViewById(R.id.txtName);
        txtName.setText(name);
        lvChat = findViewById(R.id.lvChat);
        txtMessage = findViewById(R.id.txtMessage);
        txtMessage.setEmojiconSize(80);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);
        btnMenu = findViewById(R.id.btnMenu);
        progressLayout = findViewById(R.id.viewProgress);
        btnEmoji = findViewById(R.id.buttonEmoji);
        emojIcon = new EmojIconActions(this,contentRoot,txtMessage,btnEmoji);
        emojIcon.ShowEmojIcon();
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {

            }

            @Override
            public void onKeyboardClose() {

            }
        });
        btnImage = findViewById(R.id.btnImage);

        arrMessage = new ArrayList<>();
        adapter = new MessageAdapter(ChatActivity.this,R.layout.cell_my_message,arrMessage,avatar);
        lvChat.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getAllMessageSuccess(ArrayList<MessageModel> messageDatas) {
        Log.e("Message screen","delegate");
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
    protected void onDestroy() {
        super.onDestroy();
        controller.delegate = null;
        controller.removeListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(resultCode==RESULT_OK){
//            if(requestCode == REQUEST_IMAGE){
//                Uri imageUri = data.getData();
//                InputStream inputStream;
//                try {
//                    inputStream = getContentResolver().openInputStream(imageUri);
//                    Bitmap image = BitmapFactory.decodeStream(inputStream);
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),"Image not found",Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            Uri imageUri = Uri.fromFile(new File(image.getPath()));
            InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                progressLayout.setVisibility(View.VISIBLE);
                utils.uploadImage(bitmap,idUser);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Image not found",Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void uploadImageSuccess(String image) {
        progressLayout.setVisibility(View.GONE);
        controller.sendMessage(idUser,idFriend,image,Constant.typeImage);
    }

    @Override
    public void uploadImageFailed() {
        Toast.makeText(this,"Send image failed",Toast.LENGTH_LONG).show();
    }
}
