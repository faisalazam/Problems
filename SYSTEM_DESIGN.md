# System Design / Architecture Interviews

## Back-of-the-envelope capacity estimation / planning - warehouse-scale computing

One part of system design interview is the back-of-the-envelope calculations. You have to estimate how many servers with
which specs you need. I tried to find any manuals with calculations, bit didn't find anything better than
https://servebolt.com/articles/calculate-how-many-simultaneous-website-visitors/
I would appreciate if you share your experience with such calculations.
Below I publish my humble effort to estimate Twitter (processing servers).

Preconditions and assumptions:

* 330 millions MAU;
* 5700 tweets per second in average;
* uniform distribution around the world;
* feed update requests are more frequent (say 10 times);
* we'd like to respond 95% of users for 100ms.

So we have about 60000 ( = 5700 * 11) QPS. According to the magic formula from the article 32 cores / 0.1s = 320 queries
per second for 1 server. So we need 60000 * 0.95 / 320 = 178 servers

We also know that Twitter uses Redis for improve response speed. Feed cache for each user is stored there. Say each user
has 500 tweets in this cache and each tweet is about 200 bytes. So we have 100Kb for each user. We don't know DAU value,
but assume 50% of monthly users use Twitter every day. So we have 165 millions users per day, and it's 16Tb RAM
(165000000 * 100Kb). So we have about 90Gb RAM for Redis if we have 178 servers. If we choose a server with 128Gb RAM
Redis takes about 70% of memory, it's on the verge of critical value. 256Gb RAM will be better.
In the sake of service durability we have to replicate Redis. Say we need 2 replicas, it's 178 * 3 = 534 servers.

In the end we estimate storage. 5700 tweets per second, each tweet is 200 bytes. It's about 4Gb per hour, or about 100Gb
per day. Let our servers have two weeks history in the fast access, after that we move data to archive servers
(we don't estimate them here). So we need 1.5Tb (of course in RAID).

Summary:
about 500 servers for real time processing, 32 cores, 256Gb RAM, 1.5Tb HDD RAID

P.S. We also need CDN for video, images and so on.

----------------------------------------------------------------

I have seen 2 approaches taken when calculating the back of the envelope calculations.

* The first approach as you have listed out in the bullet points starts with an overall picture of the system and
  calculations move to a single server and memory requirements. That is if there are 330 million active users and 5700
  tweets a second, how do I get to what specs will be required for a single server and thereby calculating how many
  servers/DBs are needed, etc.
  Under the interview pressure, I always felt this process to be a bit difficult when performing larger divisions.
  To quote your example "... So we need 60000 * 0.95 / 320 = 178 servers". There is no way I can do this calculation on
  the whiteboard in live interview without sweating myself.

* The second approach, which I always preferred is to start small and grow bigger with quite a few approximations.
  After all, the back of the envelope calculation is supposed to be a T-shift level "estimation". I also often start
  with
  a small number of variables preferably one.

For example, instead of managing 2 variables like "Number of active users" and "Number of tweets", I start at the server
level and ask myself a question, what factor affects my server the most? a number of active users coming to the server
or number of tweets coming to the server. If my server gets 10 tweets per second, does it matter in terms of memory and
threads requirements if 10 active users send 1 tweet/sec or 1 active user sends 10 tweets/sec? If it does not matter
then I, for now, I will ignore the number of active users and focus on how many tweets the server receives per second.
My 2 variables are down to 1.

I also make sure, I never talk on the specifics of the functionality and instead talk/focus on the raw/common server
requirements. That is instead of saying, the server receives 10 tweets per second, I will say the server receives 10
"requests" per second. Converting tweets to requests helps me memorize the same logic across twitter design where the
server receives tweets and facebook design where the server receives photo upload and comments requests. Everything is
incoming request no differentiation.

Ok so focusing on the twitter calculations, I would start something like this.

I will start saying, I will at minimum calculate servers, memory, and storage requirements

Starting small, I will say, assuming, the application gets 1000 requests per second, (1000 is an easy nice number for
any calculation and we can scale up or down easily depending on the requirement. The real twitter number would be much
higher)

* 1000 requests/sec
* 3600 seconds per hour, it will be 1000 * 4000 (approximating 3600 to nearest whole number 4000 as multiplication by 4K
  is much easier orally than 3.6K) => we get 4 million requests/hr
* 4M requests/hr translates to 4M * 30 hours (instead of 24 hours in a day as its much easier to multiply by 30 than 24)
  => 120 million requests/day
* 120 million requests/day translates to 120M * 400 days (instead of 365) = 50 billion requests/year (instead of 48B)
* Assuming the capacity planning estimates are for 5 years, we get 50B * 5 = 250 Billion request data is what we may end
  up storing in our system.

Now to calculate the number of servers, From the experience of running applications in production, I know say a
tomcat/jetty server running Spring boot application at a minimum will have 100 worker thread (200 default) to handle
HTTP requests

* The server will handle tweets, photos, video uploads
* handling 1000 requests with 100 threads I would use 10 + 20% more = 12 servers.
* If 1000 requests change to 10000 requests, 12 servers would more or less convert to 120 servers.

For server memory requirements of the server:
Now for server memory requirements, the capacity required to handle requests with video and images would be much higher
compared to tweet,

* Assuming photos are 1MB in upload size (Usually a UI side compression library will reduce a photo image size to be
  around
  500KB, but 1 MB is easy for calculation) and videos to be 50 MB in size
* To handle 100 requests/sec for video uploads, 100 * 50MB = 5GB of memory for each commodity server.

For Storage requirement, assuming we need to store data for 5 years

* As previously calculated, 250 Billion request data to store for 5 years, assuming 10% to be for videos (50MB avg),
  20% for photos (1MB avg) and 70% for tweets (200KB avg) we need
  -- Note, usual conversions are (1000 translates to KB storage, 1 million translates to 1 MB of storage, 1 billion
  translates to 1 GB of storage)
* 10% Video: 250 Billion request data (that is 250 GB) * 10% => 25 GB * 50MB ~~ 25000MB * 50MB ~~ 1250000 MB => 1250
  GB => 1.2TB
* 20% Photos: 250 Billion request data * 20 % => 50 GB * 1MB => 50000 MB * 1MB => 50000 MB => 50GB
* 70% Tweets: 250 Billion request data * 70% ~~ 200 GB * 200 bytes => 200000MB * 0.002MB => 400MB
* Total (1.2TB + 50 GB + 400 MB) ~~ 1.2TB (in reality this capacity will be much higher as video/photo storage size
  requirements will be much higher but I hope reader gets the point)

Summary

* Start with a single variable and translate specific design requirement into raw server requirements like requests/sec
  (instead of tweets/sec or photos/server)
* Start from a single server requirement instead of trying to divide total tweets or total storage by servers.
* Remember to get all the calculations done in 5 mins. Unless the interviewer wants to focus on the specifics
  calculations.
  Remember these contents are high-level estimates.

----------------------------------------------------------------

Some ideas to recall from memory the "magic formula"

* Start with the simple mental image of exact stability:
  capacity meets demand => `capacity = demand`
* What is capacity of 1 cpu
  `capacityCpu = 1/responseTimeCpu` the longer it takes for the cpu to respond, the less capacity
* Assuming independence, what is the capacity of n cpus
  `capacityAllCpu = n*capacitySingleCpu = n*(1/responseTimeCpu)`
* What is the demand of 1 user
  `demandUser = 1/responseTimeUser` the longer it takes for the user to respond, the less demand
* Assuming independence, what is the demand of m users
  `demandAllUser = m*demandSingleUser = m*(1/responseTimeUser)`

The single idea to remember, when capacity exactly meets demand we have:
`n*(1/responseTimeCpu) = m*(1/responseTimeUser)`

Translating the calculations from the
link https://servebolt.com/articles/calculate-how-many-simultaneous-website-visitors/ to this model:
`responseTimeCpu = 0.323 sec` rendering time of a PHP page
`n = 32` number of cpu cores a typical server machine has
`responseTimeUser = 120 sec` gap between user clicks

Solving for m:
`32/0.323 = m/120` giving the result `m=11888.544891640866`

Reference:
https://leetcode.com/discuss/interview-question/system-design/357656/Experience-with-back-of-the-envelope-calculations

How {fast, reliable, available, ...} a service should be is fundamentally a product question
● “100% is the wrong reliability target for (nearly) everything”
○ cost of marginal improvements grows ~exponentially
● Can always make service better on some dimension, but involves tradeoffs with $, people, time, and other priorities

Latency Numbers Every Programmer Should Know

| Latency Comparison Numbers (~2012) |                 |            |        |                             |
|------------------------------------|-----------------|------------|--------|-----------------------------|
| L1 cache reference                 | 0.5 ns          |            |        |                             |
| Branch mis-predict                 | 5   ns          |            |        |                             |
| L2 cache reference                 | 7   ns          |            |        | 14x L1 cache                |
| Mutex lock/unlock                  | 25   ns         |            |        |                             |
| Main memory reference              | 100   ns        |            |        | 20x L2 cache, 200x L1 cache |
| Compress 1K bytes with Zippy       | 3,000   ns      | 3 us       |        |                             |
| Send 1K bytes over 1 Gbps network  | 10,000   ns     | 10 us      |        |                             |
| Read 4K randomly from SSD*         | 150,000   ns    | 150 us     |        | ~1GB/sec SSD                |
| Read 1 MB sequentially from memory | 250,000   ns    | 250 us     |        |                             |
| Round trip within same datacenter  | 500,000   ns    | 500 us     |        |                             |
| Read 1 MB sequentially from SSD*   | 1,000,000   ns  | 1,000 us   | 1 ms   | ~1GB/sec SSD, 4X memory     |
| Disk seek                          | 10,000,000   ns | 10,000 us  | 10 ms  | 20x datacenter round trip   |
| Read 1 MB sequentially from disk   | 20,000,000  ns  | 20,000 us  | 20 ms  | 80x memory, 20X SSD         |
| Send packet CA->Netherlands->CA    | 150,000,000  ns | 150,000 us | 150 ms |                             |

Notes
-----
1 ns = 10^-9 seconds
1 us = 10^-6 seconds = 1,000 ns
1 ms = 10^-3 seconds = 1,000 us = 1,000,000 ns

K*K is M.
G/M is K.
4K*7M=28G.

To work with larger numbers, round both of them towards a small multiple of a power of 2 or 10.

* 27*14 ~= 30*10 = 300.
* 6500/250 ~= 6400/256 ~= 100 * 2^6 / 2^8 ~= 100 / 2^2 = 25.

Credit
------
By Jeff Dean:               http://research.google.com/people/jeff/
Originally by Peter Norvig: http://norvig.com/21-days.html#answers

## Process vs Thread

Processes and threads are related to each other but are fundamentally different.

### Process

A process can be thought of as an instance of a program in execution. A process is an independent entity to which system
resources (e.g., CPU time and memory) are allocated. Each process is executed in a separate address space, and one
process cannot access the variables and data structures of another process. If a process wishes to access another
process' resources, inter-process communications have to be used. These include pipes, files, sockets, and other forms.

### Thread

A thread exists within a process and shares the process' resources (including its heap space). Multiple threads within
the same process will share the same heap space. This is very different from processes, which cannot directly access the
memory of another process. Each thread still has its own registers and its own stack, but other threads can read and
write the heap memory. A thread is a particular execution path of a process. When one thread modifies a process
resource, the change is immediately visible to sibling threads.

Consistent Hashing - https://www.toptal.com/big-data/consistent-hashing

Eventual Consistency
Strong Consistency
CAP Theorem:
In reality, we choose between CP and AP because CA is a monolith without partitions. For large-scale systems, designers
cannot abandon P and therefore have a difficult choice between C and A. In fact, the choice between consistency and
availability is really only made when partitioning or failure occurs. At other times, no tradeoffs are required.

PACELC Theorem

## When should you use NoSQL or SQL?

### When to pick a SQL database?

If you are writing a stock trading, banking or a Finance-based app or you need to store a lot of relationships, for
instance, when writing a social networking app like Facebook, then you should pick a relational database. Here’s why:

#### Transactions & Data Consistency

If you are writing software which has anything to do with money or numbers, that makes transactions, ACID, data
consistency super important to you. Relational DBs shine when it comes to transactions & data consistency. They comply
with the ACID rule, have been around for ages & are battle-tested.

#### Storing Relationships

If your data has a lot of relationships like which friends of yours live in a particular city? Which of your friend
already ate at the restaurant you plan to visit today? etc. There is nothing better than a relational database for
storing this kind of data.

Relational databases are built to store relationships. They have been tried & tested & are used by big guns in the
industry like Facebook as the main user-facing database.

#### Popular relational databases:

* MySQL
* Microsoft SQL Server
* PostgreSQL
* MariaDB

### When to pick a NoSQL database

Here are a few reasons why you’d want to pick a NoSQL database:

#### Handling A Large Number Of Read Write Operations

Look towards NoSQL databases when you need to scale fast. For example, when there are a large number of read-write
operations on your website and when dealing with a large amount of data, NoSQL databases fit best in these scenarios.
Since they have the ability to add nodes on the fly, they can handle more concurrent traffic and large amounts of data
with minimal latency.

#### Running data analytics

NoSQL databases also fit best for data analytics use cases, where we have to deal with an influx of massive amounts of
data.

#### Popular NoSQL databases:

* MongoDB
* Redis
* Cassandra
* HBASE

Document oriented stores/dbs - mongoDB - https://neetcode.io/courses/lessons/mongodb
Graph dbs - neo4j
Key-value stores/dbs - DynamoDB
Columnar dbs
Time series dbs
Wide column dbs
Object/Blob storage (for things like images/videos) - Amazon S3, but as such objects are usually static in nature, 
so they might be cached in CDN, so see if CDN can be used for S3 data, but it really depends on the problem you're solving
Replication
Redundancy
Master/master vs Master/slave vs active/passive setup
Caching strategies e.g. Write through cache
Sharding => Shard key? => High Cardinality, Low Frequency and good for query patterns

L1 vs L2 caching

TCP vs HTTP vs Websocket vs UDP
When to Use Webhooks, WebSocket, Pub/Sub, and Polling...
Long-Polling vs WebSockets vs Server-Sent Events

graphql-vs-rest

Load-balancing (at different levels like web server, app server, db etc. as it should not be single point of failure)
Scaling
Caching
CDN - one technique used for caching the static content

Single point of failure - could be any if no backup e.g. loadbalancer, power source, server, db, router, network
switches etc.
Mitigate failure with Redundancy
Traditional approach: If an outage of one component causes a failure, use 2 and hope they don’t fail together.

Monitoring
Now that we get traffic to the right place, how do we know what the servers are doing?
We need monitoring to figure out what the frontends are doing. How loaded are they?
When are they getting overloaded?
How many requests are they handling?
Are they providing the functionality we expect?
No, we don’t actually stare at screens all day. That’s what automated alerts are for.

Application Server vs Web Server

CI/CD
Zero Downtime Deployments (ZDD)

JVM - Java memory management
Heap
Stack
Permgen
Garbage collection etc.

## How to decide on the number of tiers your app should have

* You should choose a single tier architecture when you do not want any network latency
* Choose a two tier application when you need to minimize network latency and you need more control of data within your
  application
* You should choose a three tier architecture when you need control over the code/business logic of your application &
  want it to be secure, and you need control over data in your application.
* You should choose a N tier architecture when you need your application to scale and handle large amounts of data.

## Horizontal or vertical scaling… Which is right for my app?

Build to deploy it on the cloud & always have horizontal scalability in mind right from the start. Here is a good
website for learning more about scalability. http://highscalability.com/


The Secret To 10 Million Concurrent Connections -The Kernel Is The Problem, Not The Solution
http://highscalability.com/blog/2013/5/13/the-secret-to-10-million-concurrent-connections-the-kernel-i.html?currentPage=2