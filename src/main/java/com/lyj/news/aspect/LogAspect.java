package com.lyj.news.aspect;/**
 * @Auther: babyL FinlayL
 * @Date: 2018/10/13 01:52
 * @Description:
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// 面向切面编程AOP，面向所有业务都要处理的业务
// 就是有一些业务，比如说日志是所有业务逻辑层都需要的，
// 每一个逻辑业务层都处理就很麻烦。这时候面向切面就跳出来了，
// 他把这些公共业务完成，然后以切面切片形式给各业务逻辑层使用。
// 经典应用 日志、拦截器过滤器。 设计模式中的观察者模式（回调）
// 方法调用记录，检测服务器性能。before和after都加上时间，可以体现性能
// 检测参数传递


/**
 * @program: news
 * @description: 日志切面
 * @author: babyL.FinlayL
 * @createDate: 2018-10-13 01:52
 **/
@Aspect
@Component  // 这和@Component 是向Spring注入普通类
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    // 这里很强大，可以用正则表达式去匹配。*号 是通配符
    @Before( "execution( * com.lyj.news.controller.HomeController.*(..) )" )
    public void beforeMethod(JoinPoint joinPoint){
        StringBuffer sb = new StringBuffer();
        for (Object arg : joinPoint.getArgs()){
            sb.append("arg:" + arg.toString() + "|");
        }
        logger.info("before method:" + sb.toString());
    }

    @After( "execution(* com.lyj.news.controller.IndexController.*(..))" )
    public void afterMethod(JoinPoint joinPoint ){
        logger.info("after method:");
    }





}
