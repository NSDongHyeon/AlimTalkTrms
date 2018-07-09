# *AlimTalk Procedure*
 알림톡 메세지 전송 프로시저(이하 Alim)는 SMS 전송 프로시저에 조건 및 Insert쿼리를 추가한 것이다.
 
 문자발송 기능이 사용 되는 프로시저(EX. 제휴 출고완료 정보 SMS 문자 발송)에서 Alim을 호출하여 데이터를 넘겨준다.
그러면 Alim은 받은 데이터들과 SELECT쿼리에서 조회되는 Result값들을 갖고 조건, 예외처리, Data Insert를 수행하게 된다.
 
# *Java 구현*
 Controller: 데이터를 받아서 넘겨주거나 컨트롤러에서 데이터를 바로 수정하여 넘겨주는 기능.
 
 Service: 프로시저의 조건, 예외처리 로직을 구현.
 
 DAO: SELECT query의 결과값을 list 형태로 받거나 input data를 Object 형태로 INSERT query에 넘겨주는 기능.
 
 xml: query 작성. 
