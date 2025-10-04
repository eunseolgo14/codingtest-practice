# 프로세스

**작성일**: 2025-10-04</br>
**목표**: 마킹된 문서가 몇 번째로 출력되는지 구하기

---

## 최초 제출(MySubmit.java) 시 틀린 부분
1. **반복 횟수 카운트 착각**
   - while 루프 반복 횟수를 정답으로 사용했음.
   - 문제는 “내 문서가 몇 번째로 출력되었는가”를 묻는 것.
   - “출력 시점”을 카운트해야 함.

2. **마킹 문서 추적 불가**
   - 단순히 `int`만 큐에 넣어서 내 문서를 특정할 수 없었음.
   - `Document` 객체를 만들어 `isMarked` 플래그 추가.

3. **hasBigger 변수 초기화 위치 오류**
    - while 바깥에서 `boolean hasBigger`를 선언하여 `true`가 계속 유지됨.
    - while 내부에서 매번 새로 초기화해야 했음.

4. **출력 처리 시점 불일치**
    - 출력되지 않은 경우에도 카운트를 증가시켜 정답이 틀림.
    - 반드시 문서가 실제 출력될 때만 `answer++`.

---

## 2차 제출(MySolution.java) 시 수정한 부분
- `Document` 클래스에 `isMarked` 플래그 추가하여 내 문서 추적 가능.
-  출력될 때만 `answer++` 증가하도록 수정.
- `hasBigger`를 `while` 루프 내부에서 초기화하도록 변경.
- `break` 대신 `return`으로 명확하게 정답 반환.

---

## 3차 제출(최종, PriorityQueue 버전) 시 개선한 부분
- `PriorityQueue<Integer> descQueue = new PriorityQueue<>(Collections.reverseOrder())` 사용.
-  이제 매번 큐 전체를 돌며 “더 큰 우선순위가 있는지” 확인할 필요 없음.
- `descQueue.peek()`이 현재 최고 우선순위를 알려주므로, O(1) 비교 가능.
- 출력할 때는 `descQueue.poll()`로 최대값을 제거하여 동기화 유지.

---

## 헷갈렸던 것/ 새로 배운 것
- `PriorityQueue` 기본은 최소 힙(min-heap) → `Collections.reverseOrder()`를 써야 최대 힙(max-heap).
- 두 자료구조 병행 사용 패턴:
    - `Queue<Document>` → 실제 실행 순서 시뮬레이션
    - `PriorityQueue<Integer>` → 현재 최고 우선순위 관리
- 단순 반복문보다 효율적이며, O(n²)에서 O(n log n)으로 개선.


---

## 기억할 포인트
- 출력 카운트는 “실제 출력될 때만” 증가시킨다.
- 특정 원소 추적 필요 시, 추가 정보(index/flag)를 함께 저장.
- `PriorityQueue<>(Collections.reverseOrder())` = 최대 힙 구현 패턴.
- 큐 시뮬레이션 문제에서 성능 개선을 원한다면:
    - 전체 탐색 방식 (O(n²))
    - 최대 힙(PriorityQueue) (O(n log n))
    - 값 범위 제한 → 카운팅 배열 (O(n))

