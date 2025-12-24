package com.example.instapaws.repository;

import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.instapaws.model.Complaint;


public interface ComplaintRepository extends JpaRepository<Complaint, BigInteger>,JpaSpecificationExecutor<Complaint>{
    List<Complaint> findByUserId(BigInteger userId);

}
