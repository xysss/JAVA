package 我的真正的IM客户端;
import java.awt.*;
import java.net.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
public class MZZK extends Frame implements Runnable
{
	private TextField nameBox=new TextField("<名字>");         //显示用户名
	private TextArea msgView=new TextArea();
	private TextField sendBox=new TextField();                 //消息发送的内容显示
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket socket;
	private JM jj=new JM();
	public MZZK(String title)
	{
		super(title);
		msgView.setEditable(false);
		//添加组件
		add(nameBox,"North");
		add(msgView,"Center");
		add(sendBox,"South");
		
		//关闭方式
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
				
		//添加动作事件监听器
		sendBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					//发送用户名及消息内容
					writer.println(nameBox.getText()+":"+sendBox.getText());
					sendBox.setText("");     //清除sendBox内容
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
				//从输入流读取数据，然后添加到msgView中
				msgView.append(reader.readLine()+"\n");
			}
			catch(IOException ie){}
		}
	}
	
	private void connect()
	{
		try
		{
			msgView.append("尝试与服务器套接字连接\n");
			socket=new Socket("192.168.1.104",7777);
			msgView.append("聊天准备完毕");
			reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer=new PrintWriter(socket.getOutputStream(),true);
			new Thread(this).start();
		}
		catch(Exception e)
		{
			msgView.append("连接失败");
		}
	}

	public static void main(String[] args) 
	{
		JM aa=new JM();
		MZZK client=new MZZK("聊天");
		client.setVisible(true);
		client.connect();
	}
	

}


class JM extends JFrame implements ActionListener
{
	JTextField jtf=new JTextField();                 //创建文本框
	JPasswordField jpf=new JPasswordField(10);               //创建密码框
	JLabel jl1=new JLabel("用户名");                         //创建标签
	JLabel jl2=new JLabel("密码");   
	JButton jb=new JButton("提交");                        //创建按钮
	JLabel jl=new JLabel();                                  //创建标签
	JPanel jp=new JPanel();                                  //创建面板
	public JM()
	{
		this.setTitle("用户登录");
		jp.setLayout(null);                                   //设置面板为空布局
		jl1.setBounds(30, 20, 80, 30);                        //设置用户名标签位置和大小
		jp.add(jl1);                                          //将用户名标签添加到面板中
		jl2.setBounds(30, 70, 80, 30);                       //密码
		jp.add(jl2);
		jtf.setBounds(80,20,180,30);                         //文本框
		jp.add(jtf);
		jpf.setBounds(80,70,180,30);                         //密码框
		jp.add(jpf);
		jb.setBounds(50, 130, 80, 30);                       //按钮
		jp.add(jb);
		jl.setBounds(10, 180, 300, 30);                      //标签
		jp.add(jl);
		jb.addActionListener(this);                          //为按钮注册监听器
		this.add(jp);
		this.setBounds(300, 250, 350, 250);                  //窗体
		this.setVisible(true);                                //窗体可见		
	}
	public void actionPerformed(ActionEvent e)                //监听方法
	{
		String s1=jtf.getText();                              //获取文本框内容
		String s2=new String(jpf.getPassword());                              //    密码框
		jl.setText("你的用户名为"+s1+"密码为"+s2);
	}
}


