using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Data;

namespace GestorDocumentosEntrada.Models
{
    public class ProcedureConnection
    {
        private static String connectionString = "Data Source = WIN-BURJN6TL5I0\\SQLEXPRESS; Initial Catalog=procedureDB;Integrated security=true";
        private SqlConnection connection = new SqlConnection(connectionString);

        //get the data to fill the differents departments
        public DataSet getDepartments() 
        {
            DataSet departmentTable = new DataSet();
            connection.Open();
            if (connection != null)
            {
                SqlDataAdapter sqlQuery = new SqlDataAdapter("Select * from getDepartments() Order by department", connection);
                sqlQuery.Fill(departmentTable, "DepartmentsList");
                connection.Close();
            }
            else {
                var message = "Error de Conexion";
            }
            return departmentTable;
        }

        //get the data to fill the procedure type
        public DataSet getProcedureType()
        {
            DataSet procedureTypeTable = new DataSet();
            connection.Open();
            if (connection != null)
            {
                SqlDataAdapter sqlQuery = new SqlDataAdapter("Select * from getProcedureType() Order by TypeOfProcedure", connection);
                sqlQuery.Fill(procedureTypeTable, "ProcedureTypeList");
                connection.Close();
            }
            else
            {
                var message = "Error de Conexion";
            }
            return procedureTypeTable;
        }

        //get the data to fill the id type
        public DataSet getIdentifyType()
        {
            DataSet identifyTypeTable = new DataSet();
            connection.Open();
            if (connection != null)
            {
                SqlDataAdapter sqlQuery = new SqlDataAdapter("Select * from getIdentifyType()", connection); 
                sqlQuery.Fill(identifyTypeTable, "IdentifyTypeList");
                connection.Close();
            }
            else
            {
                var message = "Error de Conexion";
            }
            return identifyTypeTable;
        }


        //Get the code to assign a the new procedure
        public string getCode(int departmentId)
        {
            String code = null;
            connection.Open();
            if (connection != null)
            {
                SqlCommand sqlQuery = new SqlCommand("select dbo.getConsecutive(" + departmentId + ")", connection);
                SqlDataReader reader = sqlQuery.ExecuteReader();
                while (reader.Read())
                {
                    code = reader[0].ToString();
                }
                connection.Close();
            }
            else
            {
                code = "Error de Conexion";
            }
            return code;
        }

        public void insertProcedure(DateTime date,int departmentId,String code,int idTypeOfIdentify,String personID,int  idTypeOfProcedure,String detail,int userId)
        {
            connection.Open();
            if (connection != null)
            {
                SqlCommand cmd = new SqlCommand("dbo.insertProcedure", connection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@date",date );
                cmd.Parameters.AddWithValue("@departmentId", departmentId);
                cmd.Parameters.AddWithValue("@code", code);
                cmd.Parameters.AddWithValue("@idTypeOfIdentify", idTypeOfIdentify);
                cmd.Parameters.AddWithValue("@personID", personID);
                cmd.Parameters.AddWithValue("@idTypeOfProcedure", idTypeOfProcedure);
                cmd.Parameters.AddWithValue("@detail", detail);
                cmd.Parameters.AddWithValue("@userId", userId);
                int rowAffected = cmd.ExecuteNonQuery();

                connection.Close();
            }
        }
    }
}