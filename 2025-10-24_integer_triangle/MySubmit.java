class Solution { 
    //전역 최대값 기록용 변수. 
    int currentMax; 
    //트리 총 깊이. 
    int MAX_DEPTH; 
    //삼각 정보 전역으로 뻄. 
    
    int[][] triangle; public int solution(int[][] triangle) { 
        //전역 변수 세팅. 
        currentMax = 0; 
        this.triangle = triangle; 
        MAX_DEPTH = this.triangle.length - 1; 
        sumBelow(0, 0, triangle[0][0]); 
        int answer = currentMax; 
        return answer; 
    } 
    private void sumBelow(int level, int index, int sum){ 
        //더 내려갈 곳이 없음, 마지막 level임. 
        if(level == MAX_DEPTH){ 
            if(currentMax < sum){ 
                currentMax = sum; 
            } 
        }else{ 
            //현재 레벨에서 한단계 아래로 내려감. 
            int nextLv = level + 1; 
            //내 왼쪽 탐색 보내기. 
            int leftIdx = index; 
            int leftSum = sum + triangle[nextLv][leftIdx]; 
            sumBelow(nextLv, leftIdx, leftSum); 
            //내 오른쪽 탐색 보내기. 
            int rightIdx = index + 1; 
            int rightSum = sum + triangle[nextLv][rightIdx]; 
            sumBelow(nextLv, rightIdx, rightSum); 
        } 
    } 
}