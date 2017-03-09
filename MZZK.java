package �ҵ�������IM�ͻ���;
import java.awt.*;
import java.net.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
public class MZZK extends Frame implements Runnable
{
	private TextField nameBox=new TextField("<����>");         //��ʾ�û���
	private TextArea msgView=new TextArea();
	private TextField sendBox=new TextField();                 //��Ϣ���͵�������ʾ
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	private JM jj=new JM();
	public MZZK(String title)
	{
		super(title);
		msgView.setEditable(false);
		//������
		add(nameBox,"North");
		add(msgView,"Center");
		add(sendBox,"South");
		
		//�رշ�ʽ
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
				
		//��Ӷ����¼�������
		sendBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					//�����û�������Ϣ����
					writer.println(nameBox.getText()+":"+sendBox.getText());
					sendBox.setText("");     //���sendBox����
				}
				catch(Exception ie){}
			}
		});
		pack();
	}

	public void run()
	{
		while(true)
		{
			try
			{
				//����������ȡ���ݣ�Ȼ����ӵ�msgView��
				msgView.append(reader.readLine()+"\n");
			}
			catch(IOException ie){}
		}
	}
	
	private void connect()
	{
		try
		{
			msgView.append("������������׽�������\n");
			socket=new Socket("192.168.1.104",7777);
			msgView.append("����׼�����");
			reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer=new PrintWriter(socket.getOutputStream(),true);
			new Thread(this).start();
		}
		catch(Exception e)
		{
			msgView.append("����ʧ��");
		}
	}

	public static void main(String[] args) 
	{
		JM aa=new JM();
		MZZK client=new MZZK("����");
		client.setVisible(true);
		client.connect();
	}
	

}


class JM extends JFrame implements ActionListener
{
	JTextField jtf=new JTextField();                 //�����ı���
	JPasswordField jpf=new JPasswordField(10);               //���������
	JLabel jl1=new JLabel("�û���");                         //������ǩ
	JLabel jl2=new JLabel("����");   
	JButton jb=new JButton("�ύ");                        //������ť
	JLabel jl=new JLabel();                                  //������ǩ
	JPanel jp=new JPanel();                                  //�������
	public JM()
	{
		this.setTitle("�û���¼");
		jp.setLayout(null);                                   //�������Ϊ�ղ���
		jl1.setBounds(30, 20, 80, 30);                        //�����û�����ǩλ�úʹ�С
		jp.add(jl1);                                          //���û�����ǩ��ӵ������
		jl2.setBounds(30, 70, 80, 30);                       //����
		jp.add(jl2);
		jtf.setBounds(80,20,180,30);                         //�ı���
		jp.add(jtf);
		jpf.setBounds(80,70,180,30);                         //�����
		jp.add(jpf);
		jb.setBounds(50, 130, 80, 30);                       //��ť
		jp.add(jb);
		jl.setBounds(10, 180, 300, 30);                      //��ǩ
		jp.add(jl);
		jb.addActionListener(this);                          //Ϊ��ťע�������
		this.add(jp);
		this.setBounds(300, 250, 350, 250);                  //����
		this.setVisible(true);                                //����ɼ�		
	}
	public void actionPerformed(ActionEvent e)                //��������
	{
		String s1=jtf.getText();                              //��ȡ�ı�������
		String s2=new String(jpf.getPassword());                              //    �����
		jl.setText("����û���Ϊ"+s1+"����Ϊ"+s2);
	}
}


