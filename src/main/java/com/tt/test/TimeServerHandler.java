package com.tt.test;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.Date;


public class TimeServerHandler extends IoHandlerAdapter
{
    @Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived( IoSession session, Object message ) throws Exception
    {
        String str = message.toString();
        if( str.trim().equalsIgnoreCase("quit") ) {
            session.close(false);
            return;
        }
        System.out.println(String.format("msg received:%s", str));
        Date date = new Date();
        session.write( date.toString() );
        System.out.println("Message written..."+session.getId());
    }

    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("session opened"+session.getId());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("session closed "+session.getId());
    }


}