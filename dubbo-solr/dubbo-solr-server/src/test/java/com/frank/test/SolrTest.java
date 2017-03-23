package com.frank.test;

import com.frank.search.util.DateUtil;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA. User: kai.wang Date: 15-12-11 Time: 上午11:48 To
 * change this template use File | Settings | File Templates.
 */
public class SolrTest {

	@Test
	public void test01() {
		Calendar c = Calendar.getInstance();
		String hour = DateUtil.formatDate(c.getTime(), DateUtil.FORMAT_PATTERN_SOLR_HOUR);
		String minute = DateUtil.formatDate(c.getTime(), DateUtil.FORMAT_PATTERN_SOLR_MINUTE);
		String second = DateUtil.formatDate(c.getTime(), DateUtil.FORMAT_PATTERN_SOLR_SECOND);
		String promotionPerDate = DateUtil.formatDate(new Date(), DateUtil.FORMAT_PATTERN_SOLR_TIME);
		c.clear(Calendar.YEAR);
		c.clear(Calendar.MONTH);
		c.clear(Calendar.DAY_OF_YEAR);
		c.clear(Calendar.HOUR_OF_DAY);
		c.clear(Calendar.MINUTE);
		c.clear(Calendar.SECOND);
		c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
		c.set(Calendar.MINUTE, Integer.valueOf(minute));
		c.set(Calendar.SECOND, Integer.valueOf(second));
		System.out.println(c.getTimeInMillis() / 1000);

		Calendar c1 = Calendar.getInstance();
		System.out.println(c1.get(Calendar.HOUR_OF_DAY));
		System.out.println(c1.get(Calendar.MINUTE));
		System.out.println(c1.get(Calendar.SECOND));
		System.out.println(
				c1.get(Calendar.HOUR_OF_DAY) * 60 * 60 + c1.get(Calendar.MINUTE) * 60 + c1.get(Calendar.SECOND));
		c1.clear(Calendar.YEAR);
		c1.clear(Calendar.MONTH);
		c1.clear(Calendar.DAY_OF_YEAR);
		System.out.println(c1.getTimeInMillis() / 1000);
	}
}
