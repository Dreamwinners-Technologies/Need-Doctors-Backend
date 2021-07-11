
git pull
mvn clean package
fuser -k 8100/tcp
nohup java -jar target/need-doctors-0.0.1-SNAPSHOT.jar >aglog.log 2>&1 &