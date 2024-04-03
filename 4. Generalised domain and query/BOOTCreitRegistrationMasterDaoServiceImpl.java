package in.cdacnoida.dava.serviceimpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cdacnoida.dava.daoservice.BOOTCreitRegistrationMasterDaoService;
import in.cdacnoida.dava.transactions.BOOTCrsTransactions;

@Service
public class BOOTCreitRegistrationMasterDaoServiceImpl implements BOOTCreitRegistrationMasterDaoService {

	@Autowired
	private BOOTCrsTransactions bOOTCrsTransactions;

	@Override
	public List<HashMap<String, Object>> updateLapsingService() {
		List<Object[]> results = bOOTCrsTransactions.getQueryData();
		List<HashMap<String, Object>> crmList = new ArrayList<>();
		for (Object[] row : results) {
			HashMap<String, Object> crm = new HashMap<>();
			crm.put("application_id", row[0]);
			Timestamp validUpToTimestamp = (Timestamp) row[1];
			LocalDateTime validUpToDateTime = validUpToTimestamp.toLocalDateTime();
			if (validUpToDateTime.getHour() >= 12) {
				validUpToDateTime = validUpToDateTime.plusDays(1);
			}
			crm.put("valid_up_to", validUpToDateTime);
			Timestamp regDateTimestamp = (Timestamp) row[2];
			LocalDateTime regDateDateTime = regDateTimestamp.toLocalDateTime();
			if (regDateDateTime.getHour() >= 12) {
				regDateDateTime = regDateDateTime.plusDays(1);
			}
			crm.put("reg_date", regDateDateTime);
			crm.put("reg_number", row[3]);
			crm.put("str_status", row[4]);
			crmList.add(crm);
		}
		String new_status = "LP";
		List<HashMap<String, Object>> receivedInfo = crmList;
		if (receivedInfo.size() > 0) {
			for (int index = 0; index < receivedInfo.size(); index++) {
				HashMap<String, Object> record = receivedInfo.get(index);
				String app_id = (String) record.get("application_id");
				String str_status = (String) record.get("str_status");
				String reg_number = (String) record.get("reg_number");
				bOOTCrsTransactions.updateQueryData(app_id, reg_number, str_status);
				bOOTCrsTransactions.updateQueryNewStatus(app_id, reg_number, new_status, str_status);
				bOOTCrsTransactions.LogSchedularInsertQueryInsert(app_id, reg_number, new_status, str_status);
			}
		}
		return crmList;
	}
	
	@Override
	public List<HashMap<String, Object>> DefermentQueryDataService() {
		List<Object[]> results = bOOTCrsTransactions.DefermentQueryData();
		List<HashMap<String, Object>> dfmList = new ArrayList<>();
		for(Object[] row : results) {
			HashMap<String, Object> dfm = new HashMap<>();
			dfm.put("application_id", row[0]);
			Timestamp validUpToTimestamp = (Timestamp) row[1];
			LocalDateTime validUpToDateTime = validUpToTimestamp.toLocalDateTime();
			if (validUpToDateTime.getHour() >= 12) {
				validUpToDateTime = validUpToDateTime.plusDays(1);
			}			
			dfm.put("valid_upto", validUpToDateTime);
			Timestamp regDateTimestamp = (Timestamp) row[2];
			LocalDateTime regDateDateTime = regDateTimestamp.toLocalDateTime();
			if (regDateDateTime.getHour() >= 12) {
				regDateDateTime = regDateDateTime.plusDays(1);
			}
			dfm.put("reg_date", regDateDateTime);
			dfm.put("reg_number", row[3]);
			dfm.put("str_status", row[4]);
			dfmList.add(dfm);
		}
		String new_status = "DF";
		List<HashMap<String, Object>> receivedInfo = dfmList;
		if (receivedInfo.size() > 0) {
			for (int index = 0; index < receivedInfo.size(); index++) {
				HashMap<String, Object> record = receivedInfo.get(index);
				String app_id = (String) record.get("application_id");
				String str_status = (String) record.get("str_status");
				String reg_number = (String) record.get("reg_number");
				bOOTCrsTransactions.updateQueryData(app_id, reg_number, str_status);
				bOOTCrsTransactions.updateQueryNewStatus(app_id, reg_number, new_status, str_status);
				bOOTCrsTransactions.LogSchedularInsertQueryInsert(app_id, reg_number, new_status, str_status);
			}
		}		
		return dfmList;
	}

}
