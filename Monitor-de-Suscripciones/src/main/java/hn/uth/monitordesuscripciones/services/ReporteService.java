package hn.uth.monitordesuscripciones.services;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import hn.uth.monitordesuscripciones.data.Suscripcion;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ReporteService {

    public byte[] generarPdf(List<Suscripcion> suscripciones) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, output);
            document.open();
            document.add(new Paragraph("Reporte de suscripciones"));
            document.add(new Paragraph("--------------------------------"));
            for (Suscripcion s : suscripciones) {
                document.add(new Paragraph(s.getServicio() + " | " + s.getCategoriaNombre() + " | " + s.getCosto()));
            }
            document.close();
            return output.toByteArray();
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo generar PDF", ex);
        }
    }

    public byte[] generarExcel(List<Suscripcion> suscripciones) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Suscripciones");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Servicio");
            header.createCell(1).setCellValue("Categoria");
            header.createCell(2).setCellValue("Costo");
            header.createCell(3).setCellValue("Renovacion");
            header.createCell(4).setCellValue("Recurrencia");

            int rowNumber = 1;
            for (Suscripcion s : suscripciones) {
                Row row = sheet.createRow(rowNumber++);
                row.createCell(0).setCellValue(s.getServicio());
                row.createCell(1).setCellValue(s.getCategoriaNombre());
                row.createCell(2).setCellValue(s.getCosto().doubleValue());
                row.createCell(3).setCellValue(s.getFechaRenovacion().toString());
                row.createCell(4).setCellValue(s.getRecurrencia());
            }
            workbook.write(output);
            return output.toByteArray();
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo generar Excel", ex);
        }
    }
}

