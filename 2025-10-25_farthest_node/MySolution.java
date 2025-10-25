import java.util.*;

class Edge{
    int to;
    int count;
    Edge(int to, int count){
        this.to = to;
        this.count = count;
    }
}
class Solution {
    
    List<Edge>[] map;
    int total;
        
    public int solution(int n, int[][] edge) {
        
        total = n;
        
        //각 그래프의 연결성을 담은 배열(인덱스를 노드 번호로 사용 예정).
        map = new ArrayList[n + 1];
        
        //연결 리스트 초기화.
        for(int i = 0; i < map.length; i++){
            map[i] = new ArrayList<>();
        }
        
        for(int[] node : edge){
            int node_1 = node[0];
            int node_2 = node[1];
            
            map[node_1].add(new Edge(node_2, 0));
            map[node_2].add(new Edge(node_1, 0));
        }
        
        int[] maxDistArr = getMaxDist(1);
        
        
        //max산출.
        int max = 0;
        for(int number : maxDistArr){
            max = Math.max(max, number);
        }
        
        //max의 갯수 산출.
        int count = 0;
        for(int number : maxDistArr){
            if(number == max) count++;
        }
        
        return count;
    }
    
    private int[] getMaxDist(int origin){
        int[] result = new int[total + 1];
        
        int MAX = Integer.MAX_VALUE / 2;
        Arrays.fill(result, MAX);
        
        result[0] = -1;//안쓰는 공간 계산에 포함되지 않게.
        result[origin] = 0;
        
        Queue<Edge> pq = new LinkedList<>();
        
        pq.add(new Edge(origin, result[origin]));
        
        while(!pq.isEmpty()){
            Edge thisNode = pq.poll();
            int thisTo = thisNode.to;
            int thisCount = thisNode.count;
            
            
            if(result[thisTo] != thisCount){
                //stale.
                continue;
            }
            
            List<Edge> linked = map[thisTo];
            
            for(Edge nextNode : linked){
                int nextTo = nextNode.to;
                int nextCount = thisCount + 1;

                if(nextCount < result[nextTo]){
                    result[nextTo] = nextCount;
                    pq.add(new Edge(nextTo, nextCount));
                }
            }
        }
        
        return result;
    }
}