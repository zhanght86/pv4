package com.websocket.config;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.log4j.Logger;

/**
 * 配置WebSocket相关
 * 
 * @author 武坤鹏
 * @version pvxp(2014GA)
 * @date 2014-10-21
 */
public class WebSocketConfig implements ServerApplicationConfig {

	Logger log = Logger.getLogger(WebSocketConfig.class.getName());

	/**
	 * 
	 */
	private ClientsManager CM = new ClientsManager();

	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> scanned) {

		Set<ServerEndpointConfig> result = new HashSet<ServerEndpointConfig>();

		// if (scanned.contains(EchoEndpoint.class)) {
		// result.add(ServerEndpointConfig.Builder.create(EchoEndpoint.class,
		// "/websocket/echoProgrammatic").build());
		// }
		//
		// if (scanned.contains(DrawboardEndpoint.class)) {
		// result.add(ServerEndpointConfig.Builder.create(DrawboardEndpoint.class,
		// "/websocket/drawboard").build());
		// }

		return result;
	}

	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {

		Set<Class<?>> results = new HashSet<Class<?>>();
		for (Class<?> clazz : scanned) {
			if (clazz.getPackage().getName().startsWith("com.websocket.")) {
				log.info(clazz.getPackage().getName() + "  加入监听队列");
				results.add(clazz);
			}
		}
		return results;
	}

}
