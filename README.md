
### Eureka server** :

To check instances:
```
http://localhost:8761
```


# Ports:

8080 - api gateway

8761 - discovery/eureka server

5432 - postgres (wallet)

27017 -  mongo (user, portfolio, wallet tx)

random - the rest

# Docker:

***DBs***:
```
docker-compose -f docker-compose-dbs.yml up
```

***Endpoints***:

```
docker-compose up
```
