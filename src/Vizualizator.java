import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Vizualizator extends JFrame
{

	JPanel panel;
	JTextField text;
	private String example;
	Calc calc;

	public Vizualizator(Calc calc)
	{
		super("Calc");
		this.calc = calc;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setVisible(true);
		panel = new JPanel();
		add(panel);
		GridBagLayout bagLayout = new GridBagLayout();
		panel.setLayout(bagLayout);

		GridBagConstraints grid = create_grid(0, 0,0,0,1,10);
		text = new JTextField(30);
		text.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				e.consume();
			}
		});
		text.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(text, grid);

		GridBagConstraints multiplicationGrid = create_grid(0,0,0,1,1,1);
		JButton multiplication = new JButton("*");
		multiplication.addActionListener(new oper("*"));
		panel.add(multiplication, multiplicationGrid);

		GridBagConstraints oneGrid = create_grid(0,0,1,1,1,1);
		JButton one = new JButton("1");
		one.addActionListener(new oper("1"));
		panel.add(one, oneGrid);

		GridBagConstraints twoGrid = create_grid(0,0,2,1,1,1);
		JButton two = new JButton("2");
		two.addActionListener(new oper("2"));
		panel.add(two, twoGrid);

		GridBagConstraints threeGrid = create_grid(0,0,3,1,1,1);
		JButton three = new JButton("3");
		three.addActionListener(new oper("3"));
		panel.add(three, threeGrid);

		GridBagConstraints firstBracket = create_grid(0,0,4,1,1,1);
		JButton one_bracket = new JButton("(");
		one_bracket.addActionListener(new oper("("));
		panel.add(one_bracket, firstBracket);

		GridBagConstraints divisionGrid = create_grid(0,0,0,2,1,1);
		JButton division = new JButton("/");
		division.addActionListener(new oper("/"));
		panel.add(division, divisionGrid);

		GridBagConstraints fourGrid = create_grid(0,0,1,2,1,1);
		JButton four = new JButton("4");
		four.addActionListener(new oper("4"));
		panel.add(four, fourGrid);

		GridBagConstraints fiveGrid = create_grid(0,0,2,2,1,1);
		JButton five = new JButton("5");
		five.addActionListener(new oper("5"));
		panel.add(five, fiveGrid);

		GridBagConstraints sixGrid = create_grid(0,0,3,2,1,1);
		JButton six = new JButton("6");
		six.addActionListener(new oper("6"));
		panel.add(six, sixGrid);

		GridBagConstraints secondBracket = create_grid(0,0,4,2,1,1);
		JButton two_bracket = new JButton(")");
		two_bracket.addActionListener(new oper(")"));
		panel.add(two_bracket, secondBracket);

		GridBagConstraints minusGrid = create_grid(0,0,0,3,1,1);
		JButton minus = new JButton("-");
		minus.addActionListener(new oper("-"));
		panel.add(minus, minusGrid);

		GridBagConstraints sevenGrid = create_grid(0,0,1,3,1,1);
		JButton seven = new JButton("7");
		seven.addActionListener(new oper("7"));
		panel.add(seven, sevenGrid);

		GridBagConstraints eightGrid = create_grid(0,0,2,3,1,1);
		JButton eight = new JButton("8");
		eight.addActionListener(new oper("8"));
		panel.add(eight, eightGrid);

		GridBagConstraints nineGrid = create_grid(0,0,3,3,1,1);
		JButton nine = new JButton("9");
		nine.addActionListener(new oper("9"));
		panel.add(nine, nineGrid);

		GridBagConstraints plusGrid = create_grid(0,0,4,3,1,1);
		JButton plus = new JButton("+");
		plus.addActionListener(new oper("+"));
		panel.add(plus, plusGrid);

		GridBagConstraints submitGrid = create_grid(0,0,0,4,1,2);
		JButton submit = new JButton("submit");
		submit.addActionListener(new ReadExample());
		panel.add(submit, submitGrid);

		GridBagConstraints clearGrid = create_grid(0,0,3,4,1,2);
		JButton clear = new JButton("clear");
		clear.addActionListener(new ClearExample());
		panel.add(clear, clearGrid);

		panel.revalidate();

	}

	private GridBagConstraints create_grid(int weightx, int weighty, int gridx, int gridy, int gridheight, int gridwidth)
	{
		GridBagConstraints grid = new GridBagConstraints();
		grid.weightx = weightx;
		grid.weighty = weighty;
		grid.gridx = gridx;
		grid.gridy = gridy;
		grid.gridheight = gridheight;
		grid.gridwidth = gridwidth;
		return (grid);
	}

	class ReadExample implements  ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			example = text.getText();
			calc.count(example);
			text.setText(calc.get_result());
		}
	}

	class ClearExample implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			text.setText("");
		}
	}

	class oper implements ActionListener
	{
		private String operation;

		oper(String oper)
		{
			this.operation = oper;
		}

		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			text.setText(text.getText() + this.operation);
		}
	}
}
