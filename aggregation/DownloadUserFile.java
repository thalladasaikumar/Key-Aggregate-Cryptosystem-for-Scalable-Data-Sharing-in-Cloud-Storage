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
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.util.ArrayList;
public class DownloadUserFile extends JFrame
{
	JButton b1;
	JLabel l1;
	JComboBox c1;
	JPanel p2;
	CustomPanel p1;
	Font f1;
	String user;
	ArrayList<String> keys = new ArrayList<String>();
public DownloadUserFile(String usr){
	super("Download User File Screen");
	user = usr;
	p1 = new CustomPanel();
	p1.setTitle("   Download User File Screen");
	p1.setLayout(null);
	f1 = new Font("Microsoft Sanserif",Font.BOLD,11);
	
	p2 = new JPanel(); 
	l1 = new JLabel("File Name");
	l1.setFont(f1);
	p2.add(l1);

	c1 = new JComboBox();
	c1.setFont(f1);
	p2.add(c1);

	b1 = new JButton("Download File");
	b1.setFont(f1);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			download();
		}
	});
	
	p2.setBackground(Color.white);
	p2.setBounds(0,45,600,400);
	p1.add(p2);
	getContentPane().add(p1,BorderLayout.CENTER);
}
public void getFiles(){
	try{
		keys.clear();
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost",3128));
		URL url = new URL("https://keyaggregatecryptoapp.appspot.com/GetFiles");
		URLConnection con = url.openConnection(proxy);
		con.setDoOutput(true);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		bw.write("t1="+user);
		bw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String response = null;
		while((response = br.readLine())!=null){
			String res[] = response.split(",");
			for(String str : res){
				keys.add(str);
				String file[] = str.split("#");
				c1.addItem(file[0]);
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void download(){
	try{
		String file = c1.getSelectedItem().toString().trim();
		int index = c1.getSelectedIndex();
		String key[] = keys.get(index).split("#");
		System.out.println(file+" "+key[0]+" "+key[1]);
		FileInputStream fin = new FileInputStream("D:/cloud/"+user+"/"+file);
		byte b[] = new byte[fin.available()];
		fin.read(b,0,b.length);
		fin.close();
		byte decdata[] = Security.decryption(b,key[1]);
		FileOutputStream fout = new FileOutputStream("C:/"+file);
		fout.write(decdata,0,decdata.length);
		fout.close(); 
		JOptionPane.showMessageDialog(this,"File downloaded in C dir with name "+file);
	}catch(Exception e){
		e.printStackTrace();
	}
}
}