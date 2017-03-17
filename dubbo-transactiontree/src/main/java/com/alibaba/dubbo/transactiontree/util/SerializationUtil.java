package com.alibaba.dubbo.transactiontree.util;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Kryo.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * 
 * @author fuhaining
 */
public class SerializationUtil {
	
	private static ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() { // NOT DESTROY
		protected Kryo initialValue() {
			Kryo kryo = new Kryo();
			kryo.setReferences(false);
			kryo.setRegistrationRequired(false);
			kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
			return kryo;
		};
	};
	
	public static byte[] serialize(Object obj) {
		if (obj == null) {
			return null;
		}
		
		Output output = new Output(1024, -1);
		kryos.get().writeClassAndObject(output, obj);
		
		return output.getBuffer();
	}
	
	public static Object deserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		
		return kryos.get().readClassAndObject(new Input(bytes));
	}
}
