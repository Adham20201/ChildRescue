package com.example.childrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportChildChatBotActivity extends AppCompatActivity implements View.OnClickListener {

    // creating variables for our
    // widgets in xml file.
    private RecyclerView chatsRV;
    private FloatingActionButton sendMsgFAB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    Boolean ChildName = false, ChildTime = false, ChildPlace = false, ChildPerson = false, ChildPhoto = false, ChildBehavior= false ,ChildAdditionalInformation= false;

    // creating a variable for
    // our volley request queue.
    private RequestQueue mRequestQueue;
    Handler myHandler;

    FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    // creating a variable for array list and adapter class.
    private ArrayList<ChatsModel> chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;

    ReportedChildren reportedChild;

    @Override
    protected void onStart() {
        super.onStart();
        chatRVAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_child_chat_bot);

        myHandler = new Handler();

        mAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance("https://child-rescue-c7aa3-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference("reported children");

        // on below line we are initializing all our views.
        chatsRV = findViewById(R.id.idRVChatsR);
        sendMsgFAB = findViewById(R.id.idFABSendR);
        userMsgEdt = findViewById(R.id.idEdtMessageR);

        sendMsgFAB.setOnClickListener(this);

        RecyclerView.ItemAnimator animator = chatsRV.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        // below line is to initialize our request queue.
        mRequestQueue = Volley.newRequestQueue(ReportChildChatBotActivity.this);
        mRequestQueue.getCache().clear();

        // below line is to initialize our request queue.
        chatsModalArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModalArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);

        chatsModalArrayList.add(new ChatsModel("What is the child's name?", BOT_KEY));

        reportedChild = new ReportedChildren();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idFABSendR:
                if (userMsgEdt.getText().toString().isEmpty()){
                    break;
                }

                sendMessage(userMsgEdt.getText().toString());

                userMsgEdt.setText("");
                break;
        }
    }


    private void sendMessage(String userMsg) {
        if (!ChildName){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            reportedChild.setName(userMsg);
            ChildName = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("When was the child kidnapped?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);

        }
        else if (!ChildTime){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            reportedChild.setTime(userMsg);
            ChildTime = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("Where was the child kidnapped?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!ChildPlace){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            reportedChild.setPlace(userMsg);
            ChildPlace = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("whom was the child kidnapped with?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!ChildPerson){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            reportedChild.setPerson(userMsg);
            ChildPerson = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("Can you send a photo for the child?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!ChildPhoto){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            reportedChild.setPhoto(userMsg);
            ChildPhoto = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("What is the child's behavior?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!ChildBehavior){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            reportedChild.setBehaviour(userMsg);
            ChildBehavior = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("Can you send additional information", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!ChildAdditionalInformation){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            reportedChild.setAdditionalInformation(userMsg);
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("Thank You", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else {
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("We will contact you soon.", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }

    }


    private void sendMessageAI(String userMsg) {
        // below line is to pass message to our
        // array list which is entered by the user.
        chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
        chatRVAdapter.notifyDataSetChanged();

        // url for our brain
        // make sure to add mshape for uid.
        // make sure to add your url.
        String url = "http://api.brainshop.ai/get?bid=167968&key=0gVWZPcrzxOb8ITX&uid=[uid]&msg=" + userMsg;

        // creating a variable for our request queue.
        RequestQueue queue = Volley.newRequestQueue(ReportChildChatBotActivity.this);

        // on below line we are making a json object request for a get request and passing our url .
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // in on response method we are extracting data
                    // from json response and adding this response to our array list.
                    String botResponse = response.getString("cnt");
                    chatsModalArrayList.add(new ChatsModel(botResponse, BOT_KEY));

                    // notifying our adapter as data changed.
                    chatRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();

                    // handling error response from bot.
                    chatsModalArrayList.add(new ChatsModel("No response", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error handling.
                chatsModalArrayList.add(new ChatsModel("Sorry no response found", BOT_KEY));
                Toast.makeText(ReportChildChatBotActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("chat bot", error.toString());
                chatRVAdapter.notifyDataSetChanged();

            }
        });

        // at last adding json object
        // request to our queue.
        queue.add(jsonObjectRequest);
    }

}