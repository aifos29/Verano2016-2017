using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using GestorDocumentosEntrada.Models;
using System.IO;
using iTextSharp.text;
using iTextSharp.text.pdf;
using System.Data;

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

        public String createBinnaclePDF(DateTime date1, DateTime date2)
        {
            DataSet binnacleToExport = con.getBinnacle(date1, date2);

            if (binnacleToExport.Tables["Table"].Rows.Count != 0)
            {
                // Creamos el documento con el tamaño de página tradicional
                Document doc = new Document(PageSize.LETTER);
                // Indicamos donde vamos a guardar el documento
                PdfWriter writer = PdfWriter.GetInstance(doc,
                                            new FileStream(@"C:\Users\ProyectoVerano_2016\Downloads\Bitacora.pdf", FileMode.Create));

             

                // Le colocamos el título y el autor
                // **Nota: Esto no será visible en el documento
                doc.AddTitle("Bitacora");
                doc.AddCreator("Gestor de Entrada de Documentos");

                // Abrimos el archivo
                doc.Open();

                // Creamos el tipo de Font que vamos utilizar
                iTextSharp.text.Font _standardFont = new iTextSharp.text.Font(iTextSharp.text.Font.FontFamily.HELVETICA, 10, iTextSharp.text.Font.NORMAL, BaseColor.BLACK);
                BaseFont titleFont = BaseFont.CreateFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, false);
                Font times = new Font(titleFont, 16, Font.BOLD, iTextSharp.text.BaseColor.BLACK);

                BaseFont dateFont = BaseFont.CreateFont(BaseFont.TIMES_ITALIC, BaseFont.CP1252, false);
                Font dateTimes = new Font(titleFont, 12, Font.NORMAL, iTextSharp.text.BaseColor.BLACK);
                // Escribimos el encabezamiento en el documento
                Paragraph title = new Paragraph("Bitácora",times);
                title.Alignment = Element.ALIGN_CENTER;
                doc.Add(title);
                doc.Add(Chunk.NEWLINE);

                Paragraph dateFrom = new Paragraph("Fecha Inicial: " + date1.ToShortDateString(), dateTimes);
                dateFrom.Alignment = Element.ALIGN_LEFT;
                doc.Add(dateFrom);
                doc.Add(Chunk.NEWLINE);

                Paragraph dateTo = new Paragraph("Fecha Final: " + date2.ToShortDateString(), dateTimes);
                dateTo.Alignment = Element.ALIGN_LEFT;
                doc.Add(dateTo);
                doc.Add(Chunk.NEWLINE);

                // Creamos una tabla que contendrá el nombre, apellido y país
                // de nuestros visitante.
                PdfPTable tblPrueba = new PdfPTable(7);
                tblPrueba.WidthPercentage = 100;

                // Configuramos el título de las columnas de la tabla
                PdfPCell clDate = new PdfPCell(new Phrase("Fecha", _standardFont));
                clDate.BorderWidth = 0;
                clDate.BorderWidthBottom = 0.75f;

                PdfPCell clConsecutive = new PdfPCell(new Phrase("Consecutivo", _standardFont));
                clConsecutive.BorderWidth = 0;
                clConsecutive.BorderWidthBottom = 0.75f;

                PdfPCell clDepartment = new PdfPCell(new Phrase("Departamento", _standardFont));
                clDepartment.BorderWidth = 0;
                clDepartment.BorderWidthBottom = 0.75f;

                PdfPCell clId = new PdfPCell(new Phrase("Cédula", _standardFont));
                clId.BorderWidth = 0;
                clId.BorderWidthBottom = 0.75f;

                PdfPCell clProcedureState = new PdfPCell(new Phrase("Estado del Trámite", _standardFont));
                clProcedureState.BorderWidth = 0;
                clProcedureState.BorderWidthBottom = 0.75f;

                PdfPCell clProcedureType = new PdfPCell(new Phrase("Tipo de Trámite", _standardFont));
                clProcedureType.BorderWidth = 0;
                clProcedureType.BorderWidthBottom = 0.75f;

                PdfPCell clPlatformist = new PdfPCell(new Phrase("Plataformista", _standardFont));
                clPlatformist.BorderWidth = 0;
                clPlatformist.BorderWidthBottom = 0.75f;

                // Añadimos las celdas a la tabla
                tblPrueba.AddCell(clDate);
                tblPrueba.AddCell(clConsecutive);
                tblPrueba.AddCell(clDepartment);
                tblPrueba.AddCell(clId);
                tblPrueba.AddCell(clProcedureState);
                tblPrueba.AddCell(clProcedureType);
                tblPrueba.AddCell(clPlatformist);


                foreach (DataRow row in binnacleToExport.Tables["Table"].Rows)
                {
                    // Llenamos la tabla con información
                    clDate = new PdfPCell(new Phrase((DateTime.Parse(row["Fecha"].ToString()).ToShortDateString()), _standardFont));
                    clDate.BorderWidth = 0;

                    clConsecutive = new PdfPCell(new Phrase((row["Consecutivo"]).ToString(), _standardFont));
                    clConsecutive.BorderWidth = 0;

                    clDepartment = new PdfPCell(new Phrase((row["Departamento"]).ToString(), _standardFont));
                    clDepartment.BorderWidth = 0;

                    clId = new PdfPCell(new Phrase((row["Cèdula"]).ToString(), _standardFont));
                    clId.BorderWidth = 0;

                    clProcedureState = new PdfPCell(new Phrase((row["Estado"]).ToString(), _standardFont));
                    clProcedureState.BorderWidth = 0;

                    clProcedureType = new PdfPCell(new Phrase((row["Tipo de Procedimiento"]).ToString(), _standardFont));
                    clProcedureType.BorderWidth = 0;

                    clPlatformist = new PdfPCell(new Phrase((row["Plataformista"]).ToString(), _standardFont));
                    clPlatformist.BorderWidth = 0;

                    // Añadimos las celdas a la tabla
                    tblPrueba.AddCell(clDate);
                    tblPrueba.AddCell(clConsecutive);
                    tblPrueba.AddCell(clDepartment);
                    tblPrueba.AddCell(clId);
                    tblPrueba.AddCell(clProcedureState);
                    tblPrueba.AddCell(clProcedureType);
                    tblPrueba.AddCell(clPlatformist);
                }

                // Finalmente, añadimos la tabla al documento PDF y cerramos el documento
                doc.Add(tblPrueba);

                doc.Close();
                writer.Close();

                return "yes";
            }
            else {
                return "no";
            }
            
        }

    }
}
