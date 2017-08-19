package com.jycar.server.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.chain.utils.crypto.CryptoFactoryBean;
import com.chain.utils.crypto.RSAUtils;
import com.github.pagehelper.PageInterceptor;
import net.sf.ehcache.CacheManager;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
//开启事务管理，也可以手动写txManager配置
@EnableTransactionManagement
@MapperScan("com.jycar.server.**.mapper")
public class DataSourceConfig {

    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private CryptoFactoryBean cryptoFactoryBean;

    //destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
    @SuppressWarnings("all")
    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() {
        logger.info("create datasource");

        RSAUtils rsaUtils = cryptoFactoryBean.getRsaUtils(true);
        String username = appConfig.getProperty("app.datasource.username");
        String password = appConfig.getProperty("app.datasource.password");
        if (appConfig.isEncrypt()) {
            username = rsaUtils.decryptByPrivateKey(username);
            password = rsaUtils.decryptByPrivateKey(password);
        }
        //使用Druid数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(appConfig.getProperty("app.datasource.url"));
        dataSource.setUsername(username);//用户名
        dataSource.setPassword(password);//密码
        dataSource.setDriverClassName(appConfig.getProperty("app.datasource.driver-class-name"));
        dataSource.setInitialSize(Integer.parseInt(appConfig.getProperty("app.datasource.initial-size")));//初始化时建立物理连接的个数
        dataSource.setMaxActive(Integer.parseInt(appConfig.getProperty("app.datasource.max-active")));//最大连接池数量
        dataSource.setMinIdle(Integer.parseInt(appConfig.getProperty("app.datasource.min-idle")));//最小连接池数量
        dataSource.setMaxWait(Integer.parseInt(appConfig.getProperty("app.datasource.max-wait")));//获取连接时最大等待时间，单位毫秒。
        dataSource.setValidationQuery(appConfig.getProperty("app.datasource.validation-query"));//用来检测连接是否有效的sql
        dataSource.setTestOnBorrow(false);//申请连接时执行validationQuery检测连接是否有效
        dataSource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。
        dataSource.setPoolPreparedStatements(false);//是否缓存preparedStatement，也就是PSCache
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        String CLASS_PATH_STRING = "classpath:";
        String CLASS_PATH2_STRING = "classpath*:";
        /** 设置mybatis configuration 扫描路径 */
        String configLocation = appConfig.getProperty("app.mybatis.config-location");
        if (configLocation.startsWith(CLASS_PATH_STRING)) {
            configLocation = configLocation.replaceAll(CLASS_PATH_STRING, "");
        }
        bean.setConfigLocation(new ClassPathResource(configLocation));
        /** 添加mapper 扫描路径 */
        String CLASS_PATH_PREFIX = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;
        String mybatisLocations = appConfig.getProperty("app.mybatis.mapper-locations");
        if (mybatisLocations.startsWith(CLASS_PATH_STRING) || mybatisLocations.startsWith(CLASS_PATH2_STRING))
            mybatisLocations = CLASS_PATH_PREFIX + mybatisLocations.replaceAll(CLASS_PATH_STRING, "");
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(pathMatchingResourcePatternResolver.getResources(mybatisLocations));
        } catch (IOException e) {
            logger.error("io exception", e);
        }
        /** 设置datasource */
        bean.setDataSource(dataSource());
        /** 设置typeAlias 包扫描路径 */
        bean.setTypeAliasesPackage(getMybatisTypeAliasesPackage());
        //bean.setPlugins(getSqlSessionFactoryPlugins());
        return bean;
    }

    private Interceptor[] getSqlSessionFactoryPlugins() {
        logger.info("getSqlSessionFactoryPlugins");

        List<Interceptor> lst = new ArrayList<>();

        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        //TODO 配置的不全，需要再完善一下，目前是启用默认自动配置
        properties.setProperty("autoRuntimeDialect", "true");
        pageInterceptor.setProperties(properties);
        lst.add(pageInterceptor);

        Interceptor[] interceptors = new Interceptor[lst.size()];
        for (int i = 0; i < interceptors.length; i++)
            interceptors[i] = lst.get(i);
        return interceptors;
    }

    //设置包的别名
    private String getMybatisTypeAliasesPackage() {
        logger.info("getMybatisTypeAliasesPackage");

        List<String> packages = new ArrayList<>();

        String mode = appConfig.getProperty("spring.profiles.active");
        if (!"prod".equals(mode))
            packages.add(getFull("test"));

        //TODO 不要忘记修改这里

        packages.add(getFull("base"));
        packages.add(getFull("web.coach"));
        packages.add(getFull("web.student"));
        packages.add(getFull("web.admin"));

        return String.join(",", packages);
    }

    private String getFull(String s) {
        String basePackage = "com.jycar.server.";
        String tailPackage = ".entities";
        return basePackage + s + tailPackage;
    }

    @Bean
    public CacheManager cacheManager() {
        logger.info("create cacheManager");
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource resource = pathMatchingResourcePatternResolver.getResource("classpath:ehcache.xml");
        ehCacheManagerFactoryBean.setConfigLocation(resource);
        return ehCacheManagerFactoryBean.getObject();
    }

}
