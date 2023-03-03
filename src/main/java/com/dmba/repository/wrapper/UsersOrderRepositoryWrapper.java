package com.dmba.repository.wrapper;

import com.dmba.report.CustomerBySum;
import com.dmba.repository.UsersOrderRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UsersOrderRepositoryWrapper {

    private UsersOrderRepository usersOrderRepository;

    public List<CustomerBySum> findTop10UsersByOverallSum() {
        List<CustomerBySum> result = new ArrayList<>();
        List<Object[]> listOfObject = usersOrderRepository.findTop10UsersByOverallSum();

        for (Object[] obj : listOfObject) {
            CustomerBySum customerBySum = new CustomerBySum((String) obj[0], (BigDecimal) obj[1]);
            result.add(customerBySum);
        }

        return result;
    }
}
