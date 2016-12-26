using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(GestorDocumentosEntrada.Startup))]
namespace GestorDocumentosEntrada
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
