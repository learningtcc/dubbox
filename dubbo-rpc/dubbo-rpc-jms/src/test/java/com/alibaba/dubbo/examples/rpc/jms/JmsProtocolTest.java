package com.alibaba.dubbo.examples.rpc.jms;

import java.net.URI;
import java.net.URISyntaxException;

import javax.jms.Queue;

import junit.framework.Assert;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.xbean.XBeanBrokerFactory;
import org.junit.Test;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.ProxyFactory;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.jms.JmsProtocol;

public class JmsProtocolTest {

	@Test
    public void testHessianProtocol() throws URISyntaxException, Exception {
		
		ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
		
		//Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
		//Invoker<DemoService> invokerp = protocol.refer(DemoService.class, URL.valueOf("jms://" + DemoService.class.getName() + "?version=1.0.0"));
        
		XBeanBrokerFactory factory = new XBeanBrokerFactory();
		BrokerService broker = factory.createBroker(new URI(JmsProtocolTest.class.getPackage().getName().replace('.', '/') +"/activemq.xml"));
		broker.start();
		
		ActiveMQConnectionFactory queueConnectionFactory = new ActiveMQConnectionFactory( "tcp://localhost:61617" );
		Queue queue = new ActiveMQQueue("DUBBO");
		
		JmsProtocol jmsProtocol = new JmsProtocol();
		jmsProtocol.setQueue(queue);
		jmsProtocol.setQueueConnectionFactory(queueConnectionFactory);
		
        DemoServiceImpl server = new DemoServiceImpl();
        
        URL url = URL.valueOf("jms://" + DemoService.class.getName() + "?version=1.0.0");
        Exporter<DemoService> exporter = jmsProtocol.export(proxyFactory.getInvoker(server, DemoService.class, url));
        Invoker<DemoService> invoker = jmsProtocol.refer(DemoService.class, url);
        DemoService client = proxyFactory.getProxy(invoker);
        
        // test integer
        int i = client.test(1);
        Assert.assertEquals(2, i);
        
        // test string and integer
        String result = client.test("haha",0);
        Assert.assertEquals("haha:1", result);
        
        // test timeout
        try {
        	client.testTimeout(1000);
        	Assert.fail("timeout doesn't work");
		} catch (Exception e) {
			Assert.assertTrue( e instanceof RpcException);
		}
        
        invoker.destroy();
        exporter.unexport();
        
        broker.stop();
    }
}
