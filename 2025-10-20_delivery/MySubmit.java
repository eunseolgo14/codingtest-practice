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
    
    //11:38 - 11:48 문제 이해
    //각 마을 좌, 우로 이동 가능
    //도로 별 이동 시간 다름
    //마을 전체 갯수 n, 충족해야하는 최대 시간 k주어짐
    //k이하로 들어오는 마을만 주문 받아야함.
    
    //11:48 - 11:51 로직 구상
    //road를 돌며 Line 객체 리스트에 저장(양방향이라 한쪽식 기준으로 2개 넣기)
    //Line 객체는 from, to, cost지니고 있음
    //PriotiryQueue마련, cost 오름차순
    //시작 지점에서 각 노드까지의 cost반환하는 유틸 제작.
    //그 값이 K미만인 노드만을 추려 리턴.
    
    //11:51 - 12:30 실제 구현
    
    int totalCityCount;
    int distanceLimit;
    List<Edge>[] cityMap;
    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        
        totalCityCount = N;
        distanceLimit = K;
        
        //각 노드별 연결성 정보를 List<Edge>로 담은 배열 cityMap생성.
        //초기화.
        cityMap = new ArrayList[totalCityCount];
        
        //배열 한칸당 리스트 생성.
        for(int i = 0; i < totalCityCount; i++){
            cityMap[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < road.length; i++){
            int node_1 = road[i][0];
            int node_2 = road[i][1];
            int cost = road[i][2];
            
            //무방향 그래프, 양쪽 기준 전부 삽입.
            cityMap[node_1 - 1].add(new Edge(node_2, cost));
            cityMap[node_2 - 1].add(new Edge(node_1, cost));
        }
        
        int[] distances = findDistanceFrom(1);
        int count = 0;
        
        for(int num: distances){
            if(num <= distanceLimit){
                count++;
            }
        }
        
        return count;
    }
    
    private int[] findDistanceFrom(int origin){
        
        int[] result = new int[totalCityCount];
        int MAX_DIST = 2000;
        
        //제출 배열 초기화.
        //시작지면 거리 0, 다른곳은 갱신을 위한 최대값 설정.
        for(int i = 0; i < totalCityCount; i++){
            if(i == (origin-1)){
                result[i] = 0;
            }else{
                result[i] = MAX_DIST;
            }
        }
        
        //갱신용 우선순위 큐 마련.
        PriorityQueue<int[]> priority = new PriorityQueue<>(Comparator.comparingInt(a->a[1]));
        
        //시작 위치, 시간 0으로 시작.
        priority.add(new int[]{origin,0});
        
        //큐 순회.
        while(!priority.isEmpty()){
            
            int[] curNode  = priority.poll();
            int thisTo = curNode[0];
            int thisCost = curNode[1];
            
            if(thisCost != result[thisTo - 1]){
                //무의미한 기록값.
                continue;
            }
            
            for(Edge line : cityMap[thisTo - 1]){
                //해당 노드에 연결된 모든 이웃 탐색.
                int nectTo = line.to;
                int nextCost = line.cost + thisCost;
                
                if(nextCost < result[nectTo - 1]){
                     priority.add(new int[]{nectTo,nextCost});
                     result[nectTo - 1] = nextCost;
                }
            }
        }
        
        return result;
    }
}