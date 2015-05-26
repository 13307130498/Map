import java.util.*;

public class node{
	String id;
	double x,y;//x = lon , y = lat;
	HashMap<String, String> tag = new HashMap<>();
	public node(String ID){
		id = ID;
		x = -1;
		y = -1;
	}
	public node(String ID,double lon,double lat){
		id = ID;
		x = lon;
		y = lat;
	}
	public node(String ID,double lon,double lat,String Name){
		id = ID;
		x = lon;
		y = lat;
		tag.put("name", Name);
	}
	public double getx(){
		return x;
	}
	public double gety(){
		return y;
	}
	public HashMap<String, String> getTag(){
		return tag;
	}
}
