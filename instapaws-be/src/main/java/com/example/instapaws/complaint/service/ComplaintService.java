package com.example.instapaws.complaint.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.instapaws.model.AuthResult;
import com.example.instapaws.model.Complaint;
import com.example.instapaws.model.User;
import com.example.instapaws.repository.ComplaintRepository;
import com.example.instapaws.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public Complaint createComplaint(User user, Complaint req) {
        Complaint c = new Complaint();
        c.setComplaint(req.getComplaint());
        c.setUser(user);
        return complaintRepository.save(c);
    }

    public List<Complaint> getUserComplaints(BigInteger userId) {
        return complaintRepository.findByUserId(userId);
    }
    
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint updateComplaint(BigInteger id, User user, Complaint req) {
        Complaint c = complaintRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"));

        if (!c.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Forbidden");
        }

        c.setComplaint(req.getComplaint());
        c.setModifiedAt(LocalDateTime.now());
        return complaintRepository.save(c);
    }
    
    public void deleteComplaint(BigInteger id, User user) {
        Complaint c = complaintRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"));

        if (!c.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Forbidden");
        }

        complaintRepository.delete(c);
    }
}


