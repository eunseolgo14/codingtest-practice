class Solution {
    public int[] solution(int N, int[] stages) {
        
        //각 스테이지별 실패율 추출.
        float[] failRate = getFailRate(N, stages);
        
        //실패율에 따른 스테이지 내림차순 정렬.
        int[] answer = setRateDesc(failRate);
        
        return answer;
    }
    
    
    private float[] getFailRate(int stageCount, int[] userCurrent){
        
        //초기 배열 설정.
        float[] failRate = new float[stageCount];
        
        //총 참여 인원 도출.
        int totalUserCount = userCurrent.length;
        
        int userPerStage;
        for(int i = 0; i < stageCount; i ++){
            userPerStage = 0;
            
            //스테이지 수 별 잔존 유저 파악.
            for(int ii = 0; ii < userCurrent.length; ii++){
                if(userCurrent[ii] == (i + 1)){
                    userPerStage++;
                }
            }

            // [✏️ 틀린부분 1] 처음엔 분모=0 체크 안 해서 0으로 나눔 → NaN 발생
            // totalUserCount < 1일 때는 실패율을 0으로 처리
            if(totalUserCount < 1){
                failRate[i] = 0.0f;
            }else{
                
                //전체 잔존유저/도전자 비율 산출.
                failRate[i] = (float)userPerStage/totalUserCount;
            
                //다음 스테이지 이동 전 전체 유저 차감.
                totalUserCount -= userPerStage;
            }
          
        }
        
        return failRate;
        
    }
    
    private int[] setRateDesc(float[] rate){
        int[] order = new int[rate.length];
        for(int i = 0; i < rate.length; i++){
            order[i] = (i + 1);
        }
        
        int tempInt = 0;
        float temtFloat = 0.0f;

        // [✏️ 틀린부분 2] 처음 버블 정렬에서
        // 1) j 시작점을 0이 아닌 i로 잘못 잡음 → 일부만 비교됨
        // 2) 동점 일시 스테이지 수 낮은게 앞에오는 조건 확인 없음 → 문제 요구 불충족
    
        // 버블 정렬에서 이미 정렬된 부분은 "끝쪽".
        // 즉, 첫 번째 패스가 끝나면 “맨 마지막 원소는 최댓값”이 확정.
        // 두 번째 패스가 끝나면 “뒤에서 두 번째 원소”까지 확정.
        // 따라서 inner for 범위를 줄일 때는 “끝쪽”을 줄이는 게 맞음.
        // → for (j = 0; j < n - 1 - i; j++)

        for(int i = 0; i < rate.length - 1; i ++){
            for(int j = 0; j < (rate.length - 1) -i; j++){
                if(rate[j + 1] > rate[j] || 
                   (rate[j + 1] == rate[j] && order[j + 1] < order[j])){
                    temtFloat = rate[j + 1];
                    rate[j + 1] = rate[j];
                    rate[j] = temtFloat;
                    
                    tempInt = order[j + 1];
                    order[j + 1] = order[j];
                    order[j] = tempInt;
                }
            }
        }
        return order;

    
    }
}