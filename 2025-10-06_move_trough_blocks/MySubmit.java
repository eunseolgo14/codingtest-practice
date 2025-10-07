import java.util.*;

//0. 상태 확인용 클래스 정의.
class Step{
    //가로열 순번.
    int r;
    //세로열 순번.
    int c;
    //0==가로, 1==세로.
    int mode;
    //경과 시간.
    int time;
    //생성자 정의.
    Step(int r, int c, int mode, int time){
        this.r = r;
        this.c = c;
        this.mode = mode;
        this.time = time;
    }
}

class Solution {
    public int solution(int[][] board) {

        int answer = 0;

        int count = board.length;
        Queue<Step> steps = new LinkedList<>();
        boolean[][][] visited = new boolean[count][count][2];

        //0,0에서 가로 모드로 시작.
        steps.add(new Step(0,0,0,0));
        //0,0 가로 상태에서 방문 기록 추가.
        visited[0][0][0] = true;

        //상하좌우 방향 벡터 정의.
        int[][] directions = {{1,0}, {-1,0}, {0,-1}, {0,1}};

        while(!steps.isEmpty()){

            //이번 확인 대상 아웃.
            Step thisStep = steps.poll();
            
            //도착 여부 확인.
            if(isArrived(thisStep.r, thisStep.c, thisStep.mode, count)){
                answer = thisStep.time;
                return answer;
            }

            //상하좌우 이동.
            for(int i = 0; i < directions.length; i++){

                //상하좌우 이동할 좌표 산출.
                int nextR = thisStep.r + directions[i][0];
                int nextC = thisStep.c + directions[i][1];

                //인접 좌표 유효성 확인, 방문 여부 확인.
                if(isCanMove(nextR, nextC, thisStep.mode, board) && !visited[nextR][nextC][thisStep.mode]){
                    visited[nextR][nextC][thisStep.mode] = true;
                    steps.add(new Step(nextR, nextC, thisStep.mode, thisStep.time+1));
                }
            }

            //인접 방향 회전 경우의 수 산출.
            List<int[]> reaches = isCanRotate(thisStep.r, thisStep.c, thisStep.mode, board);
            
            //방문 여부 확인 후 저장.
            for(int[] reach :reaches){
                if(!visited[reach[0]][reach[1]][reach[2]]){
                    visited[reach[0]][reach[1]][reach[2]] = true;
                    steps.add(new Step(reach[0],reach[1],reach[2],thisStep.time+1));
                }
            }
        }

        return answer;
    }
    
    //1. 종점에 도착했는지 확인.
    private boolean isArrived(int r, int c, int mode, int size){
        //기준점 자체가 종점에 도달.
        if(r == (size-1) && c == (size-1)){
            return true;
        }else{
            //가로모드일때 => 그 오른편이 도달했는지 확인.
            if(mode == 0){
                if(r == (size-1) && c+1 == (size-1)){
                    return true;
                }
            }else{
                //세로 모드일때 => 그 아래가 도달했는지 확인.
                 if(r+1 == (size-1) && c == (size-1)){
                    return true;
                }
            }
        }  
        return false;
    }
    
    //2. 해당 좌표가 보드 내 위치하는지 리턴.
    private boolean isInRange(int r, int c, int size){
        if(r < 0 || c < 0 || size <= r || size <= c){
            //보드 밖 영역.
            return false;
        }
        return true;
    }
    
    //3. 상하좌우 평행 이동용 이동 가능여부 리턴.
    private boolean isCanMove(int r, int c, int mode, int[][] board){
        
        //타겟 좌표 자체 유효성 확인.
        if(!isInRange(r, c, board.length)){
            return false;
        }
        
        //가로 모드일때 => 그 오른편 좌표 확인.
        if(mode == 0){
            if(isInRange(r, c+1, board.length)){
                return (board[r][c]==0 && board[r][c+1]==0) ? true : false;
            }
        }else{
            //세로 모드일때 => 그 아래 좌표 확인.
            if(isInRange(r+1, c, board.length)){
                return (board[r][c]==0 && board[r+1][c]==0) ? true : false;
            }
        }
        
        return false;
    }
    
    //4. 현재 좌표에서 회전 가능한지, 가능하다면 회전 후 도달 가능한 좌표 리턴.
    private List<int[]> isCanRotate(int r, int c, int mode, int[][] board){
        List<int[]> reaches = new ArrayList<>();
        
        //가로모드일때 => 위/아래 2칸씩 확인.
        if(mode == 0){
            //현재: [r,c],[r,c+1] 가로
            //현 좌표 선 확인 => 이후 행/열만 추가 범위 확인.
            if(!isInRange(r, c+1, board.length)){
                return reaches;
            }
            //현재 기준 위 2칸 비어있는지 확인(+ 0 <= r-1로 보드 벗어나지 않는지 확인).
            if((0 <= r-1) && (board[r-1][c] == 0 && board[r-1][c+1] == 0)){
                //좌 블럭[r, c] 기준으로 위를 향해 회전 => [r-1, c]지점 추가(방향 == 가로/1).
                reaches.add(new int[]{r-1, c, 1});
                //우 블럭[r, c+1] 기준으로 위를 향해 회전 => [r-1, c+1]지점 추가(방향 == 가로/1).
                reaches.add(new int[]{r-1, c+1, 1});
            }
            //현재 기준 아래 2칸 비어있는지 확인(r+1 < board.length로 보드 벗어나지 않는지 확인)
            if((r+1 < board.length) && (board[r+1][c] == 0 &&board[r+1][c+1] == 0)){
                //좌 블럭[r, c] 기준으로 아래를 향해 회전 => [r+1, c].
                reaches.add(new int[]{r, c, 1});
                //우 블럭[r, c+1] 기준으로 아래를 향해 회전 => [r+1, c+1]
                reaches.add(new int[]{r, c+1, 1});
            }

            return reaches;
        }else{
            //현재: [r,c],[r+1,c] 세로
            //세로모드일때 => 좌/우 2칸씩 확인.
            if(!isInRange(r+1, c, board.length)){
                return reaches;
            }
            //현재 기준 왼쪽 2칸이 비어있는지 확인.
            if((0 <= c-1) && (board[r][c-1] == 0 && board[r+1][c-1] == 0)){
                //위 블럭[r,c] 기준으로 왼쪽을 향해 회전 => [r, c-1]지점 추가(방향 == 세로/1).
                reaches.add(new int[]{r, c-1, 0});
                //아래 블럭[r+1, c] 기준으로 왼쪽을 향해 회전 => [r+1, c-1]지점 추가.
                reaches.add(new int[]{r+1, c-1, 0});
            }
            //현재 기준으로 오른쪽 2칸이 비어있는지 확인.
            if((c+1 < board.length) &&(board[r][c+1] == 0 && board[r+1][c+1] == 0)){
                //위 블럭[r,c] 기준으로 오른쪽을 향해 회전 => [r, c+1]지점 추가.
                reaches.add(new int[]{r, c, 0});
                //아래 블럭[r+1, c] 기준으로 오른쪽을 향해 회전 => [r+1, c+1]지점 추가.
                reaches.add(new int[]{r+1, c, 0});
            }

            return reaches;
        }
    }
}