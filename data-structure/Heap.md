## 힙(Heap)

- 힙은 Complete Binary Tree (완전 이진 트리) 이다.

- 모든 노드에 저장된 값(우선순위) 들은 자식 노드들의 것보다 (우선순위가) 크거나 같다.

- 힙은 리스트에서 가장 작은 요소(또는 가장 큰 요소)에 반복적으로 접근하는 프로그램에 유용하다. **(ex. 우선순위 큐)**

  

1. **최대 힙(Max Heap)** : 루트 노드로 올라갈수록 저장된 값이 커지는 구조. 우선순위는 **값이 큰 순서대로 우선** 하게 됨
2. **최소 힙(Min Heap)** : 루트 노드로 올라갈수록 값이 작아지는 구조. 우선순위는 **값이 작은 순서대로 우선** ㅎㅏ게 됨



## 힙(Heap)의 데이터 저장 개념

1. `최소 힙` 에 저장할 때

   - 노드에 저장된 값들을 우선순위로 생각하며, 숫자가 작은 순서대로 우선순위가 높은 것

   - 제일 먼저 들어올 새 노드를 **우선순위가 가장 낮다는 가정**을 하고 **맨 끝 위치**ㅇㅔ 저장

     ![](https://i.imgur.com/VeuoJbK.png)

     (https://chanhuiseok.github.io/posts/ds-4/)

     ```python
     heap[k] <= heap[2*k+1] and heap[k] <= heap[2*k+2]
     '''
          1  ---> root
        /   \
       3     5
      / \   /
     4   8 7
     '''
     ```

## 힙(Heap)의 데이터 삭제 개념

1. 최소 힙에서 삭제할 때

   ![](https://i.imgur.com/6dimHpq.png)

   

## 파이썬에서 Heapq(힙큐) 내장 모듈 사용

- 모듈 임포트 `import heapq`

```python
def heap():
    list1 = [4,6,8,1]
    # heapq.heapify() = O(n) 시간에 리스트를 힙으로 변환할 수 있음
    heapq.heapify(list1)
    print(list1) #[1,4,8,6]

    # 항목에 힙을 삽입할 땐 heapq.heappush(heap, item)을 사용
    h=[]
    heapq.heappush(h, (1,'food'))
    heapq.heappush(h, (2, 'have fun'))
    heapq.heappush(h, (3, 'work'))
    heapq.heappush(h, (4, 'study'))

    print(h) # [(1,'food'),(2,'have fun'),(3,'work'),(4,'study')]

    # heapq.heappop(heap) 함수는 힙에서 가장 작은 항목을 제거하고 반환
    print(heapq.heappop(list1)) # 1
    print(list1) # [4,6,8]

    # heapq.heappushpop(heap, item)은 새 항목을 힙에 추가한 후 가장 작은 항목을 제거하고 반환
    # heapq.heapreplace(heap,item)은 힙의 가장 작은 항목을 제거하고 반환한 후 새 항목을 추가
    # heapq.merge(*iterables)는 여러 개의 정렬된 반복 가능한 객체를 병합하여 하나의 정렬된 결과의 이터레이터를 반환
```



## 최대 힙 구현

```python
import heapq

nums = [4, 1, 7, 3, 8, 5]
heap = []

for num in nums:
  heapq.heappush(heap, (-num, num))  # (우선 순위, 값)

while heap:
  print(heapq.heappop(heap)[1])  # index 1
```

