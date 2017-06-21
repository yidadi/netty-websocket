package com.even.websocket.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.even.websocket.annation.Log;
import com.even.websocket.constant.WebSocketConstant;
import com.even.websocket.msg.MessageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MessageEventHandler
{
    @Log
    private Logger log;
    @Autowired
    private SocketIOServer server;

    @OnConnect
    public void onConnect(SocketIOClient client)
    {
        log.info("client connect to the server ,sessionId={}",client.getSessionId().toString());
       // client.joinRoom(client.getHandshakeData().getSingleUrlParam("roomId"));
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client)
    {
        log.info("client disconnect to the server ,sessionId={}",client.getSessionId().toString());
       // client.leaveRoom(client.getHandshakeData().getSingleUrlParam("roomId"));
    }

    //消息接收入口，当接收到消息后，查找发送目标客户端
    @OnEvent(value = "teacherEvent")
    public void onTeacherEvnet(SocketIOClient client, AckRequest request, MessageInfo data)
    {
        Objects.requireNonNull(data);
//        String teacherId = client.getHandshakeData().getSingleUrlParam("teacherId");
//        if(StringUtils.isEmpty(teacherId)){
//            return;
//        }
        server.getRoomOperations(data.getRoomId()).sendEvent(data.getEventType(),data);
    }

    @OnEvent(value = "studentEvent")
    public void onStudentEvent(SocketIOClient client, AckRequest request, MessageInfo data)
    {
        Objects.requireNonNull(data);
        server.getNamespace(WebSocketConstant.Namespace).getRoomOperations(data.getRoomId()).sendEvent(data.getEventType(),data);
    }

    @OnEvent(value = "studentJoinRoom")
    public void studentConectEvent(SocketIOClient client, AckRequest request, MessageInfo data)
    {
        Objects.requireNonNull(data);
        client.joinRoom(data.getRoomId());
        log.info("student join the room,roomId={},studentNickName={}",data.getRoomId(),data.getStudentNickName());
        server.getRoomOperations(data.getRoomId()).sendEvent(data.getEventType(),data);
    }

    @OnEvent(value = "teacherJoinRoom")
    public void teacherJoinRoom(SocketIOClient client, AckRequest request, MessageInfo data)
    {
        Objects.requireNonNull(data);
        client.joinRoom(data.getRoomId());
        log.info("teacher join the room,roomId={},teacherNickName={}",data.getRoomId(),data.getTeacherNickName());
        server.getRoomOperations(data.getRoomId()).sendEvent(data.getEventType(),data);
    }
}
