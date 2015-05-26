import java.util.Scanner;
public class mapFileProcessor {
	public static void main(String[] args) throws Exception{
		java.io.File file1 = new java.io.File("map.osm");
		java.io.File file2 = new java.io.File("data.osm");
		Scanner input = new Scanner(file1);
		int i,j;
		String in_oneLine;
		String out_oneLine;
		java.io.PrintWriter output = new java.io.PrintWriter(file2);
		while(input.hasNextLine()){
			in_oneLine = input.nextLine();
			if(in_oneLine.indexOf("<bounds")!=-1){
				for(i=0;in_oneLine.charAt(i)!='<';i++);
				out_oneLine = in_oneLine.substring(i);
				output.println(out_oneLine);
			}
			else if(in_oneLine.indexOf("<node")!=-1){
				output.println("<node");
				i = in_oneLine.indexOf("id");
				out_oneLine = "<id ";
				j = in_oneLine.indexOf("\"", i+4);
				out_oneLine += in_oneLine.substring(i+4, j);
				output.println(out_oneLine);
				i = in_oneLine.indexOf("lon");
				out_oneLine = "<lon ";
				j = in_oneLine.indexOf("\"", i+5);
				out_oneLine += in_oneLine.substring(i+5, j);
				output.println(out_oneLine);
				i = in_oneLine.indexOf("lat");
				out_oneLine = "<lat ";
				j = in_oneLine.indexOf("\"", i+5);
				out_oneLine += in_oneLine.substring(i+5, j);
				output.println(out_oneLine);
				if(in_oneLine.indexOf("/>")==-1){
					while(true){
						in_oneLine = input.nextLine();
						if(in_oneLine.indexOf("</node>")!=-1)break;
						if(in_oneLine.indexOf("k=\"name\"")!=-1){
							out_oneLine = "<tag";
							output.println(out_oneLine);
							out_oneLine = "<name ";
							i = in_oneLine.indexOf("v=");
							j = in_oneLine.indexOf("\"", i+3);
							out_oneLine += in_oneLine.substring(i+3, j);
							output.println(out_oneLine);
						}
					}
				}
				output.println(">");
				break;
			}
		}
		while(input.hasNextLine()){
			in_oneLine = input.nextLine();
			if(in_oneLine.indexOf("<node")!=-1){
				output.println("<node");
				i = in_oneLine.indexOf("id");
				out_oneLine = "<id ";
				j = in_oneLine.indexOf("\"", i+4);
				out_oneLine += in_oneLine.substring(i+4, j);
				output.println(out_oneLine);
				i = in_oneLine.indexOf("lon");
				out_oneLine = "<lon ";
				j = in_oneLine.indexOf("\"", i+5);
				out_oneLine += in_oneLine.substring(i+5, j);
				output.println(out_oneLine);
				i = in_oneLine.indexOf("lat");
				out_oneLine = "<lat ";
				j = in_oneLine.indexOf("\"", i+5);
				out_oneLine += in_oneLine.substring(i+5, j);
				output.println(out_oneLine);
				if(in_oneLine.indexOf("/>")==-1){
					while(true){
						in_oneLine = input.nextLine();
						if(in_oneLine.indexOf("</node>")!=-1)break;
						if(in_oneLine.indexOf("k=\"name\"")!=-1){
							out_oneLine = "<tag";
							output.println(out_oneLine);
							out_oneLine = "<name ";
							i = in_oneLine.indexOf("v=");
							j = in_oneLine.indexOf("\"", i+3);
							out_oneLine += in_oneLine.substring(i+3, j);
							output.println(out_oneLine);
						}
					}
				}
				output.println(">");
			}
			else if(in_oneLine.indexOf("<way")!=-1){
				output.println("<way");
				i = in_oneLine.indexOf("id");
				out_oneLine = "<id ";
				j = in_oneLine.indexOf("\"", i+4);
				out_oneLine += in_oneLine.substring(i+4, j);
				output.println(out_oneLine);
				while(in_oneLine.indexOf("</way>")==-1){
					in_oneLine = input.nextLine();
					if(in_oneLine.indexOf("<nd")!=-1){
						out_oneLine = "<nd ";
						i = in_oneLine.indexOf("ref=");
						j = in_oneLine.indexOf("\"", i+5);
						out_oneLine += in_oneLine.substring(i+5, j);
						output.println(out_oneLine);
					}
					else if(in_oneLine.indexOf("<tag")!=-1){
						out_oneLine = "<tag ";
						i = in_oneLine.indexOf("k=");
						j = in_oneLine.indexOf("\"", i+3);
						out_oneLine += in_oneLine.substring(i+3, j);
						out_oneLine += " ";
						i = in_oneLine.indexOf("v=");
						j = in_oneLine.indexOf("\"", i+3);
						out_oneLine += in_oneLine.substring(i+3, j);
						output.println(out_oneLine);
						break;
					}
				}
				while(true){
					in_oneLine = input.nextLine();
					if(in_oneLine.indexOf("</way>")!=-1)break;
					if(in_oneLine.indexOf("<tag")!=-1){
						out_oneLine = "<tag ";
						i = in_oneLine.indexOf("k=");
						j = in_oneLine.indexOf("\"", i+3);
						out_oneLine += in_oneLine.substring(i+3, j);
						out_oneLine += " ";
						i = in_oneLine.indexOf("v=");
						j = in_oneLine.indexOf("\"", i+3);
						out_oneLine += in_oneLine.substring(i+3, j);
						output.println(out_oneLine);
					}
				}
				output.println(">");
				break;
			}
		}
		while(input.hasNextLine()){
			in_oneLine = input.nextLine();
			if(in_oneLine.indexOf("<way")!=-1){
				output.println("<way");
				i = in_oneLine.indexOf("id");
				out_oneLine = "<id ";
				j = in_oneLine.indexOf("\"", i+4);
				out_oneLine += in_oneLine.substring(i+4, j);
				output.println(out_oneLine);
				while(in_oneLine.indexOf("</way>")==-1){
					in_oneLine = input.nextLine();
					if(in_oneLine.indexOf("<nd")!=-1){
						out_oneLine = "<nd ";
						i = in_oneLine.indexOf("ref=");
						j = in_oneLine.indexOf("\"", i+5);
						out_oneLine += in_oneLine.substring(i+5, j);
						output.println(out_oneLine);
					}
					else if(in_oneLine.indexOf("<tag")!=-1){
						out_oneLine = "<tag ";
						i = in_oneLine.indexOf("k=");
						j = in_oneLine.indexOf("\"", i+3);
						out_oneLine += in_oneLine.substring(i+3, j);
						out_oneLine += " ";
						i = in_oneLine.indexOf("v=");
						j = in_oneLine.indexOf("\"", i+3);
						out_oneLine += in_oneLine.substring(i+3, j);
						output.println(out_oneLine);
						break;
					}
				}
				while(true){
					in_oneLine = input.nextLine();
					if(in_oneLine.indexOf("</way>")!=-1)break;
					if(in_oneLine.indexOf("<tag")!=-1){
						out_oneLine = "<tag ";
						i = in_oneLine.indexOf("k=");
						j = in_oneLine.indexOf("\"", i+3);
						out_oneLine += in_oneLine.substring(i+3, j);
						out_oneLine += " ";
						i = in_oneLine.indexOf("v=");
						j = in_oneLine.indexOf("\"", i+3);
						out_oneLine += in_oneLine.substring(i+3, j);
						output.println(out_oneLine);
					}
				}
				output.println(">");
			}
			else if(in_oneLine.indexOf("<relation")!=-1){
                break;
            }
		}
		input.close();
		output.close();
	}
	
}
