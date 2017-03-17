package com.alibaba.dubbo.util;

import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownEnabledTimer extends Timer {

	private static final Logger LOG = LoggerFactory.getLogger(ShutdownEnabledTimer.class);

	private Thread cancelThread;
	private String name;

	public ShutdownEnabledTimer(String name, boolean daemon) {
		super(name, daemon);

		this.name = name;

		this.cancelThread = new Thread(new Runnable() {
			public void run() {
				ShutdownEnabledTimer.super.cancel();
			}
		});

		LOG.info("Shutdown hook installed for: {}", this.name);

		Runtime.getRuntime().addShutdownHook(this.cancelThread);
	}

	@Override
	public void cancel() {
		super.cancel();

		LOG.info("Shutdown hook removed for: {}", this.name);

		try {
			Runtime.getRuntime().removeShutdownHook(this.cancelThread);
		} catch (IllegalStateException ise) {
			LOG.info("Exception caught (might be ok if at shutdown)", ise);
		}
	}
}