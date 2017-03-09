package IM一对多服务器;
import java.io.*;
import java.net.*;
public class IMD{
	static int num=1;                //客户端计数
	
	public static void main(String[] args) 
	{
		ServerSocket serverSocket=null;
		Socket client=null;
		while(true)
		{
			try
			{
				serverSocket=new ServerSocket(4444);      //绑定端口4444监听客户端请求
				System.out.println("套接字已经建立");
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);         //打印出错信息
				System.exit(-1);
			}
			try
			{
				client=serverSocket.accept();    //使用accept阻碍等待客户请求，请求到来时产生一个Socket对象
			}
			catch(Exception e)
			{
				System.out.println("请求接受失败");
				System.exit(-1);
			}
			System.out.println("Client["+IMD.num+"]登录........");
			ServerThread st=new ServerThread(client);
			Thread t=new Thread(st);
			t.start();
			//监听客户端请求，据客户计数创建服务线程并启动
			try
			{
				serverSocket.close();
			}
			catch(IOException e)
			{
				System.out.println("关闭失败");
			}
			num++;
		}
	}
}

class ServerThread implements Runnable
{
	private Socket client;
	public ServerThread(Socket client)
	{
		this.client=client;    //初始化client变量
	}
	public void run()            //线程主体
	{
		try
		{//实现数据传输
			BufferedReader is=new BufferedReader(new InputStreamReader(client.getInputStream()));
			      //由socket对象得到输入流，并构造相应的BufferedReader对象
			PrintWriter os= new PrintWriter(client.getOutputStream());
			         //由socket对象得到输出流，并构造相应的PrintWriter对象
			BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
			   //有系统标准输入设备构造BufferedReader对象
			System.out.println("Client:"+is.readLine());    //在标准输出上打印从客户端读入的字符串
			String inputString=sin.readLine();              //从标准输入读入一字符串
			while(inputString!=null&&!inputString.trim().equals("quit"));
			{
				    //如果输入字符串为quit,则退出循环
				os.println(inputString);    //向客户端输出该字符
				os.flush();   //刷新输出流，使client马上收到该字符
				System.out.println("Server发送的消息为"+inputString);
				System.out.println("Client发送的消息为"+is.readLine());
				inputString=sin.readLine();    //从系统标准输入读取一字符串
			}//继续循环
			os.close();        //关闭socket输出流
			is.close();        //关闭socket输入流
			client.close();    //关闭socket
			System.out.println("聊天结束！");
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}



