package com.example.sofia.gestorentradadocumentos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {


    private  EditText emailText;
    private  EditText passwordText;
    private Button entrada;
    public int idPlataformist;

    public class webServiceLoggin extends AsyncTask<String,Integer,Boolean> {

        private String email;
        private String password;
        public Boolean executeResult;


        public webServiceLoggin(String email, String password) {
            this.email = email;
            this.password = password;

        }

        protected Boolean doInBackground(String... params) {

            boolean resul = true;
            executeResult = true;
            String res;
            String NAMESPACE = "http://sgoliver.net/";
            String URL = "http://192.168.1.8/ServicioWebSoap/ServicioClientes.asmx";
            String METHOD_NAME = "loginVerification"; //the webservice method that you want to call
            String SOAP_ACTION = NAMESPACE + METHOD_NAME;

            try {

                SoapObject request = new SoapObject (NAMESPACE, METHOD_NAME);
                request.addProperty ("email", email);
                request.addProperty ("password", password);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope (SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject (request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE (URL);
                androidHttpTransport.call (SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse (); //get the response from your webservice
                res = response.toString ();
                Log.d ("Information", res);
                if (res.equals ("0")) {
                    resul = false;
                } else {
                    idPlataformist = Integer.parseInt (res);

                    resul = true;
                }


            } catch (Exception e) {
                Log.d ("Excepcion", e.toString ());
                resul = false;
            }

            return resul;
        }

        protected void onPostExecute(Boolean result) {

            if (result)
                executeResult = true;
            else
                executeResult = false;
        }
    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailText = (EditText) findViewById(R.id.txtEmail);
        passwordText = (EditText) findViewById( R.id.txtPassword);
        entrada = (Button) findViewById(R.id.button);
        entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection con = new connection ();
                Boolean result = null;
                try {
                    result = con.execute().get ();

                } catch (InterruptedException e) {
                    e.printStackTrace ();
                } catch (ExecutionException e) {
                    e.printStackTrace ();
                }
                if (result){
                    Toast.makeText (getApplicationContext (),"Conectado con la base de datos....",Toast.LENGTH_SHORT).show ();
                    webServiceLoggin loggin = new webServiceLoggin(emailText.getText().toString(),passwordText.getText().toString());
                    try {
                        Boolean output =  loggin.execute().get();
                        if (output){
                            Log.d("Validation",Integer.toString(idPlataformist));
                            Toast.makeText (getApplicationContext (),"Abriendo base de datos local",Toast.LENGTH_SHORT).show ();
                            dataBase data = new dataBase ();
                            Toast.makeText (getApplicationContext (),"Recopilando información actual",Toast.LENGTH_SHORT).show ();
                            data.createTable ();
                            data.syncBase (getApplicationContext ());
                            Intent goMenu = new Intent(getApplicationContext(),Menu.class);
                            goMenu.putExtra("IdPlataformer",emailText.getText ().toString ());

                            startActivity(goMenu);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Las credenciales son incorrectas o no se encuentra autorizado para ingresar", Toast.LENGTH_LONG).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText (getApplicationContext (),"No se pudo conectar con el servidor.Recolectando datos locales",Toast.LENGTH_LONG).show ();
                    dataBase base = new dataBase ();
                    if (base.exist (emailText.getText ().toString (),passwordText.getText ().toString ())){
                        Intent goMenu = new Intent(getApplicationContext(),Menu.class);
                        goMenu.putExtra("IdPlataformer",emailText.getText ().toString ());
                        startActivity(goMenu);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Las credenciales son incorrectas o no se encuentra en el sistema", Toast.LENGTH_LONG).show();
                    }


                }




            }
        });


    }
}
