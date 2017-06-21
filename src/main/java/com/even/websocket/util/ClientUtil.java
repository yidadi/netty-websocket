//package com.haixue.websocket.util;
//
//import com.alibaba.fastjson.JSON;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.transport.NamespaceClient;
//import com.haixue.websocket.msg.ClientInfo;
//
///**
// * Created by yidadi on 17-5-25.
// */
//public class ClientUtil {
//    public static String getClientInfo(SocketIOClient client,String studentId){
//        NamespaceClient data = (NamespaceClient)client.getNamespace().getClient(client.getSessionId());
//        ClientInfo clientInfo = new ClientInfo();
//        clientInfo.setStudentId(studentId);
//        clientInfo.setNamespaceClient(data);
//        return JSON.toJSONString(clientInfo);
//    }
//}
