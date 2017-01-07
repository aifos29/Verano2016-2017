package com.example.sofia.gestorentradadocumentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class webServiceLoggin extends AsyncTask<String,Integer,Boolean> {

    private String email;
    private String password;
    public  Boolean executeResult;
    public  int idPlataformist;

    public webServiceLoggin(String email,String password){
        this.email = email;
        this.password = password;

    }
    protected Boolean doInBackground(String... params) {

        boolean resul = true;
        executeResult = true;
        String res;
        String NAMESPACE = "http://sgoliver.net/";
        String URL="http://192.168.1.8/ServicioWebSoap/ServicioClientes.asmx";
        String METHOD_NAME= "loginVerification"; //the webservice method that you want to call
        String SOAP_ACTION = NAMESPACE+METHOD_NAME;

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("email",email);
            request.addProperty("password",password);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive)envelope.getResponse(); //get the response from your webservice
            res = response.toString();
            Log.d("Information",res);
            if (res.equals("0")){
                resul = false;
            }
            else{
                idPlataformist = Integer.parseInt(res);

                resul = true;
            }


        }
        catch (Exception e)
        {
            Log.d("Excepcion",e.toString());
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

