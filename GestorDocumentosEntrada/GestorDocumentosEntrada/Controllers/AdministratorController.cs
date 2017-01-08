using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using GestorDocumentosEntrada.Models;

namespace GestorDocumentosEntrada.Controllers
{
    public class AdministratorController : Controller
    {
        private ProcedureConnection con = new ProcedureConnection();

        public ActionResult UserList()
        {
            return View();
        }

        public ActionResult CreateUser()
        {
            ViewBag.deparmentTable = con.getDepartments();
            return View();
        }

        public ActionResult procedureTypes()
        {
            return View();
        }

        public ActionResult AddUser() 
        {
            return RedirectToAction("UserList", "Administrator");
        }

    }
}
