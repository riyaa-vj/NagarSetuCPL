package com.ecosphere.service.impl;

import com.ecosphere.dto.*;
import com.ecosphere.entity.Complaint;
import com.ecosphere.entity.ComplaintStatus;
import com.ecosphere.repository.ComplaintRepository;
import com.ecosphere.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.ecosphere.dto.CategoryStatsResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintServiceImpl complaintService;

    @Override
    public AdminStatsResponse getOverviewStats() {
        LocalDateTime oneMonthAgo =
                LocalDateTime.now().minusMonths(1);

        return AdminStatsResponse.builder()
                .totalComplaints(
                        complaintRepository.countByCreatedAtAfter(oneMonthAgo)
                )
                .pending(
                        complaintRepository
                                .countByStatusAndCreatedAtAfter(
                                        ComplaintStatus.PENDING,
                                        oneMonthAgo
                                )
                )
                .inProgress(
                        complaintRepository
                                .countByStatusAndCreatedAtAfter(
                                        ComplaintStatus.IN_PROGRESS,
                                        oneMonthAgo
                                )
                )
                .resolved(
                        complaintRepository
                                .countByStatusAndCreatedAtAfter(
                                        ComplaintStatus.RESOLVED,
                                        oneMonthAgo
                                )
                )
                .rejected(
                        complaintRepository
                                .countByStatusAndCreatedAtAfter(
                                        ComplaintStatus.REJECTED,
                                        oneMonthAgo
                                )
                )
                .build();
    }

    @Override
    public ComplaintResponse forceUpdateStatus(Long id, ComplaintStatus status) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setStatus(status);

        Complaint saved = complaintRepository.save(complaint);

        return complaintService.convertToResponse(saved);
    }

    @Override
    public void deleteComplaint(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaintRepository.delete(complaint);
    }

    @Override
    public List<CategoryStatsResponse> getCategoryStats() {

        return complaintRepository.findAll()
                .stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Complaint::getCategory,
                        java.util.stream.Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(e -> CategoryStatsResponse.builder()
                        .category(e.getKey())
                        .count(e.getValue())
                        .build())
                .toList();
    }

    @Override
    public List<ComplaintResponse> getTopComplaints(int limit) {

        return complaintRepository.findAll()
                .stream()
                //.sorted((a, b) -> b.getPriorityScore() - a.getPriorityScore())
                .sorted(
                        (a,b) ->
                                b.getUpvoteCount()
                                        -
                                        a.getUpvoteCount()
                )
                .limit(limit)
                .map(complaintService::convertToResponse)
                .toList();
    }

    @Override
    public List<ComplaintResponse> getRecentComplaints(int limit) {

        return complaintRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(limit)
                .map(complaintService::convertToResponse)
                .toList();
    }
}