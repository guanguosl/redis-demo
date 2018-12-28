package cn.com.gsl;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("resource")
public class JedisPool {

	private static final Logger LOGGER= Logger.getLogger(JedisPool.class);

	public static Jedis instance() {
		Jedis jedis = new Jedis("localhost", 6379);
		return jedis;

	}
 
}
