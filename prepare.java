import java.util.*;
import java.util.Scanner;
import java.text.DecimalFormat;
public class prepare {
    java.io.File file = new java.io.File("data.osm");
    Scanner scan = new Scanner(file);
    HashMap<String, node> id_node = new HashMap<>();
    String inLine, id, name, k, v, type, role, dfs;
    DecimalFormat df = new DecimalFormat("#.0000");
    node n;
    Vector<String> s;
    Vector<way> w = new Vector<>();
    Vector<way> motw = new Vector<>();
    Vector<way> truw = new Vector<>();
    Vector<way> priw = new Vector<>();
    Vector<way> secw = new Vector<>();
    Vector<way> terw = new Vector<>();
    HashMap<String, String> namedNode = new HashMap<>();
    HashMap<String, Vector<String>> inWayNode = new HashMap<>();
    int i,j;
    double x,y;
    static double minlat,maxlat,minlon,maxlon;
    public double strDouble(String ss){
        double m = 1, result = 0;
        int size = ss.indexOf(".");
        if (size == -1)size = ss.length();
        for(j = 0;j < size; j++){
            result = result * 10 + (ss.charAt(j) - '0');
        }
        for(j++; j < ss.length(); j++){
            m /= 10;
            result += (ss.charAt(j) - '0') * m;
        }
        return result;
    }
    public prepare() throws Exception{
        while(scan.hasNextLine()){
            inLine = scan.nextLine();
            if(inLine.indexOf("<bou")!=-1){
                i = inLine.indexOf("minlon");
                j = inLine.indexOf("\"", i+8);
                minlon = strDouble(inLine.substring(i+8,j));
                i = inLine.indexOf("minlat");
                j = inLine.indexOf("\"", i+8);
                minlat = strDouble(inLine.substring(i+8,j));
                i = inLine.indexOf("maxlon");
                j = inLine.indexOf("\"", i+8);
                maxlon = strDouble(inLine.substring(i+8,j));
                i = inLine.indexOf("maxlat");
                j = inLine.indexOf("\"", i+8);
                maxlat = strDouble(inLine.substring(i+8,j));
            }
            else if(inLine.indexOf("<node")!=-1){
                name = null;
                inLine = scan.nextLine();
                i = inLine.indexOf("<id");
                id = inLine.substring(i+4);
                inLine = scan.nextLine();
                i = inLine.indexOf("<lon");
                x = strDouble(inLine.substring(i+5));
                inLine = scan.nextLine();
                i = inLine.indexOf("<lat");
                y = strDouble(inLine.substring(i+5));
                inLine = scan.nextLine();
                if((i = inLine.indexOf("<tag")) != -1){
                    inLine = scan.nextLine();
                    i = inLine.indexOf("name");
                    name = inLine.substring(i+5);
                }
                if(name == null){
                    n = new node(id,x,y);
                    id_node.put(id,n);
                }
                else{
                    n = new node(id,x,y,name);
                    id_node.put(id,n);
                    namedNode.put(name, id);
                }
                break;
            }
        }
        while(scan.hasNextLine()){
            inLine = scan.nextLine();
            if(inLine.indexOf("<node")!=-1){
                name = null;
                inLine = scan.nextLine();
                i = inLine.indexOf("<id");
                id = inLine.substring(i+4);
                inLine = scan.nextLine();
                i = inLine.indexOf("<lon");
                x = strDouble(inLine.substring(i+5));
                inLine = scan.nextLine();
                i = inLine.indexOf("<lat");
                y = strDouble(inLine.substring(i+5));
                inLine = scan.nextLine();
                if((i = inLine.indexOf("<tag")) != -1){
                    inLine = scan.nextLine();
                    i = inLine.indexOf("name");
                    name = inLine.substring(i+5);
                }
                if(name == null){
                    n = new node(id,x,y);
                    id_node.put(id,n);
                }
                else{
                    n = new node(id,x,y,name);
                    id_node.put(id,n);
                    namedNode.put(name, id);
                }
            }
            else if(inLine.indexOf("<way")!=-1){
                while(true){
                    inLine = scan.nextLine();
                    if(inLine.indexOf(">")!=-1)break;
                    if((i = inLine.indexOf("<id")) != -1){
                        id = inLine.substring(i+4);
                        w.addElement(new way(id));
                    }
                    else if((i = inLine.indexOf("<nd")) != -1){
                        id = inLine.substring(i+4);
                        if(id_node.get(id)==null){
                            continue;
                        }
                        w.lastElement().insertNode(id);
                        break;
                    }
                }
                while(true){
                    inLine = scan.nextLine();
                    if(inLine.indexOf(">")!=-1)break;
                    if((i = inLine.indexOf("<nd")) != -1){
                        id = inLine.substring(i+4);
                        if(id_node.get(id)==null){
                            continue;
                        }
                        w.lastElement().insertNode(id);
                    }
                    else if((i = inLine.indexOf("<tag")) != -1){
                        j = inLine.indexOf(" ", i+5);
                        k = inLine.substring(i+5,j);
                        v = inLine.substring(j+1);
                        w.lastElement().insertTag(k, v);
                        break;
                    }
                }
                while(true){
                    inLine = scan.nextLine();
                    if(inLine.indexOf(">")!=-1)break;
                    if((i = inLine.indexOf("<tag")) != -1){
                        j = inLine.indexOf(" ", i+5);
                        k = inLine.substring(i+5,j);
                        v = inLine.substring(j+1);
                        w.lastElement().insertTag(k, v);
                    }
                }
                if(w.lastElement().getTag().get("name")!=null)
                    namedNode.put(w.lastElement().getTag().get("name"),w.lastElement().getNdid().firstElement());
                if(w.lastElement().getTag().get("highway")!=null){
                    if(w.lastElement().getTag().get("highway").equals("motorway")){motw.addElement(w.lastElement());}
                    else if(w.lastElement().getTag().get("highway").equals("trunk"))truw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("highway").equals("primary"))priw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("highway").equals("secondary"))secw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("highway").equals("tertiary"))terw.addElement(w.lastElement());
                }
                else if(w.lastElement().getTag().get("waterway")!=null){
                    if(w.lastElement().getTag().get("waterway").equals("riverbank"))motw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("waterway").equals("river"))truw.addElement(w.lastElement());
                    else secw.addElement(w.lastElement());
                }
                else if(w.lastElement().getTag().get("natural")!=null){
                    if(w.lastElement().getTag().get("natural").equals("water"))priw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("natural").equals("grassland")||w.lastElement().getTag().get("natural").equals("wood"))truw.addElement(w.lastElement());
                }
                else if(w.lastElement().getTag().get("railway")!=null){
                    if(w.lastElement().getTag().get("railway").equals("rail"))motw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("railway").equals("subway"))truw.addElement(w.lastElement());
                    else priw.addElement(w.lastElement());
                }
                else if(w.lastElement().getTag().get("leisure")!=null){
                    if(w.lastElement().getTag().get("leisure").equals("park"))truw.addElement(w.lastElement());
                    else priw.addElement(w.lastElement());
                }
                break;
            }
        }
        while(scan.hasNextLine()){
            inLine = scan.nextLine();
            if(inLine.indexOf("<way")!=-1){
                while(true){
                    inLine = scan.nextLine();
                    if(inLine.indexOf(">")!=-1)break;
                    if((i = inLine.indexOf("<id")) != -1){
                        id = inLine.substring(i+4);
                        w.addElement(new way(id));
                    }
                    else if((i = inLine.indexOf("<nd")) != -1){
                        id = inLine.substring(i+4);
                        if(id_node.get(id)==null){
                            continue;
                        }
                        w.lastElement().insertNode(id);
                        break;
                    }
                }
                while(true){
                    inLine = scan.nextLine();
                    if(inLine.indexOf(">")!=-1)break;
                    if((i = inLine.indexOf("<nd")) != -1){
                        id = inLine.substring(i+4);
                        if(id_node.get(id)==null){
                            continue;
                        }
                        w.lastElement().insertNode(id);
                    }
                    else if((i = inLine.indexOf("<tag")) != -1){
                        j = inLine.indexOf(" ", i+5);
                        k = inLine.substring(i+5,j);
                        v = inLine.substring(j+1);
                        w.lastElement().insertTag(k, v);
                        break;
                    }
                }
                while(true){
                    inLine = scan.nextLine();
                    if(inLine.indexOf(">")!=-1)break;
                    if((i = inLine.indexOf("<tag")) != -1){
                        j = inLine.indexOf(" ", i+5);
                        k = inLine.substring(i+5,j);
                        v = inLine.substring(j+1);
                        w.lastElement().insertTag(k, v);
                    }
                }
                if(w.lastElement().getTag().get("name")!=null)
                    namedNode.put(w.lastElement().getTag().get("name"),w.lastElement().getNdid().firstElement());
                if(w.lastElement().getTag().get("highway")!=null){
                    if(w.lastElement().getTag().get("highway").equals("motorway")){motw.addElement(w.lastElement());}
                    else if(w.lastElement().getTag().get("highway").equals("trunk"))truw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("highway").equals("primary"))priw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("highway").equals("secondary"))secw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("highway").equals("tertiary"))terw.addElement(w.lastElement());
                }
                else if(w.lastElement().getTag().get("waterway")!=null){
                    if(w.lastElement().getTag().get("waterway").equals("riverbank"))motw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("waterway").equals("river"))truw.addElement(w.lastElement());
                    else secw.addElement(w.lastElement());
                }
                else if(w.lastElement().getTag().get("natural")!=null){
                    if(w.lastElement().getTag().get("natural").equals("water"))priw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("natural").equals("grassland")||w.lastElement().getTag().get("natural").equals("wood"))truw.addElement(w.lastElement());
                }
                else if(w.lastElement().getTag().get("railway")!=null){
                    if(w.lastElement().getTag().get("railway").equals("rail"))motw.addElement(w.lastElement());
                    else if(w.lastElement().getTag().get("railway").equals("subway"))truw.addElement(w.lastElement());
                    else priw.addElement(w.lastElement());
                }
                else if(w.lastElement().getTag().get("leisure")!=null){
                    if(w.lastElement().getTag().get("leisure").equals("park"))truw.addElement(w.lastElement());
                    else priw.addElement(w.lastElement());
                }
            }
            else if(inLine.indexOf("<relation")!=-1){
                break;
            }
        }
        for(int i = 0; i < w.size(); i++){
            Vector<String> vs = w.elementAt(i).getNdid();
            if(vs.firstElement().equals(vs.lastElement()) == false && w.elementAt(i).getTag().get("highway") != null){
                for(int j = 0; j < vs.size(); j++){
                    dfs = df.format(id_node.get(vs.elementAt(j)).getx()) + "&&" + df.format(id_node.get(vs.elementAt(j)).gety());
                    if(inWayNode.get(dfs)==null){
                        s = new Vector<>();
                        inWayNode.put(dfs, s);
                    }
                    inWayNode.get(dfs).addElement(vs.elementAt(j));
                }
            }
        }
    }
    public Vector<way> getw(){
        return w;
    }
    public Vector<way> getMotw(){
        return motw;
    }
    public Vector<way> getTruw(){
        return truw;
    }
    public Vector<way> getPriw(){
        return priw;
    }
    public Vector<way> getSecw(){
        return secw;
    }
    public Vector<way> getTerw(){
        return terw;
    }
    public HashMap<String, String> getNamedNode(){
        return namedNode;
    }
    public HashMap<String, Vector<String>> getInWayNode(){
        return inWayNode;
    }
    public HashMap<String, node> getIdNode(){
        return id_node;
    }
}
