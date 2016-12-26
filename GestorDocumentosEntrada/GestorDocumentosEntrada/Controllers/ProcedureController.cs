using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using GestorDocumentosEntrada.Models;


namespace GestorDocumentosEntrada.Controllers
{
    public class ProcedureController : Controller
    {
        // GET: Procedure
        public ActionResult Create()
        {
            return View();
        }

        public ActionResult SearchProcedure()
        {
            return View();
        }
    }
}