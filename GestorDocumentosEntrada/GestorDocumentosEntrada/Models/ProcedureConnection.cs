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
        private static String connectionString = "Data Source = (local)\\SQLEXPRESS; Initial Catalog=procedureDB;Integrated security=true";
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

        public string insertProcedure(DateTime date,int departmentId,int idTypeOfIdentify,String personID,int  idTypeOfProcedure,String detail,int userId)
        {
            String code = null;
            connection.Open();
            if (connection != null)
            {
                SqlCommand cmd = new SqlCommand("dbo.insertProcedure", connection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@date",date );
                cmd.Parameters.AddWithValue("@departmentId", departmentId);
                cmd.Parameters.AddWithValue("@idTypeOfIdentify", idTypeOfIdentify);
                cmd.Parameters.AddWithValue("@personID", personID);
                cmd.Parameters.AddWithValue("@idTypeOfProcedure", idTypeOfProcedure);
                cmd.Parameters.AddWithValue("@detail", detail);
                cmd.Parameters.AddWithValue("@userId", userId);
                cmd.Parameters.Add("@code", SqlDbType.VarChar, 50).Direction = ParameterDirection.Output;
                
                cmd.ExecuteNonQuery();

                code = Convert.ToString(cmd.Parameters["@code"].Value);

                connection.Close();
            }
            return code;
        }

        public DataSet getProcedure(String procedureCode)
        {
            DataSet procedureTable = new DataSet();
            connection.Open();
            if (connection != null)
            {
                string query = "Select * from getProcedure('" + procedureCode + "')";
                SqlDataAdapter sqlQuery = new SqlDataAdapter(query, connection);
                sqlQuery.Fill(procedureTable, "procedureList");
                connection.Close();
            }
            return procedureTable;
        }

        public void updateProcedure(String code, int idTypeOfIdentify,String personID,int  idTypeOfProcedure,String detail)
        {
            connection.Open();
            if (connection != null)
            {
                SqlCommand cmd = new SqlCommand("dbo.updateProcedure", connection);
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.Parameters.AddWithValue("@code", code);
                cmd.Parameters.AddWithValue("@idTypeOfIdentify", idTypeOfIdentify);
                cmd.Parameters.AddWithValue("@personID", personID);
                cmd.Parameters.AddWithValue("@idTypeOfProcedure", idTypeOfProcedure);
                cmd.Parameters.AddWithValue("@detail", detail);
                int rowAffected = cmd.ExecuteNonQuery();

                connection.Close();
            }
        }

        public DataSet getPlatformers()
        {
            DataSet platformerTable = new DataSet();
            connection.Open();
            if (connection != null)
            {
                SqlDataAdapter sqlQuery = new SqlDataAdapter("Select * from getPlatformers() Order by name", connection);
                sqlQuery.Fill(platformerTable, "PlatformerList");
                connection.Close();
            }
            else
            {
                var message = "Error de Conexion";
            }
            return platformerTable;
        }

        public DataSet getSearchDep(String dep){
            DataSet depTable = new DataSet();
            connection.Open();
            if (connection != null)
            {
                SqlDataAdapter sqlQuery = new SqlDataAdapter("Select * from dbo.searchByDepartment('"+dep+"')", connection);
                sqlQuery.Fill(depTable, "Table");
                connection.Close();
            }
            else
            {
                var message = "Error de Conexion";
            }
            return depTable;
        }

        public DataSet getSearchPlat(String plat)
        {
            DataSet depTable = new DataSet();
            connection.Open();
            if (connection != null)
            {
                SqlDataAdapter sqlQuery = new SqlDataAdapter("Select * from dbo.searchByPlatformer('"+plat+"')", connection);
                sqlQuery.Fill(depTable, "Table");
                connection.Close();
            }
            else
            {
                var message = "Error de Conexion";
            }
            return depTable;
        }

        public DataSet getSearchDate(DateTime from, DateTime to) {

            DataSet depTable = new DataSet();
            connection.Open();
            String newFrom = from.ToString("yyyy-MM-dd");
            String newTo = to.ToString("yyyy-MM-dd");
            if (connection != null)
            {
                SqlDataAdapter sqlQuery = new SqlDataAdapter("Select * from dbo.searchByDate('" + newFrom + "', '" + newTo + "')", connection);
                sqlQuery.Fill(depTable, "Table");
                connection.Close();
            }
            else
            {
                var message = "Error de Conexion";
            }
            return depTable;
        
        }

        public DataSet getSearchCode(String code)
        {
            DataSet depTable = new DataSet();
            connection.Open();
            if (connection != null)
            {
                SqlDataAdapter sqlQuery = new SqlDataAdapter("Select * from dbo.searchByCode('" + code + "')", connection);
                sqlQuery.Fill(depTable, "Table");
                connection.Close();
            }
            else
            {
                var message = "Error de Conexion";
            }
            return depTable;
        }

    }
}