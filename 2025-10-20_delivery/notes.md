# 배달

**작성일**: 2025-10-20</br>
**목표**: 1번 마을에서 출발하여, 도로별 이동 시간을 고려할 때
K 이하의 시간으로 도달할 수 있는 마을의 수를 구한다.

---

## 최초 제출(MySubmit.java) 시 틀린 부분
1. **상수 설정 오류**
   - `MAX_DIST = 2000` 으로 설정했지만, 실제 문제 조건에서 최대 이동거리 합은 2000을 초과할 수 있음.
   - `Integer.MAX_VALUE`에 가까운 충분히 큰 값(예: 500_000)을 사용해야 함.
   - 잘못된 상수로 인해 `PriorityQueue` 비교 시 overflow 또는 거리 비교 오류 발생.

2. **리스트 초기화 실수**
   - `for(List<Edge> list : cityGraph)` 으로 ArrayList를 초기화하려 했으나, 이 구문은 새 인스턴스를 cityGraph 배열 요소에 할당하지 않음.

---

## 2차 제출(MySolution.java) 시 수정한 부분
- 상수 보정 → 문제의 입력 상한을 고려한 안정적인 최대값으로 설정(`int MAX_DIST = 500000;`).
- 명시적 인덱스 기반 반복문 사용.
    ```
    for(int i = 0; i < cityGraph.length; i++){
        cityGraph[i] = new ArrayList<>();
    }
    ```
- 다익스트라 핵심 루프 개선
- `if(thisCost != dist[thisTo - 1]) continue;`
- 스태일 체크로 이미 더 짧은 경로로 방문한 노드는 스킵 → 성능 향상

---

## 헷갈렸던 것/ 새로 배운 것
- 다익스트라 알고리즘의 본질
    - “현재까지 발견된 가장 짧은 거리”를 기준으로 인접 노드를 확장해나가며 최단 경로를 보장하는 알고리즘.
    - BFS는 “가중치가 동일한 그래프”, Dijkstra는 “가중치가 다양한 그래프”에서 사용됨.
- 우선순위 큐의 역할
    - 매 반복마다 가장 짧은 거리의 노드를 먼저 꺼냄.
    - 단순 배열보다 `O((V + E) log V)` 시간복잡도로 효율적.
- Stale Check
    - 큐에 같은 노드가 여러 번 들어갈 수 있기 때문에,
    이미 더 짧은 경로로 갱신된 노드는 건너뛰어야 함.
    - `if (thisCost != dist[thisTo - 1]) continue;`
- INF(무한대) 설정의 중요성
    - 너무 작은 값은 오버플로우, 너무 큰 값은 Integer overflow 또는 비교 불가 오류를 유발할 수 있음.
    - 입력 조건 기반 “충분히 큰 수”를 지정해야 함.

---

## 기억할 포인트
- 무방향 그래프 처리
    - `graph[a].add(new Edge(b, cost));`
    - `graph[b].add(new Edge(a, cost));`
- 우선순위 큐 Comparator
    - `PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));`
- 거리 초기화
    - `for(int i = 0; i < N; i++) dist[i] = (i == start) ? 0 : MAX_DIST;`
- Stale Check 필수
    - `if(cost != dist[node]) continue;`

---
## 한 단락 요약
> 이 문제는 “1번 마을에서 각 마을까지의 최단 거리”를 구한 뒤,</br>
> 해당 거리가 K 이하인 마을을 세는 전형적인 단일 출발점 최단거리 문제이다.</br>
> 핵심은 우선순위 큐를 이용한 다익스트라 알고리즘이며,</br>
> 잘못된 INF 상수 설정으로 예외가 발생했으나,</br> 
> MAX_DIST를 문제 조건에 맞게 조정함으로써 완전히 해결되었다.</br>

