import java.util.*;

class Solution {
    public String solution(String p) {
        
        String result = "";
        
        //3:10 - 3:50 build code
        String answer = process(result, p);
    
        return answer;
    }
    
    private String process(String result, String text){
                
        if(text.equals("")){
            return "";
        }
        
        //balced부 추출해내기.
        String[] parts = findBalanced(text);
        
        String first = parts[0];
        String later = parts[1];

        if(isCorrect(first)){
            result += first;
            result += process(result, later);
            
        }else{
            result += ("(" + process(result, later) + ")");
            first = first.substring(1, first.length()-1);
            result += reverse(first);

        }
        return result;
    }
    
    private String[] findBalanced(String text){
        String[] parts = new String[]{"", ""};
        
        String firstBalance = "";
        int openCount = 0;
        int closeCount = 0;
        
        for(int i = 0; i < text.length(); i++){
            
            firstBalance += text.charAt(i);
            
            if(text.charAt(i) == '('){
                openCount++;
            }else{
                closeCount++;
            }
            
            if(openCount == closeCount && (0 < openCount && 0< closeCount)){
                break;
            }
        }
        
        parts[0] = firstBalance;
        parts[1] = text.substring(firstBalance.length());

        return parts;
    }
    
    private boolean isCorrect(String text){
        
        //balanced가 correct가 맞는지 획인용 유틸 함수.
        if(!text.startsWith("(")){
            return false;
        }
        //예: (()))((
        int result = 0;
        
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '('){
                result++;
            }
            else{
                result--;
            }
            
            if(result < 0){
                //() 규칙이 깨짐, 여는 괄호 없이 닫는 괄호가 남음 => 미완.
                return false;
            }
        }
        return true;
    }
    
    private String reverse(String text){
        String reversed = "";
        
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '('){
                reversed += ')';
            }else{
                reversed += '(';
            }
        }
        return reversed;
    }
}

//2:50 - 2:55 rule 
//(와 )의 갯수 == -> balanced
//)( == balanced, not correct, ()== balanced, and correct

//2:55 - 3:10 idea
//empty => return empty.

//전체 문자열p에서 앞 혹은 뒤에서부터 한쌍의 balanced부 u, rest v로 나눔

//u가 ()라면 v에서 앞에서 부터 다시 확인 후 올바른 쌍 u뒤에 붙임
//u가 올바르지 않으면, v의 변환 결과를 괄호로 감싸고,
//u의 맨 앞, 맨 뒤 떼어서 뒤집은 다음 v뒤에 붙인다