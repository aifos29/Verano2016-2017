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
                defaults: new { controller = "Menu", action = "platFormMenu", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "AgregarDocumento",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Procedure", action = "Create", id = UrlParameter.Optional }
            );

            routes.MapRoute(
                name: "BuscarOficio",
                url: "{controller}/{action}/{id}",
                defaults: new { controller = "Procedure", action = "SearchProcedure", id = UrlParameter.Optional }
            );
        }
    }
}
