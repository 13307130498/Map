import java.util.*;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import java.text.DecimalFormat;
public class mapDraw extends Application{
    prepare pre;
    String wname, pname1, pname2, start, end;
    HashMap<String, node> id_node;
    Vector<way> w;
    Vector<way> vw;
    Vector<way> motw;
    Vector<way> truw;
    Vector<way> priw;
    Vector<way> secw;
    Vector<way> terw;
    Vector<String> names = new Vector<>();
    Vector<String> nodes = new Vector<>();
    HashMap<String, String> namedNode;
    HashMap<String, Vector<String>> inWayNode;
    HashMap<String, String> Pre;
    HashMap<String, Double> dist;
    public class Edge{
        public String id;
        public double w;
        Edge(String ID){
            id = ID;
            w = -1;
        }
    };
    Vector<Edge> e;
    HashMap<String, Vector<Edge>> edge = new HashMap<>();
    Vector<String> vs;
    Pane pane3;
    BorderPane pane;
    Path path, spath;
    Text text;
    GridPane pane1;
    GridPane pane4;
    FlowPane pane2;
    BorderPane pane51;
    BorderPane pane5;
    FlowPane pane52;
    Circle circle;
    TextField tf1, tf2, tf3;
    Button l = new Button("<");
    Button r = new Button(">");
    Button u = new Button("∧");
    Button d = new Button("∨");
    Button b = new Button("+");
    Button s = new Button("-");
    Button fp = new Button("查找地点");
    Button fw = new Button("查找路线");
    Button fp1 = new Button("搜索地点");
    Button fw1 = new Button("搜索路线");
    Label lbal1 = new Label("   ");
    Label lbal2 = new Label(" → ");
    Group g = new Group();
    Group gc = new Group();
    DecimalFormat df = new DecimalFormat("#.0000");
    double xx, yy, xnow, ynow, mnow, nnow;
    boolean bool;
    public void drawline(way way1, String nn, double x, double y, double m, double n){
        int i = 0;
        path = new Path();
        if(m>=0.04)
            path.setStrokeWidth(0.04/m);
        else
            path.setStrokeWidth(1.5);
        path.setStrokeType(StrokeType.OUTSIDE);
        path.setStroke(Color.rgb(235,235,235));
        if(way1.getTag().get("waterway")!=null){
            if(way1.getNdid().firstElement().equals(way1.getNdid().lastElement()))
                path.setFill(Color.rgb(166,191,225));
            else {
                path.setStroke(Color.rgb(166,191,225));
            }
        }
        else if(way1.getTag().get("highway")!=null){
            if(m>=0.04){
                switch(way1.getTag().get("highway")){
                    case "motorway": path.setStroke(Color.rgb(255,196,92));path.setStrokeWidth(0.4/m);break;
                    case "trunk": path.setStroke(Color.rgb(255,223,91));path.setStrokeWidth(0.32/m);break;
                    case "primary": path.setStroke(Color.rgb(246,240,102));path.setStrokeWidth(0.24/m);break;
                    case "secondary": path.setStroke(Color.rgb(246,240,102));path.setStrokeWidth(0.16/m);break;
                    case "tertiary": path.setStroke(Color.rgb(254,254,173));path.setStrokeWidth(0.08/m);break;
                    default: path.setStroke(Color.rgb(230,230,230));break;
                }
            }
            else{
                switch(way1.getTag().get("highway")){
                    case "motorway": path.setStroke(Color.rgb(255,196,92));path.setStrokeWidth(6);break;
                    case "trunk": path.setStroke(Color.rgb(255,223,91));path.setStrokeWidth(5);break;
                    case "primary": path.setStroke(Color.rgb(246,240,102));path.setStrokeWidth(4);break;
                    case "secondary": path.setStroke(Color.rgb(246,240,102));path.setStrokeWidth(3);break;
                    case "tertiary": path.setStroke(Color.rgb(254,254,173));path.setStrokeWidth(2);break;
                    default: path.setStroke(Color.rgb(230,230,230));break;
                }
            }
        }
        else if(way1.getTag().get("natural")!=null){
            switch(way1.getTag().get("natural")){
                case "water": path.setFill(Color.rgb(166,191,225));break;
                default: path.setFill(Color.rgb(193,220,184));break;
            }
        }
        if(way1.getTag().get("railway")!=null){
            switch(way1.getTag().get("railway")){
                case "rail": path.setStroke(Color.rgb(166,171,167));path.getStrokeDashArray().addAll(20d,15d,5d,15d);path.setStrokeDashOffset(0);break;
                case "subway": path.setStroke(Color.rgb(200,175,213));path.getStrokeDashArray().addAll(20d,15d,5d,15d);path.setStrokeDashOffset(0);break;
            }
        }
        if(way1.getTag().get("building")!=null)
            path.setFill(Color.rgb(249,247,244));
        if(way1.getTag().get("leisure")!=null)
            path.setFill(Color.rgb(193,220,184));
        MoveTo move;
        LineTo line;
        wname = way1.getTag().get("name");
        if(m<=0.005)bool =true;
        else if(m<=0.01){
            if(motw.contains(way1)||truw.contains(way1)||priw.contains(way1)||secw.contains(way1)||terw.contains(way1)&&way1.getTag().get("building")==null)
                bool = true;
            else bool = false;
        }
        else if(m<=0.02){
            if((motw.contains(way1)||truw.contains(way1)||priw.contains(way1)||secw.contains(way1))&&way1.getTag().get("building")==null)
                bool = true;
            else bool = false;
        }
        else if(m<=0.04){
            if((motw.contains(way1)||truw.contains(way1)||priw.contains(way1))&&way1.getTag().get("building")==null)
                bool = true;
            else bool = false;
        }
        else if(m<=0.08){
            if((motw.contains(way1)||truw.contains(way1))&&way1.getTag().get("highway")!=null)
                bool = true;
            else bool = false;
        }
        else if(m<=0.32){
            if(motw.contains(way1)&&way1.getTag().get("highway")!=null)
                bool = true;
            else bool = false;
        }
        else
            bool = false;
        for(String str: way1.getNdid()){
            xx = id_node.get(str).getx();
            xx = 700 * (xx - x + m) / m;
            yy = id_node.get(str).gety();
            yy = 400 * (y + n - yy) / n;
            if(nn.equals(str)&&wname!=null&&bool==true){
                if(names.contains(wname)==false&&nodes.contains(str)==false){
                    if((pname1==null||wname.equals(pname1)==false)&&(pname2==null||wname.equals(pname2)==false)){
                        text = new Text(xx,yy,wname);
                        text.setFont(new Font(12.5));
                        text.setFill(Color.rgb(227,138,13));
                        g.getChildren().add(text);
                        names.add(wname);
                        nodes.add(str);
                    }
                }
            }
            if(i == 0){
                move = new MoveTo();
                move.setX(xx);
                move.setY(yy);
                path.getElements().add(move);
                
            }
            else{
                line = new LineTo();
                line.setX(xx);
                line.setY(yy);
                path.getElements().add(line);
            }
            i++;
        }
        
        pane3.getChildren().add(path);
    }
    public void drawmap(double x, double y, double m, double n){
        xnow = x;
        ynow = y;
        mnow = m;
        nnow = n;
        pane3.getChildren().clear();
        g.getChildren().clear();
        names.clear();
        nodes.clear();
        namedNode = pre.getNamedNode();
        inWayNode = pre.getInWayNode();
        if(m<=0.04){
            vw = w;
        }
        else if(m<=0.08){
            vw = new Vector<>();
            vw.addAll(motw);
            vw.addAll(truw);
            vw.addAll(priw);
            vw.addAll(secw);
        }
        else if(m<=0.32){
            vw = new Vector<>();
            vw.addAll(motw);
            vw.addAll(truw);
            vw.addAll(priw);
        }
        else if(m<=0.64){
            vw = new Vector<>();
            vw.addAll(motw);
            vw.addAll(truw);
        }
        else if(m>0.64){
            vw = new Vector<>();
            vw.addAll(motw);
        }
        for(way eachway: vw){
            for(String str: eachway.getNdid()){
                if(id_node.get(str).getx() <= x + m && id_node.get(str).getx() >= x - m && id_node.get(str).gety() <= y + 1.3 * n && id_node.get(str).gety() >= y - n){
                    drawline(eachway, str, x, y, m, n);
                    break;
                }
            }
        }
        if(pname1!=null&&namedNode.get(pname1)!=null){
            double px,py;
            px = id_node.get(namedNode.get(pname1)).getx();
            py = id_node.get(namedNode.get(pname1)).gety();
            if(px <= x + m && px >= x - m && py <= y + 1.3 * n && py >= y - n){
                px = 700 * (px - x + m) / m;
                py = 400 * (y + n - py) / n;
                circle = new Circle(px,py,5,Color.RED);
                pane3.getChildren().add(circle);
                text = new Text(0.97*px,0.97*py,pname1);
                text.setFont(new Font(17));
                text.setFill(Color.rgb(215,0,18));
                g.getChildren().add(text);
            }
        }
        if(pname2!=null&&namedNode.get(pname2)!=null){
            double px,py;
            px = id_node.get(namedNode.get(pname2)).getx();
            py = id_node.get(namedNode.get(pname2)).gety();
            if(px <= x + m && px >= x - m && py <= y + 1.3 * n && py >= y - n){
                px = 700 * (px - x + m) / m;
                py = 400 * (y + n - py) / n;
                circle = new Circle(px,py,5,Color.RED);
                text = new Text(0.97*px,0.97*py,pname2);
                text.setFont(new Font(17));
                text.setFill(Color.rgb(215,0,18));
                g.getChildren().add(text);
                pane3.getChildren().add(circle);
            }
        }
        if(dist != null){
            spath = new Path();
            double px,py;
            String snow, snext;
            spath.setStroke(Color.rgb(27,0,222));
            spath.setStrokeWidth(4);
            MoveTo move;
            LineTo line;
            move = new MoveTo();
            px = 700 * (id_node.get(namedNode.get(pname2)).getx() - x + m) / m;
            py = 400 * (y + n - id_node.get(namedNode.get(pname2)).gety()) / n;
            move.setX(px);
            move.setY(py);
            spath.getElements().add(move);
            line = new LineTo();
            px = 700 * (id_node.get(end).getx() - x + m) / m;
            py = 400 * (y + n - id_node.get(end).gety()) / n;
            line.setX(px);
            line.setY(py);
            spath.getElements().add(line);
            snow = end;
            while(snow.equals(start) == false){
                snext = Pre.get(snow);
                line = new LineTo();
                px = 700 * (id_node.get(snext).getx() - x + m) / m;
                py = 400 * (y + n - id_node.get(snext).gety()) / n;
                line.setX(px);
                line.setY(py);
                spath.getElements().add(line);
                snow = snext;
            }
            line = new LineTo();
            px = 700 * (id_node.get(start).getx() - x + m) / m;
            py = 400 * (y + n - id_node.get(start).gety()) / n;
            line.setX(px);
            line.setY(py);
            spath.getElements().add(line);
            line = new LineTo();
            px = 700 * (id_node.get(namedNode.get(pname1)).getx() - x + m) / m;
            py = 400 * (y + n - id_node.get(namedNode.get(pname1)).gety()) / n;
            line.setX(px);
            line.setY(py);
            spath.getElements().add(line);
        }
        if(spath != null)
            pane3.getChildren().add(spath);
        pane3.getChildren().add(g);
    }
    public void findPlace(String str){
        double px, py;
        if(namedNode.get(str)!=null){
            xnow = id_node.get(namedNode.get(str)).getx();
            ynow = id_node.get(namedNode.get(str)).gety();
        }
    }
    public String findNP(double x, double y){
        String str = df.format(x) + "&&" + df.format(y);
        double m, m1, m2;
        if(inWayNode.get(str)!=null)
            return inWayNode.get(str).lastElement();
        for(m = 0.0001; ; m += 0.0001){
            for(m1 = 0, m2 = m; m1 <= m; m1 += 0.0001){
                str = df.format(x + m1) + "&&" + df.format(y + m2);
                if(inWayNode.get(str)!=null)
                    return inWayNode.get(str).lastElement();
                str = df.format(x - m1) + "&&" + df.format(y + m2);
                if(inWayNode.get(str)!=null)
                    return inWayNode.get(str).lastElement();
                str = df.format(x + m1) + "&&" + df.format(y - m2);
                if(inWayNode.get(str)!=null)
                    return inWayNode.get(str).lastElement();
                str = df.format(x - m1) + "&&" + df.format(y - m2);
                if(inWayNode.get(str)!=null)
                    return inWayNode.get(str).lastElement();
            }
            for(m2 = 0, m1 = m; m2 <= m; m2 += 0.0001){
                str = df.format(x + m1) + "&&" + df.format(y + m2);
                if(inWayNode.get(str)!=null)
                    return inWayNode.get(str).lastElement();
                str = df.format(x - m1) + "&&" + df.format(y + m2);
                if(inWayNode.get(str)!=null)
                    return inWayNode.get(str).lastElement();
                str = df.format(x + m1) + "&&" + df.format(y - m2);
                if(inWayNode.get(str)!=null)
                    return inWayNode.get(str).lastElement();
                str = df.format(x - m1) + "&&" + df.format(y - m2);
                if(inWayNode.get(str)!=null)
                    return inWayNode.get(str).lastElement();
            }
        }
    }
    public void Dijkstra(double bx, double sx, double by, double sy){
        dist = new HashMap<>();
        Pre = new HashMap<>();
        Vector<String> queue = new Vector<>();
        queue.addElement(start);
        dist.put(start, 0.0);
        String snow = null;
        for(int cnt = 0; cnt < queue.size(); cnt++){
            snow = queue.elementAt(cnt);
            if(id_node.get(snow).getx() > bx + 0.02 || id_node.get(snow).getx() < sx - 0.02 || id_node.get(snow).gety() > by + 0.02 || id_node.get(snow).gety() < sy - 0.02)
                continue;
            e = edge.get(snow);
            if(e.firstElement().w == -1){
                for(int i = 0; i < e.size(); i++){
                    e.elementAt(i).w = Math.sqrt((id_node.get(snow).getx() - id_node.get(e.elementAt(i).id).getx()) * (id_node.get(snow).getx() - id_node.get(e.elementAt(i).id).getx()) + (id_node.get(snow).gety() - id_node.get(e.elementAt(i).id).gety()) * (id_node.get(snow).gety() - id_node.get(e.elementAt(i).id).gety()));
                }
            }
            for(int i = 0; i < e.size(); i++){
                if(dist.containsKey(e.elementAt(i).id) == false){
                    dist.put(e.elementAt(i).id, e.elementAt(i).w + dist.get(snow));
                    Pre.put(e.elementAt(i).id, snow);
                    queue.addElement(e.elementAt(i).id);
                }
                else{
                    if(dist.get(e.elementAt(i).id) > e.elementAt(i).w + dist.get(snow)){
                        dist.put(e.elementAt(i).id, e.elementAt(i).w + dist.get(snow));
                        Pre.put(e.elementAt(i).id, snow);
                        queue.addElement(e.elementAt(i).id);
                    }
                }
            }
        }
    }
    public void findWay(){
        double p1x, p1y, p2x, p2y, dx, dy, bx, sx, by, sy;
        if(namedNode.get(pname1)!=null&&namedNode.get(pname2)!=null){
            p1x = id_node.get(namedNode.get(pname1)).getx();
            p1y = id_node.get(namedNode.get(pname1)).gety();
            p2x = id_node.get(namedNode.get(pname2)).getx();
            p2y = id_node.get(namedNode.get(pname2)).gety();
            start = findNP(p1x, p1y);
            end = findNP(p2x, p2y);
            System.out.println(start);
            System.out.println(id_node.get(start).getx()+"  "+id_node.get(start).gety());
            System.out.println(namedNode.get(pname1));
            System.out.println(id_node.get(namedNode.get(pname1)).getx()+"  "+id_node.get(namedNode.get(pname1)).gety());
            System.out.println(end);
            System.out.println(id_node.get(end).getx()+"  "+id_node.get(end).gety());
            System.out.println(namedNode.get(pname2));
            System.out.println(id_node.get(namedNode.get(pname2)).getx()+"  "+id_node.get(namedNode.get(pname2)).gety());
            if(p1x > p2x){
                dx = p1x - p2x;
                bx = p1x + 0.2 * dx;
                sx = p2x - 0.2 * dx;
            }
            else{
                dx = p2x - p1x;
                bx = p2x + 0.2 * dx;
                sx = p1x - 0.2 * dx;
            }
            if(p1y > p2y){
                dy = p1y - p2y;
                by = p1y + 0.2 * dy;
                sy = p2y - 0.2 * dy;
            }
            else{
                dy = p2y - p1y;
                by = p2y + 0.2 * dy;
                sy = p1y - 0.2 * dy;
            }
            if(dx == 0){
                bx = p2x + 0.2 * dy;
                sx = p1x - 0.2 * dy;
            }
            if(dy == 0){
                by = p2y + 0.2 * dx;
                sy = p1y - 0.2 * dx;
            }
            Dijkstra(bx, sx, by, sy);
            drawmap((p1x + p2x) / 2, (p1y + p2y) / 2 - dy / 2, Math.max(dx, dy) * 1.5, Math.max(dx, dy) / 7 * 6);
            pane3.getChildren().add(spath);
        }
        else {
            drawmap(xnow, ynow, mnow, nnow);
        }
    }
    public void getEdge(){
        int i, j;
        for(i = 0; i < w.size(); i++){
            if(w.elementAt(i).getTag().get("highway") == null)continue;
            vs = w.elementAt(i).getNdid();
            if(vs.firstElement().equals(vs.lastElement()))
                continue;
            for(j = 0; j < vs.size() - 1; j++){
                if(edge.get(vs.elementAt(j))==null){
                    e = new Vector<>();
                    edge.put(vs.elementAt(j), e);
                }
                if(edge.get(vs.elementAt(j + 1))==null){
                    e = new Vector<>();
                    edge.put(vs.elementAt(j + 1), e);
                }
                edge.get(vs.elementAt(j)).addElement(new Edge(vs.elementAt(j + 1)));
                edge.get(vs.elementAt(j + 1)).addElement(new Edge(vs.elementAt(j)));
            }
        }
    }
    public void start(Stage primaryStage) throws Exception{
        pre = new prepare();
        id_node = pre.getIdNode();
        w = pre.getw();
        getEdge();
        motw = pre.getMotw();
        truw = pre.getTruw();
        priw = pre.getPriw();
        secw = pre.getSecw();
        terw = pre.getTerw();
        lHandler hl = new lHandler();
        l.setOnAction(hl);
        rHandler hr = new rHandler();
        r.setOnAction(hr);
        uHandler hu = new uHandler();
        u.setOnAction(hu);
        dHandler hd = new dHandler();
        d.setOnAction(hd);
        bHandler hb = new bHandler();
        b.setOnAction(hb);
        sHandler hs = new sHandler();
        s.setOnAction(hs);
        fpHandler hfp = new fpHandler();
        fp.setOnAction(hfp);
        fwHandler hfw = new fwHandler();
        fw.setOnAction(hfw);
        fp1Handler hfp1 = new fp1Handler();
        fp1.setOnAction(hfp1);
        fw1Handler hfw1 = new fw1Handler();
        fw1.setOnAction(hfw1);
        pane1 = new GridPane();
        pane4 = new GridPane();
        pane2 = new FlowPane();
        pane51 = new BorderPane();
        pane5 = new BorderPane();
        pane52 = new FlowPane();
        pane = new BorderPane();
        pane3 = new Pane();
        pane1.setAlignment(Pos.CENTER);
        pane2.setAlignment(Pos.CENTER);
        pane52.setAlignment(Pos.BOTTOM_RIGHT);
        pane51.setTop(u);
        Label lbl = new Label("  ");
        lbl.setPrefSize(10,30);
        pane51.setCenter(lbl);
        pane51.setBottom(d);
        pane52.getChildren().add(b);
        pane52.getChildren().add(new Label("   "));
        pane52.getChildren().add(s);
        pane52.getChildren().add(new Label("   "));
        pane52.getChildren().add(l);
        pane52.getChildren().add(new Label("   "));
        pane52.getChildren().add(pane51);
        pane52.getChildren().add(new Label("   "));
        pane52.getChildren().add(r);
        pane52.getChildren().add(new Label("         "));
        pane1.add(fp,0,0);
        pane1.add(new Label("   "),1,0);
        pane1.add(fw,2,0);
        tf1 = new TextField();
        tf1.setPrefSize(800,25);
        tf2 = new TextField();
        tf2.setPrefSize(400,25);
        tf3 = new TextField();
        tf3.setPrefSize(400,25);
        pane2.getChildren().add(tf1);
        pane2.getChildren().add(lbal1);
        pane2.getChildren().add(fp1);
        drawmap((prepare.minlon + prepare.maxlon) / 2, (prepare.minlat + prepare.maxlat) / 2, 0.02, 0.02 / 7 * 4);
        pane1.setPrefSize(1400,50);
        pane2.setPrefSize(1400,50);
        pane3.setPrefSize(1400,800);
        pane5.setTop(pane1);
        pane5.setCenter(pane2);
        pane.setCenter(pane3);
        pane.setTop(pane5);
        pane.setRight(pane52);
        Scene scene = new Scene(pane, 1400, 800,Color.rgb(238,236,218));
        primaryStage.setTitle("map");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    class lHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
            drawmap(xnow - mnow / 1.5, ynow, mnow, nnow);
        }
    }
    class uHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
            drawmap(xnow, ynow + nnow / 1.5, mnow, nnow);
        }
    }
    class rHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
            drawmap(xnow + mnow / 2, ynow, mnow, nnow);
        }
    }
    class dHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
            drawmap(xnow, ynow - nnow / 2, mnow, nnow);
        }
    }
    class bHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
            drawmap(xnow, ynow, mnow * 0.5, nnow * 0.5);
        }
    }
    class sHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
            drawmap(xnow, ynow, mnow * 2, nnow * 2);
        }
    }
    class fwHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
            pane2.getChildren().clear();
            pane2.getChildren().add(tf2);
            pane2.getChildren().add(lbal2);
            pane2.getChildren().add(tf3);
            pane2.getChildren().add(lbal1);
            pane2.getChildren().add(fw1);
        }
    }
    class fpHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
            pane2.getChildren().clear();
            pane2.getChildren().add(tf1);
            pane2.getChildren().add(lbal1);
            pane2.getChildren().add(fp1);
        }
    }
    class fp1Handler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
            pname1 = tf1.getText();
            spath = null;
            pname2 = null;
            dist = null;
            Pre = null;
            spath = null;
            findPlace(pname1);
            drawmap(xnow,ynow,0.01,0.01/ 7 * 4);
        }
    }
    class fw1Handler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent ae){
            spath = null;
            dist = null;
            Pre = null;
            pname1 = tf2.getText();
            pname2 = tf3.getText();
            findWay();
        }
    }
}

