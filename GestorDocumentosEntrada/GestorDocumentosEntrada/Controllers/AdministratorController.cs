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
            ViewBag.table = con.getViewUsers();
            return View();
        }

        public ActionResult CreateUser()
        {
            ViewBag.deparmentTable = con.getDepartments();
            return View();
        }
        [HttpPost]
        public ActionResult CreateUser(FormCollection form)
        {
            int boss = 0;
            if (form["userIsBoss"] != null) {boss = Int32.Parse(form["userIsBoss"]);}
            String name = form["userName"];
            String dep = form["userDepartment"];
            String email = form["userEmail"];
            String pwd = form["userPassword"];
            if (dep.CompareTo("Plataforma") == 0) 
            {
                 int flag = con.insertPlatfor(email, pwd, name, boss);
                 if (flag == -1) {
                     ViewBag.messege = "Lo sentimos, ese correo ya està registrado";
                     ViewBag.deparmentTable = con.getDepartments();
                     return View();
                 } else {
                     return RedirectToAction("UserList", "Administrator");
                 }
            } else {
                int flag = con.insertSecre(email, pwd, name,dep);
                if (flag == -1)
                {
                    ViewBag.messege = "Lo sentimos, ese correo ya està registrado";
                    ViewBag.deparmentTable = con.getDepartments();
                    return View();
                }
                else
                {
                    return RedirectToAction("UserList", "Administrator");
                }
            }

            
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
