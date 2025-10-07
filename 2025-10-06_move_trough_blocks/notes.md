# 블록 이동

**작성일**: 2025-10-16 ~ 2025-10-17 </br>
**목표**: 최단 시간(스텝 수)만 구한다. 경로 복원은 필요 없음 → BFS

---

## 풀이 개요
1. 상태를 `(row, col, dir)`로 정의하고 BFS로 “평행 이동 + 회전” 가능한 모든 다음 상태(이웃) 를 확장한다.
2. `dir=0`(가로): 기준칸 = left = `(r,c)` ⇒ 차지칸 {(r,c),(r,c+1)}
3. `dir=1`(세로): 기준칸 = top = `(r,c)` ⇒ 차지칸 {(r,c),(r+1,c)}
4. 도착은 “두 칸 중 하나라도 (n-1,n-1)”에 닿으면 종료(최초 도달 시 시간이 최단).

---

## 상태와 방문 (State & Visited)
- `State { int r, c, dir, time; }`
- `visited[r][c][dir]` (3D boolean)
    - enqueue 시점에 true로 찍어 중복 인큐 방지 (BFS 최단성 보장)

---

## 유틸 함수
- `inRange(r,c,n)` : `0 <= r,c < n`
- `cellsFree(b, r1,c1, r2,c2)` : 두 칸 모두 `inRange` & `0`
- `canMove(r,c,dir,b)`
    - 가로: `cellsFree(b, r,c, r,c+1)`
    - 세로: `cellsFree(b, r,c, r+1,c)`
- `rotateNext(r,c,dir,b)` → 0~4개의 다음 상태 반환 `{nr,nc,ndir}`
    - 가로→세로:
        - 위로: `cellsFree(b, r-1,c, r-1,c+1)` → {r-1,c,1}, {r-1,c+1,1}
        - 아래: `cellsFree(b, r+1,c, r+1,c+1)` → {r,c,1}, {r,c+1,1}
    - 세로→가로:
        - 왼쪽: `cellsFree(b, r,c-1, r+1,c-1)` → {r,c-1,0}, {r+1,c-1,0}
        - 오른쪽: `cellsFree(b, r,c+1, r+1,c+1)` → {r,c,0}, {r+1,c,0}
- `isArrived(r,c,dir,n)`
    - 기준칸 (r,c)가 (n-1,n-1) or
    - 가로면 (r,c+1)가, 세로면 (r+1,c)가 (n-1,n-1)

---

## BFS 루프 패턴
1. 큐 초기화: `(0,0,가로,0)` → `visited[0][0][0]=true`
2. while(queue):
    - `cur = poll()`
    - `if (isArrived(cur)) return cur.time;`
    - 평행 이동 4방향: `canMove(next) && !visited` → visit & enqueue(time+1)
    - 회전 후보들: `for(next in rotateNext)` 동일 처리

> 핵심: “이웃 생성” = 한 번의 행동(이동/회전)으로 갈 수 있는 모든 다음 상태 만들기

---

## 내가 자주 틀리는 포인트
1. 좌표 규약 혼동
    - `r=row(행, 위→아래)`, `c=col(열, 왼→오)`
    - `dir=0`(가로)=left가 기준 / `dir=1`(세로)=top이 기준
2. isArrived 분기 반대로 쓰기
    - 가로: `(r,c+1)` 체크 / 세로: `(r+1,c)` 체크
3. inRange 오프바이원
    - `r < n`, `c < n` (=`<=` 아님)
4. 두 칸 모두 0 체크 누락
    - 항상 “기준칸 + 짝칸” 동시 검사 (canMove, rotate 둘 다)
5. 회전 결과 좌표를 ‘top/left’로 저장하지 않음
    - 세로 결과는 “top”, 가로 결과는 “left” 좌표를 `(r,c)`에 저장
    - (잘못해 `(r+1, …)` 등 아래/오른쪽을 넣으면 visited가 꼬임)
6. enqueue 때 visited 표시 안 함
    - enqueue 시 바로 `visited=true` (중복 인큐 폭발 방지)
7. 배열 접근 전에 inRange로 가드 안 함
    - `&&` 단락 평가를 이용해 “inRange → board접근” 순서 유지
8. 회전의 2×2 빈칸 검사 누락
    - 가로→세로: 위/아래 줄 두 칸, 세로→가로: 좌/우 줄 두 칸
9. 중복 후보 추가
    - 같은 회전 케이스를 두 번 넣지 않기 (visited가 막긴 하지만 낭비)
---

## 최초 제출(MySubmit.java) 시 틀린 부분
1. **isArrived 분기 오류**
   - 가로/세로 보조칸 도착 판정 뒤바뀜
   - 기준칸만 보고 종료하는 실수

2. **회전 결과 좌표 저장 실수**
   - 세로 결과에 “아래칸”을 기준으로 저장함(top이 아님)

3. **inRange 오프바이원**
    - `size <= r/c` 가드 누락 → OOB 가능성

4. **중복 회전 케이스 추가**
    - 동일 후보를 두 번 더함(visited가 막긴 하나 낭비)
---

## 2차 제출(MySolution.java) 시 수정한 부분
- `isArrived` 가로/세로 보조칸 판정 수정
- 회전 결과를 가로=left / 세로=top 좌표로 저장하도록 수정
- `isInRange` 오프바이원 수정
- `isCanMove`/`rotateNext`에서 두 칸 동시에 검사 통일  

---

## 헷갈렸던 것/ 새로 배운 것
- State의 `(r,c)`는 “결과 모양의 기준칸”을 의미 (새로 닿는 칸이 아님)
- 회전 가능 조건은 2×2 빈칸 확인으로 깔끔히 해결
- BFS는 가중치 1일 때 최초 도착이 최단 시간   

---

## 기억할 포인트
- 항상 두 칸 기준으로 생각(경계+벽)
- enqueue 시 visited
- 회전 후 기준좌표: 가로=left / 세로=top
- cellsFree 헬퍼로 중복 로직/실수 제거

