package com.zx.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.zx.Redis.redisClass;

@Configuration
@EnableWebSocket
public class webSocketConfigureClass implements WebSocketConfigurer {
	@Autowired
	private redisClass redisclass;
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// TODO Auto-generated method stub
		System.out.println(this.getClass().getName() + " redisclass is "+ redisclass);
		registry.addHandler(new webSocketClass(redisclass), "/websocket");
	}
}
