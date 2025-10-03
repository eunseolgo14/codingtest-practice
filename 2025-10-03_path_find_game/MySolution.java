import java.util.*;

class Node{
    int x;
    int y;
    int idx;
    Node left;
    Node right;
    Node(int x, int y, int idx){
        this.x = x;
        this.y = y;
        this.idx = idx;
    }
}

class Solution {
    
    List<Integer> preOrderList = new ArrayList<>();
    List<Integer> postOrderList = new ArrayList<>();
    
    public int[][] solution(int[][] nodeinfo) {
        
        //1. 좌표 배열 => 노드 리스트로 가공.
        List<Node> nodeList = initNodeList(nodeinfo);
        
        //2. 트리 레벨 정렬.
        nodeList = sortTreeNode(nodeList);
        
        //3. 자녀 관계 삽입.
        for(int i = 0; i < nodeinfo.length - 1; i++){
            //루트만 바깥 레벨에서 호출.
            insertChildren(nodeList.get(0), nodeList.get(i+1));
        }
        
        //4. 전위 순회 + 후위 순회 후 리스트에 순서 저장.
        doPreOrder(nodeList.get(0));
        doPostOrder(nodeList.get(0));
        
        //5. 리스트에 담긴 순회 순서 배열로 가공, 제출.
        
        int[][] answer = {listToArr(preOrderList),listToArr(postOrderList)};
        return answer;
    }
    
    //1. 좌표 배열 => 노드 리스트로 가공.
    private List<Node> initNodeList(int[][] nodeinfo){
        List<Node> nodeList = new ArrayList<>();
        
        for(int i = 0; i < nodeinfo.length; i++){
            nodeList.add(new Node(nodeinfo[i][0], nodeinfo[i][1], (i + 1)));
        }
        
        return nodeList;
    }
    
    //2. 트리 레벨 정렬.
    private List<Node> sortTreeNode (List<Node> list){
        List<Node> sortList = list;
        
        sortList.sort((a,b) -> {
            
            //같은 레벨일 시 x오름차순.
            if(a.y == b.y){
                return a.x - b.x;
            }
            //우선은 y내림차순.
            return b.y - a.y;
        });
           
        return sortList;

    }
    
    //3. 자녀 관계 삽입.
    private void insertChildren(Node parent, Node child){
        //왼쪽 자손인지 확인.
        if(parent.x > child.x){
            //내 자식으로 붙일 수 있는지(자리 있는지 확인).
            if(parent.left == null){
                parent.left = child;
            }else{
                //자식 불가, 자손으로 한 레벨 들어가기.
                insertChildren(parent.left, child);
            }
        }else{
            //오른쪽 자손.
            if(parent.right == null){
                parent.right = child;
            }else{
                insertChildren(parent.right, child);
            }
        }
    }
    
    //4-1. 전위 순회 후 리스트 저장.
    private void doPreOrder(Node root){
        if(root == null){
            //빈 노드 => dead end.
            //다시 빠져나가기.
            return;
        }else{
            //루트 먼저 저장.
            preOrderList.add(root.idx);
            //좌 저장.
            doPreOrder(root.left);
            //우 저장.
            doPreOrder(root.right);
        }
    }
    
    //4-2. 후의 순회 후 리스트 저장.
    private void doPostOrder(Node root){
        if(root == null){
            return;
        }else{
            doPostOrder(root.left);
            doPostOrder(root.right);
            postOrderList.add(root.idx);
        }
    }
    
    //5. 리스트 내 정수 순서 배열로 가공. 
    private int[] listToArr(List<Integer> list){
        
        int[] intArr = new int[list.size()];
        for(int i = 0; i < intArr.length; i++){
            intArr[i] = list.get(i);
        }
        
        return intArr;
    }
}

//========================================================[추가 학습].

//중위 순회.
List<Integer> inOrderList = new ArrayList<>();

private void doInOrder(Node root){
    if(root == null){
        return;
    }else{
        //맨 처음 좌.
        doInOrder(root.left);
        //중간에 루트 자신.
        inOrderList.add(root.idx);
        //맨 마지막에 우.
        doInOrder(root.right);
    }
}

//BFS(넓이 우선 탐색) 학습.
public int[] doBFS(int[][] nodeinfo){

    List<Integer> resultList = new ArrayList<>();

    //좌표를 받아 트리로 정렬, 자녀를 붙이는 것 까지는 동일.
    List<Node> nodeList = initNodeList(nodeinfo);
    nodeList = sortTreeNode(nodeList);

    for(int i = 0; i < nodeinfo.length - 1; i++){
        //루트만 바깥 레벨에서 호출.
        insertChildren(nodeList.get(0), nodeList.get(i+1));
    }

    //현 generation을 담을 queue제작, 루트 담고 시작.
    Queue<Node> thisLevelQueue = new LinkedList<>();
    thisLevelQueue.add(nodeList.get(0));
    
    while(!thisLevelQueue.isEmpty()){

        //큐 맨 앞 꺼내서 리스트에 저장.
        Node nowNode = thisLevelQueue.poll();
        resultList.add(nowNode.idx);

        //큐 맨 앞의 자녀 있으면 탐색 큐 맨 뒤로 append.
        if(nowNode.left != null){
            thisLevelQueue.add(nowNode.left);
        }

        if(nowNode.right != null){
            thisLevelQueue.add(nowNode.right);
        }
    }
    
    int[] result = listToArr(resultList);
    return result;
}

