package readkar;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
public class read 
{
	public static void main(String[] args) throws IOException
	{
		MultiMap multiMap = new MultiValueMap();
		
		BufferedReader br = null;
		String curr;
		String[] sp;
		br = new BufferedReader(new FileReader("C:\\Users\\Shankar\\Desktop\\test\\Edge\\roadnet2mil.txt"));
		int count=0;
		while ((curr = br.readLine()) != null )
		{
			if(curr.isEmpty())
			{
				continue;
			}
			else
			{
				sp = curr.split("	");
				if(Double.parseDouble(sp[0]) > 1500000)
					continue;
				else{
					if(Double.parseDouble(sp[1]) > 1500000  )
					{
						multiMap.put(sp[0], sp[1]);
						multiMap.put(sp[1], sp[0]);
					}
					else
					{
						multiMap.put(sp[0], sp[1]);
					}
					}
				
				count++;
			}
		}
		br.close();
		Set<String> keys = multiMap.keySet();
		System.out.println("Done!!....Now begining Print");
		File file = new File("C:\\Users\\Shankar\\Desktop\\test\\Edge\\Adj\\1500000.txt");
		if (!file.exists()) 
		{
			file.createNewFile();
		}
		FileWriter fw1 = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw1 = new BufferedWriter(fw1);
		Integer i;
		for(i=0; i<2200000; i++)
		{
		if (keys.contains(i.toString())) 
		{	
			String good = multiMap.get(i.toString()).toString();
			bw1.append(i.toString()+good);
			bw1.newLine();
			
		}
		else
		{
			continue;
		}
		}
		bw1.close();
		
		
		
		
		File file1 = new File("C:\\Users\\Shankar\\Desktop\\test\\Edge\\Adj\\hadoop\\1500000.txt");
		if (!file1.exists()) 
		{
			file1.createNewFile();
		}
		FileWriter fw11 = new FileWriter(file1.getAbsoluteFile());
		BufferedWriter bw11 = new BufferedWriter(fw11);
		Integer i1;
		for(i1=0; i1<2200000; i1++)
		{
		if (keys.contains(i1.toString())) 
		{	
			String good = multiMap.get(i1.toString()).toString();
			bw11.append(i1.toString()+" 10000 "+good);
			bw11.newLine();
			
		}
		else
		{
			continue;
		}
		}
		bw11.close();
		
		
		
		System.out.println("Done!.......");
		
	}
}
