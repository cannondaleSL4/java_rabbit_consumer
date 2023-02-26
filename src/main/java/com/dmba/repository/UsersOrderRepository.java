package com.dmba.repository;

import com.dmba.dao.UsersOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersOrderRepository extends JpaRepository<UsersOrderEntity, UUID> {

}