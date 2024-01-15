FROM maven:3.8.5-openjdk-17

WORKDIR ./
COPY . .
COPY ./gen-env.sh ./gen-env.sh
RUN chmod +x ./gen-env.sh && mvn clean install

CMD chmod +x ./gen-env.sh && mvn spring-boot:run