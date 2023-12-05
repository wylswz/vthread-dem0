# vthread-demo


This is a demo for Java VirtualThread (in Spring Boot Application). It's mainly used for 
- exploring new VirtualThread APIs and their usages in SpringBoot applications
- test out whether a third party library supports virtual thread (database drivers, rpc clients) and diagnose 
  the thread pinning

# Diagnosis

```shell
-Djdk.tracePinnedThreads=short -Xmx128m -Xms128m -XX:NativeMemoryTracking=detail
```

where 
- jdk.tracePinnedThreads=short let jvm prints the stacktrace up to problematic code which causes thread to pin
- -XX:NativeMemoryTracking=detail is used to trace native memory usage (of threads)

# Docker containers

```shell
docker run -d --name mariadb -p3306:3306  -e MARIADB_ROOT_PASSWORD=admin mariadb
docker run --name some-redis -p6379:6379 -d redis
```

# Stress tests

```shell

bombardier http://127.0.0.1:8080/api/v1/kv/redis/k1

bombardier http://127.0.0.1:8080/api/v1/kv/mysql/k1

bombardier http://127.0.0.1:8080/api/v1/standard/sleep?durationMillis=100
```