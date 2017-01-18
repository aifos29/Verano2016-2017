package com.example.sofia.gestorentradadocumentos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends Activity {
    private Button EnterProcess;
    private TextView welcome;
    private Button menuSearch;
    private Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final SharedPreferences sharedPref = getSharedPreferences("Credentials", Context.MODE_WORLD_READABLE);
        String name = sharedPref.getString("name",""); ;
        welcome = (TextView) findViewById (R.id.txtwelcome);
        welcome.setText ("Bienvenido "+name);

        EnterProcess = (Button)findViewById(R.id.btnEnter);
        EnterProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openEnterProcessActivity = new Intent(getApplicationContext(),enterProcedure.class);
                startActivity(openEnterProcessActivity);
            }
        });
        menuSearch = (Button) findViewById(R.id.btnSearchDate);
        menuSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMenuSearch = new Intent(getApplicationContext(),searchMenu.class);
                startActivity(openMenuSearch);
            }
        });

        exit = (Button) findViewById(R.id.btnExit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.edit().clear().commit();
                Intent back = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(back);
            }
        });

    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}