//package com.tt.device;
//
//import com.tt.device.handler.SocketDataHandler;
//import org.apache.mina.core.service.IoAcceptor;
//import org.apache.mina.core.service.IoHandler;
//import org.apache.mina.core.session.IdleStatus;
//import org.apache.mina.filter.codec.ProtocolCodecFactory;
//import org.apache.mina.filter.codec.ProtocolCodecFilter;
//import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
//import org.apache.mina.filter.logging.LoggingFilter;
//import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.charset.Charset;
//
///**
// * Created by tt on 2016/11/25.
// */
//@Configuration
////@PropertySource("classpath:socketConf.properties")
//public class SocketConfig {
//
//    @Value("${socket.port}")
//    private int PORT;
//
//    @Bean
//    public IoAcceptor ioAcceptor(LoggingFilter loggingFilter,
//                                 ProtocolCodecFilter protocolCodecFilter,
//                                 IoHandler socketDataHandler) throws IOException {
//        IoAcceptor acceptor = new NioSocketAcceptor();
//        acceptor.getFilterChain().addLast("logger", loggingFilter);
//        acceptor.getFilterChain().addLast("codec", protocolCodecFilter);
//        acceptor.setHandler(socketDataHandler);
//        acceptor.getSessionConfig().setReadBufferSize(2048);
//        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
//        System.out.println("*********************************");
////        acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));
//        acceptor.bind(new InetSocketAddress(PORT));
//        System.out.println(String.format("socket listening on port %s", PORT));
//        return acceptor;
//    }
//
//    @Bean
//    public LoggingFilter loggingFilter() {
//        return new LoggingFilter();
//    }
//
//    @Bean
//    public ProtocolCodecFilter protocolCodecFilter() {
//        return new ProtocolCodecFilter(protocolCodecFactory());
//    }
//
//    @Bean
//    public ProtocolCodecFactory protocolCodecFactory() {
//        return new TextLineCodecFactory(Charset.forName("GBK"));
//    }
//
//    @Bean
//    public SocketDataHandler socketDataHandler() {
//        return new SocketDataHandler();
//    }
//
//}
