package com.dmba.configuration;

import com.dmba.repository.UsersOrderRepository;
import com.dmba.repository.wrapper.UsersOrderRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public UsersOrderRepositoryWrapper getUsersOrderRepositoryWrapper(@Autowired UsersOrderRepository usersOrderRepository) {
        return new UsersOrderRepositoryWrapper(usersOrderRepository);
    }
}
