package com.example.sofia.gestorentradadocumentos;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Credentials;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class dataBase {
    public class webServiceGetDepartment extends AsyncTask<String, Integer, Boolean> {
        String NAMESPACE = "http://sgoliver.net/";
        String URL = "http://192.168.1.8/ServicioWebSoap/ServicioClientes.asmx";
        String METHOD_NAME = "DepartmentList"; //the webservice method that you want to call
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        Boolean resul = true;
        public Department[] listDepartment;


        @Override
        protected Boolean doInBackground(String... params) {

            try {

                SoapObject request = new SoapObject (NAMESPACE, METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope (SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject (request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE (URL);
                androidHttpTransport.call (SOAP_ACTION, envelope);
                SoapObject resSoap = (SoapObject) envelope.getResponse ();
                listDepartment = new Department[resSoap.getPropertyCount ()];

                for (int i = 0; i < listDepartment.length; i++) {
                    SoapObject ic = (SoapObject) resSoap.getProperty (i);

                    Department cli = new Department ();
                    cli.id = Integer.parseInt (ic.getProperty (0).toString ());
                    cli.department = ic.getProperty (1).toString ();
                    cli.code =
                            ic.getProperty (2).toString ();

                    database.execSQL ("Insert into Department values(" + cli.id + ",'" + cli.department + "','" + cli.code + "')");

                }

            } catch (Exception e) {
                Log.d ("Excepcion", e.toString ());
                resul = false;
            }

            return resul;

        }

        protected void onPostExecute(Boolean result) {
            Cursor c = database.rawQuery ("SELECT * FROM " + "Department", null);
            int Column1 = c.getColumnIndex ("idDepartment");
            int Column2 = c.getColumnIndex ("department");
            int Column3 = c.getColumnIndex ("code");

            // Check if our result was valid.
            c.moveToFirst ();
            if (c != null) {
                // Loop through all Results
                do {
                    Log.d ("DATA", c.getString (Column2));
                } while (c.moveToNext ());
            }
        }
    }

    public class webServiceGetProcedures extends AsyncTask<String, Integer, Boolean> {
        String NAMESPACE = "http://sgoliver.net/";
        String URL = "http://192.168.1.8/ServicioWebSoap/ServicioClientes.asmx";
        String METHOD_NAME = "TypeProcedureList"; //the webservice method that you want to call
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        Boolean resul = true;
        public Type[] listType;

        @Override
        protected Boolean doInBackground(String... params) {

            try {

                SoapObject request = new SoapObject (NAMESPACE, METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope (SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject (request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE (URL);
                androidHttpTransport.call (SOAP_ACTION, envelope);
                SoapObject resSoap = (SoapObject) envelope.getResponse ();
                listType = new Type[resSoap.getPropertyCount ()];

                for (int i = 0; i < listType.length; i++) {
                    SoapObject ic = (SoapObject) resSoap.getProperty (i);

                    Type cli = new Type ();
                    cli.id = Integer.parseInt (ic.getProperty (0).toString ());
                    cli.name = ic.getProperty (1).toString ();


                    database.execSQL ("Insert into typeOfProcedure values(" + cli.id + ",'" + cli.name + "')");
                }

            } catch (Exception e) {
                Log.d ("Excepcion", e.toString ());
                resul = false;
            }

            return resul;

        }

        protected void onPostExecute(Boolean result) {

        }
    }

    public class webServiceGetTypes extends AsyncTask<String, Integer, Boolean> {
        String NAMESPACE = "http://sgoliver.net/";
        String URL = "http://192.168.1.8/ServicioWebSoap/ServicioClientes.asmx";
        String METHOD_NAME = "TypeIdentifyList"; //the webservice method that you want to call
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        Boolean resul = true;
        public Type[] listType;

        @Override
        protected Boolean doInBackground(String... params) {

            try {

                SoapObject request = new SoapObject (NAMESPACE, METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope (SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject (request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE (URL);
                androidHttpTransport.call (SOAP_ACTION, envelope);
                SoapObject resSoap = (SoapObject) envelope.getResponse ();
                listType = new Type[resSoap.getPropertyCount ()];

                for (int i = 0; i < listType.length; i++) {
                    SoapObject ic = (SoapObject) resSoap.getProperty (i);

                    Type cli = new Type ();
                    cli.id = Integer.parseInt (ic.getProperty (0).toString ());
                    cli.name = ic.getProperty (1).toString ();

                    database.execSQL ("Insert into typeOfIdentify values (" + cli.id + ",'" + cli.name + "')");
                }

            } catch (Exception e) {
                Log.d ("Excepcion", e.toString ());
                resul = false;
            }

            return resul;

        }

        protected void onPostExecute(Boolean result) {


        }
    }

    public class webServiceGetCredentials extends AsyncTask<String, Integer, Boolean> {
        String NAMESPACE = "http://sgoliver.net/";
        String URL = "http://192.168.1.8/ServicioWebSoap/ServicioClientes.asmx";
        String METHOD_NAME = "LoginList"; //the webservice method that you want to call
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;
        Boolean resul = true;
        public credential[] listCredential;

        @Override
        protected Boolean doInBackground(String... params) {

            try {

                SoapObject request = new SoapObject (NAMESPACE, METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope (SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject (request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE (URL);
                androidHttpTransport.call (SOAP_ACTION, envelope);
                SoapObject resSoap = (SoapObject) envelope.getResponse ();
                listCredential = new credential[resSoap.getPropertyCount ()];

                for (int i = 0; i < listCredential.length; i++) {
                    SoapObject ic = (SoapObject) resSoap.getProperty (i);

                    credential cli = new credential (Integer.parseInt (ic.getProperty (0).toString ()), ic.getProperty (1).toString ()
                            , ic.getProperty (2).toString (), Integer.parseInt (ic.getProperty (3).toString ()), ic.getProperty (4).toString (), ic.getProperty (5).toString (), Integer.parseInt (ic.getProperty (6).toString ()));

                    database.execSQL ("Insert into logging values (" + cli.idLoggin + ",'" + cli.email + "','" + cli.password +"')");
                    database.execSQL ("Insert into Plataformers values (" + cli.plataformers + ",'" + cli.name + "','" + cli.lastname
                            + "'," + cli.isBoss + "," + cli.idLoggin + ")");
                }

            } catch (Exception e) {
                Log.d ("Excepcion", e.toString ());
                resul = false;
            }

            return resul;

        }

        protected void onPostExecute(Boolean result) {

        }
    }


    SQLiteDatabase database;

    public dataBase() {
        database = SQLiteDatabase.openOrCreateDatabase ("data/data/com.example.sofia.gestorentradadocumentos/DBProcedure", null, null);
    }

    public boolean createTable() {
        boolean status = true;
        try {
            database.execSQL ("CREATE TABLE IF NOT EXISTS[typeOfProcedure] (\n" +
                    "\t[idTypeOfProcedure]\tinteger  NOT NULL,\n" +
                    "\t[TypeOfProcedure]\tnvarchar(50) NOT NULL COLLATE NOCASE\n" +
                    "\n" +
                    ")");
            database.execSQL ("CREATE TABLE IF NOT EXISTS [typeOfIdentify] (\n" +
                    "\t[idTypeOfIdentify]\tinteger  NOT NULL,\n" +
                    "\t[TypeOfIdentify]\tnvarchar(50) NOT NULL COLLATE NOCASE\n" +
                    "\n" +
                    ")");
            database.execSQL ("CREATE TABLE IF NOT EXISTS [Department] (\n" +
                    "\t[idDepartment]\tinteger   NOT NULL,\n" +
                    "\t[department]\tnvarchar(50) NOT NULL COLLATE NOCASE,\n" +
                    "\t[code]\tnvarchar(50) NOT NULL COLLATE NOCASE\n" +
                    "\n" +
                    ")");
            database.execSQL ("CREATE TABLE IF NOT EXISTS[logging] (\n" +
                    "\t[idLogging]\tinteger NOT NULL,\n" +
                    "\t[email]\tnvarchar(50) NOT NULL COLLATE NOCASE,\n" +
                    "\t[password]\tnvarchar(300) NOT NULL COLLATE NOCASE\n" +
                    "\n" +
                    ")");
            database.execSQL ("CREATE TABLE IF NOT EXISTS [Plataformers] (\n" +
                    "\t[idPlataformers]\tinteger  NOT NULL,\n" +
                    "\t[name]\tnvarchar(50) NOT NULL COLLATE NOCASE,\n" +
                    "\t[lastName]\tnvarchar(50) NOT NULL COLLATE NOCASE,\n" +
                    "\t[isABoss]\tinteger NOT NULL,\n" +
                    "\t[idLogging]\tinteger NOT NULL\n" +
                    ",\n" +
                    "    FOREIGN KEY ([idLogging])\n" +
                    "        REFERENCES [logging]([idLogging])\n" +
                    ")");

        } catch (Exception ex) {
            status = false;
        }
        return status;
    }

    public void syncBase(Context context) {
        Toast.makeText (context,"Sincronizando con el servidor",Toast.LENGTH_SHORT).show ();
        database.execSQL ("Delete from Plataformers ");
        database.execSQL ("Delete from logging");
        database.execSQL ("Delete from Department");
        database.execSQL ("Delete from typeOfProcedure");
        database.execSQL ("Delete from typeOfIdentify");
        webServiceGetCredentials credential = new webServiceGetCredentials ();
        credential.execute ();
        Toast.makeText (context,"Almacenando datos...",Toast.LENGTH_LONG).show ();
        webServiceGetDepartment department = new webServiceGetDepartment ();
        department.execute ();
        webServiceGetProcedures procedures = new webServiceGetProcedures ();
        procedures.execute ();
        webServiceGetTypes types = new webServiceGetTypes ();
        types.execute ();



    }

    public boolean exist(String email,String password){
        Cursor c = database.rawQuery ("Select * from logging where email='"+email+ "' and password = '"+password+"';",null);
        if (c.getCount ()>0){
            return  true;
        }
        else {
            return false;
        }

    }

    public Cursor getDepartments (){

        Cursor getInformation = database.rawQuery ("Select Department from department",null);

       return getInformation;
    }
}


