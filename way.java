import java.util.*;
public class way{
	String id;
	Vector<String> ndid = new Vector<>();
	HashMap<String, String> tag = new HashMap<>();
	public way(String ID){
		id = ID;
	}
	public void insertNode(String ID){
		ndid.addElement(ID);
	}
	public void insertTag(String k, String v){
		tag.put(k,v);
	}
	public HashMap<String, String> getTag(){
		return tag;
	}
	public Vector<String> getNdid(){
		return ndid;
	}
	public String getId(){
		return id;
	}
} 
