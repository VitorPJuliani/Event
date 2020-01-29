package com.example.eventproject.configuration;

import com.example.eventproject.repository.JdbcProducerRepository;
import com.example.eventproject.repository.ProducerRepository;
import com.example.eventproject.service.ProducerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfiguration {

    @Bean
    public ProducerRepository jdbcProducerRepository(JdbcTemplate jdbcTemplate){
        return new JdbcProducerRepository(jdbcTemplate);
    }

    @Bean
    public ProducerService producerService(ProducerRepository producerRepository){
        return new ProducerService(producerRepository);
    }
}
