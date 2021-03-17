package com.example.demo;

import java.util.*;

public class LinkGraph {
    private Vertex [] vertices;
    class Vertex{
        int num;
        int order;//深度优先生成树
        Vertex parent;//深度优先生成树
        int low;//深度优先生成树
        Edge firstEdge;
        int indegree;//入度数，用来拓扑排序
        int topNum;//拓扑排序号
        boolean known;
        double dist;//计算距离
        Vertex path;//记录路径
        double maxFlow;//记录最大流
        public Vertex(int num){
            this.num=num;
        }
        public void addEdge(Edge aEdge){
            var tempEdge=firstEdge;
            while(tempEdge!=null&&tempEdge.end!=aEdge.end)
                tempEdge=tempEdge.nextEdge;
            if(tempEdge==null){
                tempEdge=firstEdge;
                firstEdge=aEdge;
                aEdge.nextEdge=tempEdge;
            }
            else
                tempEdge.weight+=aEdge.weight;
        }
        public void removeEdge(Edge aEdge){
            if(aEdge==null)
                return;
            if(firstEdge==null)
                throw new NoSuchElementException();
            if(firstEdge.equals(aEdge)){
                firstEdge=firstEdge.nextEdge;
                return;
            }
            Edge currentEdge=firstEdge;
            Edge previousEdge=firstEdge;
            while(currentEdge!=null&&!currentEdge.equals(aEdge)){
                previousEdge=currentEdge;
                currentEdge=currentEdge.nextEdge;
            }
            if(currentEdge==null)
                throw new NoSuchElementException();
            previousEdge.nextEdge=currentEdge.nextEdge;
        }
    }
    class Edge{
        int start;
        int end;
        double weight;
        Edge nextEdge;
        public Edge(){}
        public Edge(int start,int end,double weight){
            this.start=start;
            this.end=end;
            this.weight=weight;
        }
        public boolean equals(Edge aEdge){
            return (start==aEdge.start&&end==aEdge.end&&weight==aEdge.weight);
        }
    }
    public LinkGraph(Vertex[] theVertices,Edge[] theEdges){
        initiateGraph(theVertices,theEdges);
    }

    public LinkGraph(){
        Vertex[] theVertices=new Vertex[6];
        Edge[] theEdges=new Edge[20];
        for(int i=0;i<6;i++){
            theVertices[i]=new Vertex(i);
        }
        theEdges[0]=new Edge(0,1,1);
        theEdges[1]=new Edge(0,2,1);
        theEdges[2]=new Edge(1,0,1);
        theEdges[3]=new Edge(1,3,1);
        theEdges[4]=new Edge(1,2,1);
        theEdges[5]=new Edge(1,4,1);
        theEdges[6]=new Edge(2,0,1);
        theEdges[7]=new Edge(2,3,1);
        theEdges[8]=new Edge(2,1,1);
        theEdges[9]=new Edge(2,4,1);
        theEdges[10]=new Edge(3,1,1);
        theEdges[11]=new Edge(3,2,1);
        theEdges[12]=new Edge(3,5,1);
        theEdges[13]=new Edge(3,4,1);
        theEdges[14]=new Edge(4,1,1);
        theEdges[15]=new Edge(4,2,1);
        theEdges[16]=new Edge(4,3,1);
        theEdges[17]=new Edge(4,5,1);
        theEdges[18]=new Edge(5,3,1);
        theEdges[19]=new Edge(5,4,1);

        initiateGraph(theVertices,theEdges);
    }
    private void initiateGraph(Vertex[] theVertices,Edge[] theEdges){
        vertices=theVertices;
        for(int i=0;i<theEdges.length;i++){
            int index=theEdges[i].start;
            theVertices[theEdges[i].end].indegree++;
            if(vertices[index].firstEdge==null)
                vertices[index].firstEdge=theEdges[i];
            else{
                Edge tempEdge=vertices[index].firstEdge;
                while(tempEdge.nextEdge!=null){
                    tempEdge=tempEdge.nextEdge;
                }
                tempEdge.nextEdge=theEdges[i];
            }
        }
    }

    public void findMaxFlow() throws Exception {
        Edge[] edges;
        LinkGraph resultGraph=new LinkGraph();
        resultGraph.vertices=new Vertex[vertices.length];
        for(int i=0;i<vertices.length;i++){
            resultGraph.vertices[i]=new Vertex(vertices[i].num);
        }
        while(true){
            edges=findAugmentingPath(vertices[0],vertices[vertices.length-1]);
            if(edges==null)
                break;
            Edge[] theEdgesCopy=new Edge[edges.length];
            for(int i=0;i<edges.length;i++)
                theEdgesCopy[i]=new Edge(edges[i].start,edges[i].end,edges[i].weight);
            resultGraph.addEdges(edges);
            this.removeEdges(edges);
            this.addReverseEdges(theEdgesCopy);


        }
        System.out.println("最大流图");
        resultGraph.printGraph();
        System.out.println("残余图");
        printGraph();
        //return resultGraph;
    }
    private void addEdges(Edge[] extraEdges){
        for(Edge aEdge:extraEdges){
            if(aEdge.start>=vertices.length||aEdge.end>=vertices.length)
                throw new NoSuchElementException();
            vertices[aEdge.start].addEdge(aEdge);
            //System.out.println(aEdge.start+" "+aEdge.end+" "+aEdge.weight);
        }
    }
    private void addReverseEdges(Edge[] extraEdges){
        for(Edge aEdge:extraEdges){
            if(aEdge.start>=vertices.length||aEdge.end>=vertices.length)
                throw new NoSuchElementException();
            int temp=aEdge.end;
            aEdge.end=aEdge.start;
            aEdge.start=temp;
        }
        addEdges(extraEdges);
    }
    private void removeReverseEdge(Edge aEdge) throws Exception {
        Edge anotherEdge=new Edge(aEdge.end,aEdge.start,aEdge.weight);
        removeEdge(anotherEdge);
    }
    private  void removeEdge(Edge aEdge) throws Exception {
        Edge tempEdge;
        if(aEdge.start>=vertices.length||aEdge.end>=vertices.length)
            throw new NoSuchElementException();
        tempEdge=vertices[aEdge.start].firstEdge;
        while(tempEdge!=null&&tempEdge.end!=aEdge.end)
            tempEdge=tempEdge.nextEdge;
        if(tempEdge!=null) {
            if(tempEdge.weight>aEdge.weight){
                tempEdge.weight-=aEdge.weight;
            }
            else if(tempEdge.weight==aEdge.weight){
                vertices[aEdge.start].removeEdge(aEdge);
            }
            else {
                throw new Exception();
            }
        }
    }
    private void removeEdges(Edge[] extraEdges) throws Exception {
        Edge tempEdge;
        for(Edge aEdge:extraEdges){
            if(aEdge.start>=vertices.length||aEdge.end>=vertices.length)
                throw new NoSuchElementException();
            tempEdge=vertices[aEdge.start].firstEdge;
            while(tempEdge!=null&&tempEdge.end!=aEdge.end)
                tempEdge=tempEdge.nextEdge;
            if(tempEdge!=null) {
                if(tempEdge.weight>aEdge.weight){
                    tempEdge.weight-=aEdge.weight;
                }
                else if(tempEdge.weight==aEdge.weight){
                    vertices[aEdge.start].removeEdge(aEdge);
                }
                else {
                    throw new Exception();
                }
            }

        }
    }
    private Edge[] findAugmentingPath(Vertex start,Vertex end){
        for(var vertex:vertices){
            vertex.maxFlow=-1;
            vertex.known=false;
        }
        start.maxFlow=Double.POSITIVE_INFINITY;
        start.dist=0;
        while(true){
            Vertex maxVertex=findNewMaxUnknownVertex();
            if(maxVertex==null)
                break;
            maxVertex.known=true;
            var tempEdge=maxVertex.firstEdge;
            while(tempEdge!=null){
                if(!vertices[tempEdge.end].known&&
                        vertices[tempEdge.end].maxFlow<Double.min(maxVertex.maxFlow,tempEdge.weight)){
                    vertices[tempEdge.end].maxFlow=Double.min(maxVertex.maxFlow,tempEdge.weight);
                    vertices[tempEdge.end].path=maxVertex;
                    vertices[tempEdge.end].dist=maxVertex.dist+1;
                }
                tempEdge=tempEdge.nextEdge;
            }
        }
        if(end.maxFlow==-1)
            return null;
        double tempFinal=end.maxFlow;
        var finalEdges=new Edge[(int)end.dist];
        int i=0;
        while(end!=start){
            finalEdges[i++]=new Edge(end.path.num,end.num,tempFinal);
            end=end.path;
        }

        return finalEdges;
    }
    private Vertex findNewMaxUnknownVertex(){
        Vertex tempVertex=null;
        for(var vertex:vertices){
            if(!vertex.known&&tempVertex==null)
                tempVertex=vertex;
            else if(!vertex.known&&vertex.maxFlow>tempVertex.maxFlow)
                tempVertex=vertex;
        }
        return tempVertex;
    }
    public void printGraph(){
        for(int i=0;i<vertices.length;i++){
            Edge currentEdge=vertices[i].firstEdge;
            System.out.print(vertices[i].num+" "+"-->");//" indegree="+vertices[i].indegree+
            while(currentEdge!=null){
                System.out.print(currentEdge.end+"("+currentEdge.weight+")"+" ");
                currentEdge=currentEdge.nextEdge;
            }
            System.out.println();
        }
    }
    public void topology() throws Exception {
        var q=new LinkedList<Vertex>();
        for(var vertex:vertices){
            if(vertex.indegree==0)
                q.addLast(vertex);
        }
        int counter=0;
        while(!q.isEmpty()){
            var v=q.removeFirst();
            v.topNum=counter++;
            System.out.println(v.num);
            var tempEdge=v.firstEdge;
            while(tempEdge!=null){
                if(--vertices[tempEdge.end].indegree==0){
                    q.addLast(vertices[tempEdge.end]);
                }

                tempEdge=tempEdge.nextEdge;
            }
        }
        if(counter!=vertices.length)
            throw new Exception();
        /*
        for(int i=0;i<vertices.length;i++){
            Vertex x=findNewVertexOfIndegreeZero();
            if(x==null)
                throw new Exception();
            x.topNum=i;
            System.out.println(x.num);
            var tempEdge=x.firstEdge;
            while(tempEdge!=null){
                vertices[tempEdge.end].indegree--;
                tempEdge=tempEdge.nextEdge;
            }
        }

         */
    }
    private Vertex findNewVertexOfIndegreeZero(){
        for(Vertex x:vertices){
            if(x.indegree==0&& !x.known){
                x.known=true;
                return x;
            }
        }
        return null;
    }
    public void unweighted(){
        unweighted(vertices[0]);
        for(int i=0;i<vertices.length;i++)
            printPath(vertices[i]);
    }
    private void unweighted(Vertex s){
        var q=new LinkedList<Vertex>();
        for(var vertex:vertices){
            vertex.dist=Double.POSITIVE_INFINITY;
        }
        s.dist=0;
        q.addLast(s);
        Edge tempEdge;
        while (!q.isEmpty()){
            var currentVertex=q.removeFirst();
            tempEdge=currentVertex.firstEdge;
            while(tempEdge!=null){
                if(vertices[tempEdge.end].dist==Double.POSITIVE_INFINITY){
                    vertices[tempEdge.end].dist=currentVertex.dist+1;
                    vertices[tempEdge.end].path=currentVertex;
                    q.addLast(vertices[tempEdge.end]);
                }
                tempEdge=tempEdge.nextEdge;
            }
        }
    }
    public void dijkstra(){
        dijkstra(vertices[0]);
        for (Vertex vertex : vertices) printPath(vertex);
    }
    private void dijkstra(Vertex s){
        for(var vertex:vertices){
            vertex.dist=Double.POSITIVE_INFINITY;
        }
        s.dist=0;
        while(true){
            Vertex minVertex=findNewMinUnknownVertex();
            if(minVertex==null)
                break;
            minVertex.known=true;
            var tempEdge=minVertex.firstEdge;
            while(tempEdge!=null){
                if(!vertices[tempEdge.end].known&&vertices[tempEdge.end].dist>minVertex.dist+tempEdge.weight){//
                    vertices[tempEdge.end].dist=minVertex.dist+tempEdge.weight;
                    vertices[tempEdge.end].path=minVertex;
                }
                tempEdge=tempEdge.nextEdge;
            }
        }
    }
    public void Prim(){
        Prim(vertices[0]);
        for (Vertex vertex : vertices) printPath(vertex);
    }
    private void Prim(Vertex root){
        for(Vertex x:vertices){
            x.dist=Double.POSITIVE_INFINITY;
        }
        root.dist=0;
        while(true){
            Vertex minVertex=findNewMinUnknownVertex();
            if(minVertex==null)
                break;
            minVertex.known=true;
            Edge tempEdge=minVertex.firstEdge;
            while(tempEdge!=null){
                if(!vertices[tempEdge.end].known&&vertices[tempEdge.end].dist>tempEdge.weight){//
                    vertices[tempEdge.end].dist=tempEdge.weight;
                    vertices[tempEdge.end].path=minVertex;
                }
                tempEdge=tempEdge.nextEdge;
            }
        }
    }
    private Vertex findNewMinUnknownVertex(){
        Vertex tempVertex=null;
        for(var vertex:vertices){
            if(!vertex.known&&tempVertex==null)
                tempVertex=vertex;
            else if(!vertex.known&&vertex.dist<tempVertex.dist)
                tempVertex=vertex;
        }
        return tempVertex;
    }

    /*
    public void test(){
        var pair= findAugmentingPath(vertices[0],vertices[vertices.length-1]);
        System.out.println(pair.getSecond());
        var x=pair.getFirst();
        for(Edge aEdge:x){
            System.out.println(aEdge.start+"-->"+aEdge.end);
        }
    }
     */




    private void printPath(Vertex x){
        var stack=new MyLinkedListStack<Vertex>();
        //System.out.print(" the distance is:"+x.dist+" ");
        while(x!=null){
            stack.push(x);
            x=x.path;
        }
        if(!stack.isEmpty())
            System.out.print(stack.pop().num);
        while(!stack.isEmpty()){
            System.out.print("-->"+stack.pop().num);
        }

        System.out.println();
    }
    public void findArticulationPoint(){
        assignOrder(vertices[1]);
        assignLow(vertices[1]);
        //printGraph();
    }
    private int count=0;
    private void assignOrder(Vertex x){
        x.order=count++;
        x.known=true;
        Edge tempEdge=x.firstEdge;
        while(tempEdge!=null){
            if(!vertices[tempEdge.end].known){
                vertices[tempEdge.end].parent=x;
                assignOrder(vertices[tempEdge.end]);
            }

            tempEdge=tempEdge.nextEdge;
        }
    }
    private void assignLow(Vertex x){
        if(x.order==0){
            int countRootChild=0;
            for(Vertex child:vertices){
                if(child.parent==x){
                    countRootChild++;
                    assignLow(child);
                }
            }
            //System.out.println(countRootChild);
            if(countRootChild>1)
                System.out.println(x.num+" is an articulation point");
        }
        else{
            x.low=x.order;
            Edge tempEdge=x.firstEdge;
            while(tempEdge!=null){
                if(vertices[tempEdge.end].order>x.order&&vertices[tempEdge.end].parent==x){
                    assignLow(vertices[tempEdge.end]);
                    if(vertices[tempEdge.end].low>=x.order)
                        System.out.println(x.num+" is an articulation point");
                    x.low= Math.min(x.low, vertices[tempEdge.end].low);
                }
                else if(x.parent!=vertices[tempEdge.end])
                    x.low=Math.min(x.low,vertices[tempEdge.end].order);
                tempEdge=tempEdge.nextEdge;
            }
        }
    }
    private LinkedList<Vertex> vertexList=new LinkedList<Vertex>();
    private LinkedList<Edge> edgeList=new LinkedList<Edge>();
    public void findEulerCircuit() throws Exception {//仍可改进
        findEulerCircuit(vertices[1]);
        for(int i=0;i<edgeList.size();i++){
            if(i==0)
                System.out.print(edgeList.get(i).start+"-->"+edgeList.get(i).end);
            else
                System.out.print("-->"+edgeList.get(i).end);
        }
        System.out.println();
        vertexList.clear();
        edgeList.clear();
    }

    private void findEulerCircuit(Vertex x) throws Exception {
        int index=-1;
        Vertex nextVertex=null;
        Edge tempEdge=x.firstEdge;
        Vertex tempVertex=vertices[x.firstEdge.end];
        for(Vertex aVertex:vertexList){
            index++;
            if(aVertex==vertices[tempEdge.start]){
                break;
            }
        }
        while(true){
            int tempInt=++index;
            edgeList.add(tempInt,tempEdge);
            vertexList.add(tempInt,tempVertex);
            removeEdge(tempEdge);
            removeReverseEdge(tempEdge);
            tempEdge=tempVertex.firstEdge;
            if(tempEdge==null)
                break;
            tempVertex=vertices[tempVertex.firstEdge.end];
        }

        for(Vertex aVertex:vertexList){
            if(aVertex.firstEdge!=null){
                findEulerCircuit(aVertex);
                break;
            }
        }
    }
    

}
