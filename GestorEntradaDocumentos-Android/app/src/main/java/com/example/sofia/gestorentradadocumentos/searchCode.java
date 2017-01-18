package com.example.sofia.gestorentradadocumentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.concurrent.ExecutionException;

public class searchCode extends Activity {

    //Clase asincronica que recupera información
     public class webServiceSearchByCode extends AsyncTask<String,Boolean,searchData[]>{
        String code;
        String METHOD_NAME = "searchByCode";
        String SOAP_ACTION = getString(R.string.nameSpace) + METHOD_NAME;
        public searchData [] listData;



        public webServiceSearchByCode(String code){
            this.code = code;
        }
        @Override
        protected searchData[] doInBackground(String... params) {

            try {

                SoapObject request = new SoapObject(getString(R.string.nameSpace), METHOD_NAME);
                request.addProperty("code",code);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(getString(R.string.url));
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject resSoap =(SoapObject)envelope.getResponse();
                listData = new searchData[resSoap.getPropertyCount()];

                for (int i = 0; i < listData.length; i++)
                {
                    SoapObject ic = (SoapObject)resSoap.getProperty(i);

                    searchData cli = new searchData(ic.getProperty(0).toString(),ic.getProperty(1).toString(),ic.getProperty(2).toString(),
                            ic.getProperty(3).toString(),ic.getProperty(4).toString(),ic.getProperty(5).toString(),ic.getProperty(6).toString());
                    listData[i] = cli;

                }

            }
            catch (Exception e)
            {
                Log.d("Excepcion",e.toString());

            }

            return listData;
        }
    }

    EditText code;
    Button search;
    TableLayout showInformation;
    TextView errorMessage;
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_code);
        SharedPreferences sharedPref = getSharedPreferences("Credentials", Context.MODE_WORLD_READABLE);
        String name = sharedPref.getString("name",""); ;
        welcome = (TextView) findViewById (R.id.txtWelcome);
        welcome.setText ("Bienvenido "+name);
        code = (EditText)findViewById(R.id.txtCodeSearch);
        search = (Button) findViewById(R.id.btnSearchDate);
        errorMessage = (TextView) findViewById(R.id.txtError);
        errorMessage.setVisibility(View.INVISIBLE);
        showInformation = (TableLayout) findViewById(R.id.tableCode);
        showInformation.setVisibility(View.INVISIBLE);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchCode = code.getText().toString();
                errorMessage.setVisibility(View.INVISIBLE);
                if (searchCode.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(searchCode.this)
                            .setMessage("El campo de código no debe de estar vacio")
                            .setTitle("Código Inválido")
                            .setPositiveButton("Aceptar",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{

                    showInformation.setVisibility(View.INVISIBLE);
                    showInformation.removeAllViews();
                    webServiceSearchByCode searchByCode = new webServiceSearchByCode(searchCode);
                    try {
                        searchData[] resultList = searchByCode.execute().get();
                        if (resultList.length != 0){
                            errorMessage.setVisibility(View.INVISIBLE);
                            showInformation.addView(createHeader());
                            for (int i=0;i<resultList.length;i++){
                                TableRow headers = new TableRow(com.example.sofia.gestorentradadocumentos.searchCode.this);
                                headers.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 5f);
                                TextView date = new TextView(searchCode.this);
                                date.setText(resultList[i].date);
                                TextView codeTest = new TextView(searchCode.this);
                                codeTest.setText(resultList[i].consecutive);
                                codeTest.setPadding(15,0,0,0);
                                TextView detail = new TextView(searchCode.this);
                                detail.setText(resultList[i].detail);
                                detail.setPadding(15,0,0,0);
                                TextView id = new TextView(searchCode.this);
                                id.setText(resultList[i].identification);
                                id.setPadding(15,0,0,0);
                                TextView status = new TextView(searchCode.this);
                                status.setText(resultList[i].state);
                                status.setPadding(15,0,0,0);
                                TextView typeProcedure = new TextView(searchCode.this);
                                typeProcedure.setText(resultList[i].type);
                                typeProcedure.setPadding(15,0,0,0);
                                TextView plataformer = new TextView(searchCode.this);
                                plataformer.setText(resultList[i].plataformer);
                                plataformer.setPadding(15,0,0,0);
                                headers.addView(date);
                                headers.addView(codeTest);
                                headers.addView(id);
                                headers.addView(status);
                                headers.addView(typeProcedure);
                                headers.addView(plataformer);
                                showInformation.addView(headers);
                            }

                            showInformation.setVisibility(View.VISIBLE);
                        }
                        else{
                            errorMessage.setVisibility(View.VISIBLE);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }


                }
            }
        });





    }

    private TableRow createHeader(){
        TableRow headers = new TableRow(com.example.sofia.gestorentradadocumentos.searchCode.this);
        headers.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        TextView date = new TextView(searchCode.this);
        date.setGravity(Gravity.CENTER);
        date.setText("Fecha");


        TextView codeTest = new TextView(searchCode.this);
        codeTest.setPadding(15,0,0,0);
        codeTest.setGravity(Gravity.CENTER);
        codeTest.setText("Consecutivo");

        TextView detail = new TextView(searchCode.this);
        detail.setGravity(Gravity.CENTER);
        detail.setPadding(15,0,0,0);
        detail.setText("Detalle");


        TextView id = new TextView(searchCode.this);
        id.setGravity(Gravity.CENTER);
        id.setPadding(15,0,0,0);
        id.setText("Cédula");

        TextView status = new TextView(searchCode.this);
        status.setGravity(Gravity.CENTER);
        status.setPadding(15,0,0,0);
        status.setText("Estado");

        TextView typeProcedure = new TextView(searchCode.this);
        typeProcedure.setGravity(Gravity.CENTER);
        typeProcedure.setPadding(15,0,0,0);
        typeProcedure.setText("Tipo de Procedimiento");

        TextView plataformer = new TextView(searchCode.this);
        plataformer.setGravity(Gravity.CENTER);
        plataformer.setPadding(15,0,0,0);
        plataformer.setText("Plataformista");

        headers.addView(date);
        headers.addView(codeTest);
        headers.addView(id);
        headers.addView(status);
        headers.addView(typeProcedure);
        headers.addView(plataformer);

        return headers;
    }

    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(getApplicationContext(),searchMenu.class);
        startActivity(goBack);
    }
}
