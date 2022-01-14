package net.haspamelodica.classfileverifier;

import net.haspamelodica.classfileverifier.UntrustedClass;

public class TrustedClass
{
	public void method()
	{
		System.out.println("TrustedClass's classloader in method(): " + getClass().getClassLoader());
		System.out.print("From TrustedClass: ");
		System.out.println("MARKER");
		new UntrustedClass().run();
	}
}
