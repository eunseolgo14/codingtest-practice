# 실패율 문제

**작성일**: 2025-10-03
**목표**: Comparator 정렬 방식 완전히 이해하기

---

## 최초 제출(MySubmit.java) 시 틀린 부분
1. **분모 0 처리 누락**
   - `totalUserCount == 0`일 때 나눗셈을 수행해서 `NaN/Infinity` 발생
   - 예외처리를 안 해서 실패율 계산이 잘못됨

2. **버블 정렬 인덱스 범위 오류**
   - inner for 시작을 `j=i`로 두어 앞쪽 비교가 누락됨
   - 결국 일부 원소가 정렬되지 않음

3. **동점 처리 미구현**
    - 실패율 같을 때 스테이지 번호 순서를 고려하지 않아, 결과 순서가 문제 조건과 달라짐

4. **실패율과 스테이지 번호 매핑 방식 혼동**
    - `failRate`와 `order` 배열을 물리적으로 같이 swap해야 한다고 생각
    - 결국 정렬이 복잡해지고 실수 발생

---

## 2차 제출(MySolution.java) 시 수정한 부분
- 분모 0일 때 실패율 = 0 처리
- Comparator 기반 정렬로 단순화
    - 정렬 대상: `order` (스테이지 번호 배열)
    - 정렬 기준: `failRate[번호-1]`
    - 실패율 같으면 a - b (스테이지 번호 오름차순)
    - 실패율 다르면 `Float.compare(failRate[b-1], failRate[a-1])` (내림차순)
- Integer[] → int[] 변환으로 반환 값 맞춤

---

## 헷갈렸던 것/ 새로 배운 것
- a, b는 “스테이지 번호”일 뿐 크기가 보장된 게 아니다
- 실패율 비교는 `failRate[a-1], failRate[b-1]`로 해야 한다
- `Float.compare(x,y)`는 오름차순, `Float.compare(y,x)`는 내림차순
- tie-breaker(동점 처리)는 “번호 작은 게 앞으로” → `a - b`로 표현
- `failRate`는 lookup table로 남고, 정렬 대상은 `order` 하나만 두면 된다

---

## 기억할 포인트
- 실패율 계산 시 분모 0 체크 필수
- 정렬: 대상은 order, 기준은 failRate
- 내림차순은 Float.compare(y, x)
- tie-breaker는 a-b (번호 오름차순)

