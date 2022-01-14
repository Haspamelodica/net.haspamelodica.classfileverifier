package net.haspamelodica.classfileverifier;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import net.haspamelodica.classfileverifier.TransformingClassLoader;
import net.haspamelodica.classfileverifier.checkingvisitors.CheckingClassVisitor;

public class TransformingClassLoaderExample
{
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		TransformingClassLoader test = TransformingClassLoader.forTransformer(TransformingClassLoaderExample::transform);
		Class<?> trustedClassClass = Class.forName(TrustedClass.class.getName(), false, test);
		Constructor<?> constructor = trustedClassClass.getConstructor();
		Method method = trustedClassClass.getMethod("method");

		Object trustedClassInstance = constructor.newInstance();
		method.invoke(trustedClassInstance);
	}

	public static byte[] transform(String name, byte[] original, URL classfileResource)
	{
		ClassReader reader = new ClassReader(original);
		//Don't use the constructor taking a ClassReader:
		//It doesn't throw away unused constants, which may cause security holes.
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		ClassVisitor transformingVisitor = new CheckingClassVisitor(writer);

		reader.accept(transformingVisitor, 0);

		return writer.toByteArray();
	}
}
