package com.example.instapaws.complaint.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Complaint> create(
            @AuthenticationPrincipal User user,
            @RequestBody Complaint req
    ) {
        return ResponseEntity.ok(
                complaintService.createComplaint(user, req)
        );
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Complaint>> myComplaints(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
                complaintService.getUserComplaints(user.getId())
        );
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Complaint>> allComplaints() {
        return ResponseEntity.ok(
                complaintService.getAllComplaints()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Complaint> update(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestBody Complaint req
    ) {
        return ResponseEntity.ok(
                complaintService.updateComplaint(id, user, req)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        complaintService.deleteComplaint(id, user);
        return ResponseEntity.noContent().build();
    }
}
