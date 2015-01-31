package cn.slimsmart.spring.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect : 标记为切面类
//@Pointcut : 指定匹配切点集合
//@Before : 指定前置通知，value中指定切入点匹配
//@AfterReturning ：后置通知，具有可以指定返回值
//@AfterThrowing ：异常通知
//@Around 环绕通知 环绕通知的方法中一定要有ProceedingJoinPoint 参数,与Filter中的  doFilter方法类似
//注意：前置/后置/异常通知的函数都没有返回值，只有环绕通知有返回值
@Component
@Aspect
public class TestInterceptor {

	//如果要设置多个切点可以使用 || 拼接
	@Pointcut("execution(* cn.slimsmart.spring.demo.aop.UserServiceImpl.*(..))")
	private void anyMethod() {
	}// 定义一个切入点

	@Before(value="anyMethod()")
	public void doBefore(JoinPoint joinPoint) {
		System.out.println("前置通知");
	}

	@AfterReturning(value="anyMethod()",returning="result")
	public void doAfter(JoinPoint jp, String result) {
		System.out.println("后置通知");
	}

	@After("anyMethod()")
	public void after() {
		System.out.println("最终通知");
	}

	@AfterThrowing(value="execution(* cn.slimsmart.spring.demo.aop.*.*(..))",throwing="e")
	public void doAfterThrow(JoinPoint joinPoint, Throwable e) {
		System.out.println("异常通知");
	}

	@Around("execution(* cn.slimsmart.spring.demo.aop.*.*(..))")
	public Object doBasicProfiling(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("进入环绕通知");
		System.out.println("目标类名称："+joinPoint.getTarget().getClass().getName());
		System.out.println("方法名称："+joinPoint.getSignature().getName());
		System.out.println("方法参数："+joinPoint.getArgs());
		System.out.println("staticPart:"+ joinPoint.getStaticPart().toShortString());
		System.out.println("kind:"+joinPoint.getKind());
		System.out.println("sourceLocation:"+joinPoint.getSourceLocation());
		Object object = joinPoint.proceed();// 执行该方法
		System.out.println("退出方法");
		return object;
	}

}
