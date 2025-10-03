import java.util.Arrays;

class Solution {
    public int[] solution(int N, int[] stages) {
        
        //스테이지별 실패율 산출.
        float[] faileRate = getFailRate(N, stages);
        
        //스테이지 배열 초기화.
        Integer[] order = new Integer[N];
        for(int i = 0; i < N; i ++){
            order[i] = (i + 1);
        }
        //order = 1,2,3,4,5... 1based.
        Arrays.sort(order, (a,b) -> {
            
            if(faileRate[a-1] == faileRate[b-1]){
                return a-b;
            }
            return Float.compare(faileRate[b-1], faileRate[a-1]);
        });
        
        int[] answer = Arrays.stream(order).mapToInt(Integer::intValue).toArray();
        
        return answer;
    }
    
    private float[] getFailRate(int stageCount, int[] usersAt){
        
        float[] rate = new float[stageCount];
        int totalUsers = usersAt.length;
        int stageUsers;
        
        for(int i = 0; i < stageCount; i++){
            stageUsers = 0;
            for(int j = 0; j < usersAt.length; j++){
                if(usersAt[j] == (i + 1)){
                    stageUsers++;
                }
            }
            
            if(totalUsers == 0 || stageUsers == 0){
                rate[i] = 0;
            }else{
                rate[i] = (float)stageUsers/totalUsers;
                totalUsers -= stageUsers;
                
                if(totalUsers < 1){
                    totalUsers = 0;
                }
            }
        }
        
        return rate;
    }
}