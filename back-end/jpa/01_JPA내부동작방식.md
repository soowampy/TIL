## JPA의 내부동작 방식

### 지연 로딩과 즉시 로딩

- **지연 로딩** : 객체가 실제 사용될 때 로딩

  ```java
  Member = member = memberDAO.find(memberId); // SELECT * FROM MEMBER
  Team team = member.getTeam();
  STring teamName = team.getName(); // SELECT * FROM TEAM
  ```

- **즉시 로딩** : JOIN SQL로 한번에 연관된 객체까지 미리 조회 

  ```java
  Member = member = memberDAO.find(memberId); // SELECT M.*, T.* FROM MEMBER JOIN TEAM..
  Team team = member.getTeam();
  STring teamName = team.getName();
  ```



### 주의 사항

- JPA의 모든 데이터 변경은 **트랜잭션 안에서 실행**



### 영속성 관리

**🐹영속성 컨텍스트? **

- 엔티티를 영구 저장하는 환경
  - 엔티티의 생명주기
    ➡ **비영속** : 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
    ➡ **영속** : 영속성 컨텍스트에 관리되는 상태
    ➡ **준영속** : 영속성 컨텍스트에 저장되었다가 분리된 상태
    ➡ **삭제** : 삭제된 상태
- `EntityManager.persist(entity);`  👉 엔티티를 영속성 컨텍스트에 저장한다

```java
// 비영속
Member m = new Member();
m.setId(100L);
m.setName("name");

// 영속
em.persist(m);
```

> **영속성 컨텍스트의 이점**
>
> ✔ 1차 캐시
>
> ```java
> // 영속성 컨텍스트에 저장
> Member m = new Member();
> m.setId(100L);
> m.setName("name");
> em.persist(member);
> 
> Member findMember = em.find(Member.class, 100L);
> /**
> * 여기서 실행시키면 select문을 반환하지 않고 insert 문만 반환됨
> * => id가 100L인 데이터의 정보가 1차캐시에 저장되어있기 때문에 db에서 조회 x
> */
> tx.commit();
> ```
>
> ✔ 동일성 보장
>
> ```java
> Member m = em.find(Member.class, "member1");
> Member b = em.bind(Member.class, "member1");
> 
> System.out.println(a == b); // 동일성 비교 true
> ```
>
> ✔ 쓰기 지연
>
> ```java
> tr.begin();
> 
> em.persist(memberA);
> em.persist(memberB);
> // 여기까진 DB에 보내지 않음!!
> 
> // 커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다
> tr.commit();
> ```
>
> 



### 플러시

영속성 컨텍스트의 변경 내용을 데이터베이스에 반영

(영속성 컨텍스트를 비우지 않음! 데이터베이스에 동기화 하는 것이 목적)



### 준영속 상태

- em.detach() : 특정 엔티티만 준영속 상태로
- em.clear() : 영속성 컨텍스트를 완전히 초기화
- em.close() : 영속성 컨텍스트를 종료
