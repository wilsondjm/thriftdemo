package me.vincent.thrift.interfaces.impl;

import me.vincent.thrift.demo.interfaces.GreetingService;
import org.apache.thrift.TException;

public class GreetingServiceImpl implements GreetingService.Iface {
    public String sayHello(String name) throws TException {
        return "Hello World, " + name + "!";
    }
}
