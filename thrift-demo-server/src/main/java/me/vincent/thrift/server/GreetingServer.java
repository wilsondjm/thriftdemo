package me.vincent.thrift.server;

import me.vincent.thrift.demo.interfaces.GreetingService;
import me.vincent.thrift.interfaces.impl.GreetingServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class GreetingServer {

    public static void main(String[] args) {
        try {
//            TServerSocket serverTransport = new TServerSocket(9000);
//            //TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();
//            TProcessor processor = new GreetingService.Processor<GreetingServiceImpl>(new GreetingServiceImpl());
//            TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverTransport);
//            serverArgs.processor(processor);
//            //serverArgs.protocolFactory(protocolFactory);
//            TServer server = new TThreadPoolServer(serverArgs);


            TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(9001);
            TProcessor processor = new GreetingService.Processor<GreetingService.Iface>(new GreetingServiceImpl());
            TNonblockingServer.Args serverArgs = new TNonblockingServer.Args(serverSocket);

            TBinaryProtocol.Factory protocalFactory = new TBinaryProtocol.Factory();

            serverArgs.processor(processor);
            serverArgs.protocolFactory(protocalFactory);

            TServer server = new TNonblockingServer(serverArgs);

            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }

    }
}
