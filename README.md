# 2nd_SchoolStock
기존 1차 Tomcat서버에서 돌아가는 mybatis 구조를 SpringBoot FW을 변환했습니다.
문서화 작업은 노션을 이용해서, 작성했습니다.

### 노션 링크
https://www.notion.so/KOSTA-337e2c8a37e7807b9a0bc6b612928f78?source=copy_link

### 브랜치(Branch)
- main
  : 통합 테스트까지 마친 default 브랜치를 최종적으로 합친다. PR은 필수
- default
  : 단위 테스트까지 마친 feature 브랜치를 주기적으로 합친다. PR은 필수
- feature
  : 각각의 기능을 개발할 때, 사용한다.

  feature -> default -> main(최종)
