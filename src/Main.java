import java.util.LinkedHashMap;
import java.util.Scanner;
import main.lib.Lib;

class Calc
{
	private int index;
	private String str;
	private int length;
	private LinkedHashMap<String, String> variables = new LinkedHashMap<>();

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

	private LinkedHashMap<String, String> get_variables()
	{
		return (this.variables);
	}


	void			put_variable(String str)
	{
		String[] var;

		var = str.split("=");
		if (!Lib.isNumeric(var[1].charAt(0)))
			this.variables.put(var[0], this.variables.get(var[1]));
		else
			this.variables.put(var[0], var[1]);
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
		int flag = 1;
		if (i == 0 && get_index() == 0 && ((get_str().charAt(get_index()) == '-') || (get_str().charAt(get_index()) == '+')))
		{
			flag = (get_str().charAt(get_index()) == '-') ? -1 : 1;
			increase_index();
			i++;
		}
		while (get_index() < get_length() && Lib.isNumeric(get_str().charAt(get_index()))) {
			increase_index();
		}
		return (Integer.parseInt(get_str().substring(i, get_index())) * flag);
	}

	String		isVariable(String str)
	{
		if (str == null)
			return ("Invalid identifier");
		if (!str.contains("="))
			return ("it's not variable");

		String[] v;
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
		v = str.split("=");
		if (!Lib.isNumeric(v[1].charAt(0)))
			if (!get_variables().containsKey(v[1]))
				return ("Unknown variable");
		return ("ok");
	}

	private	String get_number(String str)
	{
		String s;
		int i = 0;
		s = str;
		if (!Lib.isNumeric((get_variables().get(s)).charAt(0)))
			while (!Lib.isNumeric((get_variables().get(s)).charAt(0)))
				s = get_variables().get(s);
		else
			s = get_variables().get(s);
		return (s);
	}

	String replace_variables(String str)
	{
		int i = 0;
		int len = str.length();
		String s = "";
		while (i < len)
		{
			if (!Lib.isNumeric(str.charAt(i)) && Lib.isAlpa(str.charAt(i)))
				s += get_number(String.valueOf(str.charAt(i)));
			else
				s += String.valueOf(str.charAt(i));
			i++;
		}
		return (s);
	}

	static String		isExample(String str)
	{
		int i = 0;
		int len = str.length();
		return ("ok");
	}

	String		str_replace(String str)
	{
		if (str == null)
			return (null);
		int i = 0;
		int len = str.length();
		String s = "";
		int count_minus = 0;
		int[] count_bracket = new int[2];
		while (str.charAt(i) == '+')
			i++;
		while (i < len)
		{
			if (str.charAt(i) == '-')
			{
				while (i < len && str.charAt(i) == '-')
				{
					count_minus++;
					i++;
				}
				s += (count_minus % 2 == 0) ?  "+" :  "-";
			}
			else if (str.charAt(i) == '+')
			{
				while (i < len && str.charAt(i) == '+')
					i++;
				s += "+";
			}
			else if ((str.charAt(i) == '/' || str.charAt(i) == '*') && (i + 1 < len && (str.charAt(i + 1) == '*') || (str.charAt(i + 1) == '/')))
				return ("Invalid expression");
			else
			{
				if (str.charAt(i) == '(')
					count_bracket[0]++;
				else if (str.charAt(i) == ')')
					count_bracket[1]++;
				s += String.valueOf(str.charAt(i));
				i++;
			}
			count_minus = 0;
		}
		if (count_bracket[0] != count_bracket[1])
			return ("Invalid expression");
		return (s);
	}
}

class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String str;
		Calc calc = new Calc();
		String error = "";
		while (!"/exit".equals(str = scanner.nextLine()))
		{
			if (str.equals(""))
				continue;
			if (str.equals("\\help")) {
				System.out.println("you can write variables and any examples");
				continue;
			}
			error = str = calc.str_replace(str.replaceAll("\\s", ""));
			if (!error.equals("Invalid expression") && (error = calc.isVariable(str)).equals("ok"))
				calc.put_variable(str);
			if (error.equals("it's not variable") && (error = Calc.isExample(str)).equals("ok"))
			{
				str = calc.replace_variables(str);
				int n = calc.count(str);
				System.out.println(n);
			}
			else if (!"ok".equals(error))
				System.out.println(error);
		}
	}
}
