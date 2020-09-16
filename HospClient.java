import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
public class HospClient {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		PatRecord P = new PatRecord();
		
		// Stack <Integer> st = new Stack <Integer> ();
		Queue <Integer> q = new ArrayDeque <Integer> ();
		int tel=1;
		
		Scanner Sc = new Scanner(System.in);
		Scanner Sc2 = new Scanner(System.in);
		
		Socket socket = new Socket("127.0.0.1", 5125);
		Scanner Sc1 = new Scanner(socket.getInputStream());
		PrintStream p = new PrintStream(socket.getOutputStream());
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		
		Hashtable<Integer, Record>h=new Hashtable<Integer, Record>();
		
		p.println("Connection With Client Established.");
		System.out.println("Retrieving data...");
		
		h=(Hashtable<Integer, Record>)in.readObject();
		P.h=h;
		
		while(true) {
			System.out.println("Enter 1 to Login as a Paitent.");
			System.out.println("Enter 2 to Login as an Admin.");
			System.out.println("Enter 3 to Exit.");
			int choice = Sc.nextInt();
			 if(choice==1) {
				 if(P.h.isEmpty()==true) { 
					 System.out.println("No Patient Data in Rec. , Enter Patient data and try again");
				 }
				 else {
					 String name;
					 System.out.println("Enter Patient Name");
					 name=Sc.next();
					 int key = P.getKey(name);
					 P.Display(key);
					 Record Rd = new Record(key, name, P.getAdd(key), P.getAge(key), P.getDf(key), P.getCf(key));
					 System.out.println("Choose an action :");
					 System.out.println("1. Get appointment (Chat With A Doctor)");
					 System.out.println("2. Return to Login");
					 System.out.println("3. Exit");
					 int ch = Sc.nextInt();
					 if(ch==1) {
						 String Msg2 = "Data";
						 p.println(Msg2);
						 out.writeObject(Rd);
						 out.flush();
						 String Rep1 = Sc1.nextLine();
						 if(Rep1.equals("Recieved")) {
							 System.out.println("Details Sent");
						 }
						 while(true) {
							 System.out.println("Enter 1 to pass a Message to the Doctor");
							 System.out.println("Enter 2 to get Appointment from Doctor");
							 System.out.println("Enter 4 to Continue with other Tasks.");
							 int ch2 = Sc.nextInt();
							 if(ch2==1) {
								System.out.println("Enter The Message :- ");
								String Msg = Sc2.nextLine();
								p.println(Msg);
								String Rep = Sc1.nextLine();
								System.out.println("Doctor's Reply :- \t" + Rep);
							 }
							 else if(ch2==2) {
								String Msg="Appointment";
								p.println(Msg);
								String Rep = Sc1.nextLine();
								if(Rep.equals("Yes") || Rep.equals("yes") || Rep.equals("Y") || Rep.equals("y")) {
									System.out.println("Appointment Approved.");
									if(!q.contains(key)) {
										q.add(key);
									}
									else {
										System.out.println("Appointment already Taken.");
									}
									
								}
								else {
									System.out.println("Appointment Declined.");
								}
							 }
							 else {
								break;
							 }
						 }
					 }
					 else if (ch==3) {
						 break;
					 }
				 }
			 }
			 else if (choice==2){
				 System.out.println("Enter Login ID :- ");
				 String Lgn = Sc.next();
				 System.out.println("Enter Password :- ");
				 String Pass = Sc.next();
				 if(Lgn.equals("admin") && Pass.equals("admin")) {
				 	 tel=1;
					 while(tel!=0) {
							System.out.println("Enter 1 to Integrate Server Data Recieved");
							System.out.println("Enter 2 to insert to insert single patient data in Table and database");
							System.out.println("Enter 3 to check if patient already present in data");
							System.out.println("Enter 4 to display patient data"); 
							System.out.println("Enter 5 to update patient record");
							System.out.println("Enter 6 to display all data");
							System.out.println("Enter 7 to display appointment Queue");
							System.out.println("Enter 8 to exit.");
							int sel = Sc.nextInt();
							int k;
							switch(sel) {
							case 1 :
								P.h=h;
								break;
							case 2 :
								P.Insert();
								break;
							case 3 :
								System.out.print("Enter Record Number (Key) :-\t");
								k = Sc.nextInt();
								if(P.ifPresent(k)) {
									System.out.println("Exists");
								}
								break;
							case 4 :
								System.out.print("Enter Record Number (Key) :-\t");
								 k = Sc.nextInt();
								if(P.ifPresent(k)) {
									P.Display(k);
								}
								break;
							case 5 :
								System.out.print("Enter Record Number (Key) :-\t");
								int key = Sc.nextInt();
								if(P.ifPresent(key)) {
									int s1;
									System.out.print("Enter Choice of Updation :- 1 for age, 2 for name, 3 for address");
									s1=Sc.nextInt();
									if(s1==1) {
										System.out.print("Enter Age :- ");
										int ag = Sc.nextInt();
										P.UpdatePatRec(ag, s1, key);
									}
									else if(s1==2) {
										System.out.print("Enter Name :- ");
										String nam=Sc.next();
										P.UpdatePatRec(nam, s1, key);
									}
									else if(s1==3) {
										System.out.print("Enter Address :- ");
										String add=Sc.next();
										P.UpdatePatRec(add, s1, key);
									}
									else {
										System.out.println("Invalid entry");
									}
								}
								break;		
							case 6 :
								P.displayAll();
								break;
							case 7 :
								System.out.println("Current Appointments :- ");
								for(Integer que : q) {
									System.out.println(que);
								}
								break;
							case 8 :
								System.out.println("Thank You, GoodBye");
								tel=0;
								break;
						}
					 } 
				 }
				 else {
					 System.out.println("Wrong ID/Password, try again Later.");
				 }
			}
			else {
				 break;
			 }
		}
		String Msg="Exit";
		p.println(Msg);
		Sc1.close();
		p.close();
		Sc.close();
		Sc2.close();
		out.close();
		socket.close();
	}

}