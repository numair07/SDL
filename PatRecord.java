import java.util.*;

public class PatRecord {
	public Hashtable <Integer, Record> h = new Hashtable <Integer, Record>();
	Vector <String> v1 = new Vector <String> ();
	Scanner S = new Scanner(System.in);
	int cnt;
	public PatRecord() {
		cnt=1000;
	}
	public void Create(){
		while(true) {
			System.out.println("Enter a patient record? [1] [0]");
			int foo = S.nextInt();
			if(foo==1) {
				Insert();
			}
			else {
				break;
			}	
		}
	}
	public void Modify(String s) {
		Record temp = new Record();
		Set<Integer> keys = h.keySet();
		Iterator<Integer>itr=keys.iterator();
		while(itr.hasNext()) {
			int iter = itr.next();
			temp=h.get(iter);
			if(temp.name.equals(s)) {
				System.out.print("Enter the name of patient :- \t");
				String name = S.next();
				System.out.print("Enter the address of patient :- \t");
				String address = S.next();
				System.out.print("Enter the age of patient :- \t");
				int age = S.nextInt();
				System.out.println("Is the patient diabetic? [1] [0]");
				int diab = S.nextInt();
				System.out.println("Does the patient have any cardiovascular disease? [1] [0]");
				int card = S.nextInt();
				temp.name=name;
				temp.age=age;
				temp.address=address;
				temp.dflag=diab;
				temp.cflag=card;
			}
		}
	}
	public int getKey(String s) {
		Record Temp = new Record();
		Set<Integer> keys = h.keySet();
		Iterator<Integer>itr=keys.iterator();
		while(itr.hasNext()) {
			int iter = itr.next();
			Temp = h.get(iter);
			if(Temp.name.equals(s)) {
				return iter;
			}
		}
		return 0;
	}
	public int getAge(int key) {
		Record Temp = h.get(key);
		return Temp.age;
	}
	
	public String getAdd(int key) {
		Record Temp = h.get(key);
		return Temp.address;
	}
	public int getCf(int key) {
		Record Temp = h.get(key);
		return Temp.cflag;
	}
	public int getDf(int key) {
		Record Temp = h.get(key);
		return Temp.dflag;
	}
	public void Insert() {
			System.out.print("Is this the first visit of the patient : -\t");
			int b = S.nextInt();
			System.out.print("Enter the name of patient :- \t");
			String name = S.next();
			//int flag=1;
			if(b==1) {
				if(v1.contains(name)) {
					System.out.println("Patient Already Exists. Update Record?");
					int up = S.nextInt();
					if(up==0) {
						return;
					}
				}
				else {
					v1.add(name);
				}
			}
			else {
				if (v1.contains(name)) {
					System.out.println("Patient Already Exists. Update Record?");
					int up = S.nextInt();
					if(up==0) {
						return;
					}
					else {
						Modify(name);
					}
				}
				else {
					System.out.println("The Patient does not exist, add to DB?");
					int up = S.nextInt();
					if(up==0) {
						return;
					}
				}
			}
			System.out.print("Enter the address of patient :- \t");
			String address = S.next();
			System.out.print("Enter the age of patient :- \t");
			int age = S.nextInt();
			System.out.println("Is the patient diabetic? [1] [0]");
			int diab = S.nextInt();
			System.out.println("Does the patient have any cardiovascular disease? [1] [0]");
			int card = S.nextInt();
			++cnt;
			Record R = new Record(cnt, name,address,age, diab, card);
			h.put(cnt, R);
	}
	
	public boolean ifPresent(int key) {
		if(h.containsKey(key)) {
			return true;
		}
		System.out.println("Not Found.");
		return false;
	}
	
	public void Display(int key) {
		if(ifPresent(key)) {
			Record Rd = new Record();
			Rd = h.get(key);
			System.out.println("Name :\t"+Rd.name);
			System.out.println("Address :\t"+Rd.address);
			System.out.println("Age :\t"+Rd.age);
			if(Rd.dflag==1) {
				System.out.println("The Patient has Diabetes");
			}else {
				System.out.println("The Patient is Diabetes -VE");
			}
			if(Rd.cflag==1) {
				System.out.println("The Patient has cardiovascular disease");
			}else {
				System.out.println("The Patient Does not have any Cardiovascular Disease");
			}
		}
	}
	
	public <T> void UpdatePatRec(T t, int s, int key) {
		if(s==1) {
			Record Rd = new Record();
			Rd=h.get(key);
			Rd.age=(int) t;
			h.put(key, Rd);
		} else if(s==2) {
			Record Rd=new Record();
			Rd=h.get(key);
			Rd.name=(String) t; 
		} else if(s==3) {
			Record Rd=new Record();
			Rd=h.get(key);
			Rd.address=(String) t; 
		}
	}
	
	public void displayAll() {
		Set <Integer> ex = h.keySet();
		System.out.println("RecNo\tName\tAge\tAddress\tDiabetes\tCardiovascularDisease");
		for(Integer key:ex) {
			Record Rd = new Record();
			Rd=h.get(key);
			System.out.println(key+"\t"+Rd.name+"\t"+Rd.age+"\t"+Rd.address+"\t"+Rd.dflag+"\t"+Rd.cflag);
		}
	}
	
}
