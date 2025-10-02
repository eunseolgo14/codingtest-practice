//2025.10.2 12:48 ~ 13:33 까지 진행한 인생 첫 코딩테스트 제출.
//풀이를 확인하며 진행한 오류 부분은 [✏️ 틀린부분]이하 주석으로 추가.

class Solution {
    public String solution(String new_id) {
        String answer = "";
        
        //0. 유저의 입력 값 new_id을 받는다. 
        answer = new_id;
        
        //1. 모든 대문자를 소문자로 치환한다 toLower사용.
        answer = answer.toLowerCase();
        
        //2. 소문자, 숫자, -, _, .를 제외한 모든 문자 제거한다. 
        answer = answer.replaceAll("[^a-z0-9-_.]", "");
        
        //3.두번 이상 연속된 ..있을 시 마침표 .로 치환한다.
        answer = answer.replaceAll("[..]", ".");
        // ✏️[틀린부분 1]: 
        // [..]는 정규식에서 "문자 집합"이라 결과적으로 [.]과 다름 없다!
        // [.]{2,}라 명시해야 ..이 두개 나열된 패턴이 감지된다!
        
        //4.처음이나 끝에 위치한 .는 제거한다.
        if(answer.startsWith(".")){
            if(answer.length() <= 1){
                answer = "";
            }else{
               answer = answer.substring(1, answer.length() -1);
            // ✏️[틀린부분 2]: 
            // answer.substring(1, answer.length() -1)은 0번인 맨 앞, 그리고 맨 뒤
            // 각 1개씩을 "동시에" 잘라낸다! 이는 제시된 조건과 다름!
            // indexOf사용 없이 단순히 substring(1)을 하면 된다!
            }
        }
        if(answer.endsWith(".")){
            if(answer.length() <= 1){
                answer = "";
            }else{
               answer = answer.substring(0, answer.length() -1);
            }
        }
        
        //5.new_id가 빈 문자열일경우 a를 대입한다.
        if(new_id ==""){
            new_id = "a";
        }
        // ✏️[틀린부분 3]:
        //문자열 비교는 ==가 아닌 equals가 되어야한다!

        // ✏️[틀린부분 4]:
        //answer가 지금 가공되고 있는 값인데 new_id를 조건에 사용하면 안된다!

        //6.new_id가 16이상이면 첫문자부터 15개까지만
        //(제거 결과 마지막이 .이면 .제거).
        if(new_id.length() > 15){
            answer = answer.substring(0,15);
            if(answer.endsWith(".")){
                answer = answer.substring(0, answer.length() -1);
            }
        }
        // ✏️[틀린부분 5]:
        //answer가 지금 가공되고 있는 값인데 new_id길이를 조건에 사용하면 안된다!
        
        //7.new_id의 길이가 2자 이하, 3이 될때까지 마지막 반복.
        if(answer.length() <= 2){
           while(answer.length() == 3){
               answer+=answer.indexOf(answer.length() -1);
           }
        }
        // ✏️[틀린부분 6]:
        //while의 조건은 answer.length() == 3 이부분 절대 실행 안 된다. 
        //while(answer.length() < 3)로 범위 체크를 해야한다.

        // ✏️[틀린부분 7]:
        //answer에 누적해야하는건 indexOf가 아닌 해당 인덱스에 있는 문자, 즉 chatAt이어야한다.
        //indexOf하게되어 엉뚱한 숫자가 붙는 로직이 되어버렸다.

        //8. 최종 대입.
        return answer;
    }
}