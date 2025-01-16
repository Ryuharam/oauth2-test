#!/bin/bash

# Variables 여기만 신경쓰면 됩니다
JAR_NAME="oauth2-back-0.0.1-SNAPSHOT.jar" # 빌드 했을 때 나오는 jar 이름 복붙하세요 (ex. spring-boot-0.0.1-SNAPSHOT.jar)
JAR_FILE="build/libs/$JAR_NAME"
EC2_USER="ubuntu" # EC2 username
EC2_HOST="13.124.41.92" # EC2 public IP 주소 넣으세요
KEY_PATH="$HOME/.ssh/ssafy-key.pem" # Path to your EC2 key pair
DEST_PATH="~/spring_boot/" # jar 파일 넣을 폴더 경로. 끝에 / 붙여야 함

# Build the app using Gradle
echo "Building the application with Gradle..."
./gradlew clean build || { echo "Gradle build failed!"; exit 1; }

# Verify the JAR file exists
if [ ! -f "$JAR_FILE" ]; then
  echo "JAR file not found at $JAR_FILE"
  exit 1
fi

# Create dest path on EC2 instance if not exists
echo "Creating destination path on EC2..."
ssh -i "$KEY_PATH" "$EC2_USER@$EC2_HOST" "mkdir -p $DEST_PATH" || { echo "SSH failed!"; exit 1; }

# Upload the JAR file to the EC2 instance
echo "Uploading the artifact to EC2..."
scp -i "$KEY_PATH" "$JAR_FILE" "$EC2_USER@$EC2_HOST:$DEST_PATH" || { echo "SCP failed!"; exit 1; }

echo "done!"