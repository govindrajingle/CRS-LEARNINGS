package in.cdacnoida.dava.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cdacnoida.dava.entities.BOOTCRSGemReportDetails;
import in.cdacnoida.dava.transactions.BOOTCRSGemReportDetailsRepository;
import in.cdacnoida.dava.transactions.BOOTCRSGemReportHistoryRepository;
import in.cdacnoida.dava.util.BOOTFTPAction;

@Service
public class BOOTCRSExcelService {
	
	@Autowired
	BOOTCRSGemReportDetailsRepository bOOTCRSGemReportDetailsRepository;
	
	@Autowired
	BOOTCRSGemReportHistoryRepository bOOTCRSGemReportHistoryRepository;
	
	@Autowired
	BOOTFTPAction bOOTFTPAction;	
	
	
	public ByteArrayInputStream gemReportDetailsToExcel(List<BOOTCRSGemReportDetails> listTable) throws IOException {
        String[] columns = {
            "Branch", "Registration No.", "Validity", "Current Status", "Firm Name", "Standard", "Product Name", 
            "Brand", "Bis Id", "Variety"
        };

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
            ) {
                CreationHelper createHelper = workbook.getCreationHelper();

                Sheet sheet = workbook.createSheet("gem_report_dtl");

                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setColor(IndexedColors.BLUE.getIndex());

                CellStyle headerCellStyle = workbook.createCellStyle();
                headerCellStyle.setFont(headerFont);

                // Row for Header
                Row headerRow = sheet.createRow(0);

                // Header
                for (int col = 0; col < columns.length; col++) {
                    Cell cell = headerRow.createCell(col);
                    cell.setCellValue(columns[col]);
                    cell.setCellStyle(headerCellStyle);
                }

                int rowIdx = 1;
                for (BOOTCRSGemReportDetails details : listTable) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(details.getBranch());
                    row.createCell(1).setCellValue(details.getRegNo());
                    row.createCell(2).setCellValue(details.getValidity());
                    row.createCell(3).setCellValue(details.getCurrentStatus());
                    row.createCell(4).setCellValue(details.getFirm());
                    row.createCell(5).setCellValue(details.getStandard());
                    row.createCell(6).setCellValue(details.getProductName());
                    row.createCell(7).setCellValue(details.getBrand());
                    row.createCell(8).setCellValue(details.getBisId() != null ? details.getBisId().toString() : "");
                    
                    String variety = details.getVariety() != null ? details.getVariety() : "";
                    String varietyPart1 = "";
                    String varietyPart2 = "";
                    String varietyPart3 = "";
                    String varietyPart4 = "";
                    String varietyPart5 = "";
                    String varietyPart6 = "";

                    if (variety.length() > 0) {
                        varietyPart1 = variety.substring(0, Math.min(variety.length(), 32767));
                    }

                    if (variety.length() > 32767) {
                        varietyPart2 = variety.substring(32767, Math.min(variety.length(), 65534));
                    }

                    if (variety.length() > 65534) {
                        varietyPart3 = variety.substring(65534, Math.min(variety.length(), 98301));
                    }

                    if (variety.length() > 98301) {
                        varietyPart4 = variety.substring(98301, Math.min(variety.length(), 131068));
                    }

                    if (variety.length() > 131068) {
                        varietyPart5 = variety.substring(131068, Math.min(variety.length(), 163835));
                    }

                    if (variety.length() > 163835) {
                        varietyPart6 = variety.substring(163835, Math.min(variety.length(), 196602));
                    }

                    row.createCell(9).setCellValue(varietyPart1);
                    row.createCell(10).setCellValue(varietyPart2);
                    row.createCell(11).setCellValue(varietyPart3);
                    row.createCell(12).setCellValue(varietyPart4);
                    row.createCell(13).setCellValue(varietyPart5);
                    row.createCell(14).setCellValue(varietyPart6);
                }
                
                workbook.write(out);
                
                String latestDownloadId = "ABCXYZ"; 
                		//bOOTCRSGemReportHistoryRepository.getLatestValidDownloadId();
                String filename = "gemdata/"+latestDownloadId+".xlsx";
                OutputStream fileOut = bOOTFTPAction.createFile(filename);
                workbook.write(fileOut);
                fileOut.close();
                
                return new ByteArrayInputStream(out.toByteArray());
            }
        }
    }
