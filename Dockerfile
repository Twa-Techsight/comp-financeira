FROM edsonsantos2023/ubuntu23.10:1.2

ENV LANG en_US.UTF-8

ARG JAR_FILE_PATH
ENV APP_VERSION=${JAR_FILE_PATH}

ARG JAR_FILE=${APP_VERSION}
RUN echo "version=${VERSION}" > /opt/webapp/version.txt

COPY ${JAR_FILE} /opt/webapp/app.jar
RUN bash -c 'touch /opt/webapp/app.jar'

ENTRYPOINT ["java", "-Xms64m", "-Xmx2048m", "-Djava.security.egd=file:/dev/./urandom","-jar","/opt/webapp/app.jar"]