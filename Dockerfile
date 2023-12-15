FROM busybox
COPY ./hello.sh busybox .
RUN echo "executing when bulding the docker-image:" && ./hello.sh
CMD echo "executing when running the docker-image in docker-container" && ./hello.sh
