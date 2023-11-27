![board-collie](https://github.com/S09P31A104/board-collie/blob/docs/readme/assets/main2.png)


<div align=center>
    <a href="https://boardcollie.com"><del>[ Board-collie 서비스 바로가기 ]</del></a>
    <br>
    <a href="#">[ Board-collie 기획 발표영상 바로가기 ]</a>
</div>

<br>
<br>

## ⭐ About Board-collie
본 **Board-collie** 프로젝트는 기존 보드게임 카페 서비스의 문제점을 해결하여 매장 이용 고객들이 보드게임 카페를 더욱 편리하게 이용하고, <br>보드게임이라는 취미를 보다 더 쉽게 입문할 수 있게 하는 것을 목표로 진행되었습니다.

- 프로젝트 기간 : 2023/10/10 ~ 2023/11/24
- 배포 기간 : 2023/10/24 ~ 2023/11/30
> GitLab에서 프로젝트를 진행한 후, GitHub으로 옮겨왔습니다.


<br>
<br>

## ⭐ 기획 배경

먼저 저희는, 보드게임 카페 서비스의 문제점을 크게 **3가지**로 정의했습니다.
![board-collie](https://github.com/S09P31A104/board-collie/blob/docs/readme/assets/문제점.png)

- **단방향 정보**
    - 게임 시작 전, **설명서** 또는 **영상** 등의 일방적인 정보만 제공. 플레이 중 궁금한 점이 생길 경우, 직원을 호출하거나 해당 내용이 설명서 또는 영상의 어느 부분에 있는지 일일히 찾아야 함.
- **많은 게임, 부실한 추천**
    - 보드게임 카페는 평균 약 **300여 개**의 게임을 제공하지만, 단순히 매장 직원 추천 또는 인원수, 장르 별 **필터링 기능만 제공**. 개인화된 맞춤 추천이 없음.
- **튜토리얼의 부재**
    - 게임에 대한 영상 및 텍스트 기반의 **설명만 제공**. 실제로 플레이하면서 따라해볼 수 있는 기능은 제공하지 않음.
      

그 후, 저희는 해당 문제점들을 상세히 분석하여 사용자 입장에서의 요구사항을 정의했고, 이를 해결하는 방향으로 프로젝트를 진행하였습니다.

## ⭐ 프로젝트 주요 기능

### 1. 챗봇 서비스

> **게임 상세 페이지** 및 **실물 게임 상자**에 부착된 QR을 스캔하여 모바일 챗봇 서비스를 이용할 수 있습니다.
>
> 해당 챗봇을 통해 사용자는 플레이 중 발생한 **궁금증**이나 **돌발상황**에 대해 자유롭게 질문할 수 있고, 이에 대한 답변을 즉각적으로 확인할 수 있습니다.
>
> 또한 질문의 답변이 명확하지 않거나 자세한 설명이 필요할 경우, 추가적인 질문을 통해 더욱 자세한 답변을 받을 수 있습니다.

<table>
  <tr>
    <td align="center">
      <img src="https://github.com/S09P31A104/board-collie/blob/master/assets/%ED%83%9C%EA%B7%B8%20%EA%B2%80%EC%83%89.gif" />
    </td>
    <td align="center">
      <img src="https://github.com/S09P31A104/board-collie/blob/master/assets/%EC%B5%9C%EA%B7%BC%20%EB%B3%B8%20%EA%B2%8C%EC%9E%84.gif" />
    </td>
    <td align="center">
      <img src="https://github.com/S09P31A104/board-collie/blob/docs/readme/assets/%EC%B1%97%EB%B4%87_%EC%8A%A4%ED%94%8C%EB%A0%8C%EB%8D%94.gif" />
    </td>
  </tr>
  <tr>
    <td align="center">
      <span>태그 검색</span>
    </td>
    <td align="center">
      <span>최근 본 게임</span>
    </td>
    <td align="center">
      <span>챗봇 질의응답</span>
    </td>
  </tr>
</table>

<br>
<br>

### 2. 보드게임 추천

> 간단한 설문을 통해 **내 취향에 맞는 보드게임**들을 추천받을 수 있습니다.
> 
> 사용자는 보드게임의 플레이 방식에 대한 **몇 가지의 설문**을 진행하고, 전체 보드게임 중 일치율이 가장 높은 **6종류**의 보드게임을 추천받을 수 있습니다.
>
> 또한 게임 상세 페이지에서 해당 게임과 유사한 **3종류**의 게임을 확인할 수도 있습니다.

<table>
  <tr>
    <td align="center">
      <img src="https://github.com/S09P31A104/board-collie/blob/master/assets/%EA%B2%8C%EC%9E%84%20%EC%B6%94%EC%B2%9C.gif" />
    </td>
    <td align="center">
      <img src="https://github.com/S09P31A104/board-collie/blob/master/assets/%EC%9C%A0%EC%82%AC%ED%95%9C%20%EA%B2%8C%EC%9E%84.gif" />
    </td>
  </tr>
  <tr>
    <td align="center">
      <span>설문을 통한 추천</span>
    </td>
    <td align="center">
      <span>유사한 게임 추천</span>
    </td>
  </tr>
</table>

> 해당 서비스에 대한 기술적인 부분이 포함된 회고록 링크 입니다.
- <a href="https://careful-fan-de2.notion.site/3-4eb11124068746ffad1713ebd5cee890?pvs=4">3주차 주간 회고</a>
- <a href="https://careful-fan-de2.notion.site/4-4a6957eb8594486eb690c59f299e3676?pvs=4">4주차 주간 회고</a>
- <a href="https://careful-fan-de2.notion.site/5-5d222fb5651a421aa5177ddb72f4ed6d?pvs=4">5주차 주간 회고</a>

<br>
<br>

### 3. 튜토리얼

> 튜토리얼 기능을 통해 게임의 시작부터 끝까지, 게임 플레이 중 발생할 수 있는 상황에 대해 알맞은 **설명**과 **선택지**를 제공합니다.
>
> 사용자는 **상황에 대한 설명**과 해당 상황에 **취할 수 있는 행동**을 선택지로 제공받게 되고, 이를 단순히 터치하는 것 만으로 마치 온라인 게임의 **튜토리얼 시스템**을 이용하는 듯한 경험을 느낄 수 있습니다.

<table>
  <tr>
    <td align="center">
      <img src="https://github.com/S09P31A104/board-collie/blob/master/assets/%EC%8A%A4%ED%94%8C%EB%A0%8C%EB%8D%94%20%ED%8A%9C%ED%86%A0%EB%A6%AC%EC%96%BC.gif"/>
    </td>
    <td align="center">
      <img src="https://github.com/S09P31A104/board-collie/blob/master/assets/%EB%A7%88%ED%97%A4.gif" />
    </td>
    <td align="center">
      <img src="https://github.com/S09P31A104/board-collie/blob/master/assets/%EB%A3%A8%EB%AF%B8%ED%81%90%EB%B8%8C.gif"/>
    </td>
  </tr>
  <tr>
    <td align="center">
      <span>스플렌더</span>
    </td>
    <td align="center">
      <span>마헤</span>
    </td>
    <td align="center">
      <span>루미큐브</span>
    </td>
  </tr>
</table>


<br>
<br>

### 4. 기타 - 게임 검색 및 상세 페이지

> 찾고 싶은 게임을 **제목** 또는 게임의 **태그**로 검색하고, 필터 기능을 통해 **필터링**할 수 있습니다.
>
> 게임 상세 페이지의 **좌측**에는 **챗봇 QR코드**, **튜토리얼 및 YouTube 설명영상 바로가기 링크**가 제공됩니다.
>
> 게임 상세 페이지의 **우측**에는 게임의 **테마** 및 **진행방식** 분류, 게임에 대한 **유저평** 요약, 해당 게임과 **유사한 다른 게임**을 확인할 수 있습니다.

<br>
<br>






## ⭐ 기술 스택

<h3 align="center">Front-end</h3>
<p align="center">
    <img src="https://img.shields.io/badge/Node.js-339933?&logo=nodedotjs&logoColor=white">
    <img src="https://img.shields.io/badge/React-61DAFB?&logo=react&logoColor=white">
    <img src="https://img.shields.io/badge/PWA-5A0FC8?&logo=pwa&logoColor=white">
    <img src="https://img.shields.io/badge/TypeScript-3178C6?&logo=typescript&logoColor=white">
    <img src="https://img.shields.io/badge/Redux-764ABC?&logo=redux&logoColor=white">
    <img src="https://img.shields.io/badge/axios-5A29E4?&logo=axios&logoColor=white">
    <img src="https://img.shields.io/badge/ReactRouter-CA4245?&logo=reactrouter&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/ESLint-4B32C3?&logo=eslint&logoColor=white">
    <img src="https://img.shields.io/badge/Prettier-F7B93E?&logo=prettier&logoColor=white">
    <img src="https://img.shields.io/badge/Mui-007FFF?&logo=mui&logoColor=white">
    <img src="https://img.shields.io/badge/styledcomponents-DB7093?&logo=styledcomponents&logoColor=white">
    <img src="https://img.shields.io/badge/Chart.js-FF6384?&logo=chartdotjs&logoColor=white">
</p>

<h3 align="center">Back-end</h3>
<p align="center">
    <img src="https://img.shields.io/badge/Java-007396?&logo=java&logoColor=white">
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/Gradle-02303A?&logo=gradle&logoColor=white">
    <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?&logo=springsecurity&logoColor=white">
    <img src="https://img.shields.io/badge/JWT-000000?&logo=jsonwebtokens&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/Hibernate-59666C?&logo=hibernate&logoColor=white">
    <img src="https://img.shields.io/badge/MySQL-4479A1?&logo=mysql&logoColor=white">
    <img src="https://img.shields.io/badge/Redis-DC382D?&logo=redis&logoColor=white">
    <img src="https://img.shields.io/badge/H2-FF9900?&logo=h2&logoColor=white">
    <img src="https://img.shields.io/badge/Swagger-85EA2D?&logo=swagger&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/Python-3776AB?&logo=python&logoColor=white">
    <img src="https://img.shields.io/badge/Selenium-43B02A?&logo=selenium&logoColor=white">
</p>

<h3 align="center">Back-end(게임 추천)</h3>
<p align="center">
    <img src="https://img.shields.io/badge/Java-007396?&logo=java&logoColor=white">
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/Gradle-02303A?&logo=gradle&logoColor=white">
    <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?&logo=springsecurity&logoColor=white">
    <img src="https://img.shields.io/badge/JWT-000000?&logo=jsonwebtokens&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/Hibernate-59666C?&logo=hibernate&logoColor=white">
    <img src="https://img.shields.io/badge/MySQL-4479A1?&logo=mysql&logoColor=white">
    <img src="https://img.shields.io/badge/Redis-DC382D?&logo=redis&logoColor=white">
    <img src="https://img.shields.io/badge/H2-FF9900?&logo=h2&logoColor=white">
    <img src="https://img.shields.io/badge/Swagger-85EA2D?&logo=swagger&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/Python-3776AB?&logo=python&logoColor=white">
    <img src="https://img.shields.io/badge/Selenium-43B02A?&logo=selenium&logoColor=white">
</p>

<h3 align="center">Infra</h3>
<p align="center">
    <img src="https://img.shields.io/badge/Docker-2496ED?&logo=docker&logoColor=white">
    <img src="https://img.shields.io/badge/Jenkins-D24939?&logo=jenkins&logoColor=white">
    <img src="https://img.shields.io/badge/nginx-009639?&logo=nginx&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/ubuntu-E95420?&logo=ubuntu&logoColor=white">
    <img src="https://img.shields.io/badge/amazon EC2-FF9900?&logo=amazon ec2&logoColor=white">
    <img src="https://img.shields.io/badge/amazon RDS-527FFF?&logo=amazonrds&logoColor=white">
    <img src="https://img.shields.io/badge/amazon S3-569A31?&logo=amazons3&logoColor=white">
</p>

<h3 align="center">Monitoring</h3>
<p align="center">
    <img src="https://img.shields.io/badge/Docker-2496ED?&logo=docker&logoColor=white">
    <img src="https://img.shields.io/badge/Jenkins-D24939?&logo=jenkins&logoColor=white">
    <img src="https://img.shields.io/badge/nginx-009639?&logo=nginx&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/ubuntu-E95420?&logo=ubuntu&logoColor=white">
    <img src="https://img.shields.io/badge/amazon EC2-FF9900?&logo=amazon ec2&logoColor=white">
    <img src="https://img.shields.io/badge/amazon RDS-527FFF?&logo=amazonrds&logoColor=white">
    <img src="https://img.shields.io/badge/amazon S3-569A31?&logo=amazons3&logoColor=white">
</p>

<h3 align="center">Co-work tool</h3>
<p align="center">
    <img src="https://img.shields.io/badge/GitLab-FC6D26?&logo=GitLab&logoColor=white">
    <img src="https://img.shields.io/badge/Notion-000000?&logo=Notion&logoColor=white">
    <img src="https://img.shields.io/badge/Jira-0052CC?&logo=Jira Software&logoColor=white">
    <img src="https://img.shields.io/badge/Postman-FF6C37?&logo=Postman&logoColor=white">
    <img src="https://img.shields.io/badge/Figma-F24E1E?&logo=Figma&logoColor=white">
    <img src="https://img.shields.io/badge/Mattermost-0058CC?&logo=Mattermost&logoColor=white">
</p>

<br>
<br>

## ⭐ 멤버

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/sgkim6">
        <img src="https://github.com/sgkim6.png" alt="김승규" />
      </a>
    </td>
     <td align="center">
      <a href="https://github.com/yygs321">
        <img src="https://github.com/yygs321.png" alt="박소민" />
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/ykm1256">
        <img src="https://github.com/ykm1256.png" alt="윤경민" />
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/hyunin3">
        <img src="https://github.com/hyunin3.png" alt="심현재" />
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/SeungAh-Yoo99">
        <img src="https://github.com/SeungAh-Yoo99.png" alt="유승아" />
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/HJH13579">
        <img src="https://github.com/HJH13579.png" alt="허주혁" />
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/sgkim6">
        <b>김승규</b>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/yygs321">
        <b>박소민</b>
      </a>
    <td align="center">
      <a href="https://github.com/ykm1256">
        <b>윤경민</b>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/hyunin3">
        <b>심현재</b>
      </a>
    </td>   
    </td>
    <td align="center">
      <a href="https://github.com/SeungAh-Yoo99">
        <b>유승아</b>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/HJH13579">
        <b>허주혁</b>
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <span>PM/Backend(게임 추천)/Data Construction/Monitoring System</span>
    </td>
    <td align="center">
      <span>Backend/Infra</span>
    </td>
    <td align="center">
      <span>Backend</span>
    </td>
    <td align="center">
      <span>Frontend</span>
    </td>
    <td align="center">
      <span>Frontend</span>
    </td>
    <td align="center">
      <span>Frontend</span>
    </td>
  </tr>
</table>
