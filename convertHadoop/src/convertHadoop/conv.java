package convertHadoop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class conv 
{
	public static void main(String[] args) throws IOException
	{
		
		
		BufferedReader br = null;
		String curr;
		String[] sp;
		br = new BufferedReader(new FileReader("C:\\Users\\Shankar\\Desktop\\test\\Edge\\Adj\\1500000.txt"));
		File file = new File("C:\\Users\\Shankar\\Desktop\\test\\Edge\\Adj\\Giraph1500000.txt");
		if (!file.exists()) 
		{
			file.createNewFile();
		}
		FileWriter fw1 = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw1 = new BufferedWriter(fw1);
		while ((curr = br.readLine()) != null )
		{
			curr=curr.replace("]", "]*");
			curr=curr.replace("[", "[*");
			curr=curr.replace(", ", ",1],[");
			curr=curr.replace("[*", ",0,[[");
			curr=curr.replace("]*", ",1]]]");
			bw1.append("["+curr);
			bw1.newLine();
			System.out.println(curr);
		}
		br.close();
		bw1.close();
		System.out.println("Done!.......");
		
	}
}
