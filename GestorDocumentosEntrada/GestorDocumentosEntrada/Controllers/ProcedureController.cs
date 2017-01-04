using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data;
using GestorDocumentosEntrada.Models;
using System.Globalization;


namespace GestorDocumentosEntrada.Controllers
{
    public class ProcedureController : Controller
    {
        private ProcedureConnection con = new ProcedureConnection();
        // GET: Procedure
        public ActionResult Create()
        {
            ViewBag.date = DateTime.Now.ToString("yyyy-MM-dd");;
            ViewBag.deparmentTable = con.getDepartments();
            ViewBag.procedureType = con.getProcedureType();
            ViewBag.identifyType = con.getIdentifyType();
            return View();
        }

        public ActionResult Edit()
        {
            ViewBag.deparmentTable = con.getDepartments();
            ViewBag.procedureType = con.getProcedureType();
            ViewBag.identifyType = con.getIdentifyType();
            ViewBag.display = false;
            return View();
        }

        public ActionResult SearchProcedure()
        {
            ViewBag.deparmentTable = con.getDepartments();
            ViewBag.platformersTable = con.getPlatformers();
            return View();
        }

        public String CreateProcedureCode(int departmentId) 
        {
            String code = con.getCode(departmentId);
            return code;
        }

        public JsonResult updateFormatId(int idTypeOfIdentify) 
        {
            List<String> attributes = new List<String>();
            
            if (idTypeOfIdentify == 1) {
                attributes.Add("Ej: 1-0234-0567");
                attributes.Add("\\d{1}-\\d{4}-\\d{4}$");
            }else {
                attributes.Add("Ej: 1023-400-567");
                attributes.Add("\\d{4}-\\d{3}-\\d{3}$");
            }
            return Json(attributes.ToList(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult AddProcedure(FormCollection form) {

            DateTime date = DateTime.Parse(form["procedureDate"]);
            int departmentId = Int32.Parse(form["department"]);
            String code = form["codeProcedure"];
            int idTypeOfIdentify = Int32.Parse(form["idType"]);
            String personID = form["personId"];
            int idTypeOfProcedure = Int32.Parse(form["procedureType"]);
            String detail = form["procedureDetail"];
            int userId = 1;

            con.insertProcedure(date, departmentId, code, idTypeOfIdentify, personID, idTypeOfProcedure, detail, userId);
            return RedirectToAction("platformMenu", "Menu");
        }

        public JsonResult getProcedureData(String procedureCode) 
        {
            DataSet procedure = con.getProcedure(procedureCode);
            List<string> list = new List<string>();
            foreach (DataRow row in procedure.Tables["procedureList"].Rows)
            {
                for (int index = 0; index < 7; index++ )
                {
                    list.Add(row[index].ToString());
                }
            }

            string[] dateSend = list[1].Split('/');
            string[] yearTime = dateSend[2].Split(' ');
            String date = yearTime[0] + '-' + dateSend[1] + '-' + dateSend[0];
            list[1] = date;
            return Json(list.ToList(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult saveChangedProcedure(FormCollection form)
        {
            String code = form["codeProcedure"];
            int idTypeOfIdentify = Int32.Parse(form["idType"]);
            String personID = form["personId"];
            int idTypeOfProcedure = Int32.Parse(form["procedureType"]);
            String detail = form["procedureDetail"];

            con.updateProcedure(code,idTypeOfIdentify, personID, idTypeOfProcedure, detail);
            return RedirectToAction("platformBossMenu", "Menu");
        }

        public ActionResult searchDepartment(String dep) {
            ViewBag.searchTable = con.getSearchDep(dep);
            return View();
        }

        public ActionResult searchPlatformist(String plat)
        {
            ViewBag.searchTable = con.getSearchPlat(plat);
            return View();
        }

       
    }
}