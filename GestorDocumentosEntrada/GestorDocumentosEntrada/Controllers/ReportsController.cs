using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using GestorDocumentosEntrada.Models;

namespace GestorDocumentosEntrada.Controllers
{
    public class ReportsController : Controller
    {
        private ProcedureConnection con = new ProcedureConnection();

        public ActionResult Statistic()
        {
            ViewBag.deparmentTable = con.getDepartments();
            ViewBag.platformersTable = con.getPlatformers();
            return View();
        }

        public ActionResult Binnacle()
        {
            return View();
        }

        public ActionResult getBinnacle(DateTime from, DateTime to)
        {
            ViewBag.searchTable = con.getBinnacle(from, to);
            return PartialView();
        }

    }
}
