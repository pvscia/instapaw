package com.example.instapaws.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.instapaws.complaint.vo.VoComplaint;
import com.example.instapaws.model.Complaint;


public interface ComplaintRepository extends JpaRepository<Complaint, Long>,JpaSpecificationExecutor<Complaint>{
	@Query("""
		    SELECT 
		        c.id,
		        c.complaint,
		        u.username
		    FROM Complaint c
		    JOIN c.user u
		    WHERE c.userId = :userId
		""")
		List<VoComplaint> findByUserId(@Param("userId") Long userId);

	
	@Query("""
		    SELECT 
		        c.id,
		        c.complaint,
		        u.username
		    FROM Complaint c
		    JOIN c.user u
		""")
		List<VoComplaint> getAll();

}
