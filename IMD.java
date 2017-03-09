package IMһ�Զ������;
import java.io.*;
import java.net.*;
public class IMD{
	static int num=1;                //�ͻ��˼���
	
	public static void main(String[] args) 
	{
		ServerSocket serverSocket=null;
		Socket client=null;
		while(true)
		{
			try
			{
				serverSocket=new ServerSocket(4444);      //�󶨶˿�4444�����ͻ�������
				System.out.println("�׽����Ѿ�����");
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);         //��ӡ������Ϣ
				System.exit(-1);
			}
			try
			{
				client=serverSocket.accept();    //ʹ��accept�谭�ȴ��ͻ�����������ʱ����һ��Socket����
			}
			catch(Exception e)
			{
				System.out.println("�������ʧ��");
				System.exit(-1);
			}
			System.out.println("Client["+IMD.num+"]��¼........");
			ServerThread st=new ServerThread(client);
			Thread t=new Thread(st);
			t.start();
			//�����ͻ������󣬾ݿͻ��������������̲߳�����
			try
			{
				serverSocket.close();
			}
			catch(IOException e)
			{
				System.out.println("�ر�ʧ��");
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
		this.client=client;    //��ʼ��client����
	}
	public void run()            //�߳�����
	{
		try
		{//ʵ�����ݴ���
			BufferedReader is=new BufferedReader(new InputStreamReader(client.getInputStream()));
			      //��socket����õ�����������������Ӧ��BufferedReader����
			PrintWriter os= new PrintWriter(client.getOutputStream());
			         //��socket����õ����������������Ӧ��PrintWriter����
			BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
			   //��ϵͳ��׼�����豸����BufferedReader����
			System.out.println("Client:"+is.readLine());    //�ڱ�׼����ϴ�ӡ�ӿͻ��˶�����ַ���
			String inputString=sin.readLine();              //�ӱ�׼�������һ�ַ���
			while(inputString!=null&&!inputString.trim().equals("quit"));
			{
				    //��������ַ���Ϊquit,���˳�ѭ��
				os.println(inputString);    //��ͻ���������ַ�
				os.flush();   //ˢ���������ʹclient�����յ����ַ�
				System.out.println("Server���͵���ϢΪ"+inputString);
				System.out.println("Client���͵���ϢΪ"+is.readLine());
				inputString=sin.readLine();    //��ϵͳ��׼�����ȡһ�ַ���
			}//����ѭ��
			os.close();        //�ر�socket�����
			is.close();        //�ر�socket������
			client.close();    //�ر�socket
			System.out.println("���������");
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}



