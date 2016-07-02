package com.officialcookiegames.androidjsontests;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {
    //Server url
    private String url = "http://"+Url.pageUrl+"/php/DoFunctions.php?version="+Strings.version()+"&request=";
    //Variables
    private Gson gson;
    private AsyncHttpClient client;
    private Response response;
    private CustomAdapter adapter;
    private Snackbar snackbar;

    //Init Vars
    @BindView(R.id.listView) ListView list;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.user_message) EditText message;
    @BindView(R.id.user_name) EditText name;
    @BindView(R.id.room) EditText room;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.submit) Button submit;
    @BindView(R.id.ViewSwitcher) ViewSwitcher switcher;
    //User struct
    User user;

    private String lastToSend;

    final int switcherLimit = 2;
    int switcherCounter = 0;

    boolean badLastConnection;

    //Update delay in ms
    private static final int UPDATE_DELAY = 1000;
    //Set variables
    void Init(){
        client = new AsyncHttpClient();
        gson = new Gson();
        response = new Response();
        user = new User(name.getText().toString(),
                        room.getText().toString(),
                        password.getText().toString());
    }
    //Run functions
    void Start(){
        toolbar.setTitle("Czat");
        setSupportActionBar(toolbar);
        snackbar = Snackbar.make(findViewById(R.id.MainView),"" , Snackbar.LENGTH_SHORT).setDuration(2000);
    }
    //Update every UPDATE_DELAY(1000ms)
    void Update(){
        GetDataAndSetView();
    }
    //Wait on user reaction
    void Listeners(){
        //Submit data button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchLogic();
                toolbar.getMenu().getItem(0).setTitle(Strings.other.menu[1]);
                user = new User(name.getText().toString(),
                        room.getText().toString(),
                        password.getText().toString());
            }
        });
        //Switch button: 1.Menu 2.Chat
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SwitchLogic();
                item.setTitle(Strings.other.menu[switcherCounter]);
                name.setText(user.name);
                room.setText(user.room);
                password.setText(user.password);
                return false;
            }
        });
        //Send button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendData();
            }
        });
    }
    void SwitchLogic(){
        switcherCounter++;
        if(switcherCounter >= switcherLimit || switcherCounter >= Strings.other.menu.length)
            switcherCounter = 0;
        switcher.showNext();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Init();
        Start();
        update();
        Listeners();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    void SetView(){
        //Check data
        if(response.getData() == null){
            if(!isNetworkAvailable())
                snackbar.setText(Strings.error.netConnection).show();
            return;
        }
        //Draw data logic
        final Adapter[] Adapter = new Adapter[response.getData().size()];
        for (int i = 0; i < response.getData().size(); i++)
            Adapter[i] = new Adapter(
                    response.getData().get(i).getNick(),
                    response.getData().get(i).getId(),
                    response.getData().get(i).getDate(),
                    response.getData().get(i).getMessage());
        adapter = new CustomAdapter(MainActivity.this, Adapter);
        list.setAdapter(adapter);
    }
    //Return result
    void GetDataAndSetView(){
        //Check user network status
        if(!isNetworkAvailable()){
            badLastConnection = true;
            snackbar.setText(Strings.error.netConnection).show();
        }
        final String[] toReturn = {Strings.pass.downloadData};
        //Connect to server and download data
        client.get(MainActivity.this, url+"getChat"+ "&room="+user.room + "&password=" + user.password, new AsyncHttpResponseHandler() {
            //Download complete
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //Parse data to json
                Response newResponse = gson.fromJson(new String(responseBody),Response.class);
                //If last try was bad send "pass" message
                if(badLastConnection){
                    badLastConnection = false;
                    snackbar.setText(Strings.pass.netConnection).show();
                }
                //Check if new data is equal to last data
                if(response.equals(newResponse))
                    return;
                //If not change last data and draw new List
                response = newResponse;
                SetView();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }
    void SendData(){
        if(isNetworkAvailable() && lastToSend != message.getText().toString()) {
            lastToSend = message.getText().toString();
            if (lastToSend != null && !lastToSend.matches("") && user.name != null && !user.name.replaceAll("\\s+","").matches("")){
                message.setText("");
                client.get(MainActivity.this, url + "sendChat&nick=" + user.name + "&message=" + lastToSend + "&room="+ user.room + "&password=" + user.password, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        snackbar.setText(Strings.pass.sendMessage).show();
                        GetDataAndSetView();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        snackbar.setText(Strings.failure.errorSendMessage).show();
                    }
                });
            }else
                snackbar.setText(Strings.other.emptyMessage).show();
        }else
            snackbar.setText(Strings.error.netConnection).show();
    }
    void update(){
        Update();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }, UPDATE_DELAY);
    }
    private boolean isNetworkAvailable() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
