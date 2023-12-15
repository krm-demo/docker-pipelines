FROM busybox
COPY ./hello.sh .
RUN echo "environment when building:" && env | sort \
 && echo "executing when bulding the docker-image:" && ./hello.sh
CMD echo "environment when executing:" && env | sort \
 && echo "executing when running the docker-image in docker-container" && ./hello.sh
