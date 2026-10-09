  #!/bin/bash
  set -e

  CONTAINER_NAME="kassensystem-db"

  docker rm -f "$CONTAINER_NAME" >/dev/null 2>&1 || true

  docker run \
      --detach \
      --env MYSQL_ALLOW_EMPTY_PASSWORD=yes \
      --env MYSQL_DATABASE=kassensystem \
      --publish 3306:3306 \
      --name "$CONTAINER_NAME" \
      --volume "$(pwd)/database:/docker-entrypoint-initdb.d:ro" \
      mysql:8.4

  echo -n "Waiting for MySQL startup"

  until echo "SELECT 1" | mysql \
      --user=root \
      --host=127.0.0.1 \
      --port=3306 \
      kassensystem >/dev/null 2>&1
  do
      echo -n "."
      sleep 1
  done

  echo " done!"

