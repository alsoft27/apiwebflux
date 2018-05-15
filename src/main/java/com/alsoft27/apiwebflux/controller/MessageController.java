package com.alsoft27.apiwebflux.controller;

import java.time.Duration;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.alsoft27.apiwebflux.document.Message;
import com.alsoft27.apiwebflux.repository.MessageRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class MessageController {

	@Autowired
	private MessageRepository messageRepository;

	@GetMapping("/messages")
	public Flux<Message> getAllMessages() {
		return messageRepository.findAll();
	}

	@PostMapping("/messages")
	public Mono<Message> createMessages(@Valid @RequestBody Message message) {
		return messageRepository.save(message);
	}

	@ResponseBody
	@GetMapping(value = "/stream/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Message> streamAllMessages() {
		return messageRepository.findAll();
	}

	@GetMapping("/messages2")
	public String listMessages(Model model) {
		Flux<Message> flux = messageRepository.findAll().delayElements(Duration.ofSeconds(1));
		model.addAttribute("messages", new ReactiveDataDriverContextVariable(flux, 1));
		return "messages2";
	}

	@GetMapping("/")
	Mono<String> home() {
		return Mono.just("messages");
	}
}
