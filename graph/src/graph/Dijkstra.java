package graph;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;
 
public class Dijkstra extends Configured implements Tool {
 

    public static String OUT = "output";
    public static String IN = "input";
 
    public static class TheMapper extends Mapper<LongWritable, Text, LongWritable, Text> {
    	
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
        	
            
            Text word = new Text();
            String line = value.toString();
            String[] sp = line.split(" ");
          
            int distanceadd = Integer.parseInt(sp[1]) + 1;
            String[] PointsTo = sp[2].split(":");
            
            for(int i=0; i<PointsTo.length; i++){
                word.set("VALUE "+distanceadd);//tells me to look at distance value
                context.write(new LongWritable(Integer.parseInt(PointsTo[i])), word);
                word.clear();
            }
            
            word.set("VALUE "+sp[1]);
            context.write( new LongWritable( Integer.parseInt( sp[0] ) ), word );
            word.clear();
 
            word.set("NODES "+sp[2]);
            context.write( new LongWritable( Integer.parseInt( sp[0] ) ), word );
            word.clear();
            
 
        }
        
    }
 
    public static class TheReducer extends Reducer<LongWritable, Text, LongWritable, Text> {
        public void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            
            String nodes = "UNMODED";
            Text word = new Text();
        	
            int lowest = 10009;
            
            for (Text val : values) {
                String[] sp = val.toString().split(" ");
                
                if(sp[0].equalsIgnoreCase("NODES")){
                	
                    nodes = null;
                    nodes = sp[1];
                }else if(sp[0].equalsIgnoreCase("VALUE")){
                	
                   int distance = Integer.parseInt(sp[1]);
                   
                    lowest = Math.min(distance, lowest);
                }
            }
            word.set(lowest+" "+nodes);
            context.write(key, word);
            word.clear();

        }
        
    }
 
   
    public int run(String[] args) throws Exception {
        
        getConf().set("mapred.textoutputformat.separator", " ");
        
        long startTime = System.currentTimeMillis();
        
       IN = "/home/hduser/Desktop/Algo/H/1000000H.txt";
        
       OUT = "/home/hduser/Desktop/Algo/Hout/1000000H---";
 
        String infile = IN;
        String outputfile = OUT + System.nanoTime();
 
        boolean isdone = false;
        boolean success = false;
       
        HashMap <Integer, Integer> _map = new HashMap<Integer, Integer>();
        
        
        while(isdone == false){
 
            Job job = new Job(getConf());
            job.setJarByClass(Dijkstra.class);
            job.setJobName("Dijkstra");
            job.setOutputKeyClass(LongWritable.class);
            job.setOutputValueClass(Text.class);
            job.setMapperClass(TheMapper.class);
            job.setReducerClass(TheReducer.class);
            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(TextOutputFormat.class);
 
            FileInputFormat.addInputPath(job, new Path(infile));
            FileOutputFormat.setOutputPath(job, new Path(outputfile));
 
            success = job.waitForCompletion(true);
 
            
            if(infile != IN){
                String indir = infile.replace("part-r-00000", "");
                Path ddir = new Path(indir);
                FileSystem dfs = FileSystem.get(getConf());
                dfs.delete(ddir, true);
            }
            
            infile = outputfile+"/part-r-00000";
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            outputfile = OUT + System.nanoTime();
            
           
            
            isdone = true;
            Path ofile = new Path(infile);
            FileSystem fs = FileSystem.get(new Configuration());
            BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(ofile)));
 
            HashMap<Integer, Integer> imap = new HashMap<Integer, Integer>();
            String line=br.readLine();
            while (line != null){
                
                String[] sp = line.split(" ");
                int node = Integer.parseInt(sp[0]);
                int distance = Integer.parseInt(sp[1]);
                imap.put(node, distance);
                line=br.readLine();
            }
            if(_map.isEmpty()){
                
                isdone = false;
            }else{
                
                Iterator<Integer> itr = imap.keySet().iterator();
                while(itr.hasNext()){
                    int key = itr.next();
                    int val = imap.get(key);
                    if(_map.get(key) != val){
                        
                        isdone = false;
                    }
                }
            }
            if(isdone == false){
                _map.putAll(imap);
            }
        }
 
        return success ? 0 : 1;
    }
 
    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new Dijkstra(), args));
    }
}