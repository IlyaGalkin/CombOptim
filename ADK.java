package com.company;

import java.util.*;


public class ADK {
    public static Integer [] creator(String vect){
        String[]parts=vect.split(" ");
        int size=parts.length;
        ArrayList<Integer> ans = new ArrayList<Integer>();
        for(int i=0;i<size; i++){
            ans.add(Integer.parseInt(parts[i]));
        }
        return ans.toArray(Integer[]::new);
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        Integer[] n_and_m = creator(str);
        if(n_and_m[0] == 1){
            System.out.println(0);
            return;
        }
        if(n_and_m[1] == 0){
            System.out.println(-1);
            return;
        }
        Graph G = new Graph();
        Integer[] e1 = new Integer[2];
        Integer[] t = new Integer[2];
        Integer[] all = new Integer[4];
        for(int i=0; i<n_and_m[0]; i++){
            G.V.add(i);
        }
        ArrayList<Integer> e = new ArrayList<>();
        for(int i=0; i < n_and_m[1]; i++){
            if (in.hasNextLine()){
                str = in.nextLine();
            }
            all = creator(str);
            e1[0] =all[0];
            e1[1] =all[1];
            t[0] = all[2];
            t[1] = all[3];
            G.E.add(all[0]);
            G.E.add(all[1]);
            G.Time.add(all[2]);
            G.Time.add(all[3]);
        }

        int answer = G.finder();
        System.out.println(answer);
    }
}


class Graph{
    ArrayList<Integer> E= new ArrayList<>();
    ArrayList<Integer>Time = new ArrayList<>();
    ArrayList<Integer> V = new ArrayList<>();


    public ArrayList<Integer>[][] creat_matr(int n, int m){
        ArrayList<Integer>[][] graph = new ArrayList[n+1][n+1];
        for(int i = 0; i < m/2; i++)
        {
            int a = E.get(2*i);
            int b = E.get(2*i+1);
            ArrayList<Integer> t = new ArrayList<>();
            t.add(Time.get(2*i));
            t.add(Time.get(2*i+1));
            graph[b][a]= graph[a][b] = t;
        }
        ArrayList<Integer> t = new ArrayList<>();
        t.add(0);
        t.add(0);
        for(int a=0; a < n+1; a++){
            for (int b=0; b<n+1; b++) {
                if (graph[a][b] == null) {
                    graph[a][b] = graph[b][a] = t;
                }
            }
        }
        for(int a=0; a<n+1; a++){
            for(int b=0; b<n+1; b++){
                System.out.print(graph[b][a]+" ");
            }
            System.out.println();
        }
        System.out.println();
        for(int a=0; a<n+1; a++){
            for(int b=0; b<n+1; b++){
                System.out.print(graph[a][b]+" ");
            }
            System.out.println();
        }
        for(int i=0; i<n+1; i++) {
            if (graph[n][i] !=t) {
                return graph;
            }
        }
        System.out.println(-1);
        return graph;
        }
        public boolean uslesnes(ArrayList<Integer>[][] graph, int vNum, int step){
            for(int b=0; b<vNum+1; b++) {
                if(step<graph[vNum][b].get(1)){
                    return false;
                }
            }
        return true;
        }


    public int finder(){
        ArrayList<Integer>[][] graph = creat_matr(V.size(), E.size());
        int vNum = V.size(); // количество вершин
        int steps = 0;
        ArrayList<Integer> whays1 = new ArrayList<>();
        ArrayList<Integer> whays2 = new ArrayList<>();
        for (int ne = 1; ne <= vNum; ne++) {
            if (graph[1][ne].size() > 1) {
                for (int i = 0; i < (graph[1][ne].size())/2; i++)
                    if (((graph[1][ne].get(2*i)) <= steps) & (steps < (graph[1][ne].get(2*i+1))))
                    {
                        whays1.add(ne);
                    }
            }
        }
        steps += 1;
        while(whays1.size()!=0) {
            if(steps>100 & steps%100==0& steps%100==1) {
                boolean bol = uslesnes(graph, vNum, steps);
                if (bol==true){
                    return -1;
                }
                System.out.println(whays1);
                for (int w : whays1) {
                    bol = uslesnes(graph, w, steps);
                    if (bol == false){
                        break;
                    }
                }
            }
            for (int w : whays1){
                for (int ne = 1; ne <= vNum; ne++) {
                    if (graph[w][ne].get(0) > 0 | graph[w][ne].get(1)>0) {
                        for (int i = 0; i <= (graph[w][ne].size()-1)/2; i++) {
                            if (((graph[w][ne].get(i * 2)) <= steps) & (steps < graph[w][ne].get(i * 2 + 1))) {
                                whays2.add(ne);
                                if (ne == vNum) {
                                    steps += 1;
                                    return steps;
                                }
                            }
                        }
                    }
                }
            }
            whays1 =whays2;
            whays2 =new ArrayList<>();
            steps+=1;
            }
        return -1;
    }
}
