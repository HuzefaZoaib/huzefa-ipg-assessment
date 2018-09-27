package com.ipg.assessment.processor;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipg.assessment.dao.ServicePoolDAO;
import com.ipg.assessment.exception.OptimizationServiceUnavailableException;

@Service
public class IPGProcessor {

	@Autowired
	private ServicePoolDAO servicePoolDao;
	
	public String optimize() {
		
		String id = fetchServiceId();
		
		try {
			try {
				Thread.sleep(10000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Call the services of OptimizationSolver here
			// OptimizationSolver mySolver = new OptimizationSolver();
			// String result = mySolver.solve("");
			
		}finally {
			// In any case of Error / Exception, it will release the Serivce
			servicePoolDao.releaseService(id);
		}
		
		return id;
	}


	public String fetchServiceId() {
		
		String id = "";
		long waitingPeriod = 100l;
		while (true) {			
			try {
				id = servicePoolDao.updateServiceInUse();
				break;
			}catch (OptimizationServiceUnavailableException oex) {
				try {
					// Try again after 100 millisecond
					System.out.println(MessageFormat.format("  getId(): Optimization service not available, wait for {0}ms", new Object[]{waitingPeriod}));
					Thread.sleep(waitingPeriod);
				}catch (InterruptedException e) {
					System.out.println(MessageFormat.format("  getId(): InterruptedException while waiting for the next attempt to get service", new Object[]{}));
				}
			}
		}
		
		return id;
	}

}
