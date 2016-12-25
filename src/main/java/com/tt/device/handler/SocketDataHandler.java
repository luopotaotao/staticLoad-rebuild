package com.tt.device.handler;

import com.tt.device.DataHandler;
import com.tt.device.DataParser;
import com.tt.device.Validator;
import com.tt.device.model.SourceData;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;


public class SocketDataHandler extends IoHandlerAdapter {
    private static final Logger logger = Logger.getLogger(SocketDataHandler.class);

    @Autowired
    private DataHandler dataHandler;
    @Autowired
    private Validator dataValidator;
    @Autowired
    private Validator equipValidator;
    @Autowired
    private DataParser dataParser;

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.error("内部错误\n");
        logger.error(cause.getStackTrace());
    }

    @Override
    public void messageReceived(IoSession session, Object message) {

        String str = message.toString().trim();
        if (str != null && !str.isEmpty()) {
            try {
                SourceData data = dataParser.parseStr(str);
                equipValidator.validate(data);
                dataValidator.validate(data);
                dataHandler.handle(data);
                sendOK(session);
            } catch (Exception e) {
                sendMsg(session, e.getMessage());
            }
        } else {
            session.write("empty msg");
        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info(String.format("建立连接:%s", session.getRemoteAddress()));
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info(String.format("断开连接:%s", session.getRemoteAddress()));
    }

    private void sendMsg(IoSession session, String msg) {
        session.write(msg + "\r\n");
    }

    private void sendOK(IoSession session) {
        sendMsg(session, "$LRK01$OK0x0d\r\n");
    }
}