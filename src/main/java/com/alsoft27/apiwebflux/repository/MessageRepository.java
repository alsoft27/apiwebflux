package com.alsoft27.apiwebflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.alsoft27.apiwebflux.document.Message;

public interface MessageRepository extends ReactiveMongoRepository<Message, String> {

}
