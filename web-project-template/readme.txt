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

# 클라언트 테스트 케이스 

- find all
REQUEST TYPE : GET
URL : http://localhost:8080/sample/users

- find a user
REQUEST TYPE : GET
URL : http://localhost:8080/sample/users/{id}

- create 
REQUEST TYPE : POST 
URL : http://localhost:8080/sample/users
BODY :{"name":"JiYong","age":27,"activeType":"Y","dept":{"deptId":3,"deptNameType":"CODER"}}

- update
REQUEST TYPE : PUT
URL : http://localhost:8080/sample/users/{id}
BODY : {"id":1, name":"JiYong","age":27,"activeType":"Y","dept":{"deptId":3,"deptNameType":"CODER"}}

