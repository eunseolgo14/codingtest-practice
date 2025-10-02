class Solution {
    public String solution(String new_id) {
        String answer = "";
        
        answer = new_id;
        
        //1.
        answer = convertToLower(answer);
        
        //2.
        answer = replaceExceptions(answer);
        
        //3.
        answer = doubleDotsToSingle(answer);
        
        //4.
        answer = trimsDotsOnBoth(answer);
        
        //5.
        answer = fillAIfEmpty(answer);
        
        //6.
        answer = limit16AndTrim(answer);
        
        //7.
        answer = duplicateTheLast(answer);
        
        return answer;
    }
    
    //1. 대문자 => 소문자 치환.
    private String convertToLower(String text){
        return text.toLowerCase();
    }
    
    //2. 예외문자 제거 처리.
    private String replaceExceptions(String text){
        String permits = "[^a-z0-9-_.]";
        return text.replaceAll(permits, "");
    }
    
    //3. 마침표 두개이상 연속된 부분 하나의 마침표로 지환.
    private String doubleDotsToSingle(String text){
        String matches = "[.]{2,}";
        return text.replaceAll(matches, ".");
    }
    
    //4. 마침표가 처음과 끝 위치시 제거.
    private String trimsDotsOnBoth(String text){
        
        if(text.startsWith(".")){
            text = text.substring(1);
        }
        if(text.endsWith(".")){
            text = text.substring(0, text.length()-1);
        }
        
        return text;
    }
    
    //5. 빈 문자열이라면 a대입.
    private String fillAIfEmpty(String text){
        if(text.equals("")){
            text = "a";
        }
        return text;
    }
    
    //6. 16자 이상이면 15개까지, 마지막 마침표면 제거.
    private String limit16AndTrim(String text){
        if(text.length() > 15){
            text = text.substring(0, 15);
            text = trimsDotsOnBoth(text);
        }
        
        return text;
    }
    
    //7. 2자 이하일시 마지막 레터 3자까지 반복.
    private String duplicateTheLast(String text){
        if(text.length() <=2){
            while(text.length() < 3){
                text += text.charAt(text.length()-1);
            }
        }
        return text;
    }
}