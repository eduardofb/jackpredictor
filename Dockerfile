FROM steveny/predictionio
MAINTAINER Carlos Barreto

COPY DecisionTree /DecisionTree/
COPY data /data/
COPY deploy /deploy

RUN apt-get update && \
    apt-get install -y locales && \
    locale-gen en_US en_US.UTF-8

# Runit
RUN apt-get install -y --no-install-recommends runit
CMD export > /etc/envvars && /usr/sbin/runsvdir-start


# Utilities
RUN apt-get install -y --no-install-recommends vim less net-tools inetutils-ping wget curl git telnet nmap socat dnsutils netcat tree htop unzip sudo software-properties-common jq psmisc iproute python ssh rsync python-setuptools

#Python SDK
RUN apt-get install -y python-pip && \
    pip install pytz && \
    pip install predictionio

#add jack dependencies
CMD ["/deploy", "-D", "FOREGROUND"]

EXPOSE 8000
