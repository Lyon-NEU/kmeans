package com.neunlp.wangliang;
/**
 * 实现简单的k-menas聚类算法
 * @date:2013/03/10
 * @author: Wang Liang
 * @Version: 1.0
 */
import java.io.*;
import java.util.*;
import java.lang.Math;
import java.text.DecimalFormat;

public class KMeans {
	private int K;                    //聚类数目
	private int iterNum;         //迭代次数
	private int pointNum;      //点的个数
	private int dimension;     //每个点的属性维 度
	private double[][] points;//存放每个点的信息
	private double[][]center;    //每个类的中心
	private ArrayList<ArrayList<Integer>>result;    //存放每次计算后的结果
	public KMeans(int k,int iterNum,int pointNum,int dimension){
		this.K=k;
		this.iterNum=iterNum;
		this.pointNum=pointNum;
		this.dimension=dimension;
		points=new double[pointNum][];
		for(int i=0;i<pointNum;i++){
			points[i]=new double[dimension];
		}
		center=new double[K][];
		for(int j=0;j<K;j++){
			center[j]=new double[dimension];
		}
		result=new ArrayList<ArrayList<Integer>>();
	}
	/**
	 * 计算两点之间的距离，采用欧式公式
	 * @param porint_1: dimensions of point1
	 * @param point_2
	 * @return distance between two points
	 */
	public double EuclidDistance(double[]point_1,double[]point_2){
		if(point_1.length!=point_2.length){        //当两个点的维度不同时返回错误
			System.out.println("Array out of bound!");
			return -1;
		}
		//DecimalFormat df=new DecimalFormat("0.0000");
		double dis=0.0;
		for(int i=0;i<point_1.length;i++){
			dis+=Math.pow(point_1[i]-point_2[i], 2);
		}
		dis=Math.pow(dis, 0.5);
		//System.out.println("The Euclid distance is: "+dis);
		return dis;
	}
	/**
	 * 数据归一化处理
	 * 找每一个属性的最大值，对每个样本的每个属性除以最大值
	 */
	public void normalize(){
		
	}
	/**
	 * 初始化每个类的中心点,初始时分别将最初的几个点设为中心点
	 */
	public void initialize(){
		for(int i=0;i<K;i++){
			for(int j=0;j<dimension;j++){
				center[i][j]=points[i][j];                     //
			} 
			result.add(new ArrayList<Integer>());   //如果去掉这句会有错误
		}
		//System.out.println(result.size());
	}
	/**
	 * 初始分类
	 */
	public void firstCluster(){
		for(int i=0;i<pointNum;i++){
			double min=0f;
			int clsId=-1;
			for(int j=0;j<center.length;j++){
				double newmin=EuclidDistance(points[i],center[j]);
				if(clsId==-1||newmin<min){
					clsId=j;
					min=newmin;
				}
			}
			if(!result.get(clsId).contains(i)){
				result.get(clsId).add(i);
			}
		}
	}
	/**
	 * 从文件中读取每个点的信息
	 * @param path: 数据路径
	 */
	public void readData(String path){
		BufferedReader br=null;
		try{
			br=new BufferedReader(new FileReader(new File(path)));
			String line;
			int lineNum=0;
			while((line=br.readLine())!=null){
				String[] data=line.split(",|(\\s)+");     //按空格或逗号分切数据
//				for(String d:data)
//					System.out.print(d+" ");
//				System.out.println();
				for(int i=0;i<data.length;i++){
					points[lineNum][i]=Double.parseDouble(data[i]);
				}
				lineNum++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 判断两个向量是否相等
	 * @param a 
	 * @param b
	 * @return
	 */
	public boolean IsEqual(double[] a,double[] b){
		if(a.length!=b.length)
			return false;
		for(int i=0;i<a.length;i++){
			if(a[i]>0&&b[i]>0&&a[i]!=b[i])
				return false;
		}
		return true;
	}
	public void update(){
		boolean change=true;
		int times=1;  //迭代次数
		while(change&&times<=iterNum){
			change=false;
			System.out.println("Update Iteration "+(times++)+" time(s)");
			//重新计算每个类的中心点
			for(int i=0;i<K;i++){
				ArrayList<Integer>old_center=result.get(i);   //原先的数据
				double[]new_center=new double[dimension];  //保存新的均值
				
				for(Integer index:old_center){
					for(int j=0;j<dimension;j++){
						new_center[j]+=points[index][j];
					}
				}
				// sum/size
				for(int j=0;j<dimension;j++)    
					new_center[j] /=old_center.size();
				if(!IsEqual(new_center,center[i])){
					center[i]=new_center;
					change=true;
				}
			}
			//清除之前的数据
			for(ArrayList<Integer>cls:result){
				cls.clear();
			}
			
			//重新分配各个点的类别
			for(int i=0;i<pointNum;i++){
				double min=0f;
				int clsId=-1;
				for(int j=0;j<K;j++){
					double newmin=EuclidDistance(center[j],points[i]);
					if(clsId==-1||newmin<min){
						clsId=j;
						min=newmin;
					}
				}
				result.get(clsId).add(i);
			}
		}//while_end
	}
	
	public void outputResult(){
//		System.out.println("Initialize center point: ");
//		for(int m=0;m<K;m++){
//			for(int n=0;n<dimension;n++){
//				System.out.print(center[m][n]+" ");
//			}
//			System.out.println();
//		}
//		for(int i=0;i<pointNum;i++){
//			for(int j=0;j<dimension;j++){
//				System.out.print(points[i][j]+" ");
//			}
//			System.out.println();
//		}
		System.out.println("After cluster: ");
		for(ArrayList<Integer>cls:result){
			System.out.println("Class: "+cls.toString());
		}
	}
	public static void main(String[]args)throws IOException{
		System.out.println("A simple implement of K-means algorithm...");
		KMeans cluster=new KMeans(2,50,10,10);
		cluster.readData("data.txt");
		cluster.initialize();
		cluster.firstCluster();
		cluster.update();
		cluster.outputResult();
	}
}
