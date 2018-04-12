package com.zx.WebSocket;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class webSocketClass extends TextWebSocketHandler{
	Logger log=Logger.getLogger(webSocketClass.class);
	public static AtomicInteger Count=new AtomicInteger(0);
	public static ConcurrentHashMap<String, WebSocketSession> ConMap=new ConcurrentHashMap<String, WebSocketSession>();
	//触发连接之后的回掉
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		// TODO Auto-generated method stub
		if(!ConMap.containsKey(session.getId())){
			ConMap.put(session.getId(), session);
			Count.getAndIncrement();
			log.info("连入新用户 id:["+session.getId()+"] + 总用户数 :["+Count.get()+"]");
			sendMessage(session, "{user:"+session.getId()+",type:1,message:'初始化'}");
			super.afterConnectionEstablished(session);
		}
	}
	/**
    * 收到消息时触发的回调
    */
	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		log.info("收到消息 message:["+message.getPayload().toString()+"]");
		//将消息转发出去
		Collection<String> col=ConMap.keySet();
		Iterator<String> it=col.iterator();
		while(it.hasNext()){
			String key=it.next();
			WebSocketSession usersession=ConMap.get(key);
			usersession.sendMessage(message);
		}
		super.handleMessage(session, message);
	}

   /**
    * 传输消息出错时触发的回调
    */
	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		super.handleTransportError(session, exception);
	}
   /**
    * 断开连接后触发的回调
    */
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		ConMap.remove(session.getId());
		Count.getAndDecrement();
		log.info("用户登出 id:["+session.getId()+"]");
		super.afterConnectionClosed(session, status);
	}
	
	public void sendMessage(WebSocketSession session,String message) throws IOException{
		session.sendMessage(new TextMessage(message.getBytes()));
	}
}
