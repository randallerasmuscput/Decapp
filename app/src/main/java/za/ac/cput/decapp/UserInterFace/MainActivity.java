package za.ac.cput.decapp.UserInterFace;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import za.ac.cput.decapp.Conf.security.SessionManager;
import za.ac.cput.decapp.services.Impl.LoginServiceImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private LoginServiceImpl loginService;
    private boolean isBound = false;
    private EditText username;
    private EditText password;
    private EditText authorizationNumber;
    private SessionManager session;
    private static String Tag = SessionManager.class.getSimpleName();
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this conects java class and the layout file
        setContentView(R.layout.activity_main);
//        session = new SessionManager(App.getAppContext());

//        Intent intent = new Intent(this,LoginServiceImpl.class);
//        App.getAppContext().bindService(intent,connection, Context.BIND_AUTO_CREATE);

//        if(session.isLoggedIn())
//        {   //is the user is already logged on
//            Intent intentLogin = new Intent(MainActivity.this,DecappActivity.class);
//            startActivity(intentLogin);
//            finish();
//        }

        username = (EditText)findViewById(R.id.editTextUsername);
        password = (EditText)findViewById(R.id.editTextPassword);
        authorizationNumber = (EditText)findViewById(R.id.editTextAuthNumber);


        //type casting to buttons for Actionlisteners
        Button btnSignUp =(Button)findViewById(R.id.btnSignUp);
       Button btnCancelMain = (Button)findViewById(R.id.btnCancel);
        Button btnLogin = (Button)findViewById(R.id.btnLogin);

        assert btnLogin !=null;

        //this also works like a charm
        btnSignUp.setOnClickListener(this);
        btnCancelMain.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btnSignUp:
                Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);

            case R.id.btnCancel:
                System.exit(0);

            case R.id.btnLogin:
                String name = username.getText().toString();
                String pass = password.getText().toString();

                if (username.getText().length() == 0) {
                    username.setError("Please enter your username");

                } else if ((password.getText().length() == 0)) {
                    password.setError("Please enter your Password");

                }
                if (!name.isEmpty() && !pass.isEmpty()) {

//                if (loginService.checkActivition()) {
//                boolean loggedIn = loginService.checkLogin(name, pass);
//                    if (loggedIn) {
//                        session.setLogin(true);
                        Intent decappIntent = new Intent(MainActivity.this, DecappActivity.class);
                        startActivity(decappIntent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Please log in with your username and password",
                                Toast.LENGTH_LONG).show();

                    }


                }
        }

    @Override
    public void onResume() {
        super.onResume();
        //---intent to filter for file downloaded intent---
        intentFilter = new IntentFilter();
        Log.d("RECEIVER", " RECEIVER REGISTERED");
        intentFilter.addAction("ACCOUNT_ACTIVATION");
        //---register the receiver---
        registerReceiver(intentReceiver, intentFilter);
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(intentReceiver);

    }


    private ServiceConnection connection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            LoginServiceImpl.LoginServiceBinder binder = (LoginServiceImpl.LoginServiceBinder) service;
            loginService = binder.getService();
            isBound = true;
        }
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("RECEIVER", " BROADCAST CALLED !!!!");
            Toast.makeText(getBaseContext(), "ACCOUNT_ACTIVATION " + intent.getStringExtra("RESULT"),
                    Toast.LENGTH_LONG).show();
        }
    };
}
