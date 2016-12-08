package com.tt.device.handler;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import com.tt.device.model.EndData;
import com.tt.model.Equipment;
import com.tt.model.InspectData;
import com.tt.service.BInspectServiceI;
import com.tt.service.EquipmentServiceI;

import java.util.Date;


public class SocketDataHandler extends IoHandlerAdapter {
    private static final Logger logger = Logger.getLogger(SocketDataHandler.class);

    @Autowired
    private BInspectServiceI bInspectServiceI;
    @Autowired
    private EquipmentServiceI equipmentServiceI;
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.error("内部错误\n");
        logger.error(cause.getStackTrace());
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        String str = message.toString().trim();
        if (!str.isEmpty()) {
            if (str.indexOf("STATUS:\"END\"") > 0) {
                EndData item = parseEnd(str);
                if(item!=null){
                    int count = bInspectServiceI.updateStatus(item.getPRG(), item.getSTZH(), item.getDevNB());
                    session.write(123);
                    if (count > 0) {
                        // 响应成功
                        sendOK(session);
                    } else {
                        sendMsg(session,"更新数据库失败,请联系管理员处理!");
                        logger.error(String.format("标记完成失败!工程:%s,桩号:%s,设备号:%s", item.getPRG(), item.getSTZH(), item.getDevNB()));
                    }
                }else {
                    sendMsg(session,"解析数据失败,请检查数据格式是否符合要求!");
                }

            } else {
                InspectData item = parseData(str);
                if (item!=null) {
                    String error = validate(item);
                    if (error == null) {
                        Integer id = bInspectServiceI.add(item);
                        if (id != null && id > 0) {
                            // 响应成功
                            sendOK(session);
                        } else {
                            // 响应失败
                            sendMsg(session,"保存失败,请联系管理员!");
                        }
                    } else {
                        sendMsg(session,error);
                    }
                }else{
                    sendMsg(session,"解析数据失败,请检查数据格式是否符合要求!");
                }
            }
        }else{
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

    private InspectData parseData(String msg) {
        InspectData ret = null;
        try {
            ret =  JSON.parseObject(msg, InspectData.class);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
        }
        return ret;
    }

    private EndData parseEnd(String msg) {
        EndData ret = null;
        try {
            ret =  JSON.parseObject(msg, EndData.class);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
        }
        return ret;
    }

    private String validate(InspectData item) {
        String ret = null;
        Equipment equipment = equipmentServiceI.loadEquipmentByCode(item.getDevnb());
        if(equipment!=null){
            if(equipment.getExpiredDate().after(new Date())){
                String template = "报文格式错误:需要有字段 %s:%s,且值不能为空";
                if (item.getPrg() == null || item.getPrg().trim().isEmpty()) {
                    ret = String.format(template, "工程编号", "PRG");
                } else if (item.getStzh() == null || item.getStzh().trim().isEmpty()) {
                    ret = String.format(template, "桩号", "STZH");
                } else if (item.getDevnb() == null || item.getDevnb().trim().isEmpty()) {
                    ret = String.format(template, "设备编号", "DevNB");
                }
            }else {
                ret = "DevNB expired";
            }
        }else{
            ret = "no DevNB registered";
        }

        return ret;
    }

    private void sendMsg(IoSession session,String msg){
        session.write(msg+ "\r\n");
    }
    private void sendOK(IoSession session){
        sendMsg(session,"$LRK01$OK0x0d\r\n");
    }
}