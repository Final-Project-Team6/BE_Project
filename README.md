# 반포자이 - 아파트너 고도화 <a href="https://fe-project-tau.vercel.app/seoul-signiel"><img src="https://github.com/Final-Project-Team6/BE_Project/assets/131642334/17b9e1be-a56b-4afd-b2ca-67fbfd366b7a" align="left" width="100"></a>

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fdepromeet%2Fstreet-drop-server&count_bg=%2328DBE6&title_bg=%232D3540&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
[![codecov](https://codecov.io/gh/depromeet/street-drop-server/branch/dev/graph/badge.svg?token=7EHWI73ZQU)](https://codecov.io/gh/depromeet/street-drop-server)
[![Github Action](https://github.com/depromeet/street-drop-server/actions/workflows/coverage.yml/badge.svg)](https://github.com/depromeet/street-drop-server/actions)

<br/>

![image](https://github.com/Final-Project-Team6/BE_Project/assets/131642334/3a950b34-fa8c-429a-a519-94e0b29743b1)

![image](https://github.com/Final-Project-Team6/BE_Project/assets/131642334/f929ae69-a8e0-409f-be87-4df0f2512259)

![image](https://github.com/Final-Project-Team6/BE_Project/assets/131642334/ca807190-412d-4c4f-9f61-e3ee8bbf312e)


<br/>


## ✨아파트너 서비스 고도화 배경

- 아파트너의 핵심 목표는 아파트 이해관계자를 위해 아파트너만의 다양한 솔루션을 제공합니다.
- 요구 사항에 따른 아파트너 입주민들이 프라이드를 가질 만큼의 있어빌리티 UI / UX 중요한 목표입니다.
- 요구 사항에 따른 회원 / 인증, 게시판(공지사항 / 정보 / 소통 / 민원) 서비스 고도화가 중요한 목표입니다.


<br/>



##  👨‍👩‍👧‍👦  구성원

| BE(L): 임대일                                                | BE: 이규석                                                   | BE: 나승정                                                   | FE(L): 박수빈                                                | FE: 이주홍                                                   |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| <a href="https://github.com/LimdaeIl"><img src="https://avatars.githubusercontent.com/u/131642334?v=4" alt="profile" width="140" height="140"></a> | <a href="https://github.com/cutegyuseok"><img src="https://avatars.githubusercontent.com/u/103543611?v=4" alt="profile" width="140" height="140"></a> | <a href="https://github.com/NaSJ93"><img src="https://avatars.githubusercontent.com/u/145634600?v=4" alt="profile" width="140" height="140"></a> | <a href="https://github.com/subinsad"><img src="https://avatars.githubusercontent.com/u/92204014?v=4" alt="profile" width="140" height="140"></a> | <a href="https://github.com/dlwnghd"><img src="https://avatars.githubusercontent.com/u/61799492?v=4" width="140" height="140"></a> |
| <div align="center"><a href="https://github.com/LimdaeIl" target="_blank"><img src="https://img.shields.io/badge/LimdaeIl-181717?style=for-the-social&logo=github&logoColor=white"/></a></div> | <div align="center"><a href="https://github.com/cutegyuseok" target="_blank"><img src="https://img.shields.io/badge/cutegyuseok-181717?style=for-the-social&logo=github&logoColor=white"/></a></div> | <div align="center"><a href="https://github.com/NaSJ93" target="_blank"><img src="https://img.shields.io/badge/NaSJ93-181717?style=for-the-social&logo=github&logoColor=white"/></a></div> | <div align="center"><a href="https://github.com/subinsad" target="_blank"><img src="https://img.shields.io/badge/subinsad-181717?style=for-the-social&logo=github&logoColor=white"/></a></div> | <div align="center"><a href="https://github.com/dlwnghd" target="_blank"><img src="https://img.shields.io/badge/dlwnghd-181717?style=for-the-social&logo=github&logoColor=white"/></a></div> |

| PM(L): 이나현 | PM: 오로라 | PM: 김윤희 | UXUI(L): 유채연 | UXUI: 권은비 |
| ------------- | ---------- | ---------- | --------------- | ------------ |
|               |            |            |                 |              |
|               |            |            |                 |              |




<br/>


## 🚎 프로젝트 아키텍처


![image](https://github.com/Final-Project-Team6/BE_Project/assets/131642334/05ae149e-e1ec-426f-9660-9e1543dfc9f1)

- 모든 이해관계자가 쉽게 API 를 확인하고 수행할 수 있도록 HTTPS / HTTP 를 지원하는 Swagger 으로 제공합니다.
- Swap Memory, Elastic IP 등 프로젝트 특성에 불필요한 리소스를 사용하지 않고 최소한의 비용으로 서비스를 제공합니다.
- 한 명의 회원마다 다주택자를 고려해서 [회원 / 인증], [소통, 정보, 민원] 서비스 고도화를 목적으로 프로젝트를 진행했습니다.

<br/>

### 📈BE: Dependency And Devopment Team Tool

**📦 Dependency**
- 프로젝트 생성일자 기준으로 가장 최신 버전(3.2.5)에 호환되는 의존성입니다.
  - SpringBoot 3.2.5
  - Java17
  - Gradle 8.7
  - Spring Data JPA
  - Validation
  - Spring Web
  - Lombok
  - Spring Devtools
  - H2 Database 2.2.224
  - MySQL Database 8.2.0
  - Spring Security 6.2.4
  - Swagger 2.0.2
  - QueryDSL 5.0.0
  - jsonwebtoken-jjwt 0.11.5
  - Spring Cloud AWS 2.2.6
  - Spring Data Redis
 
<br/>

**🚀 Devopment Team Tool**
- **소통 도구**
  - `Slack`: 모든 이해관계자와 대화하는 공간입니다.
  - `Zoom`: 온라인 과정으로 화상 회의가 중심입니다.
  - `Kakaotalk`: 급한 일이 발생할 때를 대비하기 위해 사용합니다.
  - `Notion`: PM, UIUX, BE, FE 일정 및 도출되는 문서 관리 공간입니다.
  - `Discord`: 프로젝트 리포지토리에서 발생되는 모든 일을 전달받습니다.


- **협업 도구(BE)**
  - `Github`: Wiki, Project 를 활용하여 애자일 방법론으로 프로젝트 형상관리를 구성합니다.
  - `Code with me`: 커밋 충돌 혹은 문제 발생하면 모두 함께 리뷰하는 도구입니다.
  - `Erdcloud`: 데이터베이스 구성 요소를 시각화하기 위해 사용된 도구입니다.

<br/>

![image](https://github.com/Final-Project-Team6/BE_Project/assets/131642334/ece70e2e-fe73-4a19-92d1-432985968e79)

<br/>

## 📈 DataBase Schema
![image](https://github.com/Final-Project-Team6/BE_Project/assets/131642334/973744b8-9430-4977-81e9-1978d4955127)

<br/>

## 🍃 Contributors
**👜 Repository**
- [⚙️ BE Repository](https://github.com/Final-Project-Team6/BE_Project)
- [⚙️ FE Repository](https://github.com/Final-Project-Team6/FE_Project)

