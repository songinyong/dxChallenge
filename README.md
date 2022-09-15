# dxChallenge
내 주변 가게의 할인 정보를 실시간으로 볼 수 있는 오이소 앱 입니다

22.8.5~22.8.13 총 2주간 진행된 Dx Challenge 해커톤에서 우수상을 받은 mvp 앱입니다.

IOS 기반으로 대회기간동안 출시완료후 2번의 업데이트가 진행되었으며,
이어지는 dxCamp 캠프를 통해 더욱 앱을 발전시키는중 입니다

<div class="grid-image">
<img src=https://user-images.githubusercontent.com/30370933/188375706-9724425b-9d28-4719-a5c0-e393b735a374.gif width="200" height="400">
<img src=https://user-images.githubusercontent.com/30370933/188375621-186d30bd-5a8a-4f53-ac08-4ae7f645f3e0.gif width="200" height="400">
</div>

### 앱소개 페이지:
https://dull-plough-973.notion.site/31b5c848597b439d959e6c5c8495290d


## api 관리 링크
https://songinyong.gitbook.io/dx-sprint/

---------------------------------------------
# 팀구성

송인용 : 백엔드 개발, 인프라 관리 담당

김민기, 최진한 : ios 앱 개발 담당

박준우 : 디자인 레이아웃 담당

신현지 : 기획, 데이터 분석 담당


----------------------------------------

## 개발 코드
dx-challenge : 백엔드 프로젝트
dx-sprint/dxChallenge : 아이폰 앱 프로젝트

## 커밋 방법
2주안에 기획부터 개발후 앱마켓에 출시까지 완료해야했기에, 각각의 개발팀에서 수시로 커밋하는 단계로 이루어짐

main branch에 직접 배포할경우 코드 충돌 우려가 있어 백엔드와 앱개발 branch로 나눈후 후에 통합하는 단계를 거침 

----------------------------------------
# CI Tool - Jenkins
백엔드 코드 수정시 젠킨스 서버에서 테스트 진행 - 테스트 완료되면 빌드하여 api 서버에 배포함

<img src=https://user-images.githubusercontent.com/30370933/187950181-c136c2f2-8926-4370-9cb9-6bc756aa5497.PNG width="400" height="300">

---------------------------------------
#기타사항

대회 이후 지속적인 개발을 위해 작업 진행은 trello를 통해 공유됨

현재 mvp 단계의 앱에서 실제 지속될 수 있는 앱으로 발전시키기위해 요구사항부터 재정의 진행중

