# 숫자 문자열과 영단어 

**작성일**: 2025-10-02  
**목표**: 문자열 치환 로직 구현 및 자바 문자열/정규식/타입 변환 개념 정리

---

## 내가 최초 제출(MySubmit.java)에서 틀린 이유
1. **parseInt char타입 오류**  
   - `charAt()`은 `char` 타입 반환 → `Integer.parseInt()`는 String 입력만 받음.  
   - `Integer.parseInt(text.charAt(i))` → 타입 불일치 오류.  
   - 해결: `Character.getNumericValue(char)` 또는 `Integer.parseInt(String.valueOf(char))` 사용.  

2. **자리수 계산 오류**  
   - `value = number * place;` → 누적합이 아니라 매번 덮어씀 → 마지막 자리만 남음.  
   - 해결: `value += number * place;`  

3. **replaceAll vs replace 혼동**  
   - `replaceAll`은 정규식 기반이라 단순 문자열 치환에서는 위험.  
   - 예: `text.replaceAll("one", "1")` 은 안전하지만, 특정 패턴 문자에서는 예외 가능.  
   - 단순치환은 `replace` 사용.  

4. **자리수 연산 로직 부정확**  
   - 굳이 `Math.pow(10, …)`로 직접 자리값 계산할 필요 없음.  
   - 문자열 치환 완료 후 `Integer.parseInt(text)`로 단순화 가능.  

---

## 2차 제출(MySolution.java) 시 수정한 부분
- **HashMap → 배열로 단순화**  
  - `HashMap<String, String>` 대신 `String[] numArr`로 영단어 → 숫자 매핑.  
  - 더 직관적이고 가볍다.  

- **치환 로직 단순화**  
  ```java
  for (int i = 0; i < numArr.length; i++) {
      s = s.replace(numArr[i], String.valueOf(i));
  }
  int answer = Integer.parseInt(s);
  ```
   - 자리수 연산 불필요 → replace + parseInt로 끝.
   - 불필요한 라이브러리 제거
   - java.lang.Math, HashMap 사용하지 않아도 해결 가능.

---

## 헷갈렸던 것, 새로 배운 것
   - charAt(): char 타입 반환, 바로 parseInt 불가.
   - replace() vs replaceAll(): 전자는 단순 문자열, 후자는 정규식 기반.
   - 배열 vs HashMap: 고정 매핑(0~9)은 배열로도 충분.
   - 배열 길이: numArr.length (배열은 필드), 문자열은 s.length(), 컬렉션은 list.size().

---

## 기억할 포인트
   - 단순 치환은 replace, 정규식 패턴일 때만 replaceAll.
   - 불필요하게 자리값 계산하는 것보다 치환 후 Integer.parseInt()로 단순화하는 게 더 정확하고 빠름.
