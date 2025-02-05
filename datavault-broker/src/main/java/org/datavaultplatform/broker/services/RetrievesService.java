package org.datavaultplatform.broker.services;

import org.datavaultplatform.common.model.Deposit;
import org.datavaultplatform.common.model.Retrieve;
import org.datavaultplatform.common.model.dao.RetrieveDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetrievesService {

    private final RetrieveDAO retrieveDAO;

    @Autowired
    public RetrievesService(RetrieveDAO retrieveDAO) {
        this.retrieveDAO = retrieveDAO;
    }

    public List<Retrieve> getRetrieves(String userId) {
        return retrieveDAO.list(userId);
    }
    
    public void addRetrieve(Retrieve retrieve, Deposit deposit, String retrievePath) {
        
        Date d = new Date();
        retrieve.setTimestamp(d);
        
        retrieve.setDeposit(deposit);
        retrieve.setStatus(Retrieve.Status.NOT_STARTED);
        
        retrieve.setRetrievePath(retrievePath);
        
        retrieveDAO.save(retrieve);
    }
    
    public void updateRetrieve(Retrieve retrieve) {
        retrieveDAO.update(retrieve);
    }
    
    public Retrieve getRetrieve(String retrieveID) {
        return retrieveDAO.findById(retrieveID).orElse(null);
    }

    public int count(String userId) { return retrieveDAO.count(userId); }

    public int queueCount(String userId) { return retrieveDAO.queueCount(userId); }

    public int inProgressCount(String userId) { return retrieveDAO.inProgressCount(userId); }

    public List<Retrieve>inProgress() { return retrieveDAO.inProgress(); }
}

