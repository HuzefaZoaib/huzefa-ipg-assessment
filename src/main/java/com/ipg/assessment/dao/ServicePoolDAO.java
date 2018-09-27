package com.ipg.assessment.dao;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ipg.assessment.exception.OptimizationServiceUnavailableException;
import com.ipg.assessment.util.DBUtils;
import com.ipg.assessment.util.IPGConstants;

@Transactional
@Repository
public class ServicePoolDAO {

	@Autowired
	private DBUtils dbSession;

	public String updateServiceInUse() {
		
		System.out.println(MessageFormat.format(IPGConstants.METHOD_INVOKED_LOG_MSG, new Object[]{this.getClass().getSimpleName(), "updateServiceInUse"}));
		
		/*
		 * Get the pessimistic lock on the record 
		 */
		String serviceId = "";
		Session session = dbSession.getSession();
		
		Query lockQuery = session.createSQLQuery("select id from service_pool where available_flag = 'Y' limit 1 for update");
		
		List<String> ids = lockQuery.list();
		if(ids.size() == 0) {
			throw new OptimizationServiceUnavailableException();
		} else {
			serviceId = String.valueOf(ids.get(0));
		}
		
		/*
		 * Update the record during the Lock Session
		 */
		Query query = session.createSQLQuery("update service_pool set available_flag = 'N' where id = '" +serviceId +"'");
		int updatedRecords = query.executeUpdate();
		System.out.println(MessageFormat.format("  updateServiceInUse: Record ID:{0} Updated:{1}", new Object[]{serviceId, updatedRecords}));
		
		return serviceId;
	}
	
	public void releaseService(String id) {
		
		System.out.println(MessageFormat.format(IPGConstants.METHOD_INVOKED_LOG_MSG, new Object[]{this.getClass().getSimpleName(), "releaseService(id:"+id+")"}));
		
		Session session = dbSession.getSession();
		Query query = session.createSQLQuery("update service_pool set available_flag = 'Y' where id = '" +id +"'");
		
		int updatedRecords = query.executeUpdate();
		System.out.println(MessageFormat.format("  releaseService: Record ID:{0} Updated:{1}", new Object[]{id, updatedRecords}));
	}

}
