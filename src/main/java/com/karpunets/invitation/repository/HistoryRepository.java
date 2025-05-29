package com.karpunets.invitation.repository;

import com.karpunets.invitation.model.History;
import com.karpunets.invitation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

}