FROM busybox
COPY ./env.txt .
COPY ./hello.sh .

#ENV __IMAGE_DATE=`date -R`
#ENV __ENV_TXT_FILE=`<./env.txt)`
#ENV __two_plus_two__=`2+2`

RUN echo "environment when building:" && env | sort \
 && echo "executing when bulding the docker-image:" && ./hello.sh

CMD echo "environment when executing:" && env | sort \
 $$ echo "-----------------" \
 && echo "environment before executing:" && cat ./env.txt \
 $$ echo "-----------------" \
 && echo "executing when running the docker-image in docker-container" && ./hello.sh \
 $$ echo "-----------------"
