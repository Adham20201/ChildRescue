package com.example.childrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchChildChatBotActivity extends AppCompatActivity implements View.OnClickListener {

    // creating variables for our
    // widgets in xml file.
    private RecyclerView chatsRV;
    private FloatingActionButton sendMsgFAB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";

    Boolean ChildName = false, ChildTime = false, ChildPlace = false, ChildPerson = false, ChildPhoto = false, ChildBehavior= false, ChildEscaped = false ,ChildAdditionalInformation= false;
    Boolean FatherName =false, FatherPhone = false, FatherId =  false, FatherAddress = false;
    Boolean MotherName =false, MotherPhone = false, MotherId =  false, MotherAddress = false;
    Boolean PoliceReported;


    // creating a variable for
    // our volley request queue.
    private RequestQueue mRequestQueue;
    Handler myHandler;


    // creating a variable for array list and adapter class.
    private ArrayList<ChatsModel> chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        chatRVAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_child_chat_bot);

        myHandler = new Handler();

        // on below line we are initializing all our views.
        chatsRV = findViewById(R.id.idRVChatsS);
        sendMsgFAB = findViewById(R.id.idFABSendS);
        userMsgEdt = findViewById(R.id.idEdtMessageS);

        sendMsgFAB.setOnClickListener(this);

        RecyclerView.ItemAnimator animator = chatsRV.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        // below line is to initialize our request queue.
        mRequestQueue = Volley.newRequestQueue(SearchChildChatBotActivity.this);
        mRequestQueue.getCache().clear();

        // below line is to initialize our request queue.
        chatsModalArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModalArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);

        chatsModalArrayList.add(new ChatsModel("What is the child's name?", BOT_KEY));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.idFABSendS:
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
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("Did the child escaped before or was lost?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!ChildEscaped){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("Did you reported to police or not?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!PoliceReported){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("What is the father's name?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!FatherName){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("What is the father's phone number?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!FatherPhone){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("What is the father's  id?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!FatherId){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("What is the father's address?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!FatherAddress){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("What is the mother's name?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!MotherName){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("What is the mother's phone number?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!MotherPhone){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("What is the mother's id?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!FatherId){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("What is the mother's address?", BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
                }
            },500);
        }
        else if (!MotherAddress){
            chatsModalArrayList.add(new ChatsModel(userMsg, USER_KEY));
            ChildAdditionalInformation = true;
            chatRVAdapter.notifyDataSetChanged();
            chatsRV.smoothScrollToPosition(chatsRV.getAdapter().getItemCount());
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chatsModalArrayList.add(new ChatsModel("Thank you", BOT_KEY));
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
        RequestQueue queue = Volley.newRequestQueue(SearchChildChatBotActivity.this);

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
                Toast.makeText(SearchChildChatBotActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("chat bot", error.toString());
                chatRVAdapter.notifyDataSetChanged();

            }
        });

        // at last adding json object
        // request to our queue.
        queue.add(jsonObjectRequest);
    }

}