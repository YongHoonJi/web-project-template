# 프로젝트 설명
JPA 기반의 restful api 서버 탬플릿

# 로컬 DATABASE는 H2 DATABASE를 사용 : http://www.h2database.com/html/main.html

# 클라이언트 테스트는 fiddler로 : http://www.telerik.com/fiddler

#참고 - restful api 인증방식
http://bcho.tistory.com/955

#querydsl(mysema) 설정
1. elipse.ini 맨 위에  추가
-vm
C:/Program Files/Java/jdk1.8.0_31/bin/javaw.exe

2. 프로젝트 빌드 패스에 jre -> jdk로 변경

3. mvn eclipse:eclipse

#클라이언트 테스트는 postman으로 사용
see test_collection_postman.json