package main.lib;

public class Lib {

	public static boolean isNumeric(char c)
	{
		try
		{
			Integer.parseInt(String.valueOf(c));
		}
		catch (NumberFormatException e)
		{
			return (false);
		}
		return (true);
	}

	public static boolean isAlpa(char c)
	{
		if (String.valueOf(c).matches("[a-zA-z]"))
			return (true);
		return (false);
	}
}
