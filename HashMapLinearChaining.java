/**
 * @author Ying Cui
 * A resizing hash table linear chaining in java.
 * Hash function: FNV1-32bits
 * 
 */

import java.io.FileInputStream;
import java.util.Scanner;

public class HashMapLinearChaining
{
	public static class Chain
	{
		private int key;
		private String word;
		private Chain next;
		
		Chain(int key, String word)
		{
			this.key = key;
			this.word = word;
			this.next = null;
		}
		
		public String getWord()
		{
			return word;
		}
		
		public void setWord(String word)
		{
			this.word = word;
		}
		
		public int getKey()
		{
			return key;
		}
		
		public Chain getNext()
		{
			return next;
		}
		
		public void setNext(Chain next)
		{
			this.next = next;
		}
	}
	
	private Chain[] table;
	private int used; // index
	private double limit; // threshold
	private int[] hist; // collision times
	
	// after grow, the hash table should be calculated again
	private void grow()
	{
		Chain[] temp = table;
		int length = 2 * table.length;
		table = new Chain[length];
		for (int i = 0; i < table.length; i++)
			table[i] = null;
		this.used = 0;
		for (int i = 0; i < temp.length; i++)
		{
			if (temp[i] != null) // the old hash table is not null. contain words
			{
				Chain entry = temp[i]; // copy the old one as entry
				while (entry != null)
				{
					add(entry.getWord());
					entry = entry.getNext();
				}
			}
		}
	}
	
	public HashMapLinearChaining(int initialSize)
	{
		table = new Chain[initialSize];
		for (int i = 0; i < table.length; i++)
			table[i] = null;
		this.used = 0;
		this.limit = 0.95 * initialSize;
		hist = new int[11];
	}
	
	public int hash(String word)
	{
		int rv = 0x811c9dc5;
		for (int i = 0; i < word.length(); i++)
		{
			rv ^= word.charAt(i);
			rv *= 0x01000193;
		}
		return Math.abs(rv);
	}
	
	public void add(String word)
	{
		this.used++;
		if (this.used > this.limit)
			grow();
		int code = hash(word) % table.length;
		int key = code % table.length;
		int count = 0;
		if (table[code] == null) // not exist
		{
			table[code] = new Chain(key, word);
			hist[count]++;
		}
		else // exist and collision
		{
			this.used--;
			count++;
			Chain entry = table[code];
			while (entry.getNext() != null) // exist in chain
			{
				entry = entry.getNext();
				count++;
			}
			if (count > 10)
				count = 10;
			hist[count]++;
			entry.setNext(new Chain(key, word)); // come next chain node and add in chain
		}
	}
	
	public boolean contains(String word)
	{
		int code = hash(word) % table.length;
		while (table[code] != null)
		{
			Chain entry = table[code];
			if (entry.getWord().equals(word))
				return true;
			else
				break;
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception
	{
		HashMapLinearChaining dic = new HashMapLinearChaining(150000);
		Scanner dictionary = new Scanner(new FileInputStream("dict.txt"));
		while (dictionary.hasNext())
			dic.add(dictionary.next());
		int[] collision = dic.hist;
		for (int i = 0; i < collision.length; i++)
			System.out.println("Insert time of " + (i+1) + ": " + collision[i]);
		dictionary.close();
		
		Scanner test = new Scanner(new FileInputStream("hw8.dat"));
		while (test.hasNext())
			System.out.println("Test word in dictionary: " + dic.contains(test.next()));
		test.close();
	}
}
