import java.util.*;

/**
 * 
 * @author Ying Cui
 * Build an expression tree with calculation function.(Postfix to infix and prefix)
 */

public class ExpressionTree {
	class Node
	{
		char val; // number or operator
		Node left;
		Node right;
		
		Node(char val)
		{
			this.val = val;
			this.left = null;
			this.right = null;
		}
	}
	class StackNode
	{
		Node node;
		StackNode next;
		
		StackNode(Node node)
		{
			this.node = node;
			this.next = null;
		}
	}
	
	private static StackNode top;
	// Constructor
	public ExpressionTree()
	{
		top = null;
	}
	// clear method
	public void clear()
	{
		top = null;
	}
	// push a node
	private void push (Node p)
	{
		if (top == null)
			top = new StackNode(p);
		else
		{
			StackNode newp = new StackNode(p);
			newp.next = top;
			top = newp;
		}
	}
	// pop a node
	private Node pop()
	{
		if (top == null)
			throw new RuntimeException("Underflow");
		else
		{
			Node p = top.node;
			top = top.next;
			return p;
		}
	}
	// peek
	private Node peek()
	{
		return top.node;
	}
	// insert value
	private Node buildTree(char val[])
	{
		Node p;
		Node p1;
		Node p2;
		
		for (int i = 0; i < val.length; i++)
		{
			if (!isOperator(val[i]))
			{
				p = new Node(val[i]);
				push(p);
			}
			else 
			{
				p = new Node(val[i]);
				p1 = pop();
				p2 = pop();
				p.right = p1;
				p.left = p2;
				push(p);
			}
		}
		p = peek();
		pop();
		return p;
	}
	// check function
	private boolean isOperator(char c)
	{
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '%';
	}
	
	public double evaluate(Node p)
	{
		if (p.left == null && p.right == null)
			return p.val - '0';
		else
		{
			double result = 0;
			double left = evaluate(p.left);
			double right = evaluate(p.right);
			char operator = p.val;
			switch (operator)
			{
				case '+': 
					result = left + right;
					break;
				case '-':
					result = left - right;
					break;
				case '*':
					result = left * right;
					break;
				case '/':
					if (right == 0)
						throw new IllegalArgumentException("The dividend is invalid.");
				result = left / right;
				case '^':
					result = Math.pow(left, right);
					break;
				case '%':
					result = left % right;
					break;
			}
			return result;
		}
	}
	// in order traversal
	private void inOrder(Node p)
	{
		if (p != null)
		{
			inOrder(p.left);
			System.out.print(p.val + " ");
			inOrder(p.right);
		}
	}
	// pre order traversal
	private void preOrder(Node p)
	{
		if (p != null)
		{
			System.out.print(p.val + " ");
			preOrder(p.left);
			preOrder(p.right);
		}
	}
	
	public static void main(String args[])
	{
		ExpressionTree obj = new ExpressionTree();
		String st;
		do {		
			System.out.println("No parenthesis!!");
			System.out.println("Please enter the input without space (In postfix): ");
			Scanner s = new Scanner(System.in);
			char[] arr = s.nextLine().toCharArray();
			Node root = obj.buildTree(arr);
			System.out.println("The inorder expression is: ");
			obj.inOrder(root);
			System.out.println();
			System.out.println("The preorder expression is: ");
			obj.preOrder(root);
			System.out.println();
			System.out.println("The result is: " + obj.evaluate(root));
			System.out.println("Enter a new input? (Y/N)");
			Scanner n = new Scanner(System.in);
			st = n.next();
		}
		while(st.equals("Y"));
	}
	
	
	
	
	
	
}