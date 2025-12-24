package com.example.instapaws.complaint.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.instapaws.model.Complaint;
import com.example.instapaws.model.User;
import com.example.instapaws.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public Complaint createComplaint(User user, Complaint req) {
        Complaint c = new Complaint();
        c.setComplaint(req.getComplaint());
        c.setUserId(user.getId());
        return complaintRepository.save(c);
    }

    public List<Complaint> getUserComplaints(Long userId) {
        return complaintRepository.findByUserId(userId);
    }
    
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint updateComplaint(Long id, User user, Complaint req) {
        Complaint c = complaintRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"));

        if (!c.getUserId().equals(user.getId())) {
            throw new RuntimeException("Forbidden");
        }

        c.setComplaint(req.getComplaint());
        c.setModifiedAt(LocalDateTime.now());
        return complaintRepository.save(c);
    }
    
    public void deleteComplaint(Long id, User user) {
        Complaint c = complaintRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"));

        if (!c.getUserId().equals(user.getId())) {
            throw new RuntimeException("Forbidden");
        }

        complaintRepository.delete(c);
    }
}


