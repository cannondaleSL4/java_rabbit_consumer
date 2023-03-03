package com.dmba.repository;

import com.dmba.dao.UsersOrder;
import com.dmba.report.CustomerBySum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsersOrderRepository extends JpaRepository<UsersOrder, UUID> {

    @Query(value = "SELECT ut.user_name, SUM(uo.account_total) as overall_sum\n" +
            "FROM User_Table ut\n" +
            "JOIN Users_Order uo ON ut.id = uo.user_id\n" +
            "GROUP BY ut.user_name\n" +
            "ORDER BY overall_sum DESC\n" +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10UsersByOverallSum();
}