package org.zt.sqlhint;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath*:/META-INF/spring/app*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class Bootstrap extends AbstractJUnit4SpringContextTests {

	@Test
	public void run() throws IOException {
		System.in.read();
	}
}
