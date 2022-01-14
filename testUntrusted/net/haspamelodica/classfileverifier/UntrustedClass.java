package net.haspamelodica.classfileverifier;

public class UntrustedClass
{
	private static final String testStaticFinalStringField = "From UntrustedClass: ";

	static
	{
		System.out.println("UntrustedClass's class loader in static initializer: " + UntrustedClass.class.getClassLoader());
	}
	public void run()
	{
		System.out.println(concat().apply("S", "o", "m", "e duplicated ", "strings:", "hallo"));
		System.out.print(testStaticFinalStringField);
		System.out.println("MARKER");
	}
	static Test3 concat()
	{
		return (a, b, c, d, e, f) -> "\u0001" + a + b + c + d + e + "From UntrustedClass: " + f;
	}

	interface Test3
	{
		public String apply(String a, String b, String c, String d, String e, String f);
	}
}
