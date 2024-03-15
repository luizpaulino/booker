#!/bin/bash
docker-compose stop

echo "Building Gradle project..."
./gradlew build

# Build Docker image
echo "Building Docker image..."
docker build -t booker .

# Start Docker Compose services
echo "Starting Docker Compose services..."
docker-compose up -d
