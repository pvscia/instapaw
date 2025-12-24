package com.example.instapaws.complaint.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.instapaws.complaint.service.ComplaintService;
import com.example.instapaws.model.Complaint;
import com.example.instapaws.model.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    // CREATE
    @PostMapping
    public ResponseEntity<Complaint> create(
            @AuthenticationPrincipal User user,
            @RequestBody Complaint req
    ) {
        return ResponseEntity.ok(
                complaintService.createComplaint(user, req)
        );
    }

    // GET MY COMPLAINTS
    @GetMapping("/my")
    public ResponseEntity<List<Complaint>> myComplaints(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
                complaintService.getUserComplaints(user.getId())
        );
    }

    // GET ALL (optional: admin only later)
    @GetMapping("/all")
    public ResponseEntity<List<Complaint>> allComplaints() {
        return ResponseEntity.ok(
                complaintService.getAllComplaints()
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Complaint> update(
            @PathVariable BigInteger id,
            @AuthenticationPrincipal User user,
            @RequestBody Complaint req
    ) {
        return ResponseEntity.ok(
                complaintService.updateComplaint(id, user, req)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable BigInteger id,
            @AuthenticationPrincipal User user
    ) {
        complaintService.deleteComplaint(id, user);
        return ResponseEntity.noContent().build();
    }
}
