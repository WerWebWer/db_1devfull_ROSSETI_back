FROM openjdk:11
COPY . ./digitalassistant
RUN apt-get update && apt-get install -y maven
RUN sudo add-apt-repository ppa:jonathonf/python-3.6
RUN sudo apt-get install python3.6
RUN python -m pip install nltk
RUN python ./src/main/java/rosseti/devful/digitalassistant/python/settings.py
RUN mvn -f /digitalassistant/pom.xml -Dmaven.test.skip=true clean install
EXPOSE 8080