package com.example.sofia.gestorentradadocumentos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pinball83.maskededittext.MaskedEditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class enterProcedure extends Activity {
    private String pattern;
    //Declaración de objetos de la vista
    public EditText date;
    private Button openDatePicker;
    private DatePickerDialog changeDate;
    public SimpleDateFormat sdf;
    private Spinner department ;
    private Spinner spinnerProcedure;
    private Spinner spinnerType;
    private MaskedEditText ID;
    private Button register;
    private EditText details;
    private TextView code;
    public ArrayList<Integer>departmentsID;
    public ArrayList<Integer>typeID;
    public ArrayList<Integer>procedureID;


    public class webServiceGetDepartment extends AsyncTask<String,Integer,Boolean> {
        String NAMESPACE = "http://sgoliver.net/";
        String URL="http://192.168.1.8/ServicioWebSoap/ServicioClientes.asmx";
        String METHOD_NAME= "DepartmentList"; //the webservice method that you want to call
        String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        Boolean resul = true;
        public Department [] listDepartment;

        @Override
        protected Boolean doInBackground(String... params) {

            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject resSoap =(SoapObject)envelope.getResponse();
                listDepartment = new Department[resSoap.getPropertyCount()];

                for (int i = 0; i < listDepartment.length; i++)
                {
                    SoapObject ic = (SoapObject)resSoap.getProperty(i);

                    Department cli = new Department();
                    cli.id = Integer.parseInt(ic.getProperty(0).toString());
                    cli.department = ic.getProperty(1).toString();
                    cli.code =
                            ic.getProperty(2).toString();

                    listDepartment[i] = cli;
                    departmentsID.add(cli.id);
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
            {
                //Rellenamos la lista con los nombres de los clientes
                Log.d("INFORMATIOM","HERE");
                final String[] datos = new String[listDepartment.length];

                for(int i=0; i<listDepartment.length; i++) {
                    datos[i] = listDepartment[i].department;

                }

                ArrayAdapter<String> adaptador =
                        new ArrayAdapter<String>(enterProcedure.this,
                                android.R.layout.simple_list_item_1, datos);


                Log.d("SIZEDATA",Integer.toString (listDepartment.length));
                department.setAdapter (adaptador);

            }
            else
            {
                Log.d("ShowError","Error");
            }
        }
    }

    public class webServiceGetProcedures extends AsyncTask<String,Integer,Boolean> {
        String NAMESPACE = "http://sgoliver.net/";
        String URL="http://192.168.1.8/ServicioWebSoap/ServicioClientes.asmx";
        String METHOD_NAME= "TypeProcedureList"; //the webservice method that you want to call
        String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        Boolean resul = true;
        public Type [] listType;

        @Override
        protected Boolean doInBackground(String... params) {

            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject resSoap =(SoapObject)envelope.getResponse();
                listType = new Type[resSoap.getPropertyCount()];

                for (int i = 0; i < listType.length; i++)
                {
                    SoapObject ic = (SoapObject)resSoap.getProperty(i);

                    Type cli = new Type();
                    cli.id = Integer.parseInt(ic.getProperty(0).toString());
                    cli.name = ic.getProperty(1).toString();


                    listType[i] = cli;
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
            {
                //Rellenamos la lista con los nombres de los clientes
                Log.d("INFORMATIOM","HERE");
                final String[] datos = new String[listType.length];

                for(int i=0; i<listType.length; i++)
                    datos[i] = listType[i].name;

                ArrayAdapter<String> adaptador =
                        new ArrayAdapter<String>(enterProcedure.this,
                                android.R.layout.simple_list_item_1, datos);


                Log.d("SIZEDATA",Integer.toString (listType.length));
                spinnerProcedure.setAdapter (adaptador);

            }
            else
            {
                Log.d("ShowError","Error");
            }
        }
    }

    public class webServiceGetTypes extends AsyncTask<String,Integer,Boolean> {
        String NAMESPACE = "http://sgoliver.net/";
        String URL="http://192.168.1.8/ServicioWebSoap/ServicioClientes.asmx";
        String METHOD_NAME= "TypeIdentifyList"; //the webservice method that you want to call
        String SOAP_ACTION = NAMESPACE+METHOD_NAME;
        Boolean resul = true;
        public Type [] listType;

        @Override
        protected Boolean doInBackground(String... params) {

            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                androidHttpTransport.call(SOAP_ACTION, envelope);
                SoapObject resSoap =(SoapObject)envelope.getResponse();
                listType = new Type[resSoap.getPropertyCount()];

                for (int i = 0; i < listType.length; i++)
                {
                    SoapObject ic = (SoapObject)resSoap.getProperty(i);

                    Type cli = new Type();
                    cli.id = Integer.parseInt(ic.getProperty(0).toString());
                    cli.name = ic.getProperty(1).toString();


                    listType[i] = cli;
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
            {
                //Rellenamos la lista con los nombres de los clientes
                Log.d("INFORMATIOM","HERE");
                final String[] datos = new String[listType.length];

                for(int i=0; i<listType.length; i++)
                    datos[i] = listType[i].name;

                ArrayAdapter<String> adaptador =
                        new ArrayAdapter<String>(enterProcedure.this,
                                android.R.layout.simple_list_item_1, datos);


                Log.d("SIZEDATA",Integer.toString (listType.length));
                spinnerType.setAdapter (adaptador);

            }
            else
            {
                Log.d("ShowError","Error");
            }
        }
    }

    public class webServiceInsertProcedure extends AsyncTask<String,Integer,Boolean> {
        String NAMESPACE = "http://sgoliver.net/";
        String URL="http://192.168.1.8/ServicioWebSoap/ServicioClientes.asmx";
        String METHOD_NAME= "insertProcedure"; //the webservice method that you want to call
        String SOAP_ACTION = NAMESPACE+METHOD_NAME;

        private String date;
        private int departmentId;
        private int typeProcedure;
        private int typeIdentyfy;
        private String identify;
        private int idUser;
        private String details;
        String res;
        public webServiceInsertProcedure(String date,int departmentId,int typeProcedure,int typeIdentyfy,String identify,int UserId,String details){
            this.date = date;
            this.departmentId = departmentId;
            this.typeProcedure = typeProcedure;
            this.typeIdentyfy=typeIdentyfy;
            this.identify = identify;
            this.idUser = UserId;
            this.details =details;

        }

        @Override
        protected Boolean doInBackground(String... params) {

            boolean resul = true;
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty ("date",date);
                request.addProperty ("departmentID",departmentId);
                request.addProperty ("identify",typeIdentyfy);
                request.addProperty ("idPerson",identify);
                request.addProperty ("typeProcedure",typeProcedure);
                request.addProperty ("detail",details);
                request.addProperty ("userId",idUser);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope (SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject (request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE (URL);
                androidHttpTransport.call (SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse (); //get the response from your webservice
                res = response.toString ();


            } catch (Exception e) {
                Log.d ("Excepcion", e.toString ());
                resul = false;
            }

            return resul;
        }


        protected void onPostExecute(Boolean result) {
            code.setText ("El código del trámite es "+res);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_procedure);

        departmentsID = new ArrayList<> ();
        typeID =  new ArrayList<> ();
        procedureID = new ArrayList<> ();

        pattern = "[1-9]\\-0[1-9][1-9][1-9]\\-0[1-9][1-9][1-9]";
        //Asociar los EdtiText con la vista
        ID = (MaskedEditText) findViewById (R.id.txtID);
        details =(EditText) findViewById (R.id.txtDetail);
        code = (TextView) findViewById (R.id.txtcode);
        //Llenar el combox con los departamentos llamando al web service
        department = (Spinner) findViewById(R.id.spinnerDepartment);
        webServiceGetDepartment chargeDepartement = new webServiceGetDepartment();
        chargeDepartement.execute();

        //Llenar el combox con los tipos de trámites llamando al web service
        spinnerProcedure = (Spinner) findViewById (R.id.spinnerProcedure);
        webServiceGetProcedures chargeProcedure = new webServiceGetProcedures ();
        chargeProcedure.execute ();
        //Llenar el combox con los tipos de identificación llamando al web service
        spinnerType = (Spinner) findViewById (R.id.spinnerType);
        webServiceGetTypes chargeTypes = new webServiceGetTypes ();
        chargeTypes.execute();
        spinnerType.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    ID.setText (" ");
                    ID.setHint ("Ej: 1-0234-0567");
                    pattern = "[1-9]\\-0[1-9][1-9][1-9]\\-0[1-9][1-9][1-9]";
                    ID.setBackgroundColor (Color.WHITE);
                }
                if (position == 1){
                    ID.setText (" ");
                    ID.setHint ("Ej: 1023-400-567");
                    pattern = "[1-9][1-9][1-9][1-9]\\-[1-9][1-9][1-9]\\-[1-9][1-9][1-9]";
                    ID.setBackgroundColor (Color.WHITE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Declaración del botón y funcionamiento del botón
        register = (Button) findViewById (R.id.btnRegister);
        register.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Log.d("PATTERTEXT","HERE");
                Log.d("PATTERTEXT",pattern);
                Pattern idPattern = Pattern.compile (pattern);
                Matcher matcher = idPattern.matcher(ID.getText ().toString ());
                if (matcher.find()){
                    String detail = details.getText ().toString ();
                    if (!detail.isEmpty ()){
                        webServiceInsertProcedure insertProcedure = new webServiceInsertProcedure (
                                date.getText ().toString (),department.getSelectedItemPosition ()+1,
                                spinnerProcedure.getSelectedItemPosition ()+1,spinnerType.getSelectedItemPosition ()+1,
                                ID.getText ().toString (),1,details.getText ().toString ());
                        insertProcedure.execute ();

                    }
                    else{
                        Toast.makeText (getApplicationContext (),"Recuerde completar todos los campos para seguir",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    ID.setBackgroundColor (Color.RED);
                }

            }
        });
        /*Declara el EditText del día y programa el calendario que se hará visible cuando el usuario presione el botón
         de cambiar de fecha*/
        date = (EditText)findViewById(R.id.txtDate);
        openDatePicker = (Button) findViewById(R.id.btnChangeDate);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        date.setText(currentDateandTime);
        openDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
                changeDate.show();
            }
        });





    }

    private void openDatePicker(){
        //Rutina programada para abrir el calendario y asignarle el formato preprogramando
        Calendar newCalendar = Calendar.getInstance();
        changeDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                date.setText(sdf.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

}
