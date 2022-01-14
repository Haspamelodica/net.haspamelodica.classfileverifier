package net.haspamelodica.classfileverifier;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public abstract class TransformingClassLoader extends ClassLoader
{
	/**
	 * This method is given every class to be loaded by this {@link TransformingClassLoader},
	 * except those in <code>java.*</code>.<br>
	 * This is usually not a big problem as <code>java.*</code> only contains standard library classes,
	 * which can't reference classes outside the standard library.<br>
	 * TODO is this true even for reflection?
	 */
	protected abstract byte[] transformClassfile(String name, byte[] original, URL classfileResource);

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException
	{
		return loadClass(name, false);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		if(dontTryDefine(name))
			return super.loadClass(name, resolve);

		return defineTransformedClassOrThrow(name);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		if(dontTryDefine(name))
			return super.findClass(name);

		return defineTransformedClassOrThrow(name);
	}
	@Override
	protected Class<?> findClass(String moduleName, String name)
	{
		if(dontTryDefine(name))
			return super.findClass(moduleName, name);

		if(moduleName != null)
			//We don't support modules (yet?)
			return null;

		return defineTransformedClassOrNull(name);
	}

	private boolean dontTryDefine(String name)
	{
		// We can't define classes in "java.*" ourselves. This limitation comes from ClassLoader#preDefineClass.
		return name.startsWith("java.");
	}

	private Class<?> defineTransformedClassOrThrow(String name) throws ClassFormatError, ClassNotFoundException
	{
		Class<?> result = defineTransformedClassOrNull(name);
		if(result == null)
			throw new ClassNotFoundException(name);

		return result;
	}

	private Class<?> defineTransformedClassOrNull(String name) throws ClassFormatError
	{
		URL resource = getOriginalClassfileResource(name);
		byte[] originalClassfile = readBytesOrNull(resource);
		if(originalClassfile == null)
		{
			System.out.println("Didn't find or couldn't read classfile for " + name);
			return null;
		}

		byte[] transformedClassfile = transformClassfile(name, originalClassfile, resource);

		//We have to define every class ourselves; even those which the transformer leaves unchanged.
		//Otherwise, if an unchanged class references a class the transformer would like to change,
		//the parent would directly be asked, skipping the transformer.

		//This might result in a SecurityException; for example for sealed packages. Ignore to implicitly rethrow.
		return defineClass(name, transformedClassfile, 0, transformedClassfile.length);
	}
	private byte[] readBytesOrNull(URL resource)
	{
		if(resource == null)
			return null;

		try(InputStream in = resource.openStream())
		{
			return in.readAllBytes();
		} catch(IOException e)
		{
			return null;
		}
	}

	private URL getOriginalClassfileResource(String name)
	{
		//TODO does this work for all binary names?
		//Specifically, inner classes, anonymous classes, local classes?
		String resourceName = name.replace('.', '/') + ".class";
		URL resource = getResource(resourceName);
		return resource;
	}

	/**
	 * Creates a {@link TransformingClassLoader} based on the given {@link ClassfileTransformer}.
	 * Classes in <code>java.*</code> won't be transformed:
	 * See {@link TransformingClassLoader#transformClassfile(String, byte[], URL)}.
	 */
	public static TransformingClassLoader forTransformer(ClassfileTransformer transformer)
	{
		return new TransformingClassLoader()
		{
			@Override
			protected byte[] transformClassfile(String name, byte[] original, URL classfileResource)
			{
				return transformer.transformClassfile(name, original, classfileResource);
			}
		};
	}

	@FunctionalInterface
	public static interface ClassfileTransformer
	{
		public byte[] transformClassfile(String name, byte[] original, URL classfileResource);
	}
}
