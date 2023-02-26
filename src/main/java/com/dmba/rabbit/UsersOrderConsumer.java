package com.dmba.rabbit;

import com.dmba.dao.UsersOrderEntity;
import com.dmba.dao.proto.UsersOrderProto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.dmba.dao.UsersOrder;
import com.dmba.repository.UsersOrderRepository;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

//import static sun.security.util.KnownOIDs.Data;

@Component
@Slf4j
public class UsersOrderConsumer {

    @Autowired
    private UsersOrderRepository repository;

    @Value("${spring.rabbitmq.name}")
    private String queue;

    @RabbitListener(queues = "${spring.rabbitmq.name}")
    public void onMessage(Message message) {
        UsersOrderProto.UsersOrder usersOrder = convertMessageToMyData(message);
        UsersOrderEntity usersOrderEntity = new UsersOrderEntity(usersOrder);
        // Save the data in the database
        repository.save(usersOrderEntity);
    }

    @SneakyThrows
    private UsersOrderProto.UsersOrder convertMessageToMyData(Message message) {
        log.info("Stored data for queue: {}", queue);
        return UsersOrderProto.UsersOrder.parseFrom(message.getBody());
    }
}