package com.alibaba.dubbo.loadbalancer.rule;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.dubbo.loadbalancer.BaseLoadBalancer;
import com.alibaba.dubbo.loadbalancer.LoadBalancer;
import com.alibaba.dubbo.loadbalancer.Ping;
import com.alibaba.dubbo.loadbalancer.Server;
import com.alibaba.dubbo.loadbalancer.ServerListChangeListener;
import com.alibaba.dubbo.loadbalancer.ServerStatusChangeListener;

public class RoundRobinRuleTest {

	private BaseLoadBalancer lb;
	private RoundRobinRule rule;
	private Ping ping;

	@BeforeClass
	public void beforeClass() {
//		rule = new RandomRule();
		rule = new RoundRobinRule();

		ping = new Ping() {
			@Override
			public boolean isAlive(Server server) {
				// if (server.getHost().equals("192.168.1." + (new
				// Random().nextInt(9) + 1))) {
//				return new Random().nextBoolean();
				// }
				 return true;
			}
			@Override
			public LoadBalancer getLoadBalancer() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public void setLoadBalancer(LoadBalancer lb) {
				// TODO Auto-generated method stub
				
			}
		};

		lb = new BaseLoadBalancer();
		lb.setRule(rule);
		lb.setPing(ping);
		lb.setPingInterval(1);
		lb.addServerListChangeListener(new ServerListChangeListener() {
			@Override
			public void serverListChanged(List<Server> oldList, List<Server> newList) {
				// System.out.println("serverListChanged " +
				// toServerString(newList));
			}
		});
		lb.addServerStatusChangeListener(new ServerStatusChangeListener() {
			@Override
			public void serverStatusChanged(Collection<Server> servers) {
//				System.out.println(Thread.currentThread() + "    " + toServerString(servers));
//				System.out.println("serverStatusChanged1 " + toServerString(servers));
//				System.out.println("serverStatusChanged2 " + lb.getServerList(true));
			}
		});

		lb.addServer(new Server("192.168.1.1", 81));
		lb.addServer(new Server("192.168.1.2", 82));
		lb.addServer(new Server("192.168.1.3", 83));
		lb.addServer(new Server("192.168.1.4", 84));
		lb.addServer(new Server("192.168.1.5", 85));
		lb.addServer(new Server("192.168.1.6", 86));
		lb.addServer(new Server("192.168.1.7", 87));
		lb.addServer(new Server("192.168.1.8", 88));
		lb.addServer(new Server("192.168.1.9", 89));
		lb.addServer(new Server("192.168.1.10", 90));
	}

	private AtomicInteger counter = new AtomicInteger(0);

	@Test /// (threadPoolSize=500, invocationCount=1000)
	public void test1() throws InterruptedException { // 2.7
		ExecutorService e = Executors.newFixedThreadPool(100);
		final CountDownLatch latch = new CountDownLatch(1000 * 10000);
		for (int j = 0; j < 1000; j++) {
			e.execute(new Runnable() {
				public void run() {
					for (int i = 0; i < 10000; i++) {
//						try {
//							Thread.sleep(100);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						String uuid = UUID.randomUUID().toString();
//						Server s = lb.chooseServer(uuid);
						Server s = lb.chooseServer();
						if (s != null) {
//							if (!s.isAlive()) {
//								System.out.println(Thread.currentThread() + "  " + uuid + "   " + s.getId() + "  " + s.isAlive());
//								System.out.println(lb.getServerList(true) + "  " + uuid + "   " + s.getId() + "  " + s.isAlive());
//							System.out.println(s.getId() + "  " + s.isAlive());
//							}
						}
						// Server s = rule.choose(null);
						// if (s == null) {
						// lb.markServerDown(s);
						// }
//						System.out.println(counter.incrementAndGet() + "   " + s.getId());
						// counter.incrementAndGet();
						latch.countDown();
					}
				}
			});
		}
		latch.await();
		e.shutdown();
		System.out.println("END......");
	}

	@AfterClass
	public void afterClass() {
//		System.out.println(counter.get());
//		System.out.println(rule.getNextIndex());
	}

	public static String toServerString(Collection<Server> servers) {
		StringBuilder buf = new StringBuilder();
		for (Server s : servers) {
			buf.append(s.getId() + "=>" + s.isAlive() + "\t");
		}
		return buf.toString();
	}
}