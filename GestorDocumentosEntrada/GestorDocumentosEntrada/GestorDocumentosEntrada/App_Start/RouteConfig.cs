using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace GestorDocumentosEntrada
{
    public class RouteConfig
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            routes.MapRoute(
                name: "Default",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "LogIn", action = "Index", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "PlatformMenu",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Menu", action = "platFormMenu", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "PlatformBossMenu",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Menu", action = "platFormBossMenu", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "AdministrativeMenu",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Menu", action = "administrativeMenu", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "AgregarDocumento",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Procedure", action = "Create", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "EditarTramite",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Procedure", action = "Edit", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "BuscarOficio",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Procedure", action = "SearchProcedure", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "VerOficio",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Procedure", action = "AddProcedure", id = UrlParameter.Optional }
            );
        }
    }
}
