import java.util.*;

class Edge{
    int to;
    int cost;
    Edge(int to, int cost){
        this.to = to;
        this.cost = cost;
    }
}

class Solution {
    
    List<Edge>[] cityGraph;

    public int solution(int N, int[][] road, int K) {

        
        cityGraph = new ArrayList[N];
        
        // 이건 안 되고
        // for(List<Edge> list : cityGraph){
        //     list = new ArrayList<>();
        // }
        
        //이건 된다.
        for(int i = 0; i < cityGraph.length; i++){
            cityGraph[i] = new ArrayList<>();
        }
        
        for(int[] line : road){
            int node_1 = line[0];
            int node_2 = line[1];
            int cost = line[2];
                    
            cityGraph[node_1 - 1].add(new Edge(node_2, cost));
            cityGraph[node_2 - 1].add(new Edge(node_1, cost));
        }
        
        int[] distanceFrom1 = getShortestCost(1, N);
        
        int count = 0;

        
        for(int dist : distanceFrom1){
            if(dist <= K){
                count++;
            }
        }
        return count;
    }
    
    private int[] getShortestCost(int origin, int count){
        
        int[] dist = new int[count];
        int MAX_DIST = 500000;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        
        for(int i = 0; i < count; i++){
            if(i == (origin - 1)){
                dist[i] = 0;
            }else{
                dist[i] = MAX_DIST;
            }
        }
        
        pq.add(new int[]{origin, 0});
        
        while(!pq.isEmpty()){
            
            int[] thisNode = pq.poll();
            
            int thisTo = thisNode[0];
            int thisCost = thisNode[1];

            if(thisCost != dist[thisTo - 1]){
                //stale
                continue;
            }
            
            List<Edge> nextList = cityGraph[thisTo - 1];
            for(Edge hahaha: nextList){
                int nextTo = hahaha.to;
                int nextCost = hahaha.cost + thisCost;
                
                if(nextCost < dist[nextTo - 1]){
                    //갱신.
                    dist[nextTo - 1] = nextCost;
                    pq.add(new int[]{nextTo, nextCost});
                }
            }
        }
        return dist;
    }
}