# 주차 요금 계산

**작성일**: 2025-10-07</br>
**목표**: 차량별 총 주차 시간 집계 후 요금표에 따라 정산, 차량번호 오름차순으로 반환

---

## 최초 제출(MySubmit.java) 시 틀린 부분
1. **기본요금 분기 누락**
   - `time ≤ 기본시간`이어도 `ceil((time-기본시간)/단위)`를 곱해버림.
   - 결과적으로 기본시간 이하에서도 단위요금이 붙는 오계산 발생.
   - 교훈: `if (time <= allowance) return basicFee;` 분기 필수.

2. **Map 순회 중 수정 (ConcurrentModificationException)**
   - `for (int car : inRecords.keySet()) { inRecords.remove(car); }`
   - 동일 컬렉션을 순회하면서 구조 변경 → CME 발생.
   - 해결: 키 복사 후 순회, 또는 순회 후 `clear()` 중 택1.

3. **정렬 키 타입 실수**
    - `List<String> keys = new ArrayList(totalRecords.keySet());`
    - 실제 키는 `Integer`이므로 `get(String)` 시 NPE/형변환 문제 가능.
    - 해결: `List<Integer>`로 받아야함

4. **불필요한 split 중복 호출 비효율**
    - `parts[0].split(":")`를 여러 번 호출.
    - 해결: 한 번 파싱 후 재사용(가독성+성능 미세 개선).

---

## 2차 제출(MySolution.java) 시 수정한 부분
- `calcFee`에 기본요금 분기 추가: `if (time <= allowance) return basicFee;`
- 남은 `IN`은 23:59(=1439분)로 일괄 정산 후 `inRecords.clear()`로 마무리.
- 정렬 키를 `List<Integer>`로 받고 오름차순 정렬 후 요금 계산.
- `processRecord`에서 시간 파싱을 한 번만 수행하여 분 단위로 변환.  

---

## 헷갈렸던 것/ 새로 배운 것
- 누적 시간 모델: 같은 차가 여러 번 드나들어도 총 체류 시간을 합산한 뒤 한 번에 요금 계산.
- 올림(ceil) 정수식: `units = (extra + timeUnit - 1) / timeUnit` (extra=음수 방지로 `max(0, time-allowance)` 권장).
- CME 회피 패턴: 복사본 순회 / 사후 `clear()` 중 상황에 맞게 선택.
- 정렬 간소화 팁: 결과 정렬이 필수면 `TreeMap<Integer, Integer>` 사용 시 키 정렬 자동.

---

## 기억할 포인트
- time ≤ 기본시간 → 무조건 기본요금.
- 남은 IN은 반드시 23:59로 정산.
- 정수 올림 공식을 습관화: (x + d - 1) / d.
- Map 순회 중 구조 변경 금지 → 복사/이터레이터/clear.
- 키 타입 일관성 유지(차량번호는 Integer).

