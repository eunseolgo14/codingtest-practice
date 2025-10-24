# 수식 최대화

**작성일**: 2025-10-24</br>
**목표**: 주어진 수식의 연산자 우선순위를 모든 조합으로 바꿔가며 계산했을 때,
결과의 절댓값이 최대가 되는 경우의 값을 구하는 것.

---

## 최초 제출(MySubmit.java) 시 틀린 부분
1. **비교 대상 컬렉션 혼동**
   - 아래 발생한 문제 해결을 위해 원본 `splitedText`을 복제한 `thisList`로 가공 연산 진행.
   - `if (splitedText.get(j).equals(operator))`로 작성했지만 실제 연산은 `thisList`(복제 리스트)에서 수행해야 함.
   - 원본 리스트(`splitedText`)는 수정되지 않으므로, 루프 도중 인덱스가 어긋나 `IndexOutOfBoundsException` 발생하거나 비정상적 값 대입됨.

2. **리스트 복제 누락**
   - `splitedText`를 그대로 사용했기 때문에 이전 조합에서 수정된 데이터가 이후 조합 연산에 누적됨.
   - 참고로 `List` 인터페이스는 `clone()`을 직접 지원하지 않음.
   - `ArrayList.clone()`은 존재하지만 `Object` 타입으로 반환되어 명시적 캐스팅이 필요하고, 제네릭 정보가 사라짐.
   - 따라서 `new ArrayList<>(원본)` 방식이 가장 안전한 얕은 복제법.

---

## 2차 제출(MySolution.java) 시 수정한 부분
- 리스트 복제 방식 교체
    - `new ArrayList<>(splitedText)` 사용으로 각 연산자 우선순위별로 독립된 연산 리스트 확보.
- 정렬 로직 교정
    - `Arrays.reverse()`는 존재하지 않음. 
    - `Collections.reverse()`는 `List` 전용이므로 배열에는 적용 불가.
    - 최종 해결: `Arrays.sort(values, Comparator.reverseOrder());`

---

## 헷갈렸던 것/ 새로 배운 것
- `clone()` vs `new ArrayList<>(list)`
    - `clone()`은 `Object` 반환이라 제네릭 안전성 없음.
    - `new ArrayList<>(list)`는 복제 후에도 타입 안정성과 독립성 보장.
-  `i-= 2`의 함정
    - `remove()`가 리스트를 앞으로 2칸 압축하기 때문에, for문 내부 i++이 없다면 실제 인덱스는 하나만, 있다면 두개 줄여야 한다.
- 문자열 토크나이징
    - `StringTokenizer(expression, "+-*", true)`
    - `true`설정 시, 구분자(+, -, *)도 토큰으로 남겨줌.

---

## 기억할 포인트
- 비가역적, 파괴적 연산을 수행하는 리스트는 항상 복제해서 사용하라.
- `remove()` 후 압축된 갯수만큼의 인덱스 보정 반드시 필요하다.
- 복제 리스트를 매 라운드마다 새로 만들어야 “조합별 독립 연산”이 가능하다.

