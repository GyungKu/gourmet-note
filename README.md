# 식도락 노트
<p align="center">
  <img src="https://github.com/user-attachments/assets/612eab23-7ed9-48d2-86ec-5fdbcffab7fc"  width="300" height="300"/>
</p>

- 배포: https://gourmetnote.kro.kr/

## 프로젝트 소개
- 공개된 곳에 리뷰를 남기기 싫고 기록 해두고 나중에 참고하고 싶을 때 이용할 수 있는 나만의 리뷰 서비스입니다.

## 서비스 화면
![image](https://github.com/user-attachments/assets/e674c5b4-140e-4526-8eab-4d85d358a0ea)



## 개발 환경
- Backend: Java 17, Spring Boot 3
- FrontEnd: TypeScrpit, Vue 3
- DataBase: PostgreSQL
- Deploy: Termux(Ubuntu)

## 프로젝트 구조
<details>
<summary>토글 접기/펼치기</summary>

├─.github             
│  └─workflows          
├─front           
│  ├─public            
│  └─src            
│      ├─assets           
│      │  ├─image           
│      │  └─js           
│      ├─components           
│      ├─model           
│      ├─router           
│      ├─stores           
│      └─views           
│          ├─login           
│          ├─my           
│          └─review           
├─gradle           
│  └─wrapper           
└─src           
    ├─main           
    │  ├─java           
    │  │  └─com           
    │  │      └─gk           
    │  │          └─gourmet_note           
    │  │              ├─common           
    │  │              │  ├─config           
    │  │              │  ├─entity           
    │  │              │  ├─exception           
    │  │              │  │  └─vo           
    │  │              │  ├─filter           
    │  │              │  └─naver           
    │  │              ├─image           
    │  │              │  ├─entity           
    │  │              │  ├─repository           
    │  │              │  ├─service           
    │  │              │  └─vo           
    │  │              ├─review           
    │  │              │  ├─controller           
    │  │              │  ├─entity           
    │  │              │  ├─repository           
    │  │              │  ├─service           
    │  │              │  └─vo           
    │  │              ├─shop           
    │  │              │  ├─controller           
    │  │              │  ├─entity           
    │  │              │  ├─repository           
    │  │              │  ├─service           
    │  │              │  └─vo           
    │  │              └─user           
    │  │                  ├─controller           
    │  │                  ├─entity           
    │  │                  ├─repository           
    │  │                  ├─service           
    │  │                  └─vo           
    │  └─resources           
    │      ├─static           
    │      │  └─assets           
    │      └─templates           
    └─test           
        ├─java           
        │  └─com           
        │      └─gk           
        │          └─gourmet_note           
        └─resources           

</details>

## 아키텍처
<p align="center">
  <img src="https://github.com/user-attachments/assets/9e9a492c-74c1-4f28-99c6-272d8656b654"   width="600" height="400"/>
</p>


## ERD
<p align="center">
  <img src="https://github.com/user-attachments/assets/dfae193a-24dd-4142-9119-7c29a9efe74f"   width="800" height="400"/>
</p>
