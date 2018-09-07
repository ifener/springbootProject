package com.wey.springboot.controller;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint("/websocket/{username}")
@Component // 默认是单例模式，这里为什么会是多例还要找下原因
public class WebSocketServer {
    
    private static volatile int onlineCount = 0;
    
    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    private String username;
    
    // 连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        webSocketSet.add(this);
        System.out.println("有人上线" + username + ",当前在线人数为" + getOnlineCount());
        this.username = username;
        for (WebSocketServer item : webSocketSet) {
            try {
                
                item.sendMessage(username + "上线了。");
            }
            catch (IOException e) {
                continue;
            }
        }
        
    }
    
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        subOnlineCount(); // 在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
    
    /**
     * 收到客户端消息后调用的方法`
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到来自窗口" + username + "的信息:" + message);
        // 群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
    
    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message, @PathParam("username") String username) throws IOException {
        System.out.println("推送消息到窗口" + username + "，推送内容:" + message);
        for (WebSocketServer item : webSocketSet) {
            
            System.out.println(item);
            try {
                // 这里可以设定只推送给这个sid的，为null则全部推送
                if (username == null) {
                    item.sendMessage(message);
                }
                else if (item.username.equals(username)) {
                    item.sendMessage(message);
                }
                
            }
            catch (IOException e) {
                continue;
            }
        }
    }
    
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
    
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
    
}
