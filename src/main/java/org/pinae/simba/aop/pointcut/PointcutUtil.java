package org.pinae.simba.aop.pointcut;

import java.lang.reflect.Method;

import org.pinae.simba.aop.Pointcut;


/**
 * 切入点工具
 * 
 * @author Huiyugeng
 *
 */
final public class PointcutUtil {
	/**
	 * 将两个切入点进行合并
	 * 
	 * @param point1 切入点1
	 * @param point2 切入点2
	 * @return 合并后的切入点
	 */
	public static Pointcut union(final Pointcut point1, final Pointcut point2){
		return new DefaultPointcut(){
			public boolean matcher(Method method, Object[] arg) {
				return (point1.matcher(method, arg) && point2.matcher(method, arg));
			}

			public boolean matcher(Class<?> clazz) {
				return (point1.matcher(clazz) && point2.matcher(clazz));
			}
		};
	}
}
