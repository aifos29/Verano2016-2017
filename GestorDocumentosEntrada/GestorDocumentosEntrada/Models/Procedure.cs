using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GestorDocumentosEntrada.Models
{
    public class Procedure
    {
        public DateTime procedureDate { get; set; }
        public string departmentName { get; set; }
        public string procedureCode { get; set; }
        public string idType { get; set; }
        public int personId { get; set; }
        public string procedureType { get; set; }
        public string procedureDetail { get; set; }

        public enum id_type
        {
            Nacional,
            Extranjero
        }
    }

    

}