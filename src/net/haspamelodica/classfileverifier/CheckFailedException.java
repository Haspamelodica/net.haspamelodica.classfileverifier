package net.haspamelodica.classfileverifier;

public class CheckFailedException extends SecurityException
{
	public CheckFailedException()
	{}
	public CheckFailedException(String s)
	{
		super(s);
	}
	public CheckFailedException(Throwable cause)
	{
		super(cause);
	}
	public CheckFailedException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
