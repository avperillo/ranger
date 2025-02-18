```shell
mvn clean package install -P ranger-admin -pl org.apache.ranger:security-admin-web -pl org.apache.ranger:ranger-distro -DskipTests -DskipRat -Denunciate.skip=true -Dcheckstyle.skip=true -Dspotbugs.skip=true
```

### rebuild ranger
```shell
cd ~/code/ranger/
./ranger_dev.sh rebuild
```

### down up
```shell
cd ~/code/ranger/
./ranger_dev.sh up
```

### down ranger
```shell
cd ~/code/ranger/
./ranger_dev.sh down
```

### copy all to distro
```shell
cd ~/code/ranger/
cp -v target/ranger-* dev-support/ranger-docker/dist/
# cp -v target/ranger-*-admin.tar.gz dev-support/ranger-docker/dist/
cp -v target/version dev-support/ranger-docker/dist/
```

### build base image
```shell
cd ~/code/ranger/dev-support/ranger-docker/
docker-compose -f docker-compose.ranger-base.yml build --no-cache
```

### ranger UP
```shell
cd ~/code/ranger/dev-support/ranger-docker/
docker-compose  -f docker-compose.ranger-base.yml \
                -f docker-compose.ranger.yml \
                -f docker-compose.ranger-postgres-mounted.yml \
                -f docker-compose.ranger-usersync.yml \
                up -d
```

### ranger DOWN
```shell
cd ~/code/ranger/dev-support/ranger-docker/
sudo docker-compose -f docker-compose.ranger-base.yml -f docker-compose.ranger.yml -f docker-compose.ranger-postgres-mounted.yml -f docker-compose.ranger-usersync.yml down --remove-orphans
```