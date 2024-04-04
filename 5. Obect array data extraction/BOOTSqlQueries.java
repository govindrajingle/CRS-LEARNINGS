package in.cdacnoida.dava.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BOOTSqlQueries {

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	String currentDate = formatter.format(new Date());
	
	private String MailContent = "select subject,content from creit_surveillance_format_master where format_id= ? ";

	private String ListToAddMailToSendQueryLapsed = "  select application_id,reg_number,reg_date,to_char(valid_upto,'dd/MM/yyyy') as to_char1 ,tr_dt \r\n"
			+ "                                             ,mail_send_content,is_no \r\n"
			+ "                                             ,get_product_name(application_id),brand_name,email_in_form_vi,email_contact_person \r\n"
			+ "                                             ,email_indian_rep,user_email,str_user_id,str_manufacturing_unit \r\n"
			+ "                                             ,str_manufacure_address,name_of_ind_representative,address_of_ind_rep \r\n"
			+ "                                             ,to_char(add_months(valid_upto,3),'dd/MM/yyyy') as to_char2,email_correspondence \r\n"
			+ "                                             from log_schedular_deferment_lapsed_dtl \r\n"
			+ "                                             where reg_number = ? \r\n"
			+ "                                             and application_id = ? order by tr_dt desc limit 1";

	private String MailToSendQueryLapsed = " select reg_number,application_id,seq_no   \r\n"
			+ "                                 from log_schedular_deferment_lapsed_dtl   \r\n"
			+ "                                 where email_delivered='N'   \r\n"
			+ "                                 and str_target_status='LP' ";

	private String GazettedHoliday = "SELECT dt_holiday \r\n" + "FROM creitg_gazetted_holidays \r\n"
			+ "WHERE isvalid=1 AND to_char(TO_DATE('" + currentDate
			+ "', 'DD/MM/YYYY'),'dd/mm/yyyy') = to_char(dt_holiday,'dd/mm/yyyy')";

	private String defermentQuery = "SELECT crm.application_id,crm.valid_up_to,crm.reg_date, crm.reg_number,crm.str_status FROM creit_registration_master crm WHERE crm.str_status IN ('gr','RD') AND "
			+ " crm.valid_up_to <= :currentDate ORDER BY 2 limit 1";

	private String LogSchedularInsertQuery = "insert into log_schedular_deferment_lapsed_dtl "
			+ "(application_id,reg_number,reg_date,valid_upto,tr_dt,seq_no "
			+ ",str_target_status,str_old_status,mail_send_content,is_no "
			+ ",product_category,brand_name,email_in_form_vi,email_contact_person "
			+ ",email_indian_rep,user_email,str_user_id,str_manufacturing_unit "
			+ ",str_manufacure_address,name_of_ind_representative,address_of_ind_rep, email_delivered,email_correspondence,is_processed ) "
			+ "(select crm.application_id,crm.reg_number,crm.reg_date,crm.valid_up_to,sysdate "
			+ ",(select nvl(max(seq_no),0)+1 from log_schedular_deferment_lapsed_dtl) as seq_no "
			+ ",? as newStatus, ? as oldStatus " + ",'' as mail_content "
			+ ",get_standard_number(cam.standard_id) as isno "
			+ ",get_product_category(cam.product_category) as product_category "
			+ ",get_brand_name(cam.application_id) as brand_name " + ",nvl(cma.m_email,'NA')  as email_in_form_vi "
			+ ",nvl(cam.applicant_emailid,'NA') as email_contact_person "
			+ ",nvl(cam.email_id_of_ind_rep,'NA') as  email_indian_rep " + ",nvl(tur.str_e_mail,'NA') as user_email "
			+ ",tur.str_user_id as str_user_id " + ",get_manf_unit(tur.str_user_id) as str_manufacturing_unit "
			+ ",cma.m_address||', '||upper(get_country_name(cma.m_country)) as str_manufacturing_address "
			+ ",cam.name_of_ind_representative as name_of_ind_representative "
			+ ",cam.address_of_ind_rep as address_of_ind_rep,'N',nvl(cma.o_email,'NA')  as email_correspondence,'N' "
			+ "from creit_registration_master crm, " + "creit_application_master cam, " + "tdc_user_registration tur, "
			+ "creit_manufacturing_address cma " + "where cam.application_id=crm.application_id "
			+ "and tur.str_user_id=cam.str_user_id " + "and cma.man_address_id=tur.man_address_id "
			+ "and cma.company_id=tur.company_id " + "and crm.reg_number = ? " + "and crm.application_id= ?) ";

	String updateQuery = "update creit_registration_master set str_status = ? "
			+ "where application_id= ? and reg_number= ? " + "and str_status= ? ";

	private String InsertLapsingQuery = "insert into creit_registration_history ( "
			+ "application_id,str_version,reg_date,reg_number,reg_cert_flag, "
			+ "str_status,trans_date,trans_user_id,dealing_officer,granting_officer,remarks, "
			+ "agree_form_flag,trans_type_id,num_sequence,surv_initiated, "
			+ "transaction_id,intimation_flag,letter_of_intimation,valid_up_to)  " + "( "
			+ "select application_id,str_version,reg_date,reg_number,reg_cert_flag, "
			+ "str_status,trans_date,trans_user_id,dealing_officer,granting_officer,remarks, "
			+ "agree_form_flag,trans_type_id,num_sequence,surv_initiated,transaction_id, "
			+ "intimation_flag,letter_of_intimation,valid_up_to from creit_registration_master "
			+ "where application_id= ? and reg_number= ? " + "and str_status= ? )";

	private String UpdateLapsingQuery = "SELECT crm.application_id,crm.valid_up_to,crm.reg_date, crm.reg_number,crm.str_status "
			+ "FROM creit_registration_master crm " + "WHERE crm.str_status = 'DF' "
			+ "AND (crm.valid_up_to + interval '90 days') <= (current_timestamp - interval '1 day') "
			+ "AND crm.application_id NOT IN " + "(SELECT renewal.application_id FROM creit_renewal_master renewal "
			+ "WHERE renewal.date_of_request >= (crm.valid_up_to - interval '3 months') and renewal.application_id is not null) "
			+ "UNION "
			+ "SELECT crm.application_id,crm.valid_up_to,crm.reg_date, crm.reg_number,crm.str_status FROM creit_renewal_master rm, bis_payment_details bpd, creit_registration_master crm "
			+ "WHERE crm.reg_number = rm.reg_number " + "AND rm.renewal_id = bpd.process_id "
			+ "AND bpd.status_code not in ('0', '300','0300') AND bpd.process_type='R' " + "AND rm.str_status = 'PP' "
			+ "AND crm.str_status = 'DF' " + "AND rm.date_of_request >= (crm.valid_up_to - interval '3 months') "
			+ "AND (crm.valid_up_to + interval '90 days') <= (current_timestamp - interval '1 day') " + "UNION "
			+ "SELECT crm.application_id,crm.valid_up_to,crm.reg_date, crm.reg_number,crm.str_status FROM creit_renewal_master rm, creit_registration_master crm "
			+ "WHERE crm.reg_number = rm.reg_number " + "AND rm.str_status = 'PP' " + "AND crm.str_status = 'DF' "
			+ "AND rm.renewal_id NOT IN (SELECT process_id FROM bis_payment_details WHERE process_type = 'R') "
			+ "AND rm.date_of_request >= (crm.valid_up_to - interval '3 months') "
			+ "AND (crm.valid_up_to + interval '90 days') <= (current_timestamp - interval '1 day') " + "UNION "
			+ "SELECT crm.application_id,crm.valid_up_to,crm.reg_date, crm.reg_number,crm.str_status "
			+ "FROM creit_renewal_master rm, bis_payment_details bpd, creit_registration_master crm "
			+ "WHERE crm.reg_number = rm.reg_number " + "AND rm.renewal_id = bpd.process_id "
			+ "AND bpd.status_code = '0' AND bpd.process_type='R' " + "AND rm.str_status = 'PP' "
			+ "AND crm.str_status = 'DF' "
			+ "AND bpd.process_type||bpd.process_id NOT IN (SELECT process_type||process_id FROM bis_payment_details WHERE status_code = '300') "
			+ "AND rm.date_of_request >= (crm.valid_up_to - interval '3 months') "
			+ "AND (crm.valid_up_to + interval '90 days') <= (current_timestamp - interval '1 day') limit 1";

	public String getListToAddMailToSendQueryLapsed(String regNumber, String applicationId) {
		return ListToAddMailToSendQueryLapsed.replace("?1", regNumber).replace("?2", applicationId);
	}

	public String getInsertLapsingQuery(String applicationId, String regNumber, String strStatus) {
		return InsertLapsingQuery.replace("?1", applicationId).replace("?2", regNumber).replace("?3", strStatus);
	}

	public String getUpdateQuery(String app_id, String reg_number, String new_status, String str_status) {
		return updateQuery.replace("?1", new_status).replace("?2", app_id).replace("?3", reg_number).replace("?4",
				str_status);
	}

	public String getLogSchedularInsertQuery(String app_id, String reg_number, String new_status, String str_status) {
		return LogSchedularInsertQuery.replace("?1", new_status).replace("?2", str_status).replace("?3", reg_number)
				.replace("?4", app_id);
	}

	public String getMailContent() {
		return MailContent;
	}
	
	public String getUpdateLapsingQuery() {
		return UpdateLapsingQuery;
	}

	public void setUpdateLapsingQuery(String updateLapsingQuery) {
		UpdateLapsingQuery = updateLapsingQuery;
	}

	public void setInsertLapsingQuery(String insertLapsingQuery) {
		InsertLapsingQuery = insertLapsingQuery;
	}

	public void setUpdateQuery(String updateQuery) {
		this.updateQuery = updateQuery;
	}

	public void setLogSchedularInsertQuery(String logSchedularInsertQuery) {
		LogSchedularInsertQuery = logSchedularInsertQuery;
	}

	public String getDefermentQuery() {
		return defermentQuery;
	}

	public void setDefermentQuery(String defermentQuery) {
		this.defermentQuery = defermentQuery;
	}

	public String getLogSchedularInsertQuery() {
		return LogSchedularInsertQuery;
	}

	public String getUpdateQuery() {
		return updateQuery;
	}

	public String getInsertLapsingQuery() {
		return InsertLapsingQuery;
	}

	public String getGazettedHoliday() {
		return GazettedHoliday;
	}

	public void setGazettedHoliday(String gazettedHoliday) {
		GazettedHoliday = gazettedHoliday;
	}

	public SimpleDateFormat getFormatter() {
		return formatter;
	}

	public void setFormatter(SimpleDateFormat formatter) {
		this.formatter = formatter;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getMailToSendQueryLapsed() {
		return MailToSendQueryLapsed;
	}

	public void setMailToSendQueryLapsed(String mailToSendQueryLapsed) {
		MailToSendQueryLapsed = mailToSendQueryLapsed;
	}

	public void setListToAddMailToSendQueryLapsed(String listToAddMailToSendQueryLapsed) {
		ListToAddMailToSendQueryLapsed = listToAddMailToSendQueryLapsed;
	}


	public void setMailContent(String mailContent) {
		MailContent = mailContent;
	}

}
