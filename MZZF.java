package �ҵ�������IM������;
import java.net.*;
import java.sql.*;
import java.io.*;
import java.util.*;
public class MZZF 
{ 
	private ServerSocket server;
	private BManager bMan=new BManager();   //��Ϣ�㲥��
	private dl dd=new dl();
	public MZZF(){}
	void startServer()
	{
		try
		{
			server=new ServerSocket(7777,10,InetAddress.getByName("192.168.1.106"));
			System.out.println("�������׽����Ѿ�������");
			while(true)
			{
				Socket socket=server.accept();
				System.out.println("�Ѿ���ͻ�������");
				
				//������ͻ�������ͨ�ŵ��߳� ��������
				new Chat_Thread(socket).start();
				
				//����Ϣ�㲥���б������׽���
				bMan.add(socket);
				
				//�Ե�ǰ�ͻ����������������
				bMan.sendClientInfo();
				
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			
		}
	}
	

	public static void main(String[] args) 
	{
		MZZF server=new MZZF();
		server.dd.sjk();
		server.startServer();                            //���з�����
		
	}
	//��ͻ�������ͨѶ���߳���
	class Chat_Thread extends Thread
	{
		Socket socket;                                  //�׽������ñ���
		private BufferedReader reader;                 //�׽���������
		private PrintWriter writer;                    //�׽��������
		Chat_Thread(Socket socket)
		{
			this.socket=socket;
		}
		public void run()
		{
			try
			{
				//��ȡ�׽������������
				reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer=new PrintWriter(socket.getOutputStream(),true);
				String msg;
				//����������ȡ��Ϣ
				while((msg=reader.readLine())!=null)     
				{
					System.out.println(msg);
					//��ͻ���������Ϣ
					bMan.sendToAll(msg);	
				}
			}
			catch(Exception e){}
			finally
			{
				//��ͻ����Ͽ�����еĴ���
				try
				{
					bMan.remove(socket);        //���׽��ֹ������б���ɾ���׽���
					
					//�ر�������������׽���
					if(reader!=null)
						reader.close();
					if(writer!=null)
						writer.close();
					if(socket!=null)
						socket.close();
					reader=null;
					writer=null;
					socket=null;
					
					//����Ļ��������Ϣ
					System.out.println("�ͻ����뿪");
					bMan.sendClientInfo();	
				}
				catch(Exception e){}
			}
		}
	}
	
	
	
	//��Ϣ�㲥����
	class BManager extends Vector
	{
		Vector v=new Vector();
		BManager(){}
		void add(Socket sock)
		{
			super.add(sock);
		}
		void remove(Socket sock)
		{
			super.remove(sock);
		}
		
		
		
		//�����пͻ��˴���msg ͬ��������
		synchronized void sendToAll(String msg)
		{
			PrintWriter writer=null;
			Socket sock;
			
			for(int i=0;i<size();i++)
			{
				sock=(Socket)elementAt(i);    //��ȡ��i���׽���
				try
				{
					writer=new PrintWriter(sock.getOutputStream(),true);
				}
				catch(IOException ie){}
				if(writer!=null)
					writer.println(msg);
			}
		}
		
		
		//�����пͻ������͵�ǰ����
		synchronized void sendClientInfo()
		{
			String info="��ǰ����������"+size();
			System.out.println(info);
			sendToAll(info);
		}
	}	
	
	class dl
	{
		
		 void sjk()
		 {
			//�������ݿ�
			try {
			      Class.forName("com.mysql.jdbc.Driver");     //����MYSQL JDBC��������   
			     System.out.println("Success loading Mysql Driver!");
			    }
			    catch (Exception e) {
			      System.out.print("Error loading Mysql Driver!");
			      e.printStackTrace();
			    }
			    try {
			      Connection connect = DriverManager.getConnection(
			          "jdbc:mysql://localhost:3306/IM","root","xys5084695");
			           //����URLΪ   jdbc:mysql//��������ַ/���ݿ���  �������2�������ֱ��ǵ�½�û���������
			      
			      System.out.println("Success connect Mysql server!");
			      Statement stmt = connect.createStatement();
			      ResultSet rs = stmt.executeQuery("select * from itable");
			                                                              //user Ϊ���������
			while (rs.next()) {
			        System.out.println(rs.getString("id")+" "+rs.getString("mm"));
			      }
			    }
			    catch (Exception e) 
			    {
			      System.out.print("get data error!");
			      e.printStackTrace();
			      
			  
			    }
		}
	}
}