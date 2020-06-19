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
        for(int i = 0; i < m/2; i++)
        {
            int a = E.get(2*i);
            int b = E.get(2*i+1);
            t = new ArrayList<>();
            t.add(Time.get(2*i));
            t.add(Time.get(2*i+1));
            if (graph[a][b].get(1)!=0) {
                ArrayList<Integer> t1 = graph[a][b];
                t1.addAll(t);
                t = t1;
            }
            graph[b][a]= graph[a][b] = t;;
        }
        t = new ArrayList<>();
        t.add(0);
        t.add(0);
        /*for(int a=0; a<n+1; a++){
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
        }*/
        return graph;
        }

        public int if_circl(ArrayList<Integer>[][] graph, HashSet<Integer> whays1,HashSet<Integer> whays2){
        int bol=0;
        if((whays1==whays2)|(whays1.size()==1& whays2.size()==1)) {
            return bol;
        }
        bol=1;
        return bol;
        }


    public int finder(){
        ArrayList<Integer>[][] graph = creat_matr(V.size(), E.size());
        int vNum = V.size(); // количество вершин
        int steps = 0;
        HashSet<Integer> whays1 = new HashSet<>();
        HashSet<Integer> whays2 = new HashSet<>();
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
            for (int w : whays1){
                for (int ne = 1; ne <= vNum; ne++) {
                    if (graph[w][ne].get(0) > 0 | graph[w][ne].get(1)>0) {
                        for (int i = 0; i <= (graph[w][ne].size())/2-1; i++) {
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
            if ((steps>=31 & steps%31==0)|(steps>=83 & steps%83==0)){
                int bol = if_circl(graph, whays1, whays2);
                if(bol==0){
                    ArrayList<Integer> s= new ArrayList<>();
                    for (int w1:whays1) {
                        for (int w2 : whays2) {
                            for (int i = 1; i <= vNum; i++) {
                                for (int j = 0; j <= (graph[w1][w2].size())/2-1; j++) {
                                    if (graph[w2][i].get(j * 2) > steps & graph[w2][w1].get(j * 2 + 1) >= graph[w2][i].get(0)) {
                                        s.add(graph[w2][i].get(0) - steps);
                                    }
                                    s.add(graph[w1][w2].get(j*2));
                                }
                            }
                        }
                    }
                    if (s.size()!=0) {
                        Collections.sort(s);
                        if (s.get(0) % 2 == 0 | whays2.size() > 1) {
                            steps = steps + s.get(0);
                        } else {
                            steps += s.get(0) + 1;
                        }
                    }else{
                        return -1;
                    }
                }
            }
            whays1 =whays2;
            whays2 =new HashSet<>();
            steps+=1;
            }
        return -1;
    }
}
