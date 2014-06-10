package com.neunlp.wangliang;
import java.io.*;
import java.util.*;
public class FileToVector {
	private List<String>line;
	private HashMap<String,Integer>tf;     //保存单词在该文本中出现的次数
	private HashMap<String,ArrayList<Integer>>df;   //保存单词在哪些文档集中出现过
	private Set<String>allword;                //保存所有的单词
	public void FileToVector(){
		line=new ArrayList<String>();
		tf=new HashMap<String,Integer>();
		df=new HashMap<String,ArrayList<Integer>>();
	}
	//将文档集中的出现过的所有单词进行统计 
	public void readAllFiles(String path){
		
	}
	//返回一篇文章中的所有单词
	public  ArrayList<String> readFile(String path){
		ArrayList<String>word=new ArrayList<>();
		if(path.length()==0){
			System.out.println("Please check file path!");
		}
		BufferedReader br=null;
		try{
			br=new BufferedReader(new FileReader(new File(path)));
			String line;
			while((line=br.readLine())!=null){
				String []words=line.split(" ");
				for(String w:words){
					//去除stop-word
					word.add(w);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return word;
	}
	public void countTF(){
		
	}
	public void compute(){
		
	}
}
