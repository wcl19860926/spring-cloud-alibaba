
安装Nacos
1、执行cd  bin.
2、执行 sh startup.sh  -m  standalone ,启动服务。
3、服务启动后，可以通过，http://127.0.0.1:8848/nacos访问Nacose的控制台。控制台主要用于增强对服务列表，健康状态管理，服务治理，分布式配置等方面的管控能力，可以进一
   一步帮助开发者降低管理微服务应用架构的成本。


 nacos 的高可用部署
   一、环境准备
   在分布式架构中，任何中间件或者应用都不充许单点存在，所以开源组件一般都最会自己支持高可用集群解决方案
      1、环境要求，64bit OS linux/UNIX/Mac,推荐使用Linux系统。
      2、64bit JDK1.8及以上，
      3、Maven3.2x及以上。
      4、3个及3个以上Nacos节点才能构成集群。
      5、MYSQL数据库。
    二、安装包及环境准备。
      准备3台服务器
         下载安装包，分别进行解压： tar  -axvf  nacos-server.1.1.4.tar.gz
         解压后会得到5个文件夹：bin(服务启动/停止脚本）、conf（配置文件）、logs（日志）、data（derby数据库存储）、target（编译打包后的文件）。

    三、集群配置

      在conf目录下包含以下文件。
         application.properties:Spring Boot项目默认的配置文件。
         cluster.conf.example集群配置样例文件。
         nacos-mysql.sql , schema.sql：MySQL数据库脚本。Nacos支持Derby和MySQL两种持久化机制，默认采用Derby数据库。如果采用MySQL，需要运行该脚本创建数据库和表。
         nacos-logback.xml：Nacos日志配置文件。
      配置Nacos集群需要用到cluster.conf文件，我们可以直接重命名提供的example文件，修改该配置信息如下：
         192.168.13.104：8848
         192.168.13.105：8848
         192.168.13.106：8848
    四、配置MySQL数据库
        Derby数据库是一种文件类型的数据库，在使用时会存在一定的局限性。比如它无法支持多用户同时操作，在数据量大，连接数多的情况下会产生大量连接的积压。所
        以在生产环境中，可以使用MySQL 。
           1、执行nacos-mysql.sql初始化。
           2、分别修改3台机器中${NACOS_HOME}\config下的application.properties文件，增加MySQL的配置。
              server.contextPath=/nacos
              server.servlet.contextPath=/nacos
              server.port=8848

              nacos.cmdb.dumpTaskInterval=3600
              nacos.cmdb.eventTaskInterval=10
              nacos.cmdb.labelTaskInterval=300


              spring.datasource.platform=mysql
              db.num=1
              db.url.0=jdbc:mysql://192.168.0.1:3306/nacos_config
              db.user=root
              db.password=root
     五、启动Nacos
        分别进入3台机器的bin目录，执行sh  ./startup.sh -m standalone 或者 startup.sh 命令启动服务










