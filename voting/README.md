## 초기 설정 값

<hr/>

#### :incoming_envelope: 개발 환경

##### :o:언어 : JAVA

##### :o: 프레임워크 : SpringBoot

##### :o:데이터베이스 : h2

<hr/>

* application.yml

![](https://user-images.githubusercontent.com/22831002/147255355-369b0360-1c7a-4247-9bfe-2da59258c821.PNG)



jpa.hibername.ddl-auto : create 로 설정한 이유는 요구사항이 사용자와 게시글은 이미 존재하다고 가정을 하기 때문에 처음 스프링을 실행했을 때

initDb를 통해서 초기값을 생성하였음

![](https://user-images.githubusercontent.com/22831002/147255360-3250b1cf-12ea-4b07-b6ae-44b544d9e5e6.PNG)

초기에 스프링 실행 시 h2 DB를 확인해 보면

![](https://user-images.githubusercontent.com/22831002/147255363-68d40a35-ca9b-4985-9156-d99f1d6576f2.PNG)

useA와 useB값과 이 유저들이 만든 게시글이 삽입되어 있게 되어 있다.

<hr/>

* h2 설정

![](https://user-images.githubusercontent.com/22831002/147255357-aff99d0c-f36c-4938-bc18-83314578d074.PNG)

<hr/>



## 실행 방법

<hr/>

```shell
{base_dir}/voting> gradlew build # 빌드
{base_dir}/voting> cd build/libs
{base_dir}/voting/build/libs/>java -jar voting-1.0.0.jar # 실행
```





## API 명세

#### :card_index:기본 헤더 설정(현재 사용자의 ID)

![](https://user-images.githubusercontent.com/22831002/147255352-c8b8d815-bfcd-4f51-92d1-e1c26a5dd7f3.PNG)



#### :balloon:투표 생성

| 필드     | 설명                                                         | 타입   | 필수여부 |
| -------- | ------------------------------------------------------------ | ------ | -------- |
| boardId  | 게시글 id                                                    | Long   | 필수     |
| name     | 투표 제목(최대길이 100)                                      | String | 필수     |
| content  | 투표 설명()                                                  | String | 옵셔널   |
| elements | 투표 항목                                                    | Array  | 필수     |
| time     | 투표의 마감시간할 시간을 의미하여 60을 입력한다면 60분 후에 종료하겠다는 의미 (default 값은 3600L(24시간), 값이 0일 때 default 값으로 설정) | Long   | 필수     |



#### :play_or_pause_button: 사용 예시(postman 사용)

![](https://user-images.githubusercontent.com/22831002/147255338-66a07b36-7e85-4a48-94b8-ba54647e52e0.PNG)

* 결과(id 생성, 한 게시글엔 한 투표만 생성)

![](.\readme_img\createResult.PNG)

* 결과 테이블

![](https://user-images.githubusercontent.com/22831002/147255346-4b3e4391-96fd-4ea3-a02e-543f5b790dcb.PNG)





<hr/>

#### :balloon: 투표 조회

| 필드    | 설명        | 타입   | 필수여부 |
| ------- | ----------- | ------ | -------- |
| boardId | 게시글의 id | Long   | 필수     |
| voteId  | 투표의 id   | String | 필수     |



#### :play_or_pause_button:사용 예시

![](https://user-images.githubusercontent.com/22831002/147255348-e82b481a-25c0-4080-8fbe-e0a02bd51c06.PNG)

* 결과

![](https://user-images.githubusercontent.com/22831002/147255351-48f88dfb-7338-4277-a64e-fb391bad36f7.PNG)





<hr/>

#### :balloon:투표 선택

| 필드      | 설명        | 타입   | 필수여부 |
| --------- | ----------- | ------ | -------- |
| boardId   | 게시글 id   | Long   | 필수     |
| voteId    | 투표 id     | String | 필수     |
| elementId | 투표항목 id | Long   | 필수     |



#### :play_or_pause_button:사용 예시

![](https://user-images.githubusercontent.com/22831002/147255365-ed509474-c05d-4519-b658-744b17b08061.PNG)

* 결과

  * 첫 번째 시도

    ![](https://user-images.githubusercontent.com/22831002/147255367-e6db7452-f956-48a2-90f5-a5ee43f005ac.PNG)

  * 똑같은 request로 두 번째 시도

    ![](https://user-images.githubusercontent.com/22831002/147255368-85880d82-5816-443c-a3da-461af4ca9797.PNG)

  * 투표 조회로 다시 확인했을 때 (투표 항목 카운트 증가)

    ![](https://user-images.githubusercontent.com/22831002/147255370-abfa71de-8313-4c6b-aea1-421a592d34b4.PNG)

  * 투표 시간이 지났을 때

    ![](https://user-images.githubusercontent.com/22831002/147255371-c0ef7a0d-fe69-4e08-b60c-0532a9fb09de.PNG)

  * Header의 user를 바꿔 선택을 하고 투표를 조회할 때, 카운트가 증가함

    ![](https://user-images.githubusercontent.com/22831002/147255373-bb96a1a8-f4a5-4984-be3b-f7592d7a7661.PNG)