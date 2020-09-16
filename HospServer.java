import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HospServer implements Runnable{
	
	static Hashtable <Integer, Record> hpatS = new Hashtable <Integer, Record>();
	Hashtable <Integer, Doctor> hdocS = new Hashtable <Integer, Doctor>();
	
	public ServerSocket S = null;
	public int serverPort = 5125;
	public boolean isStopped    = false;
    public Thread runningThread= null;
	
    public HospServer(int port){
        this.serverPort = port;
    }
    
    private synchronized boolean isStopped() {
        return this.isStopped;
    }
    
    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.S.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
    
    private void openServerSocket() {
        try {
            this.S = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
    
	public void run() {
		synchronized(this){
            this.runningThread = Thread.currentThread();
        }
		openServerSocket();
		while(! isStopped()){
            Socket ss = null;
            try {
                ss = this.S.accept();
        		Scanner Sc = new Scanner(ss.getInputStream());
        		PrintStream p = new PrintStream(ss.getOutputStream());
        		
        		Scanner S1 = new Scanner(System.in);
        		
        		ObjectInputStream in = new ObjectInputStream(ss.getInputStream());
        		ObjectOutputStream out = new ObjectOutputStream(ss.getOutputStream());
        		
        		String Conf = Sc.nextLine();
    			System.out.println(Conf);
    			
    			out.writeObject(hpatS);
    			
    			Record Rec = new Record();
    			
    			while(true) {
    				String Msg = Sc.nextLine();
    				if(Msg.equals("Appointment")) {
    					System.out.println("The Patient is Requesting an appointment, Authorize? [1] [0]");
    					int auth = S1.nextInt();
    					if(auth==1) {
    						p.println("Yes");
    					}
    					else {
    						p.println("No");
    					}
    				}
    				else if(Msg.equals("Exit")) {
    					S1.close();
    					S.close();
    					p.close();
    					Sc.close();
    					System.out.println("Thank You");
    					break;
    				}
    				else if(Msg.equals("Data")) {
    					Record temp;
						try {
							temp = (Record) in.readObject();
							temp.GetData();
							Rec=temp;
	    					p.println("Recieved");
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
    				}
    				else {
    					//String SS = Sc.next();
    					System.out.println("Message from Patient :- " + " " + Rec.recno + " " + Rec.name + " " + Msg);
    					System.out.println("Enter Reply");
    					String pp = S1.nextLine();
    					p.println(pp);
    				}
    				
    			}
        		
        	} 
            catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            
        }
    }
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub 


		Hashtable <Integer, Record> hpat = new Hashtable <Integer, Record>();
		Hashtable <Integer, Doctor> hdoc = new Hashtable <Integer, Doctor>();
		
		System.out.println("Retrieving all data from Database...");
		try {  
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/_31158?autoReconnect=true&useSSL=false","root","Numair@2000");  
			
			Statement stmt=con.createStatement();  
			
			ResultSet rs=stmt.executeQuery("select * from patient");
			
			while(rs.next()) {  
				Record P = new Record(rs.getInt(1), rs.getString(2), rs.getString(6), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				hpat.put(rs.getInt(1), P);
			}
			
			rs=stmt.executeQuery("select Doctor.*, doctor_pass.password from doctor left join doctor_pass on Doctor.doct_id=doctor_pass.doct_id");
			while(rs.next()) {
				Doctor Doc = new Doctor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				hdoc.put(rs.getInt(1), Doc);
			}
			
		}
		catch(Exception e) { 
			System.out.println(e);
		}
		
		Set <Integer> ex = hdoc.keySet();
		System.out.println("Doctor ID\tFirstName\tLastName\tSpecialization");
		
		for(Integer key:ex) {
			Doctor Doc = new Doctor();
			Doc = hdoc.get(key);
			System.out.println(key+"\t"+Doc.doct_fname+"\t"+Doc.doct_lname+"\t"+Doc.specialization);
		}
		
		Scanner Scan = new Scanner (System.in);
		
		System.out.print("Enter Your Doctor's ID : \t");
		int Doc_id = Scan.nextInt();
		Doctor Temp = new Doctor(); 
		Temp = hdoc.get(Doc_id);
		
		System.out.println("Enter Your Password");
		String Pass = Scan.nextLine();
		
		if(Pass.equals(Temp.password)) {
			System.out.println("Welcome Dr. " + Temp.doct_fname + " " + Temp.doct_lname);
		}
		else {
			System.out.println("Sorry Try Again Later...");
			System.exit(0);
		}
		hpatS=hpat;
		
		
		
		Scan.close();
	}
}


/*
Scanner Scan = new Scanner (System.in);
System.out.print("Username :- \t");
String User = Scan.nextLine();
if(User.equals("Doctor") || User.equals("doctor")) {
System.out.print("Enter Password :- \t");
String pass = Scan.next();
if(pass.equals("doctor") || pass.equals("Doctor")) {
	
	
	
}
}
Scan.close();
*/

/*

System.out.println("Retrieving all data from Database...");
		try {  
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/_31158?autoReconnect=true&useSSL=false","root","Numair@2000");  
			
			Statement stmt=con.createStatement();  
			
			ResultSet rs=stmt.executeQuery("select * from patient");
			
			while(rs.next()) {  
				Record P = new Record(rs.getInt(1), rs.getString(2), rs.getString(6), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				hpat.put(rs.getInt(1), P);
			}
			
			rs=stmt.executeQuery("select Doctor.*, doctor_pass.password from doctor left join doctor_pass on Doctor.doct_id=doctor_pass.doct_id");
			while(rs.next()) {
				Doctor Doc = new Doctor(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				hdoc.put(rs.getInt(1), Doc);
			}
			
		}
		catch(Exception e) { 
			System.out.println(e);
		}












*/