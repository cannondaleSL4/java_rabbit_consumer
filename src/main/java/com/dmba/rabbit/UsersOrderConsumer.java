package com.dmba.rabbit;

import com.dmba.dao.UsersOrder;
import com.dmba.dao.proto.UsersOrderProto;

import com.dmba.storage.Storage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UsersOrderConsumer {

    @Autowired
    private Storage storage;

    @Autowired
    private TaskExecutor taskExecutor;

    @Value("${spring.rabbitmq.name}")
    private String queue;

    @RabbitListener(queues = "${spring.rabbitmq.name}",  concurrency = "3")
    public void onMessage(Message message) {
        taskExecutor.execute(() -> {
            UsersOrderProto.UsersOrder usersOrder = convertMessageToMyData(message);
            UsersOrder usersOrderEntity = new UsersOrder(usersOrder);
            // Save the data in the database
            storage.saveMessage(usersOrderEntity);
        });
    }

//    @RabbitListener(queues = "${spring.rabbitmq.name}")
//    public void onMessage(Message message) {
//        UsersOrderProto.UsersOrder usersOrder = convertMessageToMyData(message);
//        UsersOrder usersOrderEntity = new UsersOrder(usersOrder);
//        // Save the data in the database
//        storage.saveMessage(usersOrderEntity);
//    }

    @SneakyThrows
    private UsersOrderProto.UsersOrder convertMessageToMyData(Message message) {
        log.debug("Stored data for queue: {}", queue);
        return UsersOrderProto.UsersOrder.parseFrom(message.getBody());
    }
}