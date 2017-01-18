package com.example.sofia.gestorentradadocumentos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private EditText ID;
    private Button register;
    private EditText details;
    private TextView code;
    public ArrayList<Integer>departmentsID;
    public ArrayList<Integer>typeID;
    public ArrayList<Integer>procedureID;
    public dataBase dataBase;
    public int idPlataformist;
    public Boolean status;
    public EditText nameClient;
    public EditText contact;
    public TextView welcome;

    public class webServiceInsertProcedure extends AsyncTask<String,Integer,String> {
        String NAMESPACE = "http://sgoliver.net/";
        String URL="http://192.168.0.15/ServicioWebSoap/ServicioClientes.asmx";
        String METHOD_NAME= "insertProcedure"; //the webservice method that you want to call
        String SOAP_ACTION = NAMESPACE+METHOD_NAME;

        private String date;
        private int departmentId;
        private int typeProcedure;
        private int typeIdentyfy;
        private String identify;
        private int idUser;
        private String details;
        private String name;
        private String contact;
        public enterProcedure enter;
        String res;
        public webServiceInsertProcedure(String date,int departmentId,int typeProcedure,int typeIdentyfy,String identify,String name,String contac,int UserId,String details){
            this.date = date;
            this.departmentId = departmentId;
            this.typeProcedure = typeProcedure;
            this.typeIdentyfy=typeIdentyfy;
            this.identify = identify;
            this.idUser = UserId;
            this.details =details;
            this.name = name;
            this.contact = contac;

        }

        private ProgressDialog pdia;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdia = new ProgressDialog(enter);
            pdia.setMessage("Loading...");
            pdia.show();
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            pdia.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            String code ="";
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty ("date",date);
                request.addProperty ("departmentID",departmentId);
                request.addProperty ("identify",typeIdentyfy);
                request.addProperty ("idPerson",identify);
                request.addProperty ("typeProcedure",typeProcedure);
                request.addProperty ("detail",details);
                request.addProperty ("userId",idUser);
                request.addProperty("name",name);
                request.addProperty("contact",contact);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope (SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject (request);
                HttpTransportSE androidHttpTransport = new HttpTransportSE (URL);
                androidHttpTransport.call (SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse (); //get the response from your webservice
                code = response.toString ();


            } catch (Exception e) {
                Log.d ("Excepcion", e.toString ());

            }

            return code;
        }

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_procedure);
        connection con = new connection();
        try {
            status = con.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        SharedPreferences sharedPref = getSharedPreferences("Credentials", Context.MODE_WORLD_READABLE);
        String name = sharedPref.getString("name",""); ;
        welcome = (TextView) findViewById (R.id.txtWelcome);
        welcome.setText ("Bienvenido "+name);

        idPlataformist = sharedPref.getInt("id",0);
        Log.d("PLATAFORMIST","este es"+idPlataformist);

        nameClient = (EditText) findViewById(R.id.txtName);
        contact = (EditText)findViewById(R.id.txtContact);

        departmentsID = new ArrayList<> ();
        typeID =  new ArrayList<> ();
        procedureID = new ArrayList<> ();
        dataBase = new dataBase ();

        pattern = "[1-9]\\-0[1-9][1-9][1-9]\\-0[1-9][1-9][1-9]";
        //Asociar los EdtiText con la vista
        ID = (EditText) findViewById (R.id.txtId);
        details =(EditText) findViewById (R.id.txtDetail);
        code = (TextView) findViewById (R.id.txtcode);
        //Llenar el combox con los departamentos llamando al web service
        department = (Spinner) findViewById(R.id.spinnerDepartment);
        loadDepartmentsSpinner ();

        //Llenar el combox con los tipos de trámites llamando al web service
        spinnerProcedure = (Spinner) findViewById (R.id.spinnerProcedure);
        loadProcedureSpinner();

        //Llenar el combox con los tipos de identificación llamando al web service
        spinnerType = (Spinner) findViewById (R.id.spinnerType);
        loadIdentifySpinner ();
        spinnerType.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    ID.setBackgroundColor (Color.TRANSPARENT);
                    ID.setHint ("Ej: 1-0234-0567");
                    pattern = "[1-9]\\-0[1-9][1-9][1-9]\\-0[1-9][1-9][1-9]";

                }
                if (position == 1){
                    ID.setBackgroundColor (Color.TRANSPARENT);
                    ID.setHint ("Ej: 1023-400-567");
                    pattern = "[1-9][1-9][1-9][1-9]\\-[1-9][1-9][1-9]\\-[1-9][1-9][1-9]";

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
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String finalDate="";
                try {
                    Date from = sdf.parse(date.getText().toString());
                    finalDate = dateFormat.format(from);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int departmentID = dataBase.getDepartmentID (department.getSelectedItem ().toString ());
                int procedureID = dataBase.getProcedureID (spinnerProcedure.getSelectedItem ().toString ());
                Pattern idPattern = Pattern.compile (pattern);
                Matcher matcher = idPattern.matcher(ID.getText ().toString ());
                if (matcher.find()){
                    String detail = details.getText ().toString ();
                    String contactName = nameClient.getText().toString();
                    String contactData = contact.getText().toString();
                    if (!detail.isEmpty () ||!contactName.isEmpty() || !contactData.isEmpty() ){
                        if(status){
                            webServiceInsertProcedure insertProcedure = new webServiceInsertProcedure (
                                    finalDate,departmentID,
                                    procedureID,spinnerType.getSelectedItemPosition ()+1,
                                    ID.getText ().toString (),contactName,contactData,idPlataformist,details.getText ().toString ());
                            try {
                                insertProcedure.enter = enterProcedure.this;
                                String followCode = insertProcedure.execute ().get();
                                Intent showCode = new Intent(getApplicationContext(), showCode.class);
                                showCode.putExtra("code",followCode);
                                startActivity(showCode);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            dataBase.insertProcedure(finalDate,departmentID,spinnerType.getSelectedItemPosition()+1,ID.getText().toString(),contactName
                            ,contactData,procedureID,details.getText().toString(),idPlataformist);
                            Intent showCode = new Intent(getApplicationContext(), showCode.class);
                            showCode.putExtra("code","NA");
                            startActivity(showCode);

                        }



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
        sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = sdf.format(new Date());
        date.setText(currentDateandTime);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
                changeDate.show();
            }
        });

    }
    private void loadDepartmentsSpinner(){
        // Spinner Drop down elements
        List<String> lables = dataBase.getDepartments ();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        department.setAdapter(dataAdapter);
    }

    private void loadProcedureSpinner(){
        // Spinner Drop down elements
        List<String> lables = dataBase.getProcedures ();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerProcedure.setAdapter(dataAdapter);
    }


    private void loadIdentifySpinner(){
        // Spinner Drop down elements
        List<String> lables = dataBase.getIdentify ();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerType.setAdapter(dataAdapter);
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

    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(getApplicationContext(),Menu.class);
        startActivity(goBack);
    }

}
