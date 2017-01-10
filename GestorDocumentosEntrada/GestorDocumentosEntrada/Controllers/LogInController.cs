using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using GestorDocumentosEntrada.Models;
using System.Globalization;

namespace GestorDocumentosEntrada.Controllers
{
    public class LogInController : Controller
    {
        //
        // GET: /LogIn/
        private ProcedureConnection con = new ProcedureConnection();
        public ActionResult Index()
        {
            return View();
        }
        [HttpPost]
        public ActionResult Index(FormCollection form)
        {
            String email = form["email"];
            String pwd = form["pwd"];
            int generalExist = con.getIDLogin(email, pwd);
            int idTablePlat = 0;
            if (generalExist == 0) 
            {
                ViewBag.messege = "Lo sentimos, el correo y la contraseña no coinciden";
                return View();
            }
            else if (generalExist == -1)
            {
                ViewBag.messege = "Lo sentimos, Hubo un error de conexiòn";
                return View();
            }
            else if (generalExist != 0 && generalExist != -1) 
            {
                idTablePlat = con.getIDPlatformer(generalExist);
                if (idTablePlat != 0 && idTablePlat != -1)
                {
                    int auxID = con.IsABoss(idTablePlat);
                    if (auxID == 0)
                    {
                        Session["userName"] = con.getNamePlatformer(idTablePlat);
                        Session["platfomerID"] = idTablePlat;
                        Session["isABoss"] = 0;
                        return RedirectToAction("platFormMenu", "Menu");
                    }
                    else
                    {
                        Session["userName"] = con.getNamePlatformer(idTablePlat);
                        Session["platfomerID"] = idTablePlat;
                        Session["isABoss"] = 1;
                        return RedirectToAction("platFormBossMenu", "Menu");
                    }
                }
                else 
                { 
                    int idTableSec = con.getIDSecretary(generalExist);
                    if (idTableSec != 0 && idTableSec != -1)
                    {
                        Session["userName"] = con.getNameSecretary(idTableSec);
                        Session["platfomerID"] = idTableSec;
                        return RedirectToAction("administrativeMenu", "Menu");
                    }
                    else 
                    {
                        int idTableAdm = con.getIDAdministrator(generalExist);
                        if (idTableAdm != 0 && idTableAdm != -1)
                        {
                            Session["userName"] = con.getNameAdministrator(idTableAdm);
                            Session["platfomerID"] = idTableAdm;
                            return RedirectToAction("administratorMenu", "Menu");
                        }
                    } 
                    
                }
            }

           
            return View();
        }
        
        
    }
}
