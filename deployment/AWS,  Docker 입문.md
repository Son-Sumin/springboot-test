### AWS,  Docker 입문 시 소개 내용
<br>

<details>
    <summary> AWS (클릭:wink:) </summary> 
<br>

- 캡처 : window + shift + s   
<br>

- aws azure gcp 비교   
  한 브라우저에 동시 접속 불가   
  창 2개 띄우기(다른 브라우저, 시크릿모드 등)   
<br>

- AWS Service   
1. billing   
   - 결제 - 청구서 - 틈틈히 확인(18h~24h 후 최신화)   
   - 기본설정 - 청구서 설정 - 프리 티어 사용량 알림 받기, 결제 알림 받기 check   
   - Cost Management - Budgets - 예산 생성 후 사용   

2. IAM   
   - 실무에서는 root에서 한 사용자를 만드는데 권한(AdministratorAccess) 주고 'admin'(예시)으로서 활동시키다가 문제 생기면 그 사용자를 삭제   
   - 'admin' 계정은 billing 권한만 없음, root 계정에 준하는 권한 소유    
   - MFA 할당 (root계정은 설정 비추, 폰 교체/분실 시 복구 어려움)   
   - 사용자 - 권한 주기 - 직접 연결(AmazonEC2FullAccess 이면 다 가능)   
     회사에서는 그룹 생성 후 그 그룹에 연결   

3. EC2
   - check the region
   - 리소스 - '보안그룹 = 0' 이면 안 됨   
     (기본으로 부여되는 default는 절대 삭제하면 안됨)
   - 인스턴스 : 클라우드의 가상서버   
   - 이미지 : 운영체계(OS)   
   - 인스턴스 시작   
     - 이름 설정 : 아무거나   
     - OS이미지 : Ubuntu, 프리티어   
     - 인스턴스 유형 : t2.micro   
     - 키 페어(로그인) : 생성하기 (pem 파일 위치 확인 중요)   

   - 인스턴스 사용법 3가지   
     (해당 인스턴스 접속 후 '연결' 클릭)   
    1. web socket 방식의 접속   
       - 상단) EC2 인스턴스 연결   
       - 연결   
       - cmd
       ```
         $ sudo apt -y update   
       ```

    2. SSH 클라이언트 사용
       - 상단) SSH 클라이언트
       - pem있는 파일 위치에서 ssh 복사하여 실행
       - cmd   
       ```   
         $ sudo apt -y update   
         $ sudo apt -y upgrade   
         $ sudo apt -y install nginx   
         $ sudo systemctl status nginx   
       ```   
       <br>
       
       - 처음 만들 때 보안그룹 생성 시 HTTP 선택 가능, 미선택 시 아래 실시)   
       - ssh(secure shell)를 사용해서 접속 후 scp(secure copy) 설치하여 사용   
       - 인스턴스 → 퍼블릭 IPv4 주소 접근 시 **방화벽**으로 인해 접근 불가
       - 중간 보안 탭 → 보안그룹 접속 → 인바운드 규칙 편집 → HTTP(유형), Anywhere-IPv4 → 규칙 저장
       <br>
       
       - 인스턴스 이미지(OS) 생성
         - 해당 인스턴스 종료(삭제)
         - 더 큰 용량의 인스턴스에 이전 만들었던 이미지를 설치 (기존 이미지 안에 있는 nginx 남아있으므로 타작업 필요없음
         - 해당 인스턴스 우클릭 → 이미지 및 켐플릿 → 이미지 생성

    3. mobaxterm 다운로드   
       - session → ssh → Advanced SSH settings   
         - hostname : 인스턴스 퍼블릭 ip   
         - Specifty username : ubuntu    
         - 해당 인스턴스 연결 - EC2 인스턴스 연결 - 사용자 이름 - 입력
         - use private key : pem 파일 찾기   
<br>

4. S3 (Simple Storage Service) : 웹 하드디스크

5. RDS
</details>
<br>

* * *
<br>

<details>
    <summary> AWS Docker - mysql 설치 (클릭:wink:) </summary> 
<br>

- MSA(Micro Service Architecture)  ←→   Monolithic   
- MSA를 구현하기 위한 docker / container   
<br>
    
- 중요 링크 (docker.com 으로 접속하면 아래 두 링크 나옴)   
  - hub.docker.com   
  - docs.docker.com   

- docs.docker.com 접속   
  - home → Download and install → 펭귄 (Install Docker Desktop on Linux)   

- 우리가 필요한 것은 Docker Desktop이 아니라 **Docker Engine** 설치 필요
  (좌측 배너 참고)   
- Docker Engine 설치하기
  - Docker Engine → Install → Ubuntu → OS requirement에서 Ubuntu 종류 확인   
  - terminal
    ``` 
    [Ubuntu 종류 확인]
    $ cd /etc
    $ sudo cat os-release
    또는
    $ sudo uname -a
    또는
    $ sudo uname -r

    [참고 - CentOS]
    $ cat /etc/**-release
    ```
  - Installation methods → Set up the repository, Install Docker Engine   
    - OS requirement에서 Ubuntu 종류 확인 후
    - 방법1 : [스크립트 실행](https://github.com/Son-Sumin/docker_minikube_kubectl)   
    - 방법2 : 아래 내용 차례로 설치   
    - terminal   
    ```
    [Set up the repository]
    $ sudo apt-get update
    $ sudo apt-get install \
    ca-certificates \
    curl \
    gnupg
    
    $ sudo mkdir -m 0755 -p /etc/apt/keyrings
    $ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    
    $ echo \
    "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
    "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
    sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    
    [Install Docker Engine]
    $ sudo apt-get update
    
    $ sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    
    $ sudo docker run hello-world
    ```

  여기까지 docker engine 설치 완료   
  <br>
 
    - terminal
    ```
    [도커 / 컨테이너 명령어 확인]
    $ sudo docker --help
    또는 $ sudo docker -h

    [위에서 나온 명령어 세부사항 보기 ex. run]
    $ sudo docker run --help

    [docker 상태 확인]
    $ sudo systemctl status docker
    $ sudo systemctl stop docker
    $ sudo systemctl start docker

    [컨테이너에 ubuntu pull하여 사용]
    $ sudo docker  run -it ubuntu bash

    [실행 중인 목록, 컨테이너id 확인]
    $ sudo docker ps

    [모든 process 확인 (끝난 것 포함)]
    (실행한 컨테이너 history)
    $ sudo docker ps -a

    $ sudo docker ps -aq

    [pull한 images 확인]
    $ sudo docker images

    [컨테이너 지우기
    (id 2자리씩만 입력해도 됨)
    $ sudo docker rm 컨테이너id
    (전부 지우기)
    $ sudo docker rm `sudo docker ps -aq`

    [이미지 지우기]
    (id 2자리씩만 입력해도 됨)
    $ sudo docker rmi 이미지id

    [컨테이너 강제 죽이기]
    $ sudo docker rm -f 컨테이너id

    [이미지 강제 죽이기]
    $ sudo docker rmi -f 이미지id

    컨테이너 안에서 밖으로 나갈 때 방법 2가지
    1. ctrl 누른 상태에서 P  Q    (컨테이너 실행 중 밖으로 나옴, 후에 다시 들어가기 위해)
    2. $ exit    (컨테이너 종료시키면서 나옴)

    [컨테이너 밖에서 안으로 들어갈 때 방법]
    $ sudo dockerc -it 컨테이너id bash


    sudo docker run hello-world
    이거 실행할 때마다 이미지 생성됨
    컨테이너는 1번만 생성됨

    * 컨테이너?
    독립된 실행환경 (+ 실행 프로그램)
    컨테이너는 가볍다(lightweight), == 꼭 필요한 files만 존재
    이미지가 있어야 컨데이너 생성 가능
    ubuntu는 실행 코드라고 생각

    아래 전체를 담고 있는 EC2 존재, 그 안에
    etc 하드웨어 위에 ubuntu 라는 os 존재, 그 위에 docker engine 설치(nginx 여러 개 설치 가능)

    [nginx를 컨테이터로 띄우고, 외부(PC)에서 접속 가능]
    $ sudo docker run -d -p 80:80 nginx
    $ sudo docker exec -it 컨테이너id bash



    sudo docker exec nginx컨테이너id bash
    nginx 접속 후
    cd /usr/share/nginx/html
    cat >> welcome.html
    ctrl d 하고 나오기

    필요한 file을 컨테이터 안으로 카피, 컨테이너로부터 밖으로 카피 가능

    [컨테이너 안으로 copy]
    (ubuntu에서 파일 생성 후, 파일 생성 시 root 에서 하지 않도록 주의)
    $ sudo docker cp ./welcome2.html 컨테이너id:/usr/share/nginx/html/.



    - 또 다른 nginx 실행, 즉 web server n개 실행하고 싶을 때
    ubuntu (도커 호스트 : 도커 엔진이 설치된 서버)
    도커 엔진은 도커 명령어 (ex. docker run, docker cp, docker exec)
    도커 명령어를 실행하는 패키지(시스템)

    (도커호스트의 호스트번호 : 생성된 컨테이너에 기본적으로 부여된 포트번호)
    $ sudo docker run -d -p 8000:80 nginx
    $ sudo docker run -d -p 9000:80 nginx



    - mysql 설치
    docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag

    sudo docker run -e MYSQL_ROOT_PASSWORD=1234 -d mysql

    --name some-mysql : 해도 되고 안해도됨 (이름 설정)

    $ sudo docker run -e MYSQL_ROOT_PASSWORD=1234 -p 3306:3306 -d mysql
    $ sudo docker ps
    (mysql 돌고 있는것 확인)
    (root로 접근하는 것 가능함)

    - mysql workbench
    connection 생성
     - hostname : 퍼블릭 ip

    $ sudo docker exec -it <mysql_container_ID> mysql -u root -p
    $ password 입력
    (mysql 진입)

    (1) 외부에서 mysql 접근하려면 root로는 접근이 안되니
    사용자 만드는 단계가 필요함
    (2) /etc/mysql/mysql.conf

    (1)
    mysql> create user 'host' identified by 'host1234';
    mysql> grant all privileges on *.* to 'host';
    mysql> flush privileges;
    mysql> quit;

    (2)
    mysql 실행 중인 인스턴스 보안그룹에서 3306 포트 열어두기
    $ sudo docker exec -it <container ID> bash

    [mysql 삭제]
    $ sudo docker ps
    $ sudo docker rm <mysql container ID>
    ```


    ubuntu, nginx, mysql, hello-world 실행 명령어 암기
    
</details>
<br><br>
