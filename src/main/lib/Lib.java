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
}
