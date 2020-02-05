import java.util.LinkedHashMap;
import java.util.Scanner;
import main.lib.Lib;

class Calc
{
	private int index;
	private String str;
	private int length;

	Calc()
	{
		this.index = 0;
	}

	private int		get_index()
	{
		return (this.index);
	}

	private void	increase_index()
	{
		this.index++;
	}

	private int		get_length()
	{
		return (this.length);
	}

	private String	get_str()
	{
		return (this.str);
	}

	int		count(String str)
	{
		int result;

		this.str = str;
		this.length = str.length();
		if (get_index() >= get_length())
			this.index = 0;
		result = second();
		while (get_index() < get_length() && (get_str().charAt(get_index()) == '+' || get_str().charAt(get_index()) == '-'))
		{
			if (get_str().charAt(get_index()) == '+')
			{
				increase_index();
				result += second();
			}
			else if (get_str().charAt(get_index()) == '-')
			{
				increase_index();
				result -= second();
			}
		}
		this.index = 0;
		return (result);
	}

	private int		second()
	{
		int result;

		result = third();
		while	( get_index() < get_length() &&
					(get_str().charAt(get_index()) == '*' ||
					 get_str().charAt(get_index()) == '/' ||
					 get_str().charAt(get_index()) == '%'
					)
				)
		{
			if (get_str().charAt(get_index()) == '*')
			{
				increase_index();
				result *= third();
			}
			else if (get_str().charAt(get_index()) == '/')
			{
				increase_index();
				result /= third();
			}
			else if (get_str().charAt(get_index()) == '%')
			{
				increase_index();
				result %= third();
			}
		}
		return (result);
	}

	private int		third()
	{
		int result;

		if (get_index() < get_length() && get_str().charAt(get_index()) == '(')
		{
			increase_index();
			result = count(get_str());
			increase_index();
		}
		else
			result = atoi();
		return (result);
	}

	private int		atoi()
	{
		int i = get_index();
		while (get_index() < get_length() && Lib.isNumeric(get_str().charAt(get_index())))
			increase_index();
		return (Integer.parseInt(get_str().substring(i, get_index())));
	}

	static String		isVariable(String str)
	{
		if (str == null)
			return ("Invalid identifier");

		int i = 0;
		int length;
		length = str.length();

		while (i < length && !Lib.isNumeric(str.charAt(i)) && str.charAt(i) != '=')
			i++;
		if (i >= length || str.charAt(i) != '=')
			return ("Invalid identifier");
		i++;
		if (i >= length)
			return ("Invalid identifier");
		if (Lib.isNumeric(str.charAt(i)))
			while (i < length && Lib.isNumeric(str.charAt(i)))
				i++;
		else if (!Lib.isNumeric(str.charAt(i)))
			while (i < length && !Lib.isNumeric(str.charAt(i)))
				i++;
		if (i != length)
			return ("Invalid assignment");
		return ("ok");
	}

	static String		isExample(String str)
	{
		return ("ok");
	}
}

class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String str;
		LinkedHashMap<String, String> variables = new LinkedHashMap<>();
		String[] var;
		Calc calc = new Calc();
		String error;
		while (!(str = scanner.nextLine().replaceAll("\\s", "")).equals("/exit"))
		{
			if ((error = Calc.isVariable(str)).equals("ok"))
			{
				var = str.split("=");
				variables.put(var[0], var[1]);
			}
			if(error.equals("ok") && (error = Calc.isExample(str)).equals("ok"))
			{
				int n = calc.count(str);
				System.out.println(n);
			}
		}
	}
}
