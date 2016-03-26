import java.io.FileInputStream;
import java.util.Scanner;

/**
 * @author Ying Cui
 * Load a dictionary and test file. This program could find whether the words in test file also in dictinary or not.
 * 
 */
class Trie
{
	private static class TrieNode
	{
		TrieNode[] childern;
		boolean isLeaf;
		
		TrieNode()
		{
			childern = new TrieNode[26];
			isLeaf = false;
		}
	}
	private TrieNode root;
	
	// Constructor
	public Trie()
	{
		root = new TrieNode();
	}
	
	// insert a word into the trie
	public void insert(String word)
	{
		TrieNode node = root;
		char[] arr = word.toCharArray();
		for (int i = 0; i < arr.length; i++)
		{
			if (node.childern[arr[i] - 'a'] == null)  // the char doesn't exist. create new one
				node.childern[arr[i] - 'a'] = new TrieNode();
			node = node.childern[arr[i] - 'a'];
		}
		node.isLeaf = true;
	}
	
	// search word in trie
	public boolean searchWord(String word)
	{
		TrieNode node = root;
		char[] arr = word.toCharArray();
		for (int i = 0; i < arr.length; i ++)
		{
			if (node.childern[arr[i] - 'a'] == null)  // the char doesn't exist. return false
				return false;
			else
				node = node.childern[arr[i] - 'a']; // the char exists. come to next node.
		}
		return node.isLeaf;
	}
	
	// search by prefix
	public boolean searchPrefix(String prefix)
	{
		TrieNode node = root;
		char[] arr = prefix.toCharArray();
		for (int i = 0; i < arr.length; i++)
		{
			if (node.childern[arr[i] - 'a'] == null)
				return false;
			else
				node = node.childern[arr[i] - 'a'];
		}
		return true;
	}
}

public class TrieDictionary {
	public static void main(String[] args) throws Exception
	{
		Trie dic = new Trie();
		Scanner dictionary = new Scanner(new FileInputStream("dict.txt"));
		while (dictionary.hasNext())
			dic.insert(dictionary.next());
		dictionary.close();
		Scanner test = new Scanner(new FileInputStream("hw7.dat"));
		while (test.hasNext())
		{
			System.out.println("Test word in dictionary: " + dic.searchWord(test.next()));
		}
		test.close();
	}
}
