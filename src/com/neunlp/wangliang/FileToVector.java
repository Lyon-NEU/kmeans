package com.neunlp.wangliang;
import java.io.*;
import java.util.*;
public class FileToVector {
	private List<String>line;
	private HashMap<String,Integer>tf;     //���浥���ڸ��ı��г��ֵĴ���
	private HashMap<String,ArrayList<Integer>>df;   //���浥������Щ�ĵ����г��ֹ�
	private Set<String>allword;                //�������еĵ���
	public void FileToVector(){
		line=new ArrayList<String>();
		tf=new HashMap<String,Integer>();
		df=new HashMap<String,ArrayList<Integer>>();
	}
	//���ĵ����еĳ��ֹ������е��ʽ���ͳ�� 
	public void readAllFiles(String path){
		
	}
	//����һƪ�����е����е���
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
					//ȥ��stop-word
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
