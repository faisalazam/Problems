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

### Data Volumes

|       |                                    |       |             |                                    |
|-------|------------------------------------|-------|-------------|------------------------------------|
| Kilo  | 1,000;                             | 10^3  | Thousand    | a Kilobyte is one thousand bytes.  |
| Mega  | 1,000,000;                         | 10^6  | Million     | a Megabyte is a million bytes.     |
| Giga  | 1,000,000,000;                     | 10^9  | Billion     | a Gigabyte is a billion bytes.     |
| Tera  | 1,000,000,000,000;                 | 10^12 | Trillion    | a Terabyte is a trillion bytes.    |
| Peta  | 1,000,000,000,000,000;             | 10^15 | Quadrillion | a Petabyte is 1,000 Terabytes.     |
| Exa   | 1,000,000,000,000,000,000;         | 10^18 | Quintillion | an Exabyte is 1,000 Petabytes.     |
| Zetta | 1,000,000,000,000,000,000,000;     | 10^21 | Sextillion  | a Zetta-byte is 1,000 Exabytes.    |
| Yotta | 1,000,000,000,000,000,000,000,000; | 10^24 | Septillion  | a Yotta-byte is 1,000 Zetta bytes. |

#### Examples of Data Volumes

| Unit	            | Value	            | Example                                                                    |
|------------------|-------------------|----------------------------------------------------------------------------|
| Kilobytes (KB)   | 1,000 bytes       | a paragraph of a text document                                             |
| Megabytes (MB)   | 1,000 Kilobytes   | a small novel                                                              |
| Gigabytes (GB)   | 1,000 Megabytes   | Beethoven’s 5th Symphony                                                   |
| Terabytes (TB)   | 1,000 Gigabytes   | all the X-rays in a large hospital                                         |
| Petabytes (PB)   | 1,000 Terabytes   | half the contents of all US academic research libraries                    |
| Exabytes (EB)    | 1,000 Petabytes   | about one fifth of the words people have ever spoken                       |
| Zetta-bytes (ZB) | 1,000 Exabytes    | as much information as there are grains of sand on all the world’s beaches |
| Yotta-bytes (YB) | 1,000 Zetta-bytes | as much information as there are atoms in 7,000 human bodies               |

### Availability Formula

99.9999% availability means the system can be unavailable only for 31.5 seconds in a whole year:

```
number of seconds per day = 60 * 60 * 24 = 86400
number of seconds per year = number of seconds per day * 365 = 31,536,000

(number of seconds per year) - ((number of seconds per year) * 99.9999%) = 31,536,000 - (31,536,000 * 99.9999%) = 31.5 seconds
(number of seconds per year) - ((number of seconds per year) * 99.999%)  = 31,536,000 - (31,536,000 * 99.999%)  = 315.4 seconds  = 5.3 minutes
(number of seconds per year) - ((number of seconds per year) * 99.99%)   = 31,536,000 - (31,536,000 * 99.99%)   = 3153.6 seconds = 52.56 minutes = 0.876 hours
(number of seconds per year) - ((number of seconds per year) * 99.9%)    = 31,536,000 - (31,536,000 * 99.9%)    = 31536 seconds  = 525.6 minutes = 8.76 hours
(number of seconds per year) - ((number of seconds per year) * 99%)      = 31,536,000 - (31,536,000 * 99%)      = 315360 seconds = 5256 minutes = 87.6 hours
```

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

CAP Theorem:
In reality, we choose between CP and AP because CA is a monolith without partitions. For large-scale systems, designers
cannot abandon P and therefore have a difficult choice between C and A. In fact, the choice between consistency and
availability is really only made when partitioning or failure occurs. At other times, no tradeoffs are required.

PACELC Theorem

### Eventual Consistency vs Strong Consistency

Strong consistency is a property in distributed systems that ensures that all nodes in the system see the same data at
the same time, regardless of which node they are accessing. In other words, when a write operation is performed, all
subsequent read operations from any node will return the most recent write value. This guarantees that there is a linear
ordering of operations, and the system behaves as if it were a single, coherent entity, and there is no possibility of
one client seeing an outdated value while another client observes the updated value.

Whereas in case of Eventual Consistency, over time, the system converges towards consistency, but during the transient
period, users accessing different data centers may observe different versions of the data. This is the characteristic
behavior of eventual consistency. While the system guarantees that updates will eventually be propagated and all nodes
will reach a consistent state, there can be temporary discrepancies between nodes at any given moment.

Choosing between strong and eventual consistency depends on the specific needs of the application and its users. Some
systems may adopt a hybrid approach, selectively applying strong consistency to certain critical data and eventual
consistency to less critical or non-critical data, striking a balance between data accuracy, performance, and
availability. The decision requires careful consideration of the tradeoffs to meet the desired requirements and
constraints of the distributed system.

Strong consistency guarantees that every read operation returns the latest write operation’s result, regardless of the
node on which the read operation is executed. This is typically achieved using consensus algorithms like Paxos or Raft:

Strong consistency aims to provide a higher level of consistency. Unlike eventual consistency, strong consistency
ensures that all nodes see the same data without any temporary inconsistencies.

This approach benefits systems that require strict consistency, such as financial systems or real-time data processing
applications. Some examples of distributed systems that employ strong consistency include Google’s Spanner.

However, strong consistency can come at the cost of performance and scalability since it requires more coordination and
communication between nodes to maintain consistency.

Thing about the trade off for data accuracy, performance, and availability while choosing between strong vs eventual
consistency.

https://medium.com/@abhirup.acharya009/strong-consistency-vs-eventual-consistency-19ce6f87c112

## What is database caching?

Database caching is a buffering technique that stores frequently-queried data in temporary memory. A cache is a
high-speed data storage layer which stores a subset of data that is often read requested. This transient storage layer
results in future requests for this data to be served up faster than is possible by accessing the primary database.

A database caching strategy assists your primary database by easing the burden it might carry. This is most commonly
seen by the rerouting of queries for frequently read data to the cache itself, rather than the primary database. The
cache itself, resides in either the database, application, or even as a standalone access layer.

For example, your application requests user information from the database for the first time, this request goes from
application server to database server and returns back the requested information. With caching, this user profile is
stored closer to the requester after initial read, and there is a significant reduction in query processing time and
database workload for all subsequent read requests for that data.

### What are the benefits of database caching?

Data retrieval speed greatly affects the user experience of an application. Implementing a caching strategy on your
database can result in improved database performance, availability, and scalability for minimal cost depending on the
strategy, all factors contributing to an overall positive application experience.

#### Performance

As touched on briefly, database caching improves the performance of a database by making data more easily accessed. The
cache acts as a sort of “keyboard short-cut” or “hot-key” for the application to reference data that it frequently is
calling upon.

This speedier request can minimize the workload of the database, keeping it from spending inefficient amounts of time
doing repetitive tasks. Instead making these tasks more efficient and simplifying data access.

#### Availability

While not a 100% failover strategy, caching also provides benefits to overall database availability. Depending on where
the cache is stored, the cache can still provide a place for the application to call upon for data in the case of the
primary database server becoming unavailable for any reason.

While database performance is generally the primary reason for adopting a caching strategy, you also have the added
benefit of some additional resiliency in the case of any backend failures.

#### Scalability

Similarly to added high availability, database caching has a positive effect on scalability. While it shouldn’t be your
main consideration for a database scaling strategy, implementing caching to improve database performance reduces your
database workload, therefore distributing backend queries across entities.

This distribution lightens the load on a primary database and can reduce costs and provide more flexibility in the
processing of your data. This result alleviates the need to scale and does more with the resources you already have on
hand, potentially pushing the need to scale into the future.

### What are the different database caching strategies?

Before adopting database caching into your data access flow, it is important to consider which caching strategy is best
suited for the job. For any scenario, the relationship between the database and the cache can have a different impact on
performance and system structure. Planning ahead and considering all options will lead to fewer headaches down the road.

The **five most popular strategies** to consider are:

* cache-aside (particularly useful for applications with read-heavy workloads)
* read-through (good for read-heavy workloads)
* write-through
* write-back (particularly useful for applications with write-heavy workloads), and
* write-around.

We’ll cover the data source to cache relationship and process of each strategy.

#### Cache-aside (also known as lazy loading)

In a cache-aside arrangement, the database cache sits next to the database. When the application requests data, it will
check the cache first. If the cache has the data (a cache hit), then it will return it. If the cache does not have the
data (a cache miss), then the application will query the database. The application then stores that data in the cache
for any subsequent queries.

A cache-aside design is a good general purpose caching strategy. This strategy is particularly useful for applications
with **read-heavy workloads**. This keeps frequently read data close at hand for the many incoming read requests. Two
additional benefits stem from the cache being separated from the database. In the instance of a cache failure, the
system relying on cache data can still go directly to the database but performance will degrade. This provides some
resiliency. Secondly, with the cache being separated, it can employ a different data model than that of the database.

On the other hand, the main drawback of a cache-aside strategy is the window being open for inconsistency from the
database. Generally, any data being written will go to the database directly. Therefore, the cache may have a period of
**inconsistency with the primary database**. There are different cache strategies to combat this depending on your
needs.

##### Pros

1. Application works in case there is a cache-miss but performance degrades.
2. Since only requested data is being written over the cache due to lazy loading, it avoids cache being filled up with
   unnecessary data.

##### Cons

1. For each cache-miss, it causes a noticeable delay.
2. Initially, the cache would be empty which would cause cache-miss for most queries. As a result, latency time
   increases.

#### Read-through

In a read through cache arrangement, the cache sits between the application and the database. It can be envisioned like
a straight line from application to database with the cache in the middle. In this strategy, the application will always
speak with the cache for a read, and when there is a cache hit, the data is immediately returned. In the case of a cache
miss, the cache will populate the missing data from the database and then return it to the application. For any data
writes, the application will still go directly to the database.

Read-through caches are also good for **read-heavy workloads**. The main differences between read-through and
cache-aside is that in a cache-aside strategy the application is responsible for fetching the data and populating the
cache, while in a read-through setup, the logic is done by a library or some separate cache provider. A read-through
setup is similar to a cache-aside in regards to potential **data inconsistency between cache and database**.

A read-through caching strategy also has the disadvantage of needing to go to the database to get the data anytime a new
read request comes through. This data has never been cached before so the data needs to be loaded. It is common for
developers to mitigate this delay by ‘warming’ the cache by issuing likely to happen queries manually.

##### Pros

1. Application doesn't need to handle if there is a cache-miss. The cache server handles itself.

##### Cons

1. Stale data may present in the cache if data changes in a database.
2. In the case of cache-miss, it also causes noticeable delay.

#### Write-through

A write-through caching strategy differs from the previously two mentioned because instead of writing data to the
database, it will write to the cache first and the cache immediately writes to the database synchronously. The
arrangement can still be visualized like the read-through strategy, in a straight line with the cache in the middle.

The benefit to a write-through strategy is that the cache is ensured to have any written data and no new read will
experience delay while the cache requests it from the main database. If solely making this arrangement, there is the big
disadvantage of extra write latency because the action must go to the cache and then to the database. This should happen
immediately, but there are still two writes occurring in succession.

The **real benefit comes from pairing a write-through with a read-through cache**. This strategy will adopt all the
aforementioned benefits of the read-through caching strategy with the added benefit of removing the potential for data
inconsistency.

##### Pros

1. No cache-miss as data is always present.
2. No stale data present in the cache.
3. Data consistency is guaranteed if paired with read-through.

##### Cons

1. Most of the data present in the cache might never get requested.
2. Write latency increases as the application has to wait for write acknowledgement from cache storage and database.

#### Write-back (also known as write-behind)

Write-back works almost exactly the same as the write-through strategy except for one key detail. In a write-back
strategy, the application again writes directly to the cache. However, the cache does not immediately write to the
database, and it instead writes after a delay.

By writing to the database with a delay instead of immediately, the strain on the cache is reduced in a write-heavy
workload. This makes a **write-back, read-through combination good for mixed workloads**. This pairing ensures that the
most recently written data and accessed data is always present and accessible via the cache.

The delay in cache to database writes can improve overall write performance and if batching is supported then also a
reduction in overall writes. This opens up the potential for some cost savings and overall workload reduction. However,
in the case of a cache failure, this delay opens the door for possible data loss if the batch or delayed write to the
database has not yet occurred.

##### Pros

1. Tolerant to modern database failures.
2. Reduce load and cost by reducing writes to the database.

##### Cons

1. In case of any direct operation on the database, we may end up using stale data, formally said Eventual Inconsistency
   between the database and caching server.
2. If Cache fails, permanent data loss may occur.

#### Write-around

A write-around caching strategy will be **combined with either a cache-aside or a read-through**. In this arrangement,
data is always written to the database and the data that is read goes to the cache. If there is a cache miss, then the
application will read from the database and then update the cache for next time.

This particular strategy is going to be most performant in instances where data is **only written once and not
updated**. The data is read very infrequently or not at all.

References: https://www.prisma.io/dataguide/managing-databases/introduction-database-caching

## When should you use SQL or NoSQL or New SQL?

### NewSQL

NewSQL databases follow the relational structure and semantics, but are built using more modern, scalable designs. The
goal is to offer greater scalability than relational databases and greater consistency guarantees than NoSQL
alternatives. They achieve this by sacrificing certain amounts of availability in the event of a networking partition.
The trade offs between consistency and availability is a fundamental problem of distributed databases described by the
CAP theorem. In other words, these systems want to achieve the scalability of NoSQL without having to discard the
relational model with SQL and transaction support of the legacy DBMS.

Examples:

* MemSQL
* VoltDB
* Spanner
* Calvin
* CockroachDB
* FaunaDB
* yugabyteDB
* PlanetScale

### When to pick a SQL database?

SQL is a domain-specific language used to query and manage data. It works by allowing users to query, insert, delete,
and update records in relational databases. SQL also allows for complex logic to be applied through the use of
transactions and embedded procedures such as stored functions or views.

It’s perfect for complex queries. However, SQL can be too restrictive. You have to use predefined schemas to determine
your data structure before you can work with it. All of your data must follow the same structure, and this process
requires significant upfront preparation. If you ever need to change your data structure, it would be difficult and
disruptive to your whole system.

SQL databases are vertically scalable in most situations.

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

NoSQL stands for Not only SQL. It is a type of database that uses non-relational data structures, such as documents,
graph databases, and key-value stores to store and retrieve data. NoSQL systems are designed to be more flexible than
traditional relational databases and can scale up or down easily to accommodate changes in usage or load. This makes
them ideal for use in applications.

NoSQL databases are horizontally scalable. You can handle higher traffic via a process called sharding, which adds more
servers to your NoSQL database. Horizontal scaling has a greater overall capacity than vertical scaling, making NoSQL
databases the preferred choice for large and frequently changing data sets. For example, you might use a NoSQL database
if you have large data objects like images and videos. An SQL database wouldn't be able to handle these objects as
effectively, making it difficult to fulfill your data requirements.

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

*

MongoDB[README.md](..%2F..%2FAtlassian%2FUsers%2Fmfaisal%2FDocuments%2FAtlassian%2FWebStorm-Projects%2Fpollinator%2FREADME.md)
[run_tests.bash](..%2F..%2FAtlassian%2FUsers%2Fmfaisal%2FDocuments%2FAtlassian%2FWebStorm-Projects%2Fpollinator%2Frun_tests.bash)

* Redis
* Cassandra
* HBASE

### Difference Between SQL, NoSQL, and NewSQL?

| Feature               | 	SQL                                                                          | NoSQL                                                                                         | NewSQL                                                                        |
|-----------------------|-------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------|
| Schema                | Fixed Schema                                                                  | Schema-free                                                                                   | Supports both, i.e. fixed schema as well as schema free                       |
| Relational Property   | Yes, it follows relational modeling to a large extent.                        | No, it doesn't follow a relational model. It was designed to be entirely different from that. | Yes, since the relational model is equally essential for real-time analytics. |
| ACID                  | Yes, ACID properties are fundamental to their application                     | No, rather provides for CAP support                                                           | Yes, Acid properties are taken care of.                                       |
| SQL                   | Support for SQL                                                               | No support for old SQL                                                                        | Yes, proper support and even enhanced functionalities for Old SQL             |
| OLTP                  | Inefficient for OLTP (online transaction process) databases.                  | It supports such databases, but it is not the best suited.                                    | Fully functionally supports OLTP databases and is highly efficient            |
| Scaling               | Vertical scaling                                                              | Only Horizontal scaling                                                                       | Vertical + Horizontal scaling                                                 |
| Query Handling        | Can handle simple queries with ease and fails when they get complex in nature | Better than SQL for processing complex queries                                                | Highly efficient in processing complex queries and smaller queries.           |
| Distributed Databases | N0                                                                            | Yes                                                                                           | Yes                                                                           |

Document oriented stores/dbs - mongoDB - https://neetcode.io/courses/lessons/mongodb
Graph dbs - Neo4j, DGraph
Key-value stores/dbs - DynamoDB, Redis, memcached, etcd
Columnar dbs - Cassandra, HBase
Time series dbs - OpenTSDB, Prometheus, InfluxDB, TimescaleDB
Wide column dbs
Object/Blob storage (for things like images/videos) - Amazon S3, but as such objects are usually static in nature,
so they might be cached in CDN, so see if CDN can be used for S3 data, but it really depends on the problem you're
solving
Replication
Redundancy

### Sharding

#### What is database sharding?

Sharding is a method for distributing or partitioning a single dataset across multiple databases, which can then be
stored on multiple machines. This allows for larger datasets to be split into smaller chunks and stored in multiple data
nodes, increasing the total storage capacity of the system.

Similarly, by distributing the data across multiple machines, a sharded database can handle more requests than a single
machine can.

Sharding is a form of scaling known as horizontal scaling or scale-out, as additional nodes are brought on to share the
load. Horizontal scaling allows for near-limitless scalability to handle big data and intense workloads. In contrast,
vertical scaling refers to increasing the power of a single machine or single server through a more powerful CPU,
increased RAM, or increased storage capacity.

#### Sharding Benefits

1. Increased read/write throughput
2. Increased storage capacity
3. Data Locality

#### Disadvantages of sharding

1. Query overhead
2. Complexity of administration
3. Increased infrastructure costs

#### Shard Key

The shard key is either a single indexed field or multiple fields covered by a compound index that determines the
distribution of the collection's documents among the cluster's shards.

The choice of shard key affects the creation and distribution of chunks across the available shards. The distribution of
data affects the efficiency and performance of operations within the sharded cluster.

When you choose your shard key, consider:

1. the cardinality of the shard key
2. the frequency with which shard key values occur
3. whether a potential shard key grows monotonically
4. Sharding Query Patterns
5. Shard Key Limitations

##### Shard Key Cardinality

Cardinality refers to the number of possible values of the Shard key.
The cardinality of a shard key determines the maximum number of chunks the balancer can create. Where possible, choose a
shard key with high cardinality. A shard key with low cardinality reduces the effectiveness of horizontal scaling in the
cluster.

Each unique shard key value can exist on no more than a single chunk at any given time. Consider a dataset that contains
user data with a continent field. If you chose to shard on continent, the shard key would have a cardinality of 7. A
cardinality of 7 means there can be no more than 7 chunks within the sharded cluster, each storing one unique shard key
value. This constrains the number of effective shards in the cluster to 7 as well - adding more than seven shards would
not provide any benefit.

If your data model requires sharding on a key that has low cardinality, consider using an indexed compound of fields to
increase cardinality.

A shard key with high cardinality does not, on its own, guarantees even distribution of data across the sharded cluster.
The frequency of the shard key and the potential for monotonically changing shard key values also contribute to the
distribution of the data.

##### Shard Key Frequency

The frequency of the shard key represents how often a given shard key value occurs in the data. If the majority of
documents contain only a subset of the possible shard key values, then the chunks storing the documents with those
values can become a bottleneck within the cluster. Furthermore, as those chunks grow, they may become indivisible chunks
as they cannot be split any further. This reduces the effectiveness of horizontal scaling within the cluster.

If your data model requires sharding on a key that has high frequency values, consider using a compound index using a
unique or low frequency value.

A shard key with low frequency does not, on its own, guarantee even distribution of data across the sharded cluster. The
cardinality of the shard key and the potential for monotonically changing shard key values also contribute to the
distribution of the data.

##### Monotonically Changing Shard Keys

A shard key on a value that increases or decreases monotonically is more likely to distribute inserts to a single chunk
within the cluster.

This occurs because every cluster has a chunk that captures a range with an upper bound of MaxKey. maxKey always
compares as higher than all other values. Similarly, there is a chunk that captures a range with a lower bound of
MinKey. minKey always compares as lower than all other values.

If the shard key value is always increasing, all new inserts are routed to the chunk with maxKey as the upper bound. If
the shard key value is always decreasing, all new inserts are routed to the chunk with minKey as the lower bound. The
shard containing that chunk becomes the bottleneck for write operations.

To optimize data distribution, the chunks that contain the global maxKey (or minKey) do not stay on the same shard. When
a chunk is split, the new chunk with the maxKey (or minKey) chunk is located on a different shard.

If the shard key value was monotonically decreasing, then all inserts would route to Chunk A instead.

If your data model requires sharding on a key that changes monotonically, consider using Hashed Sharding.

A shard key that does not change monotonically does not, on its own, guarantee even distribution of data across the
sharded cluster. The cardinality and frequency of the shard key also contribute to the distribution of the data.

##### Sharding Query Patterns

The ideal shard key distributes data evenly across the sharded cluster while also facilitating common query patterns.
When you choose a shard key, consider your most common query patterns and whether a given shard key covers them.

In a sharded cluster, the mongos routes queries to only the shards that contain the relevant data if the queries contain
the shard key. When the queries do not contain the shard key, the queries are broadcast to all shards for evaluation.
These types of queries are called scatter-gather queries. Queries that involve multiple shards for each request are less
efficient and do not scale linearly when more shards are added to the cluster.

This does not apply for aggregation queries that operate on a large amount of data. In these cases, scatter-gather can
be a useful approach that allows the query to run in parallel on all shards.

### Comparing common database infrastructure patterns

Master/master vs Master/slave vs active/passive setup
Sharding => Shard key? => High Cardinality, Low Frequency and good for query patterns

L1 vs L2 caching

TCP vs HTTP vs Websocket vs UDP
When to Use Webhooks, WebSocket, Pub/Sub, and Polling...
Long-Polling vs WebSockets vs Server-Sent Events
No. of Websocket connections: https://josephmate.github.io/2022-04-14-max-connections/

graphql-vs-rest

Proxy vs ReverseProxy
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