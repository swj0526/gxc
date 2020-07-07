package com.swj.util;

import com.sun.istack.internal.logging.Logger;
import com.swj.annotation.LogAnnotation;
import com.swj.entity.TbLog;
import com.swj.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;


/**
 * 日志切面类
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;
    private Logger log = Logger.getLogger(getClass());

    //只要使用了该注解,就会进入切面
    @Pointcut("@annotation(com.swj.annotation.LogAnnotation)")
    public void operationLog() {
    }


    /**
     * 方法返回之后调用
     *
     * @param joinPoint
     *
     */
    @AfterReturning(value = "operationLog()")
    public void doAfter(JoinPoint joinPoint) {
        TbLog log = getLog(joinPoint);
    }

    private TbLog getLog(JoinPoint joinPoint) {
//        //登录的session中去拿当前登录的用户Id
//        Subject subject = SecurityUtils.getSubject();
//        SysUserBaseVo user = null;
//        Long currentUserId = null;
//        //判断是否通过shiro认证
//        if (subject.getPrincipal() != null) {
//            //user = (SysUserBaseVo) subject.getPrincipal();
//            //currentUserId = user.getUserId();
//        } else {
//            return null;
//        }

        TbLog log = new TbLog();
        //获取类名称
        String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = null;
        LogAnnotation logAnnotation = null;
        try {
            //反射
            targetClass = Class.forName(targetName);
            //获得切入点所在类的所有方法
            Method[] methods = targetClass.getMethods();
            //获取切入点的方法名称
            String methodName = joinPoint.getSignature().getName();
            //获取切入点的参数
            Object[] arguments = joinPoint.getArgs();

            //遍历方法名
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    //比较声明的参数个数和传入的是否相同
                    if (clazzs.length == arguments.length) {
                        //获取切入点方法上的注解
                        logAnnotation = method.getAnnotation(LogAnnotation.class);
                        break;
                    }
                }
            }
            //为日志实体类赋值
            log.setState(logAnnotation.operationType().getOperateDesc());

                log.setRemark(logAnnotation.operateContent());

            //logVo.setUserId(currentUserId);
            //获取ip地址
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            log.setIp(IpUtil.getIpAddr(request));
            //添加日志
            logService.addLog(log);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return log;

    }
}
