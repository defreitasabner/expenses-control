cd ../expenses_api/
export JAVA_HOME="/lib/jvm/jdk-17"
./mvnw package -Dmaven.test.skip
cd ..
docker compose up