package cn.com.gyl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import redis.clients.jedis.Jedis;
 
@SuppressWarnings("resource")
public class JedisTest {
	private static final Logger LOGGER= Logger.getLogger(JedisTest.class);
	private static final String HASH_KEY = "key";
 
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost", 6379);
		long beginTime=System.currentTimeMillis();
		/**
		 * 存储String key-value
		 */
		jedis.set(HASH_KEY, "value");
		/**
		 * 如果已经存在key了，先删除掉
		 */
		if (jedis.exists(HASH_KEY)) {
			System.out.println(jedis.get(HASH_KEY));
			jedis.del(HASH_KEY);
		}
 
		/**
		 * 存入单个key-value
		 */
		jedis.hset(HASH_KEY, "username", "yourUsername");
 
		/**
		 * 存入多个key-value键值对
		 */
		Map<String, String> keyValueMap = new HashMap<String, String>();
		keyValueMap.put("password", "YourPassword");
		keyValueMap.put("age", "20");
		jedis.hmset(HASH_KEY, keyValueMap);
 
		/**
		 * 判断某个key是否在指定的Hash key中
		 */
		boolean existsUsernameKey = jedis.hexists(HASH_KEY, "username");
		// true
		System.out.println(existsUsernameKey);
 
		/**
		 * 获取某个hash中键值对的数量
		 */
		Long len = jedis.hlen(HASH_KEY);
		// 3
		System.out.println(len);
 
		/**
		 * 获取一个hash中的所有key
		 */
		Set<String> keys = jedis.hkeys(HASH_KEY);
		// [password, username, age]
		System.out.println(keys);
 
		/**
		 * 获取一个hash中的所有values
		 */
		List<String> values = jedis.hvals(HASH_KEY);
		// [yourUsername, YourPassword, 20]
		System.out.println(values);
 
		/**
		 * 获取指定hash的所有的键值对
		 */
		Map<String, String> allKVMap = jedis.hgetAll(HASH_KEY);
		// {username=yourUsername, age=20, password=YourPassword}
		System.out.println(allKVMap);
 
		/**
		 * 从一个hash中获取某个field的值
		 */
		String value = jedis.hget(HASH_KEY, "username");
		// yourUsername
		System.out.println(value);
 
		List<String> multValues = jedis.hmget(HASH_KEY, "username", "password");
		// [yourUsername, YourPassword]
		System.out.println(multValues);
		for (int i = 0; i < 100000; i++) {
			jedis.set(HASH_KEY+"g"+i, "valuevaluevaluevaluevaluevaluevaluevaluevaluevaluevaluevalue"+i);
		}
		long endTime=System.currentTimeMillis();
		System.out.println("cost:"+(endTime-beginTime));


		beginTime=System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			jedis.get(HASH_KEY+"g"+i);
		}
		 endTime=System.currentTimeMillis();
		System.out.println("cost:"+(endTime-beginTime));
	}
 
}
