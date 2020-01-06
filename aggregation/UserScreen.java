package aggregation;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.net.URL;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.URLConnection; 
import java.net.Proxy;
import java.net.InetSocketAddress;
public class UserScreen extends JFrame
{
	JButton b1,b2,b3,b4,b5;
	JPanel p2;
	CustomPanel p1;
	Font f1;
	JFileChooser chooser;
	String user;
	Login login;
public UserScreen(String usr,Login log){
	super("User Screen");
	user = usr;
	login = log;
	p1 = new CustomPanel();
	p1.setTitle("   User Screen");
	p1.setLayout(null);
	f1 = new Font("Microsoft Sanserif",Font.BOLD,11);
	
	chooser = new JFileChooser();
	p2 = new JPanel(); 
	b1 = new JButton("Upload File");
	b1.setFont(f1);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(UserScreen.this);
			if(option == JFileChooser.APPROVE_OPTION){
				File file = chooser.getSelectedFile();
				File userdir = new File("D:/cloud/"+user);
				if(!userdir.exists())
					userdir.mkdir();
				try{
					FileInputStream fin = new FileInputStream(file);
					byte b[] = new byte[fin.available()];
					fin.read(b,0,b.length);
					fin.close();
					byte encdata[] = Security.encryption(b);
					String keyset = Security.keydata;
					FileOutputStream fout = new FileOutputStream(userdir.getPath()+"/"+file.getName());
					fout.write(encdata,0,encdata.length);
					fout.close();
					sendKeyToCloud(user,file.getName(),keyset);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	});
	b2 = new JButton("Download File");
	b2.setFont(f1);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			DownloadUserFile dsf = new DownloadUserFile(user);
			dsf.setVisible(true);
			dsf.setSize(500,200);
			dsf.setLocationRelativeTo(null);
			dsf.getFiles();
		}
	});

	b3 = new JButton("Share File");
	b3.setFont(f1);
	p2.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			ShareFile sf = new ShareFile(user);
			sf.setVisible(true);
			sf.setSize(500,250);
			sf.setLocationRelativeTo(null);
			sf.getUser();
			sf.getFiles();
		}
	});

	b4 = new JButton("Download Share File");
	b4.setFont(f1);
	p2.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			DownloadShareFile sf = new DownloadShareFile(user);
			sf.setVisible(true);
			sf.setSize(500,250);
			sf.setLocationRelativeTo(null);
			sf.getFiles();
		}
	});

	b5 = new JButton("Logout");
	b5.setFont(f1);
	p2.add(b5);
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			setVisible(false);
			login.clear();
			login.setVisible(true);
		}
	});

	p2.setBackground(Color.white);
	p2.setBounds(0,45,600,400);
	p1.add(p2);
	getContentPane().add(p1,BorderLayout.CENTER);
}
public void sendKeyToCloud(String user,String file,String key){
	try{
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost",3128));
		URL url = new URL("https://keyaggregatecryptoapp.appspot.com/FileKeys");
		URLConnection con = url.openConnection(proxy);
		con.setDoOutput(true);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		bw.write("t1="+user+"&t2="+file+"&t3="+key);
		bw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String response = br.readLine();
		if(response.equals("success")){
			JOptionPane.showMessageDialog(this,"File key sent to cloud");
		}else{
			JOptionPane.showMessageDialog(this,"Error in sending file key");
		}
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
}

}