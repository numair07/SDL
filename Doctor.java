
public class Doctor {
	public int doct_id;
	public String doct_fname;
	public String doct_lname;
	public String specialization;
	public String password;
	
	public Doctor() {
		
	}
	public Doctor(int doct_id, String doct_fname, String doct_lname, String specialization, String password) {
		this.doct_id=doct_id;
		this.doct_fname=doct_fname;
		this.doct_lname=doct_lname;
		this.specialization=specialization;
		this.password=password;
	}
}
