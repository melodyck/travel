import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class redisDemo {
    @Test
    public void jedisSingleTest(){
        // 1.获得连接对象
        // 设置ip地址和端⼝口
        Jedis jedis = new Jedis("192.168.56.103", 6379);
        System.out.println(jedis);
        // 2.设置数据
        jedis.set("username", "hzt");
        // 3.获得数据
        String username = jedis.get("username");
        System.out.println(username);
        // 4.释放资源
        jedis.close();
    }
    @Test
    public void jedisPoolTest(){
        //1.创建连接池配置对象,设置配置项
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //1.1设置最大连接数
        poolConfig.setMaxTotal(30);
        //1.2设置最大空余连接数
        poolConfig.setMaxIdle(10);

        //2.获取连接池
        JedisPool jedisPool = new JedisPool(poolConfig, "192.168.56.103", 6379);

        //3.获得核心对象
        Jedis jedis = jedisPool.getResource();

        //4.设置数据
        jedis.set("username", "hzt");
        //5.获取数据
        String username = jedis.get("username");
        System.out.println(username);

        //6.释放资源
        jedis.close();
        //虚拟机关闭时关闭连接池
        jedisPool.close();
    }
}
