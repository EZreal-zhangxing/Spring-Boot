package com.zx.WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.mysql.jdbc.TimeUtil;
import com.zx.Redis.redisClass;

public class webSocketClass extends TextWebSocketHandler{
	private redisClass redisclass;
	public webSocketClass(redisClass redisclass){
		this.redisclass=redisclass;	
	}
	Logger log=Logger.getLogger(webSocketClass.class);
	public static AtomicInteger Count=new AtomicInteger(0);
	public static ConcurrentHashMap<String, WebSocketSession> ConMap=new ConcurrentHashMap<String, WebSocketSession>();
//	public static ScheduledThreadPoolExecutor stp=new ScheduledThreadPoolExecutor(10);//最多7个线程
	public static ThreadPoolExecutor tpe=new ThreadPoolExecutor(10,500,60L,TimeUnit.SECONDS,new LinkedBlockingDeque());
	public static List<String> chatList=new ArrayList<String>();
	private String channelName="chat";
	//触发连接之后的回掉
	@Override
	public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
		//一个聊天室启动一个监听线程 然后做转发
		// TODO Auto-generated method stub
		if(!ConMap.containsKey(session.getId())){
			ConMap.put(session.getId(), session);
			Count.getAndIncrement();
			log.info("连入新用户 id:["+session.getId()+"] + 总用户数 :["+Count.get()+"]");
			sendMessage(session, "{user:'"+session.getId()+"',type:1,message:'初始化'}");
			//用户注册
			redisclass.lSet(channelName, session.getId());
			if(!chatList.contains(channelName)){
				Thread t=new threadListener(session, channelName, redisclass,ConMap);
				t.setName("chat["+session.getId()+"]");
				tpe.getThreadFactory().newThread(t).start();
				chatList.add(channelName);
			}
			//直接subscribe会导致线程阻塞 会导致websocket接受不到消息
//			redisclass.subscribe(channelName, session);
			super.afterConnectionEstablished(session);
		}
		System.out.println(tpe.getQueue());
	}
	/**
    * 收到消息时触发的回调
    */
	@Override
	public void handleMessage(WebSocketSession session,WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		log.info("收到消息 message:["+message.getPayload().toString()+"]");
		//将消息转发出去
		//通过ConcurrentHashmap集合将信息发送出去 
//		Collection<String> col=ConMap.keySet();
//		Iterator<String> it=col.iterator();
//		while(it.hasNext()){
//			String key=it.next();
//			WebSocketSession usersession=ConMap.get(key);
//			usersession.sendMessage(message);
//		}
		//从redis推送消息出去
		redisclass.publish(channelName, message.getPayload().toString());
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
	public void afterConnectionClosed(WebSocketSession session,CloseStatus status) throws Exception {
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
class threadListener extends Thread{
	private redisClass redisclass;
	private WebSocketSession session;
	private String channelName;
	public ConcurrentHashMap<String, WebSocketSession> ConMap;
	public threadListener(WebSocketSession session,String channelName,redisClass redisclass, ConcurrentHashMap<String, WebSocketSession> ConMap){
		this.session=session;
		this.channelName=channelName;
		this.redisclass=redisclass;
		this.ConMap=ConMap;
	}
	public void run() {
		System.out.println(Thread.currentThread().getName() + ",session is:["+session.getId()+"]  启动监听");
		// TODO Auto-generated method stub
		try {
			redisclass.subscribe(channelName, session,ConMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
