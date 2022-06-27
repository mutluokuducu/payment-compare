# Transaction  Compare

## Running it locally
## Clean build whole project from command line
```
./gradlew clean build
```
### API Swagger url
```
http://localhost:8080/swagger-ui.html
```
```
http://localhost:8080
```

## Web URL Google Cloud [Kubernetes] 
(This url will stop on tuesday 11 pm Uk time)

http://35.224.215.111/

http://35.224.215.111/swagger-ui.html

## Project Structure
In this project, comparisons were made according to the following columns. These:
* TransactionDate
* TransactionAmount
* TransactionId
* WalletReference

When I first started this project, I decided to do it with Spring Batch and wrote the necessary configurations.
But I cancelled due to a file mismatch and used Apache commons-csv instead. The party is on the sidelines. 
But I didn't use it. I did not delete some of the dependencies I used, 
some of them are H2, batch, Repository, JPA etc.

I delivered Backend, frontend and Cloud(CI/CD)
* Docker image
* Google Kubernetes Engine (GKE)
### Testing
JUnit test. To test the service the following command is used from the root project.
Component test : I started but I did not complete

## Running unit tests from command line
```
./sbuild.sh
```
## Running component tests from command line
```
./cbuild.sh
```
## Running all tests from command line
```
./buildAll.sh
```


