
https://maven.aliyun.com/mvn/search
添加依赖。
<dependency>
    <groupId>com.alibaba.boot</groupId>
    <artifactId>nacos-config-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
</dependency>
注意：版本 0.2.x.RELEASE 对应的是 Spring Boot 2.x 版本，版本 0.1.x.RELEASE 对应的是 Spring Boot 1.x 版本。
在 application.properties 中配置 Nacos server 的地址：
nacos.config.server-addr=127.0.0.1:8848
使用 @NacosPropertySource 加载 dataId 为 example 的配置源，并开启自动更新：
@SpringBootApplication
@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class NacosConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigApplication.class, args);
    }
}
通过 Nacos 的 @NacosValue 注解设置属性值。
@Controller
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }
}
启动 NacosConfigApplication，调用 curl http://localhost:8080/config/get，返回内容是 false。

通过调用 Nacos Open API 向 Nacos server 发布配置：dataId 为example，内容为useLocalCache=true

curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=example&group=DEFAULT_GROUP&content=useLocalCache=true"
再次访问 http://localhost:8080/config/get，此时返回内容为true，说明程序中的useLocalCache值已经被动态更新了。
启动服务发现