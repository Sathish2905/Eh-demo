package com;

import java.net.URI;
import java.net.URL;
import java.util.EnumSet;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.event.EventFiring;
import org.ehcache.event.EventOrdering;
import org.ehcache.event.EventType;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;

import static java.net.URI.create;
import static org.ehcache.clustered.client.config.builders.ClusteredResourcePoolBuilder.clusteredDedicated;
import static org.ehcache.clustered.client.config.builders.ClusteringServiceConfigurationBuilder.cluster;
import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManager;
import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManagerBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;
import static org.ehcache.config.units.MemoryUnit.MB;
import static org.slf4j.LoggerFactory.getLogger;

public class RemoteCache1 {
	private static final Logger LOGGER = getLogger(RemoteCache1.class);

	private static Cache<Long, CAOCache> remoteCache = null;

	public static void main(String[] args) {
		URL myUrl = RemoteCache1.class.getResource("/ehcache.xml");
		Configuration xmlConfig = new XmlConfiguration(myUrl);
		try (CacheManager cacheManager = newCacheManager(xmlConfig)) {
			cacheManager.init();
			remoteCache = cacheManager.getCache(
					"remoteCache", Long.class, CAOCache.class);
			ListenerObject listener = new ListenerObject();
			remoteCache.getRuntimeConfiguration().registerCacheEventListener(
					listener,
					EventOrdering.ORDERED,
					EventFiring.SYNCHRONOUS,
					EnumSet.of(EventType.CREATED, EventType.UPDATED,
							EventType.REMOVED));
			for (int i = 0; i < 10; i++) {
				CAOCache ch = new CAOCache(Long.valueOf(i), "WEB-1", "Obj" + i);
				putInCache(ch.getId(), ch);
			}
			while (true) {
				
			}
		}
	}

	public static void putInCache(Long key, CAOCache value) {
		remoteCache.put(key, value);
	}

	public static CAOCache getFromCache(Long key) {
		return remoteCache.get(key);
	}

}