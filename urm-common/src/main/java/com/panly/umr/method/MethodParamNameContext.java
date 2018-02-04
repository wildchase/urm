package com.panly.umr.method;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * 切面方法参数名缓存
 * </p>
 *
 * @project core
 * @class MethodParamNameContext
 * @author a@panly.me
 * @date 2017年9月4日下午2:14:27
 */
public class MethodParamNameContext {

	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(MethodParamNameContext.class);

	private static Map<String, String[]> methodParamNames = new ConcurrentHashMap<>();

	/**
	 * <p>
	 * 获取方法的参数名，先读缓存，若是没有，则获取
	 * </p>
	 * 
	 * @param m
	 * @return
	 */
	public static String[] getMethodParamNames(Method m) {
		String name = getMethodKey(m);
		String[] paramNames = methodParamNames.get(name);
		if (paramNames == null) {
			paramNames = getParams(m);
			methodParamNames.put(name, paramNames);
		}
		return paramNames;
	}

	/**
	 * 获取 缓存key
	 * 
	 * @param m
	 * @return
	 */
	public static String getMethodKey(Method m) {
		StringBuilder strBu = new StringBuilder();
		Class<?>[] parameterTypes = m.getParameterTypes();
		strBu.append(m.getDeclaringClass().getName());
		strBu.append(".");
		strBu.append(m.getName());
		if (parameterTypes != null && parameterTypes.length > 0) {
			for (Class<?> clazz : parameterTypes) {
				strBu.append("$").append(clazz.getName());
			}
		}
		return strBu.toString();
	}

	/**
	 * 获取参数名称
	 * 
	 * @param m
	 * @return
	 */
	protected static String[] getParams(final Method m) {
		final String[] paramNames = new String[m.getParameterTypes().length];
		final String n = m.getDeclaringClass().getName();
		ClassReader cr = null;
		try {
			cr = new ClassReader(n);
		} catch (IOException e) {
			logger.error("", e);
			throw new RuntimeException(e);
		}
		cr.accept(new ClassVisitor(Opcodes.ASM4) {
			@Override
			public MethodVisitor visitMethod(final int access, final String name, final String desc,
					final String signature, final String[] exceptions) {
				final Type[] args = Type.getArgumentTypes(desc);
				// 方法名相同并且参数个数相同
				if (!name.equals(m.getName()) || !sameType(args, m.getParameterTypes())) {
					return super.visitMethod(access, name, desc, signature, exceptions);
				}
				MethodVisitor v = super.visitMethod(access, name, desc, signature, exceptions);
				return new MethodVisitor(Opcodes.ASM4, v) {
					@Override
					public void visitLocalVariable(String name, String desc, String signature, Label start, Label end,
							int index) {
						int i = index - 1;
						// 如果是静态方法，则第一就是参数
						// 如果不是静态方法，则第一个是"this"，然后才是方法的参数
						if (Modifier.isStatic(m.getModifiers())) {
							i = index;
						}
						if (i >= 0 && i < paramNames.length) {
							paramNames[i] = name;
						}
						super.visitLocalVariable(name, desc, signature, start, end, index);
					}
				};
			}
		}, 0);
		return paramNames;
	}

	/**
	 * <p>
	 * 比较参数类型是否一致
	 * </p>
	 * 
	 * @param types
	 *            asm的类型({@link Type})
	 * @param clazzes
	 *            java 类型({@link Class})
	 * @return
	 */
	private static boolean sameType(Type[] types, Class<?>[] clazzes) {
		// 个数不同
		if (types.length != clazzes.length) {
			return false;
		}
		for (int i = 0; i < types.length; i++) {
			if (!Type.getType(clazzes[i]).equals(types[i])) {
				return false;
			}
		}
		return true;
	}

}
