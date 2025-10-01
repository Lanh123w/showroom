# Sử dụng image Java chính thức
FROM eclipse-temurin:17-jdk-alpine

# Tạo thư mục ứng dụng
WORKDIR /app

# Copy file jar vào container
COPY target/*.jar app.jar

# Cấu hình cổng
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
