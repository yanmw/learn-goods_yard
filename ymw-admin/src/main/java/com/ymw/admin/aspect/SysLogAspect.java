package com.ymw.admin.aspect;

import javax.servlet.http.HttpServletRequest;

import com.ymw.admin.model.sys.SysLog;
import com.ymw.admin.sevice.sys.SysLogService;
import com.ymw.admin.util.HttpUtils;
import com.ymw.admin.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ymw.admin.util.IPUtils;
import com.ymw.admin.util.SecurityUtils;

import java.lang.reflect.Method;


/**
 * 系统日志，切面处理类，记录日志
 */
@Aspect
@Component
@EnableAsync
public class SysLogAspect {
	
	@Autowired
	private SysLogService sysLogService;
	
	/*@Pointcut("execution(* com.ymw.*.sevice.*.*(..))")
	public void logPointCut() { 
		
	}*/
	//表示匹配com.ymw.*.controller包及其子包下的所有方法
	@Pointcut("execution(* com.ymw.*.controller..*.*(..))")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		// 保存日志
		saveSysLog(point, time);
		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) throws Exception {
		String userName = SecurityUtils.getUsername();
		if(joinPoint.getTarget() instanceof SysLogService) {
			return ;
		}
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		SysLog sysLog = new SysLog();

		// 请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		// 请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSONObject.toJSONString(args[0]);
			if(params.length() > 200) {
				params = params.substring(0, 200) + "...";
			}
			sysLog.setParams(params);
		} catch (Exception e){
		}
		Log log = getAnnotationLog(joinPoint);
		if (log != null) {
			getControllerMethodDescription(log,sysLog);
		}
		// 获取request
		HttpServletRequest request = HttpUtils.getHttpServletRequest();
		// 设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));

		// 用户名
		sysLog.setUserName(userName);
		
		// 执行时长(毫秒)
		sysLog.setTime(time);
		
		// 保存系统日志
		sysLogService.save(sysLog);
	}

	/**
	 * 获取注解中对方法的描述信息 用于Service层注解
	 *
	 * @param
	 * @return 方法描述
	 * @throws Exception
	 */
	public void getControllerMethodDescription(Log log, SysLog sysLog) throws Exception
	{
		sysLog.setTitle(log.title());
	}

	/**
	 * 是否存在注解，如果存在就获取
	 */
	private Log getAnnotationLog(JoinPoint joinPoint) throws Exception
	{
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null)
		{
			return method.getAnnotation(Log.class);
		}
		return null;
	}
}
