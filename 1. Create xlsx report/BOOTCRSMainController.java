package in.cdacnoida.dava.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cdacnoida.dava.entities.BOOTCRSGemReportDetails;
import in.cdacnoida.dava.service.BOOTCRSExcelService;
import in.cdacnoida.dava.transactions.BOOTCRSGemReportDetailsRepository;
import in.cdacnoida.dava.transactions.BOOTCRSGemReportHistoryRepository;
import in.cdacnoida.dava.util.BOOTAccessPaths;
import in.cdacnoida.dava.util.BOOTFTPAction;

@RestController
public class BOOTCRSMainController {

	@Autowired
	BOOTCRSGemReportDetailsRepository bOOTCRSGemReportDetailsRepository;
	
	@Autowired
	BOOTCRSGemReportHistoryRepository bOOTCRSGemReportHistoryRepository;
	
	@Autowired
	BOOTCRSExcelService bOOTCRSExcelService;
	
	@Autowired
	BOOTAccessPaths bOOTAccessPaths;
	

	
	
	//26-03-2024 (Excel report generation)
	@GetMapping("/GemReportDetailsData")
	public ResponseEntity<byte[]> getGemReportDetails() throws IOException {
	    List<BOOTCRSGemReportDetails> listTable = bOOTCRSGemReportDetailsRepository.findValidDetails();
	    ByteArrayInputStream in = bOOTCRSExcelService.gemReportDetailsToExcel(listTable);
	    byte[] bytes = toByteArray(in); 
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attachment; filename=GemReport.xlsx");
	    return ResponseEntity.ok().headers(headers).body(bytes);
	}
	
	
	
	public static byte[] toByteArray(ByteArrayInputStream in) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024];
	    int len;
	    while ((len = in.read(buffer)) != -1) {
	        out.write(buffer, 0, len);
	    }
	    return out.toByteArray();
	}
	
	
}
