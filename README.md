# 🛻 스파르타 물류 관리 시스템 🛻
AI API를 활용한 허브 및 업체 간 배송과 주문 관리 시스템

<br>
<br>

# 👨‍👩‍👧‍👦 Our Team
| 조성진(리더)   | 김혜린(부리더)   | 박지안(부리더)   |
|--------|--------|----------|
| 1. API Gateway <br> 2. Spring Security <br> 3. Hub| 1. AI API를 통한 배송 관리 <br> 2. slack message <br> 3.허브 관리자 및 업체 관리  | 1. 주문 <br> 2. 업체의 상품 관리 <br> 3. 배송      |

<br>
<br>


# 프로젝트 목적
1. Spring Cloued와 MSA를 기반의 플랫폼 구축 역량 향상  
2. MSA와 DDD 개념 통합 적용  
3. 날씨 API, GeminiAPI 등 외부 API 통신에 대한 이해 증진  
4. Spring Security 및 jwt를 이용한 사용자 인증/인가 관리  

<br>
<br>


# 서비스 구성 및 실행 방법  
![image](https://github.com/user-attachments/assets/21f5fe86-2e12-4ab6-bbda-77059b155964)  

**MASTER**  
[auth] Master 회원가입, 로그인    
- 회원가입 : `POST` http://auth-service:19098/api/auth/sign-up  
- 로그인 : `GET` http://auth-service:19098/user/login
  
[hub] 여러 허브 생성  
- 허브 생성 : `POST` http://hub-service:19091/api/hubs/many

[hub-management] 생산업체, 배송기사 등록  
- 생산 업체 생성 : `POST` http://hub-management-service:19091/api/companies  
- 배송 기사 생성 : `POST` http://hub-management-service:19091/api/couriers

[auth] 허브매니저, 생산업체, 배송기사 회원가입  
- 허브 매니저 회원 가입 : `POST` http://auth-service:19098/api/auth/sign-up  
- 생산 업체 회원 가입 : `POST` http://auth-service:19098/api/auth/sign-up  
- 배송 기사 회원 가입 : `POST` http://auth-service:19098/api/auth/sign-up  

**생산업체**  
[auth] 로그인  
- 로그인 : `GET` http://auth-service:19098/user/login


[product-company] 상품 등록, 조회(재고확인)  
- 상품 등록 : `POST` http://product-company-service:19091/api/products  
- 상품 조회 : `GET` http://product-company-service:19091/api/products/{product_id}

[order] 주문 및 배송 등록
- 주문 및 배송 등록 : `POST` http://orders-service:19091/api/orders  
  
**허브매니저**  
[auth] 로그인  
- 로그인 : `GET` http://auth-service:19098/user/login

[hub-management] 허브매니저 본인 정보 수정
- 허브 매니저 정보 수정 : `PATCH` http://hub-management-service:19091/api/hubmanagers/{hub_manager_id}
  
[slack-message] 메세지 보내기
- 슬랙 메시지 생성 : `POST` http://slack-message-service:19091/api/messages
- 슬랙 메시지 보내기 : `GET` http://slack-message-service:19091/api/messages/{slack_message_id}/send



**배송기사**  
[auth] 로그인  
- 로그인 : `GET` http://auth-service:19098/user/login  
[hub-management] 배송기사 본인정보 수정  
[ai] 배송정보 메세지확인  


<br>
<br>

# ERD   
![미니물류시스템ERD (1)](https://github.com/user-attachments/assets/e41f4863-4f94-4dc5-9682-068ab540acd3)  

  

<br>
<br>


# 기술 스택 

![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-%236DB33F.svg?style=for-the-badge&logo=spring-security&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-%236DB33F.svg?style=for-the-badge)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-%23316192.svg?style=for-the-badge&logo=postgreSQL&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=gradle&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Git](https://img.shields.io/badge/Git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Eureka](https://img.shields.io/badge/Eureka-4A154B?style=for-the-badge)
![API Gateway](https://img.shields.io/badge/API%20Gateway-FF6C37?style=for-the-badge)
![Feign Client](https://img.shields.io/badge/Feign%20Client-6DB33F?style=for-the-badge)
![RestTemplate](https://img.shields.io/badge/RestTemplate-02303A?style=for-the-badge)
![QueryDSL](https://img.shields.io/badge/QueryDSL-000000?style=for-the-badge)
![Weather API](https://img.shields.io/badge/Weather%20API-1E90FF?style=for-the-badge)
![Gemini API](https://img.shields.io/badge/Gemini%20API-8A2BE2?style=for-the-badge)

<br>
<br>


# 트러블 슈팅 
**build.gradle에서 MapStruct와 Lombok 설정 충돌 해결** [WIKI 보기](https://github.com/choose-name-quickly/logistics-proto-msa/wiki/%5BTrouble-Shooting%5D-build.gradle%EC%97%90%EC%84%9C-MapStruct%EC%99%80-Lombok-%EC%84%A4%EC%A0%95-%EC%B6%A9%EB%8F%8C-%ED%95%B4%EA%B2%B0)  
**FeignClient 호출문제 및 정리** [WIKI 보기](https://github.com/choose-name-quickly/logistics-proto-msa/wiki/%5BTrouble-Shooting%5D-FeignClient-%ED%98%B8%EC%B6%9C%EB%AC%B8%EC%A0%9C-%EB%B0%8F-%EC%A0%95%EB%A6%AC)  
**postgreSQL의 스키마와 DB** [WIKI 보기](https://github.com/choose-name-quickly/logistics-proto-msa/wiki/%5BTrouble-Shooting%5D-postgreSQL%EC%9D%98-%EC%8A%A4%ED%82%A4%EB%A7%88%EC%99%80-DB)  
**멀티 모듈을 통한 유지 보수성 향상** [WIKI 보기](https://github.com/choose-name-quickly/logistics-proto-msa/wiki/%5BTrouble-Shooting%5D-%EB%A9%80%ED%8B%B0-%EB%AA%A8%EB%93%88%EC%9D%84-%ED%86%B5%ED%95%9C-%EC%9C%A0%EC%A7%80-%EB%B3%B4%EC%88%98%EC%84%B1-%ED%96%A5%EC%83%81)  

<br>
<br>
<br> 
<br>
 
