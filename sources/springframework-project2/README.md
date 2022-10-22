# Info
Spring Core 과정 결산 프로젝트 과제입니다.

## Todos
- 제공하는 상수도 요금표를 메모리에 로드하고 사용자가 입력한 사용량에 따라 요금을 지자체별, 업종별로 표시하는 기능을 제공하라.
- 가장 저렴한 가격(billTotal)을 가진 항목을 5개만 가격 오름차순으로 표시한다.

## 설계 요구사항
- 다음의 역할을 하는 스프링 빈을 각각 생성해야 한다.
  - csv 파일을 파싱하는 스프링 빈(인터페이스 포함)
  - 결과를 화면에 표시하는 스프링 빈(인터페이스 포함)
  - 요금표 데이터를 저장하고 조회하는 역할을 하는 스프링 빈
  - 입력받은 사용량으로 요금표에서 구간을 찾아내고 요금을 계산해 주는 스프링 빈
- AOP와 Logger를 활용하여 실행하는 스프링 빈의 모든 메소드의 실행 시간을 elapse.log 파일에 저장하라.
- 테스트 코드를 작성하라.
  - Test Coverage는 50%를 넘어야 한다.
- 원본 데이터가 CSV 형식에서 JSON 형식으로 변경되었다. JSON 포멧의 데이터를 읽어 동일한 기능을 하도록 수정하라.
  - 가능하면 확장자에 따라 자동으로 parser를 선택하도록 구현하라.
  - jackson 라이브러리를 사용할 것.

## 논리 설계
![lLDDJi906DtFARei2te2uq8yW8a7QCPIQmDCoKp5XPhuqnX431en4bFyIS34X4KZBaf2F51dUWU7bfGoHM5Xx6fcllU-zxut7L8oKHLYM95se5OoiBMFYWMhR679nX0HNjAH3KnawtWNQhh4pazPlKDR3ZiRiCTtzjWE_3fpN3foMACd7GA9VzFJhXCCESuBxrrwrPFfO1oJwFDb3Xm3e9KX8LBWUtJegt_PU4cljQDyqJf0](https://user-images.githubusercontent.com/60968342/197342787-1e5876d9-6f75-45e4-b88e-fcffba4bb8c0.png)


## Test Coverage
![스크린샷 2022-10-22 오후 10 31 54](https://user-images.githubusercontent.com/60968342/197342766-89bf27ff-fe0d-4fbf-b34b-d85d242c9423.png)
