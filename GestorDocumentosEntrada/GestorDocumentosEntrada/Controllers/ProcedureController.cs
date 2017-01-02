using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data;
using GestorDocumentosEntrada.Models;


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
            ViewBag.date = form["procedureDate"];
            ViewBag.department = form["department"];
            ViewBag.code = form["codeProcedure"];
            ViewBag.idType = form["idType"];
            ViewBag.personId = form["personId"];
            ViewBag.procedureType = form["procedureType"];
            ViewBag.detail = Request["procedureDetail"];
            return View();
        }
    }
}