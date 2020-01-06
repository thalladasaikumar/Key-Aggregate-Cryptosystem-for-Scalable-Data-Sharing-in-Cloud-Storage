package aggregation;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.net.URL;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.URLConnection; 
import java.net.Proxy;
import java.net.InetSocketAddress;
public class Login extends JFrame
{
	CustomPanel p1;
	JLabel l1,l2;
	JTextField tf1,tf2;
	JButton b1,b2,b3;
	Font f1;
public Login(){
	super("Login Screen");
	p1 = new CustomPanel();
	p1.setTitle("    Login Screen");
	p1.setLayout(null);
	
	JPanel main = new JPanel();
	main.setLayout(new BorderLayout());

	f1 = new Font("Microsoft Sanserif",Font.BOLD,11);
	JPanel pan1 = new JPanel(); 
	l1 = new JLabel("Username");
	l1.setForeground(Color.white);
	l1.setFont(f1);
	pan1.add(l1);
	tf1 = new JTextField(15);
	tf1.setFont(f1);
	pan1.add(tf1);
	
	JPanel pan2 = new JPanel(); 
	l2 = new JLabel("Password");
	l2.setForeground(Color.white);
	l2.setFont(f1);
	pan2.add(l2);
	tf2 = new JPasswordField(15);
	tf2.setFont(f1);
	pan2.add(tf2);

	JPanel pan3 = new JPanel(); 
	b1 = new JButton("Login");
	b1.setFont(f1);
	pan3.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			login();
		}
	});
	b2 = new JButton("Register");
	b2.setFont(f1);
	pan3.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			register();
		}
	});

	b3 = new JButton("Reset");
	b3.setFont(f1);
	pan3.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			tf1.setText("");
			tf2.setText("");
		}
	});

	main.setBackground(new Color(128, 128, 128));
	pan1.setBackground(new Color(128, 128, 128));
	pan2.setBackground(new Color(128, 128, 128));
	pan3.setBackground(new Color(128, 128, 128));
	main.add(pan1,BorderLayout.NORTH);
	main.add(pan2,BorderLayout.CENTER);
	main.add(pan3,BorderLayout.SOUTH);
	main.setBounds(50,80,300,100);
	p1.add(main);
	getContentPane().add(p1,BorderLayout.CENTER);
}
public void register(){
	Register reg = new Register(this);
	reg.setVisible(true);
	reg.setLocationRelativeTo(null);
	reg.setSize(400,400);
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	Login login = new Login();
	login.setVisible(true);
	login.setSize(400,300);
	login.setLocationRelativeTo(null);
	login.setResizable(false);
}
public void clear(){
	tf1.setText("");
	tf2.setText("");
}
public void login(){
	String user = tf1.getText();
	String pass = tf2.getText();
	
	if(user == null || user.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Username must be enter");
		tf1.requestFocus();
		return;
	}
	if(pass == null || pass.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Password must be enter");
		tf2.requestFocus();
		return;
	}
	try{
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.1.8",3128));
		URL url = new URL("https://keyaggregatecryptoapp.appspot.com/Login");
		URLConnection con = url.openConnection(proxy);
		con.setDoOutput(true);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		bw.write("t1="+user+"&t2="+pass);
		bw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String response = br.readLine();
		if(response.equals("success")){
			setVisible(false);
			UserScreen us = new UserScreen(user,this);
			us.setVisible(true);
			us.setLocationRelativeTo(null);
			us.setSize(600,200);
		}else{
			JOptionPane.showMessageDialog(this,"Error in login");
		}
		
	
		
	}catch(Exception e){
		e.printStackTrace();
	}
}
}