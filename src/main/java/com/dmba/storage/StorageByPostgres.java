package com.dmba.storage;

import com.dmba.dao.UsersOrder;
import com.dmba.repository.UsersOrderRepository;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StorageByPostgres implements Storage {

    @Value("${spring.rabbitmq.batch.size}")
    public Integer batchSize;

    @Autowired
    private UsersOrderRepository usersOrderRepository;

    private Set<UsersOrder> messageSet = ConcurrentHashMap.newKeySet();

    @Override
    public void saveMessage(UsersOrder usersOrderEntity) {
        messageSet.add(usersOrderEntity);
        if (messageSet.size() >= batchSize) {
            saveMessages();
        }
    }

    @Transactional
    @Retryable(value = { LockAcquisitionException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void saveMessages() {
        if (!messageSet.isEmpty()) {
            usersOrderRepository.saveAll(messageSet);
            messageSet.clear();
        }
    }
}
