FROM ubuntu:latest
LABEL authors="panarin"

ENTRYPOINT ["top", "-b"]