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
