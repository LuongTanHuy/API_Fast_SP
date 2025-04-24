# BackEnd_Fast-DeliveryAPI APP FAST FOOD
Giới thiệu
API viết bằng Spring Boot cho ứng dụng Fast Food (đồ án chuyên ngành 1):

Cài đặt và Chạy Ứng Dụng
1. Cài đặt và chỉnh sửa database (chạy bằng docker) trong file: application.yml
spring: datasource: url: jdbc:mysql://localhost:(thay đổi post sql đang chạy)/(tên database)?useSSL==false&serverTimezone=UTC:
- username: root
- password: root

jpa: hibernate: ddl-auto: update server: enable-csrf: false enable-cors: false servlet: session: cookie: http-only: true secure: true max-age: 1800

2. Chạy API bằng file: ApiApplicaiton.java
