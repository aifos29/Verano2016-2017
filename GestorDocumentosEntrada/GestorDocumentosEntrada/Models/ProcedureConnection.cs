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

        public DataSet getBinnacle(DateTime from, DateTime to)
        {

            DataSet depTable = new DataSet();
            connection.Open();
            String newFrom = from.ToString("yyyy-MM-dd");
            String newTo = to.ToString("yyyy-MM-dd");
            if (connection != null)
            {
                SqlDataAdapter sqlQuery = new SqlDataAdapter("Select * from dbo.getBinnacle('" + newFrom + "', '" + newTo + "') Order by Fecha", connection);
                sqlQuery.Fill(depTable, "Table");
                connection.Close();
            }
            else
            {
                var message = "Error de Conexion";
            }
            return depTable;

        }

        public int getIDLogin(string email, string pwd) {
            connection.Open();
            int id = 0;
            if (connection != null)
            {
                SqlCommand sqlQuery = new SqlCommand("select [dbo].[LoginUsuario]('" + email+ "','"+pwd+"')", connection);
                SqlDataReader reader = sqlQuery.ExecuteReader();
                while (reader.Read())
                {
                    id = (int) reader[0];
                }
                connection.Close();
            }
            else
            {
                id = -1;
            }
            return id;
        }

        public int getIDPlatformer(int idLog)
        {
            connection.Open();
            int id = 0;
            if (connection != null)
            {
                SqlCommand sqlQuery = new SqlCommand("select [dbo].[seacrhInPlatformer] ("+idLog+")", connection);
                SqlDataReader reader = sqlQuery.ExecuteReader();
                while (reader.Read())
                {
                    id = (int)reader[0];
                }
                connection.Close();
            }
            else
            {
                id = -1;
            }
            return id;
        }

        public string getNamePlatformer(int platformertId)
        {
            String code = null;
            connection.Open();
            if (connection != null)
            {
                SqlCommand sqlQuery = new SqlCommand("select [dbo].[getNamePlatforme] (" + platformertId + ")", connection);
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

        public int IsABoss(int idPlat)
        {
            connection.Open();
            int flag = 0;
            if (connection != null)
            {
                SqlCommand sqlQuery = new SqlCommand("select [dbo].[IsABoss] (" + idPlat + ")", connection);
                SqlDataReader reader = sqlQuery.ExecuteReader();
                while (reader.Read())
                {
                    flag = (int)reader[0];
                }
                connection.Close();
            }
            else
            {
                flag = -1;
            }
            return flag     ;
        }


        public int getIDSecretary(int idLog)
        {
            connection.Open();
            int id = 0;
            if (connection != null)
            {
                SqlCommand sqlQuery = new SqlCommand("select [dbo].[seacrhInSecretary] (" + idLog + ")", connection);
                SqlDataReader reader = sqlQuery.ExecuteReader();
                while (reader.Read())
                {
                    id = (int)reader[0];
                }
                connection.Close();
            }
            else
            {
                id = -1;
            }
            return id;
        }

        public string getNameSecretary(int secretarytId)
        {
            String code = null;
            connection.Open();
            if (connection != null)
            {
                SqlCommand sqlQuery = new SqlCommand("select [dbo].[getNameSecretary] (" + secretarytId + ")", connection);
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



        public int getIDAdministrator(int idLog)
        {
            connection.Open();
            int id = 0;
            if (connection != null)
            {
                SqlCommand sqlQuery = new SqlCommand("select [dbo].[seacrhInAdministrator] (" + idLog + ")", connection);
                SqlDataReader reader = sqlQuery.ExecuteReader();
                while (reader.Read())
                {
                    id = (int)reader[0];
                }
                connection.Close();
            }
            else
            {
                id = -1;
            }
            return id;
        }

        public string getNameAdministrator  (int administratortId)
        {
            String code = null;
            connection.Open();
            if (connection != null)
            {
                SqlCommand sqlQuery = new SqlCommand("select [dbo].[getNameAdministrator] (" + administratortId + ")", connection);
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

        public DataSet getViewUsers()
        {
            DataSet depTable = new DataSet();
            connection.Open();
            if (connection != null)
            {
                SqlDataAdapter sqlQuery = new SqlDataAdapter("Select * from [dbo].[getViewOfUser] ()", connection);
                sqlQuery.Fill(depTable, "Table");
                connection.Close();
            }
            else
            {
                var message = "Error de Conexion";
            }
            return depTable;
        }
        public int existEmail(String email) { 
             int code = 0;
            int flag = 0;
            int idLog = 0;
            connection.Open();
            if (connection != null)
            {

                SqlCommand sqlQuery = new SqlCommand("select [dbo].[existEmail] ('" + email + "')", connection);
                SqlDataReader reader = sqlQuery.ExecuteReader();
                while (reader.Read())
                {
                    flag = (int)reader[0];
                }
            }
            connection.Close();
            return flag;

        }

        public int insertLoggin(String email, String pwd) { 
             int code = 0;
         
            connection.Open();
            if (connection != null)
            {
         
                    SqlCommand cmd = new SqlCommand("dbo.insertLogging", connection);
                    cmd.CommandType = CommandType.StoredProcedure;
                    cmd.Parameters.AddWithValue("@email", email);
                    cmd.Parameters.AddWithValue("@password", pwd);
                    cmd.ExecuteNonQuery();
             }
            connection.Close();
            return code;

        }
        public int searchInLogging(String email, String pwd){
            int idLog = 0;
             connection.Open();
            if (connection != null)
            {
                
                    SqlCommand sqlQuery2 = new SqlCommand("select [dbo].[searchInLogging] ('" + email + "','" + pwd + "')", connection);
                    SqlDataReader reader2 = sqlQuery2.ExecuteReader();
                    while (reader2.Read())
                    {
                        idLog = (int)reader2[0];
                    }
            }
            connection.Close();
            return idLog;

        }
        public int insertPlatfor(String email, String pwd, String name, int boss)
        {
            int code = 0;
            int flag = 0;
            int idLog = 0;
            connection.Open();
            if (connection != null)
            {
                connection.Close();
                flag = existEmail(email);

                if (flag == 0)
                {
                    insertLoggin(email, pwd);
                    idLog = searchInLogging(email,pwd);
                    connection.Open();
                    SqlCommand cmd2 = new SqlCommand("dbo.insertPlatformers", connection);
                    cmd2.CommandType = CommandType.StoredProcedure;
                    cmd2.Parameters.AddWithValue("@name", name);
                    cmd2.Parameters.AddWithValue("@isABoss", boss);
                    cmd2.Parameters.AddWithValue("@idLogging", idLog);
                    cmd2.ExecuteNonQuery();
                    code = 0;
                }
                else
                { code = -1; }

                connection.Close();
            }
            return code;
        }


        public int insertSecre(String email, String pwd, String name, String dep)
        {
            int code = 0;
            int flag = 0;
            int idLog = 0;
            int idDep = 0;
            connection.Open();
            if (connection != null)
            {
                connection.Close();
                flag = existEmail(email);

                if (flag == 0)
                {
                    insertLoggin(email, pwd);
                    idLog = searchInLogging(email, pwd);
                    connection.Open();

                    SqlCommand sqlQuery3 = new SqlCommand("select [dbo].[searchInDepartment] ('" + dep+ "')", connection);
                    SqlDataReader reader3 = sqlQuery3.ExecuteReader();
                    while (reader3.Read())
                    {
                        idDep = (int)reader3[0];
                    }
                    connection.Close();
                    connection.Open();
                    SqlCommand cmd2 = new SqlCommand("dbo.insertSecretary", connection);
                    cmd2.CommandType = CommandType.StoredProcedure;
                    cmd2.Parameters.AddWithValue("@name", name);
                    cmd2.Parameters.AddWithValue("@idLoggingin", idLog);
                    cmd2.Parameters.AddWithValue("@idDepartment", idDep);
                    cmd2.ExecuteNonQuery();
                    code = 0;
                }
                else
                { code = -1; }

                connection.Close();
            }
            return code;
        }

    }
}