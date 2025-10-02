class Solution {
    int rightThumb;
    int leftThumb;
    String hand;

    public String solution(int[] numbers, String hand) {
        rightThumb = 10;
        leftThumb = 12;
        // [✏️ 틀린부분 1]:
        // 초기 오른손 위치: 문제 정의상 '#'과 '*'이어야 하는데
        // 지금은 10으로 시작 → 서로 바뀌었으며 의미 명확히 할 필요 있음
        this.hand = hand;

        String answer = "";

        for(int i = 0; i < numbers.length; i++){

            int thisNum = replaceZero(numbers[i]);
            // [✏️ 틀린부분 2]:
            // 0을 11로 치환하는 트릭
            // 직관성 떨어짐, 좌표 직접 매핑이 더 명확

            //thisNum가 1이거나 4이거나 7이면 L로 치환.
            if(thisNum == 1 || thisNum == 4 || thisNum == 7){
                answer += "L";
                //LT저장.
                leftThumb = thisNum;
            }
            //i가 3이거나 6이거나 9이면 R로 치환.
            else if(thisNum == 3 || thisNum == 6 || thisNum == 9){
                answer += "R";
                //RT저장.
                rightThumb = thisNum;
            }
            //i가 그 이외일때.
            else{
                answer += findClosest(thisNum, hand);
            }
        }
        return answer;
    }


    private String findClosest(int num, String hand){

        int leftOff = getDistance(num, leftThumb);
        int rightOff = getDistance(num, rightThumb);

        //오른손 엄지 더 가까움.
        if(leftOff > rightOff){
            //RT저장.
            rightThumb = num;
            return "R";
        }
        //왼손 엄지 더 가까움.
        else if(rightOff > leftOff){
            //LT저장.
            leftThumb = num;
            return "L";
        }else{
            //동일한 거리.
            if(hand.equals("right")){
                //RT저장.
                rightThumb = num; 
                return "R";
            }else{
                //LT저장.
                leftThumb = num;
                return "L";
            }

        }


    }

    private int getDistance(int target, int handPos){

        int totalOff = 0;

        int targetX = getXPos(target);
        int targetY = getYPos(target);

        int handX = getXPos(handPos);
        int handY = getYPos(handPos);

        if(targetX > handX){
            totalOff += (targetX - handX);
        }else{
            totalOff += (handX - targetX);
        }

        if(targetY > handY){
            totalOff += (targetY - handY);
        }else{
            totalOff += (handY - targetY);
        }
        return totalOff;
    }

    private int getXPos(int num){
        switch(num){
            case 1:
            case 2:
            case 3:{
                return 1;
            }
            case 4:
            case 5:
            case 6:{
                return 2;
            }
            case 7:
            case 8:
            case 9:{
                return 3;
            }
            default:{
                return 4;
            }
        }
    }

    private int getYPos(int num){
        // [✏️ 틀린부분 3]:
        // num % 3 == 0이면 3 리턴 → 3,6,9,12(#) 처리 되긴 함
        // 하지만 "0 → 11 치환" 로직을 전제로 함 → 좌표 계산이 우회적
        if(num % 3 == 0){
            return 3;
        }else{
            return num % 3;
        }
    }

    private int replaceZero(int num){
        if(num == 0){
            return 11;
        }
        else{
            return num;
        }
    }
}
