package com.example.sofia.gestorentradadocumentos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends Activity {
    private Button EnterProcess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent passScreenParameter = getIntent(); // gets the previously created intent
        final String idPlataformerResult = passScreenParameter.getStringExtra("IdPlataformer"); // will return "FirstKeyValue"
        dataBase data = new dataBase ();

        EnterProcess = (Button)findViewById(R.id.btnEnter);
        EnterProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openEnterProcessActivity = new Intent(getApplicationContext(),enterProcedure.class);
                openEnterProcessActivity.putExtra("idPlataformer",idPlataformerResult);
                startActivity(openEnterProcessActivity);
            }
        });

    }
}