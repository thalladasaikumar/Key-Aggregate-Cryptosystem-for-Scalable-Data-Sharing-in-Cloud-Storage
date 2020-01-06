package aggregation;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.net.URL;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.URLConnection; 
import java.net.Proxy;
import java.net.InetSocketAddress;
public class Register extends JFrame{
	CustomPanel p1;
	JLabel l1,l2,l3,l4,l5;
	JTextField tf1,tf2,tf3,tf4,tf5;
	JButton b1;
	Font f1;
	JTextArea area;
	Login login;
public Register(Login log){
	super("User Registration Screen");
	login = log;
	p1 = new CustomPanel();
	p1.setTitle("    User Registration Screen");
	p1.setLayout(null);
	
	JPanel main = new JPanel();
	main.setLayout(new BorderLayout());

	f1 = new Font("Microsoft Sanserif",Font.BOLD,11);
	
	l1 = new JLabel("Username");
	l1.setForeground(Color.white);
	l1.setFont(f1);
	l1.setBounds(50,50,100,30);
	p1.add(l1);
	tf1 = new JTextField();
	tf1.setFont(f1);
	tf1.setBounds(150,50,120,30);
	p1.add(tf1);

	l2 = new JLabel("Password");
	l2.setForeground(Color.white);
	l2.setFont(f1);
	l2.setBounds(50,100,100,30);
	p1.add(l2);
	tf2 = new JPasswordField();
	tf2.setFont(f1);
	tf2.setBounds(150,100,120,30);
	p1.add(tf2);

	l3 = new JLabel("Contact No");
	l3.setForeground(Color.white);
	l3.setFont(f1);
	l3.setBounds(50,150,100,30);
	p1.add(l3);
	tf3 = new JTextField();
	tf3.setFont(f1);
	tf3.setBounds(150,150,120,30);
	p1.add(tf3);

	l4 = new JLabel("Email ID");
	l4.setForeground(Color.white);
	l4.setFont(f1);
	l4.setBounds(50,200,100,30);
	p1.add(l4);
	tf4 = new JTextField();
	tf4.setFont(f1);
	tf4.setBounds(150,200,120,30);
	p1.add(tf4);

	l5 = new JLabel("Address");
	l5.setForeground(Color.white);
	l5.setFont(f1);
	l5.setBounds(50,250,100,30);
	p1.add(l5);
	tf5 = new JTextField();
	tf5.setFont(f1);
	tf5.setBounds(150,250,120,30);
	p1.add(tf5);
	
	b1 = new JButton("Register");
	b1.setFont(f1);
	b1.setBounds(70,300,100,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			register();
		}
	});

	getContentPane().add(p1,BorderLayout.CENTER);
}
public void register(){
	String user = tf1.getText();
	String password = tf2.getText();
	String contact = tf3.getText();
	String email = tf4.getText();
	String address = tf5.getText();
	if(user == null || user.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Username must be enter");
		tf1.requestFocus();
		return;
	}
	if(password == null || password.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Password must be enter");
		tf2.requestFocus();
		return;
	}
	if(contact == null || contact.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Contact no must be enter");
		tf3.requestFocus();
		return;
	}
	if(email == null || email.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Email id must be enter");
		tf4.requestFocus();
		return;
	}
	if(address == null || address.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Address must be enter");
		tf5.requestFocus();
		return;
	}
	boolean flag = false;
	try{
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost",3128));
		URL url = new URL("https://keyaggregatecryptoapp.appspot.com/AddUser");
		URLConnection con = url.openConnection(proxy);
		con.setDoOutput(true);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		bw.write("t1="+user+"&t2="+password+"&t3="+contact+"&t4="+email+"&t5="+address);
		bw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String response = br.readLine();
		if(response.equals("success")){
			setVisible(false);
			login.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(this,"Error in adding user");
		}
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
}
}