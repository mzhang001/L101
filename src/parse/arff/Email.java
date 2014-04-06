package parse.arff;
import parse.Type;
import weka.core.Utils;
public class Email {
	String subject;
	String from;
	String body;
	String embedded;
	Type type;
	
	public String toString() {
		//System.out.println(from);
		//System.out.println(subject);
		//System.out.println(body);
		//return Utils.quote(from) + ", " + " " + ", " + " " + ", " + type; 
		return Utils.quote(from) + ", " + Utils.quote(subject) + ", " + Utils.quote(body) + ", " + type; 
	}
	
	public String toString(boolean addfrom, boolean addsubject, boolean addbody, boolean addembedded) {
		StringBuffer sb = new StringBuffer();
		if(addfrom) {
			if(from != null)
				sb.append(Utils.quote(from) + ", ");
		}
		if(addsubject) {
			if(subject != null) {
				sb.append(Utils.quote(subject) + ", ");
			}
		}
		if(addbody) {
			if(body != null)
				sb.append(Utils.quote(body) + ", ");
		}
		if(addembedded) {
			if(embedded != null)
				sb.append(Utils.quote(embedded) + ", ");
		}
		sb.append(type);
		return sb.toString();
	}
}
