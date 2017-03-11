package com.alibaba.dubbo.examples.rpc.jms;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.xbean.XBeanBrokerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JmsProtocolSpring {

	public static void main(String[] args) throws Exception {
		
		
		XBeanBrokerFactory factory = new XBeanBrokerFactory();
		BrokerService broker = factory.createBroker(new URI(JmsProtocolTest.class.getPackage().getName().replace('.', '/') +"/activemq.xml"));
		broker.start();
		
        ClassPathXmlApplicationContext providerContext = new ClassPathXmlApplicationContext( 
        		JmsProtocolSpring.class.getPackage().getName().replace('.', '/') + "/dubbo-demo-provider.xml");
        providerContext.start();
        try {
            ClassPathXmlApplicationContext consumerContext = new ClassPathXmlApplicationContext( 
            		JmsProtocolSpring.class.getPackage().getName().replace('.', '/') + "/dubbo-demo-consumer.xml");
            consumerContext.start();
            try {
            	DemoService demoService = (DemoService) consumerContext.getBean("demoService");
                String hello = demoService.test("world",0);
                assertEquals("world:1", hello);
            } finally {
                consumerContext.stop();
                consumerContext.close();
            }
        } finally {
            providerContext.stop();
            providerContext.close();
        }
        
        broker.stop();
    }
	
}
