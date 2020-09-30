# ExportDatabaseWord
导出Mysql表结构为Word文档

此项目设计面向接口编程可灵活扩展导出各种数据库表结构为Word文档

当前完成了Mysql表结构的导出实现，也可以自定义扩展导出Oracle数据库表结构等。

## 使用
方法1：下载此项目  运行Main类中的main方法 即可

（如果执行报错，就把项目mvn clean一下再运行执行）

方法2：[可运行jar包直接下载！](http://forsnow.xin/export-database-word-1.0-SNAPSHOT-jar-with-dependencies.jar)

本地环境安装jdk8的java运行环境 在下载的jar包路径下执行命令行

java -jar export-database-word-1.0-SNAPSHOT-jar-with-dependencies.jar

（如果配置了环境变量 双击jar包选择打开方式用java打开也可直接运行）

（也可以下载项目代码打包为可执行jar包,具体参考 [项目打包为可执行jar包操作步骤](#jar) ）

方法3：打包为exe运行文件 直接运行即可（待研究完善）


## <a name="jar">项目打包为可执行jar包操作步骤</a>

使用maven的maven-assembly-plugin(pom文件中已设置)插件打包为可执行jar包([图片链接地址](http://forsnow.xin/data2word-example3.png))


 ![](http://forsnow.xin/data2word-example3.png)

## 项目运行截图 

1.工具运行截图([图片链接地址](http://forsnow.xin/data2word-example1.png))



![](http://forsnow.xin/data2word-example1.png)

2.导出文档截图([图片链接地址](http://forsnow.xin/data2word-example2.png))



![](http://forsnow.xin/data2word-example2.png)

## 备注
1.如果运行过程中遇到有问题或者有优化建议，请提issues。

2.作者联系邮箱 418995952@qq.com

3.[码云同步地址](https://gitee.com/nqpe/export-database-word)

## 参考项目

https://github.com/msuno/export-database-structure




