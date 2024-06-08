# 智卷阁

## 一、开发环境
项目在`IDEA`上进行开发，类型为`Maven`项目，在运行之前先到`pom.xml`更新`Maven`包。

界面设计使用了`JavaFX`库，`JavaFX API`与`Java SE`运行时环境`JRE`和Java开发工具包`JDK`绑在一起。开发时使用的版本为`jkd-1.8`，自带`JavaFX`，如果使用更高版本确保安装了相应的库。

## 二、文件说明

    ├─.idea
    ├─src
    │  ├─main
    │  │  ├─java    
    │  │  │  ├─controller       //控制
    │  │  │  ├─dao              //数据存储
    │  │  │  ├─model            //实体定义
    │  │  │  ├─service          //服务
    │  │  │  └─view             //图形化界面库
    │  │  └─resources           //problem.csv文件用于导入题库

## 三、运行测试
图形化界面主函数为`.\src\main\java\view\MainApp.java`，注意不是`Main.java`。

点击运行`MainApp`即可弹出注册和登录界面。

建议测试步骤如下

    1.注册professor并登录
    2.从main\resources\problem.csv导入数据
    3.点击查看题目(例如Math)
    4.注册student并登录
    *5.进行测验，注意每一个空都写上
    6.查看成绩

## 四、开发日志
20240608

    完成迭代一，包括了智能出题、扩增题目和界面设计三个基本内容，此外还简要完成了选填批改和成绩统计。