import java.io.Serializable;

@SuppressWarnings("serial")
public class Record implements Serializable{
	int recno;
	String name;
	String address;
	int age;
	int dflag;
	int cflag;
	
	public Record (int recno, String name, String address, int age, int dflag, int cflag) {
		this.name=name;
		this.address=address;
		this.age=age;
		this.dflag=dflag;
		this.cflag=cflag;
		this.recno=recno;
	}
	
	public Record () {
		//empty constructor
	}
	public void GetData() {
		System.out.println("******************************************************************");
		System.out.println("PATIENT DETAILS");
		System.out.println("Name :- " + name );
		System.out.println("Address :- " + address);
		System.out.println("Age :- " + age);
		System.out.println("Diabetes :- " + dflag);
		System.out.println("CVD :- " + cflag);
		System.out.println("******************************************************************");
	}
}