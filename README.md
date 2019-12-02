# WebCrawlerRestified
Exposing web crawler functionality via REST APIs

# How to run the project

## Software prerequisites

The following softwares are required to run this project:

  - [Mongodb](https://docs.mongodb.com/manual/installation/)
  - [Rabbitmq](https://www.rabbitmq.com/download.html)
  - Latest version of [maven](https://maven.apache.org/install.html)
  - [Robomongo](https://robomongo.org/download) (a GUI client for mongodb to easily see mongodb data)
  - [Postman](https://www.getpostman.com/downloads/) (to test the application)

## Steps to follow

1) Start an instance of Mongo Daemon from the command line. The command is
```sh
$ mongod
```

2) Start Robomongo mongodb client and connect to the local mongo server instance.

3) Start RabbitMQ server with default port. See instructions [here](https://www.rabbitmq.com/download.html). Verify that it is running by going to the location [http://localhost:15672](http://localhost:15672/). A login window appears, login here using guest:guest as credentials. This logins to the RabbitMQ management window. Here, go to the tab "Queues". Any available queue and its message count is visible here. If the application is being run for the first time, no queue would be shown until the application is started in the final steps.

4) Checkout the project to local using command
```sh
$ git clone https://github.com/SahinSarkar/WebCrawlerRestified.git
```

5) Go to the directory where the project has been checked out and build the project using maven like so
```sh
$ cd WebCrawlerRestified
$ mvnw clean install
```

6) Now we will run two instances of the built jar, one without any arguments like
```sh
$ java -jar target/WebCrawlerRestified-0.0.1-SNAPSHOT.jar
```

7) We start another instance of the same jar but on a different port and the "receiver" profile configured.
```sh
$ java -jar target/WebCrawlerRestified-0.0.1-SNAPSHOT.jar --spring.profiles.active=receiver --server.port=8082
```

8) The application is up and running.

## Test the application

This application exposes 3 REST APIs which can be tested using postman. All of the APIs are hosted on the first application instance that we run previously having default port 8080. So the prefix for the APIs would look like http://<hostname>:8080. Of course, when running locally, the <hostname> would be "localhost". The APIs are shown below.

| API | Path | HTTP Method | Parameters | Result | Side-effect |
| ------ | ------ | ------ | ------ | ------ | ------ |
| Crawl And Get Token | /crawlAndGetToken | GET | url (String), depth (int) | Returns a **tokenId** value which is needed to call the other APIs. | Starts a background process of crawling from the **url** parameter and crawls upto a depth of **depth** and persists the information to mongoDB collection "singleCrawlRequestData". This API puts a request on the message queue to be worked on later but returns immediately with the **tokenId** and does not block.|
| Crawl Result | /crawlResult | GET | tokenId (String) | Gives the **results of the crawl request** which returned the **tokenId** when API /crawl was called. Its in JSON format. An example of the response is given after this section. | |
| Request Status | /requestStatus | GET | tokenId (String) | Gives the **status of the request** which returned the **tokenId** during the call to API /crawl. It can be one of "Submitted", "InProcess", "Processed" or "Failed". | |

## Examples of calling the above mentioned API:
### 1) Crawl And Get Token

```sh 
GET http://localhost:8080/crawlAndGetToken?url=https://dzone.com/articles/asynchronous-task-executor-using-redis-and-spring&depth=1
```

##### Response:

1575268560621

#### 2) Crawl Result
The **tokenId** parameter is the same as the one coming in the response of /crawlAndGetToken previously.

```sh 
GET http://localhost:8080/crawlResult?tokenId=1575268560621
```

##### Response:

This is a sample response, it doesn't show the complete actual response. This is just to show the format of data that gets returned.
	
	{
		"tokenId": "1575268560621",
		"totalLinks": "3",
		"totalImages": "16",
		"details": [
			{
				"pageTitle": "Asynchronous Task Execution Using Redis and Spring Boot - DZone Java",
				"pageLink": "https://dzone.com/articles/asynchronous-task-executor-using-redis-and-spring",
				"imageCount": "6"
			},
			{
				"pageTitle": "Analytics Tools & Solutions for Your Business - Google Analytics",
				"pageLink": "https://marketingplatform.google.com/about/analytics/",
				"imageCount": "7"
			},
			{
				"pageTitle": "Redis",
				"pageLink": "https://redis.io/",
				"imageCount": "3"
			}
		]
	}

### 3) Request Status
Here also, the **tokenId** supplied is the same as the response of /crawlAndGetToken in point 1.

```sh
GET http://localhost:8080/requestStatus?tokenId=1575268560621
```

##### Response:

INPROCESS