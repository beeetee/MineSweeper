# MineSweeper
## 지뢰찾기 전문가 시스템

### 개요
Eclipse 환경에서 Java를 이용하여 지뢰찾기 게임을 실행시키면, 자동으로 지뢰를 찾아주는 간단한 전문가 시스템

### 개발 환경
1. Eclipse
2. java </br>
   jdk1.8 </br>
   jre1.8 </br>
   
### 구현
1. 시작 위치는 랜덤하게 선정하여 선택
2. 알려진 위치를 (x, y)라고 가정하면, xArr = {-1, 0, 1, -1, 1, -1, 0, 1}, yArr={-1, -1, -1, 0, 0, 1, 1, 1} 이용하여 이동
3. 알려진 위치 (x, y)의 값은 0~8로, 주변에 위치한 지뢰의 개수 표현
4. 확실하게 지뢰가 위치하는 곳 먼저 제거
5. 확실하게 지뢰가 위치하는 곳이 없다면, 확실하게 지뢰가 없는 곳 선택하여 정보 수집

