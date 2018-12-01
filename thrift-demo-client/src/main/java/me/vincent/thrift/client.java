package me.vincent.thrift;

import me.vincent.thrift.demo.interfaces.GreetingService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.*;

import java.io.IOException;

public class client {

    public static void main(String[] args) {
//        TTransport transport = new TSocket("127.0.0.1", 9001);

        TFramedTransport transport = new TFramedTransport(new TSocket("127.0.0.1",9001));

        try {

            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);

            GreetingService.Client client = new GreetingService.Client(protocol);
            String result = client.sayHello("Wilson");

            System.out.println(result);


            TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
            TNonblockingTransport NioTransport = new TNonblockingSocket("127.0.0.1",9001);

            GreetingService.AsyncClient asyncClient = new GreetingService.AsyncClient(
                    protocolFactory,
                    new TAsyncClientManager(),
                    NioTransport
            );

            asyncClient.sayHello("Wilson", new AsyncMethodCallback() {
                public void onComplete(Object response) {
                    GreetingService.AsyncClient.sayHello_call object = (GreetingService.AsyncClient.sayHello_call)response;
                    try {
                        System.out.println(object.getResult());
                    } catch (TException e) {
                        e.printStackTrace();
                    }
                }

                public void onError(Exception exception) {
                    System.err.println("Error");
                }
            });

            Thread.sleep(10000);


        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            transport.close();
        }
    }
}
