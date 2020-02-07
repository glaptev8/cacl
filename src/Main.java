import java.util.LinkedHashMap;
import java.util.Scanner;
import main.lib.Lib;

class Calc
{
	private int index;
	private String str;
	private int length;
	private LinkedHashMap<String, String> variables = new LinkedHashMap<>();
	private LinkedHashMap<String, String> commands = new LinkedHashMap<>();

	Calc()
	{
		this.index = 0;
		commands.put("/help", "Ты можешь просто ввести пример по типу:\n" +
				"\t1 + 2 - 3 -- 4 * (1 + 2 / 2)\n\n" +
				"также ты можешь обьявить переменную и использовать ее в примере по типу:\na = 10\n" +
				"b=20\n" +
				"c=a a + b - c");
	}

	public void print_command(String com)
	{
		System.out.println(this.commands.get(com));
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


	private void			put_variable(String str)
	{
		String[] var;

		var = str.split("=");
		if (!Lib.isNumeric(var[1].charAt(0)))
			this.variables.put(var[0], this.variables.get(var[1]));
		else
			this.variables.put(var[0], var[1]);
	}

	void			count(String str)
	{
		String var;
		String exam;
		String com;

		if ("".equals(str))
			return;
		str = str_replace(str);
		if ("".equals(str) || str.equals("Invalid expression"))
		{
			System.out.println("Invalid expression");
			return;
		}
		var = isVariable(str);
		exam = isExample(str);
		com = isCommand(str);
		if (com.equals("Unknow command") && str.charAt(0) == '/')
			System.out.println("Unknow command");
		else if (var.equals("ok"))
			put_variable(str);
		else if (!var.equals("it's not variable"))
			System.out.println(var);
		else if (exam.equals("ok")) {
			str = replace_variables(str);
			if (str.contains("null")) {
				System.out.println("Unknown variable");
				return;
			}
			this.index = 0;
			int n = first(str);
			System.out.println(n);
		}
		else System.out.println(exam);
	}

	private int		first(String str)
	{
		int result;
		this.str = str;
		this.length = str.length();

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

		if (get_index() >= get_length())
			return (0);
		if (get_str().charAt(get_index()) == '(')
		{
			increase_index();
			result = first(get_str());
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
		if (i >= get_length() || !Lib.isNumeric(get_str().charAt(i)))
			return (0);
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
		s = str;
		if (get_variables().containsKey(str) && !Lib.isNumeric((get_variables().get(s)).charAt(0)))
			while (get_variables().containsKey(str) && !Lib.isNumeric((get_variables().get(s)).charAt(0)))
				s = get_variables().get(s);
		else
			s = get_variables().get(s);
		return (s);
	}

	private String replace_variables(String str)
	{
		int i = 0;
		int len = str.length();
		String s = "";
		int j;
		while (i < len)
		{
			if (!Lib.isNumeric(str.charAt(i)) && Lib.isAlpa(str.charAt(i)))
			{
				j = i;
				while (i < len && Lib.isAlpa(str.charAt(i)))
					i++;
				s += get_number(str.substring(j, i));
			}
			else
			{
				s += String.valueOf(str.charAt(i));
				i++;
			}
		}
		return (s);
	}

	static String		isExample(String str)
	{
		char[] s = str.toCharArray();
		int brack_one = 0;
		int brack_two = 0;
		char p = ' ';
		for(char q: s)
		{
			if (q == '(')
				brack_one++;
			else if (q == ')')
				brack_two++;
			else if (!Lib.isNumeric(q) && !Lib.isAlpa(q) && q != '-' && q != '+' && q != '/' && q != '%' && q != '*')
				return ("Invalid expression");
			else if ((p == '-' || p == '+' || p == '/' || p == '%' || p == '*') && !Lib.isNumeric(q))
				return ("Invalid expression");
			if (q == '(' && p != '-' && p != '+' && p != '/' && p != '%' && p != '*')
				return ("Invalid expression");
			p = q;
		}
		if (brack_one != brack_two)
			return ("Invalid expression");
		return ("ok");
	}

	private String		str_replace(String str)
	{
		if (str == null)
			return ("");
		int i = 0;
		int len = str.length();
		String s = "";
		int count_minus = 0;
		while (i < len && str.charAt(i) == '+')
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
			else if ((str.charAt(i) == '/' || str.charAt(i) == '*') && (i + 1 < len && (str.charAt(i + 1) == '*') || (i + 1 < len && str.charAt(i + 1) == '/')))
				return ("Invalid expression");
			else
			{
				s += String.valueOf(str.charAt(i));
				i++;
			}
			count_minus = 0;
		}
		return (s);
	}

	static public String isCommand (String str)
	{
		if (str.equals("/help"))
			return ("/help");
		return ("Unknow command");
	}

}

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String str;
		Calc calc = new Calc();
		System.out.println("write example");
		while (!"/exit".equals(str = scanner.nextLine().replaceAll("\\s","")))
			calc.count(str);
		System.out.println("Bye!");
	}
}
