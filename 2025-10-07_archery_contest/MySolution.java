import java.util.*;

class Solution {
    
    //어피치, 라이언 점수 배열 마련.
    int[] apeach;
    int[] lion;
    
    //갱신용 최대 점수차 변수, 최대 점수차 점수 배열 마련.
    int[] best;
    int maxDiff;
    
    public int[] solution(int n, int[] info) {
        //어피치, 라이언 배열 초기화.
        apeach = info;
        lion = new int[11];

        //최대 점수차 -1로 초기화.
        maxDiff = -1;    
        best = new int[]{-1};
        
        //점수 재귀 탐색 시작 => 시작은 0번 점수부터 주어진 화살 전부.
        searchScores(0, n);

        int[] answer = best;
        return answer;
    }
    
    private void searchScores(int round, int remainArrow){
        
        //남은 화살 0거나 round가 11도달 확인 => 즉 이번 가지 탐색 종료 확인.
        if(round == 11 || remainArrow == 0){
            
            //남은 화살이 있다면 0번 점수에 저장
            lion[10] += remainArrow;
            
            //이번 가지 점수 - 어피지 총점차이 계산
            int diff = calcDiff();
            
            //전역 최대 점수차보다 클시, 최대점수차/ 최고 배열 갱신
            if(diff > 0 && maxDiff < diff){
                maxDiff = diff;
                best = lion.clone();
            }else if(diff > 0 && maxDiff == diff){
                //전역 최대 점수차와 같을 시 선택을 위한 유틸함수 호출.
                if(isThisBetter()){
                    //더 낮은 점수 과녁 화살이 많은 배열을 최고 배열로 선택  
                    maxDiff = diff;
                    best = lion.clone();
                }
            }
            
            //0점에 넣어둔 남은 화살 다시 빼어 재귀 함수 탈출 다음 가지 탐색 
            lion[10] -= remainArrow;
            return;
        }
       
        //어피치에게 승리할 경우 탐색.
        //이번 라운드 승리를 위해 필요한 화살 갯수 추출(어피치 +1)
        int needArrow = apeach[round] + 1;

        //남은 화살이 승리 화살보다 같거나 큰지 확인
        //같거나 크다면 이길 경우 탐색 가능
        if(needArrow <= remainArrow){
            
            //라이언 배열 이번 인덱스에 승리 화살 갯수 대입
            lion[round] = needArrow;
            
            //다음 라운드(round+1)로 남은 화살 (현재 화살 - 승리화살) 실어 재귀 내려보내기
            searchScores(round + 1, remainArrow - needArrow);
            
            //다음 탐색을 위해 함수 종료 후 라이언 이번 인덱스에 화살갯수 0개
            lion[round] = 0;

        }

        //승리 포기 경우
        //다음 라운드(round + 1)로 남은 화살 전부 실어 재귀 내려 보내기
        searchScores(round + 1, remainArrow);
    }
    
    //유틸 1. 어피치 배열과 라이언 배열 둘의 점수차이 계산
    private int calcDiff(){
        int lionScore = 0;
        int apeachScore = 0;
        
        //두 배열의 전체 길이인 11만큼 순회
        for(int i = 0; i < 11; i++){
            
            // 둘 모두 0점일 시 스킵(아무도 점수 가져가지 않음)
            if(lion[i] == 0 && apeach[i] == 0 ){
                continue;
            }
            
            //어피치가 라이언보다 크거나 같을 시 어피치가 해당 점수 획득
            if(lion[i] <= apeach[i]){
                apeachScore += (10 - i);
            }else{
                //라이언이 어피치보다 클 시 라이언이 해당 점수 획득
                //apeach[i] < lion[i].
                lionScore += (10 - i);
            }
        }
        
        return lionScore - apeachScore;
    }
    
    //유틸 2.이번 배열과 최고 배열 중 선택할 것 고르기
    private boolean isThisBetter(){
        
        //두 배열의 전체 길이인 11만큼 순회
        for(int i = 0; i < 11; i++){
            //더 낮은 인덱스 배열 요소 값 비교, 이번 배열이 최고보다 큰 경우
            if(best[10 - i] < lion[10 - i]){
                //갱신을 위해 true리턴
                return true;
            }else if (best[10 - i] > lion[10 - i]){
                return false;
            }
        }
        
        return false;
    }
}