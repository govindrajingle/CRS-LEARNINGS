package in.cdacnoida.dava.transactions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.cdacnoida.dava.util.BOOTSqlQueries;

@Repository
@Transactional
public class BOOTCrsTransactions {

	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Object[]> DefermentQueryData() {
		BOOTSqlQueries queries = new BOOTSqlQueries();
		String query = queries.getDefermentQuery();
		Query q = em.createNativeQuery(query);
		q.setParameter("currentDate", new java.sql.Timestamp(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
		// q.executeUpdate();
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getQueryData() {
		BOOTSqlQueries queries = new BOOTSqlQueries();
		Query q = em.createNativeQuery(queries.getUpdateLapsingQuery());
		return q.getResultList();
	}

	public void updateQueryData(String applicationId, String regNumber, String strStatus) {
		BOOTSqlQueries queries = new BOOTSqlQueries();
		String query = queries.getInsertLapsingQuery(applicationId, regNumber, strStatus);
		Query q = em.createNativeQuery(query);
		q.setParameter(1, applicationId);
		q.setParameter(2, regNumber);
		q.setParameter(3, strStatus);
		q.executeUpdate();
	}

	public void updateQueryNewStatus(String app_id, String reg_number, String new_status, String str_status) {
		BOOTSqlQueries queries = new BOOTSqlQueries();
		String query = queries.getUpdateQuery(app_id, reg_number, new_status, str_status);
		Query q = em.createNativeQuery(query);
		q.setParameter(1, new_status);
		q.setParameter(2, app_id);
		q.setParameter(3, reg_number);
		q.setParameter(4, str_status);
		q.executeUpdate();
	}

	public void LogSchedularInsertQueryInsert(String app_id, String reg_number, String new_status, String str_status) {
		BOOTSqlQueries queries = new BOOTSqlQueries();
		String query = queries.getLogSchedularInsertQuery(app_id, reg_number, new_status, str_status);
		Query q = em.createNativeQuery(query);
		q.setParameter(1, new_status);
		q.setParameter(2, str_status);
		q.setParameter(3, reg_number);
		q.setParameter(4, app_id);
		q.executeUpdate();
	}

}
