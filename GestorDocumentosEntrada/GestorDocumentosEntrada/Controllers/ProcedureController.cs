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
            ViewBag.date = DateTime.Now;
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
            return Json(attributes.ToList(), JsonRequestBehavior.AllowGet); ;
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

        public String getProcedureData(String procedureCode) 
        {
            return procedureCode;
        }
    }
}