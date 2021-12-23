## 초기 설정 값

<hr/>

#### :incoming_envelope: 개발 환경

##### :o:언어 : JAVA

##### :o: 프레임워크 : SpringBoot

##### :o:데이터베이스 : h2

<hr/>

* application.yml

![](.\readme_img\init01.PNG)



jpa.hibername.ddl-auto : create 로 설정한 이유는 요구사항이 사용자와 게시글은 이미 존재하다고 가정을 하기 때문에 처음 스프링을 실행했을 때

initDb를 통해서 초기값을 생성하였음

![](.\readme_img\init03.PNG)

초기에 스프링 실행 시 h2 DB를 확인해 보면

![](.\readme_img\init04.PNG)

useA와 useB값과 이 유저들이 만든 게시글이 삽입되어 있게 되어 있다.

<hr/>

* h2 설정

![](.\readme_img\init02.PNG)

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

![](.\readme_img\header.PNG)



#### :balloon:투표 생성

| 필드     | 설명                                                         | 타입   | 필수여부 |
| -------- | ------------------------------------------------------------ | ------ | -------- |
| boardId  | 게시글 id                                                    | Long   | 필수     |
| name     | 투표 제목(최대길이 100)                                      | String | 필수     |
| content  | 투표 설명()                                                  | String | 옵셔널   |
| elements | 투표 항목                                                    | Array  | 필수     |
| time     | 투표의 마감시간할 시간을 의미하여 60을 입력한다면 60분 후에 종료하겠다는 의미 (default 값은 3600L(24시간), 값이 0일 때 default 값으로 설정) | Long   | 필수     |



#### :play_or_pause_button: 사용 예시(postman 사용)

![](.\readme_img\createBody.PNG)

* 결과(id 생성, 한 게시글엔 한 투표만 생성)

![](.\readme_img\createResult.PNG)

* 결과 테이블

![](.\readme_img\createResultTable.PNG)





<hr/>

#### :balloon: 투표 조회

| 필드    | 설명        | 타입   | 필수여부 |
| ------- | ----------- | ------ | -------- |
| boardId | 게시글의 id | Long   | 필수     |
| voteId  | 투표의 id   | String | 필수     |



#### :play_or_pause_button:사용 예시

![](.\readme_img\getBody.PNG)

* 결과

![](.\readme_img\getResult.PNG)





<hr/>

#### :balloon:투표 선택

| 필드      | 설명        | 타입   | 필수여부 |
| --------- | ----------- | ------ | -------- |
| boardId   | 게시글 id   | Long   | 필수     |
| voteId    | 투표 id     | String | 필수     |
| elementId | 투표항목 id | Long   | 필수     |



#### :play_or_pause_button:사용 예시

![](.\readme_img\selectBody.PNG)

* 결과

  * 첫 번째 시도

    ![](.\readme_img\selectResult01.PNG)

  * 똑같은 request로 두 번째 시도

    ![](.\readme_img\selectResult02.PNG)

  * 투표 조회로 다시 확인했을 때 (투표 항목 카운트 증가)

    ![](.\readme_img\selectResult03.PNG)

  * 투표 시간이 지났을 때

    ![](.\readme_img\selectResult04.PNG)

  * Header의 user를 바꿔 선택을 하고 투표를 조회할 때, 카운트가 증가함

    ![](.\readme_img\selectResult05.PNG)