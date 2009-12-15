package chubyqc.gaeDistributed.server.network.messages.incoming;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;

import org.json.JSONException;

import chubyqc.gaeDistributed.server.network.messages.Message;

public abstract class IncomingMessage<T> extends Message {
	
	private T _dataStore;
	private String _address;
	
	public IncomingMessage() {
		setDataStore();
	}
	
	protected T getDataStore() {
		return _dataStore;
	}
	
	private Object getAttribute(String key) {
		try {
			return getJSON().get(key);
		} catch (JSONException e) {
			return new String();
		}
	}
	
	protected String getAddress() {
		return _address;
	}
	
	public void setAddress(String address) {
		_address = address;
	}
	
	@SuppressWarnings("unchecked")
	private void setDataStore() {
		_dataStore = (T) Proxy.newProxyInstance(getClass().getClassLoader(), getDataStoreType(), 
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						if (method.getName().startsWith(PREFIX_GET)) {
							return getAttribute(method.getName());							
						} else {
							return method.invoke(proxy, args);
						}
					}
				});
	}
	
	private Class<?>[] getDataStoreType() {
		return new Class<?>[] {
				(Class<?>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]
		};
	}
	
	public abstract void execute() throws Exception;
}
