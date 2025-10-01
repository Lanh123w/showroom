# Sử dụng image Maven để build ứng dụng
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Tạo thư mục làm việc
WORKDIR /build

# Copy toàn bộ mã nguồn vào container
COPY . .

# Build ứng dụng và tạo file jar
RUN mvn clean package -DskipTests

# Giai đoạn chạy ứng dụng
FROM eclipse-temurin:17-jdk-alpine

# Tạo thư mục ứng dụng
WORKDIR /app

# Copy file jar từ giai đoạn build
COPY --from=builder /build/target/*.jar app.jar

# Mở cổng 8080
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
