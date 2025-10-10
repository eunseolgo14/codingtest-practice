import java.util.*;

//각 노드별 간선 정보 담는 클래스.
class Line{
    
    //간선 도달 노드 번호.
    int to;
    //도달까지 비용.
    int cost;
    
    //세팅용 생성자.
    Line(int to, int cost){
        this.to = to;
        this.cost = cost;
    }
}

class Solution {
    
    int NODE_COUNT;
    public int solution(int n, int s, int a, int b, int[][] fares) {
        
        //그래프 내 총 노드 수 전역으로 저장.
        NODE_COUNT = n;
        
        //노드, fares내 연결 정보를 graph로 구현.
        List<Line>[] graph = new ArrayList[NODE_COUNT];
        
        //그래프 초기회 => 내부 List 생성해두기.
        for(int i = 0; i < NODE_COUNT; i++){
            graph[i] = new ArrayList<>();
        }
        
        //fares 내부 연결 정보 graph에 저장.
        for(int i = 0; i < fares.length; i++){
            //fares[i] => [4(0), 1(1), 10(2)]형태로 저장.
            //무방향 그래프 양쪽 경우의 수 모두 저장.
            int node_1 = fares[i][0];
            int node_2 = fares[i][1];
            int cost = fares[i][2];
            
            //node는 1 base, graph index는 0 base.
            graph[node_1 - 1].add(new Line(node_2, cost));
            graph[node_2 - 1].add(new Line(node_1, cost));
        }
        
        //확인이 필요한 세 지점 s,a,b로부타 모든 노드까지의 최소 거리 배열 구하기.
        int[] cheapest_S = getCheapest(s, graph);
        int[] cheapest_A = getCheapest(a, graph);
        int[] cheapest_B = getCheapest(b, graph);
        
        //시뮬레이션 전 요금 합 초기화 => 갱신을 위한 자료형 최대값(임의 무한대) 설정.
        int cheapestCost = Integer.MAX_VALUE;
        int totalCost = 0;
        //전 모드를 탑승 분기점으로 시뮬레이션.
        for(int i = 0; i < NODE_COUNT; i++){
            
            totalCost = cheapest_S[i] + cheapest_A[i] + cheapest_B[i];
            
            //총 비용이 제일 적은 경우의 수 산출.
            if(totalCost < cheapestCost){
                cheapestCost = totalCost;
            }
        }
        
        int answer = cheapestCost;
        return answer;
    }
    
    private int[] getCheapest(int start, List<Line>[] graph){
        //제출용 배열 생성.
        int[] dist = new int[NODE_COUNT];
        
        //임의 무한대 값 생성, 여유분 확보.
        int TEMP_INF = Integer.MAX_VALUE - 200000;
        
        //dist배열 초기화.
        for(int i = 1; i < NODE_COUNT + 1; i++){
            if(i == start){
                dist[i-1] = 0;
            }else{
                dist[i-1] = TEMP_INF;
            }
        }
        
        //[0]:to, [1]:cost.
        PriorityQueue<int[]> priority = new PriorityQueue<>(Comparator.comparingInt(n-> n[1]));
        //시작 좌표 추가.
        priority.add(new int[]{start, 0});
        
        //프라이오리티큐 순회.
        while(!priority.isEmpty()){
            
            int[] data = priority.poll();
            int thisTo = data[0];
            int thisCost = data[1];
            
            //큐에서 꺼낸 비용이 최소비율 배열내 값과 다름 => 무의미한 값, 스킵.
            if(thisCost != dist[thisTo - 1]){
                continue;
            }
            
            //최소비용 데이터 => 인접 노드 간선 확인 필요.
            for(Line nodeLine: graph[thisTo - 1]){
                //src부터 다음 타겟 노드 추출, 다음 타겟 노드까지의 거리 산출.
                int nextTo = nodeLine.to;
                int nextCost = nodeLine.cost + thisCost;
                
                if(nextCost < dist[nextTo - 1]){
                    priority.add(new int[]{nextTo, nextCost});
                    dist[nextTo - 1] = nextCost;
                }
            }
        }
        
        return dist;
    }
}