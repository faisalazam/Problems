# System Design / Architecture Interviews

## Back-of-the-envelope capacity estimation / planning - warehouse-scale computing

Back-of-the-envelope calculations are estimates you create using a combination of thought experiments and common
performance numbers to get a good feel for which designs will meet your requirements - Jeff Dean

One part of system design interview is the back-of-the-envelope calculations. You have to estimate how many servers with
which specs you need. I tried to find any manuals with calculations, bit didn't find anything better than
https://servebolt.com/articles/calculate-how-many-simultaneous-website-visitors/
I would appreciate if you share your experience with such calculations.
Below I publish my humble effort to estimate Twitter (processing servers).

Remember, while doing these calculations, consider the peak hours load, because otherwise the system might break during
peak hours if the calculations were just good enough for the off-peak hours.

Preconditions and assumptions:

* 330 millions MAU (Monthly Active Users);
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

### Example: Estimate Twitter QPS and storage requirements

Requests per second (RPS) for service level
Queries per second (QPS) for database level

Please note the following numbers are for this exercise only as they are not real numbers from Twitter.

Assumptions:

- 300 million MAU (Monthly Active Users).
- 50% of users use Twitter daily => 150 millions DAU (Daily Active Users).
- Users post 2 tweets per day on average.
- 10% of tweets contain pictures of size ~100KB each
- 1% of tweets contain videos of size ~100MB each
- Data is stored for 5 years.
- files are replicated, with 3 copies each

#### Storage calculation for multimedia files for twitter

i.e. for storing pictures:

``` 
Picture Storage  = 150 millions tweets * (10% pictures * 100KB) * 3 copies * (400 days per year * 5 years)
                 = 15 * 10^6 tweets * (0.1 pictures * 100 * 10^3 B) * 3 copies * (4 * 10^2 days per year * 5 years)
                 = 1.5 * 10^8 * (10^-1 * 10^5 B) * 3 copies * (20 * 10^2)
           Group all the powers of 10 together and other numbers together for easy calculation
                 = 1.5 * 3 * 20 * 10^8 * 10^-1 * 10^5 * 10^2
                 = 90 * 10^(8-1+5+2)
                 = 90 * 10^14
                 = 9 * 10^15
                 = 9PB
```

now for videos, as video are like 1/10th of pictures (i.e. 1% vs 10%), so their storage can be calculated as:

```
Video Storage = (1% * 100MB / (10% * 100KB)) * Picture Storage
              = (10^-2 * 100 * 10^6 B / (10^-1 * 100 * 10^3 B)) * 9PB
              = (10^6 B / (10 * 10^3 B)) * 9PB
              = 10^2 B * 9PB
              = 100 * 9PB
              = 900PB
```

### Tips

Back-of-the-envelope estimation is all about the process. Solving the problem is more important than obtaining results.
Interviewers may test your problem-solving skills. Here are a few tips to follow:

* Rounding and Approximation. It is difficult to perform complicated math operations during the interview. For example,
  what is the result of "99987 / 9.1"? There is no need to spend valuable time to solve complicated math problems.
  Precision is not expected. Use round numbers and approximation to your advantage. The division question can be
  simplified as follows: "100,000 / 10".
* Write down your assumptions. It is a good idea to write down your assumptions to be referenced later.
* Label your units. When you write down "5", does it mean 5 KB or 5 MB? You might confuse yourself with this. Write down
  the units because "5 MB" helps to remove ambiguity.
* Commonly asked back-of-the-envelope estimations: QPS, peak QPS, storage, cache, number of servers, etc. You can
  practice these calculations when preparing for an interview. Practice makes perfect.

https://bytebytego.com/courses/system-design-interview/back-of-the-envelope-estimation
https://www.youtube.com/watch?v=UC5xf8FbdJc&list=PLCRMIe5FDPseVvwzRiCQBmNOVUIZSSkP8&index=4
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
● "100% is the wrong reliability target for (nearly) everything"
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

https://www.youtube.com/watch?v=FqR5vESuKe0&list=PLCRMIe5FDPsd0gVs500xeOewfySTsmEjf&index=3&pp=iAQB

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

By analyzing the numbers, we get the following conclusions:

- Memory is fast but the disk is slow.
- Avoid disk seeks if possible.
- Simple compression algorithms are fast.
- Compress data before sending it over the internet if possible.
- Data centers are usually in different regions, and it takes time to send data between them.

Credit
------
By Jeff Dean:               http://research.google.com/people/jeff/
Originally by Peter Norvig: http://norvig.com/21-days.html#answers

### Data Volumes

Although data volume can become enormous when dealing with distributed systems, calculation all boils down to the
basics. To obtain correct calculations, it is critical to know the data volume unit using the power of 2. A byte is a
sequence of 8 bits. An ASCII character uses one byte of memory (8 bits).

|                                    | Scientific Notation | Memory Units | Number Units |                                    |
|------------------------------------|---------------------|--------------|--------------|------------------------------------|
| 1,000;                             | 10^3                | KB - Kilo    | Thousand     | a Kilobyte is one thousand bytes.  |
| 1,000,000;                         | 10^6                | MB - Mega    | Million      | a Megabyte is a million bytes.     |
| 1,000,000,000;                     | 10^9                | GB - Giga    | Billion      | a Gigabyte is a billion bytes.     |
| 1,000,000,000,000;                 | 10^12               | TB - Tera    | Trillion     | a Terabyte is a trillion bytes.    |
| 1,000,000,000,000,000;             | 10^15               | PB - Peta    | Quadrillion  | a Petabyte is 1,000 Terabytes.     |
| 1,000,000,000,000,000,000;         | 10^18               | EB - Exa     | Quintillion  | an Exabyte is 1,000 Petabytes.     |
| 1,000,000,000,000,000,000,000;     | 10^21               | ZB - Zetta   | Sextillion   | a Zetta-byte is 1,000 Exabytes.    |
| 1,000,000,000,000,000,000,000,000; | 10^24               | YB - Yotta   | Septillion   | a Yotta-byte is 1,000 Zetta bytes. |

#### Examples of Data Volumes

| Unit	            | Power of 2 | Scientific Notation | Value	            | Example                                                                    |
|------------------|------------|---------------------|-------------------|----------------------------------------------------------------------------|
| Kilobytes (KB)   | 2^10       | 10^3                | 1,000 bytes       | a paragraph of a text document                                             |
| Megabytes (MB)   | 2^20       | 10^6                | 1,000 Kilobytes   | a small novel                                                              |
| Gigabytes (GB)   | 2^30       | 10^9                | 1,000 Megabytes   | Beethoven’s 5th Symphony                                                   |
| Terabytes (TB)   | 2^40       | 10^12               | 1,000 Gigabytes   | all the X-rays in a large hospital                                         |
| Petabytes (PB)   | 2^50       | 10^15               | 1,000 Terabytes   | half the contents of all US academic research libraries                    |
| Exabytes (EB)    |            | 10^18               | 1,000 Petabytes   | about one fifth of the words people have ever spoken                       |
| Zetta-bytes (ZB) |            | 10^21               | 1,000 Exabytes    | as much information as there are grains of sand on all the world’s beaches |
| Yotta-bytes (YB) |            | 10^24               | 1,000 Zetta-bytes | as much information as there are atoms in 7,000 human bodies               |

If there are `10 million` products in the system, can we store each (along with a count) in a hash table? Yes. If each
product ID is four bytes {which is big enough to hold up to 4 billion unique IDs) and each count is four bytes (more
than enough), then such a hash table would only take about 40 megabytes.

### Availability numbers

High availability is the ability of a system to be continuously operational for a desirably long period of time. High
availability is measured as a percentage, with 100% means a service that has 0 downtime. Most services fall between 99%
and 100%.

Uptime is traditionally measured in nines. The more the nines, the better. As shown in Table below, the number of nines
correlate to the expected system downtime.

| Availability % | Downtime per day   | Downtime per week | Downtime per month | Downtime per year |
|----------------|--------------------|-------------------|--------------------|-------------------|
| 99%            | 14.40 minutes      | 1.68 hours        | 7.31 hours         | 3.65 days         |
| 99.99%         | 8.64 seconds       | 1.01 minutes      | 4.38 minutes       | 52.60 minutes     |
| 99.999%        | 864.00             | 6.05 seconds      | 26.30 seconds      | 5.26 minutes      |
| 99.9999%       | 86.40 milliseconds | 604.80            | 2.63 seconds       | 31.56 seconds     |

#### Availability Formula

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

## Program vs Process vs Thread vs Coroutines

### Program

A 𝐏𝐫𝐨𝐠𝐫𝐚𝐦 is an executable file containing a set of instructions and passively stored on disk. One program can have
multiple processes. For example, the Chrome browser creates a different process for every single tab.

Processes and threads are related to each other but are fundamentally different.

### Process

A 𝐏𝐫𝐨𝐜𝐞𝐬𝐬 means a program is in execution. When a program is loaded into the memory and becomes active, the program
becomes a process. The process requires some essential resources such as registers, program counter, and stack.

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

### Coroutines

Kotlin's documentation often refers to coroutines as lightweight threads. This is mostly because, like threads,
coroutines define the execution of a set of instructions for a processor to execute. Also, coroutines have a similar
life cycle to that of threads.

A coroutine is executed inside a thread. One thread can have many coroutines inside it, but as already mentioned, only
one instruction can be executed in a thread at a given time. This means that if you have ten coroutines in the same
thread, only one of them will be running at a given point in time.

The biggest difference between threads and coroutines, though, is that coroutines are fast and cheap to create. Spawning
thousands of coroutines can be easily done, it is faster and requires fewer resources than spawning thousands of
threads.

It's important to understand that even though a coroutine is executed inside a thread, it's not bound to it. As a matter
of fact, it's possible to execute part of a coroutine in a thread, suspend the execution, and later continue in a
different thread.

### Consistent Hashing

Consistent Hashing is a distributed hashing scheme that operates independently of the number of servers or objects in a
distributed hash table. It powers many high-traffic dynamic websites and web applications.

#### What Is Hashing?

What is "hashing" all about? Merriam-Webster defines the noun hash as "chopped meat mixed with potatoes and browned,"
and the verb as "to chop (as meat and potatoes) into small pieces." So, culinary details aside, hash roughly means "chop
and mix", and that’s precisely where the technical term comes from.

A hash function is a function that maps one piece of data, typically describing some kind of object, often of arbitrary
size, to another piece of data, typically an integer, known as hash code, or simply hash. A suitable hash function can
be used to map an arbitrary piece of data to an integer. A good hash function generally has a wide output range.

For instance, some hash function designed to hash strings, with an output range of 0 .. 100, may map the string Hello
to, say, the number 57, Hasta la vista, baby to the number 33, and any other possible string to some number within that
range. Since there are way more possible inputs than outputs, any given number (i.e. hash code or hashed value) will
have many different strings mapped to it, a phenomenon known as collision. Good hash functions should somehow
"chop and mix" (hence the term) the input data in such a way that the outputs for different input values are spread as
evenly as possible over the output range.

#### Scaling Out: Distributed Hashing

In some situations, it may be necessary or desirable to split a hash table into several parts, hosted by different
servers. One of the main motivations for this is to bypass the memory limitations of using a single computer, allowing
for the construction of arbitrarily large hash tables (given enough servers).

In such a scenario, the objects (and their keys) are distributed among several servers, hence the name.

A typical use case for this is the implementation of in-memory caches, such as Memcached.

##### How does distribution take place? What criteria are used to determine which keys to host in which servers?

The simplest way is to take the hash modulo of the number of servers. That is, `server = hash(key) mod N`, where N is
the size of the pool. To store or retrieve a key, the client first computes the hash, applies a modulo N operation, and
uses the resulting index to contact the appropriate server (probably by using a lookup table of IP addresses). Note that
the hash function used for key distribution must be the same one across all clients, but it need not be the same one
used internally by the caching servers.

##### The Rehashing Problem

This distribution scheme is simple, intuitive, and works fine. That is, until the number of servers changes. What
happens if one of the servers crashes or becomes unavailable? Keys need to be redistributed to account for the missing
server, of course. The same applies if one or more new servers are added to the pool;keys need to be redistributed to
include the new servers. This is true for any distribution scheme, but the problem with our simple modulo distribution
is that when the number of servers changes, most `hashes modulo N` will change, so most keys will need to be moved to a
different server. So, even if a single server is removed or added, all keys will likely need to be rehashed into a
different server.

#### The Solution: Consistent Hashing

So, how can this problem be solved? We need a distribution scheme that does not depend directly on the number of
servers, so that, when adding or removing servers, the number of keys that need to be relocated is minimized. One such
scheme—a clever, yet surprisingly simple one—is called consistent hashing.

Consistent Hashing is a distributed hashing scheme that operates independently of the number of servers or objects in a
distributed hash table by assigning them a position on an abstract circle, or hash ring. This allows servers and objects
to scale without affecting the overall system.

Imagine we mapped the hash output range onto the edge of a circle. That means that the minimum possible hash value,
zero, would correspond to an angle of zero, the maximum possible value (some big integer we’ll call INT_MAX) would
correspond to an angle of 2𝝅 radians (or 360 degrees), and all other hash values would linearly fit somewhere in
between. So, we could take a key, compute its hash, and find out where it lies on the circle’s edge.

Now imagine we also placed the servers on the edge of the circle, by pseudo-randomly assigning them angles too. This
should be done in a repeatable way (or at least in such a way that all clients agree on the servers’ angles). A
convenient way of doing this is by hashing the server name (or IP address, or some ID)—as we’d do with any other key—to
come up with its angle.

Since we have the keys for both the objects and the servers on the same circle, we may define a simple rule to associate
the former with the latter: Each object key will belong in the server whose key is closest, in a counterclockwise
direction (or clockwise, depending on the conventions used). In other words, to find out which server to ask for a given
key, we need to locate the key on the circle and move in the ascending angle direction until we find a server.

From a programming perspective, what we would do is keep a sorted list of server values (which could be angles or
numbers in any real interval), and walk this list (or use a binary search) to find the first server with a value greater
than, or equal to, that of the desired key. If no such value is found, we need to wrap around, taking the first one from
the list.

To ensure object keys are evenly distributed among servers, we need to apply a simple trick: To assign not one, but many
labels (angles) to each server. So instead of having labels A, B and C, we could have, say, A0 .. A9, B0 .. B9 and C0 ..
C9, all interspersed along the circle. The factor by which to increase the number of labels (server keys), known as
weight, depends on the situation (and may even be different for each server) to adjust the probability of keys ending up
on each. For example, if server B were twice as powerful as the rest, it could be assigned twice as many labels, and as
a result, it would end up holding twice as many objects (on average).

For our example we’ll assume all three servers have an equal weight of 10 (this works well for three servers, for 10 to
50 servers, a weight in the range 100 to 500 would work better, and bigger pools may need even higher weights).

##### So, what’s the benefit of all this circle approach?

Imagine server C is removed. To account for this, we must remove labels C0 .. C9 from the circle. This results in the
object keys formerly adjacent to the deleted labels now being randomly labeled Ax and Bx, reassigning them to servers A
and B.

But what happens with the other object keys, the ones that originally belonged in A and B? Nothing! That’s the beauty of
it: The absence of Cx labels does not affect those keys in any way. So, removing a server results in its object keys
being randomly reassigned to the rest of the servers, leaving all other keys untouched.

Something similar happens if, instead of removing a server, we add one. If we wanted to add server D to our example (
say, as a replacement for C), we would need to add labels D0 .. D9. The result would be that roughly one-third of the
existing keys (all belonging to A or B) would be reassigned to D, and, again, the rest would stay the same.

This is how consistent hashing solves the rehashing problem.

In general, only k/N keys need to be remapped when k is the number of keys and N is the number of servers
(more specifically, the maximum of the initial and final number of servers).

There are clients for several systems, such as Memcached and Redis, that include support for consistent hashing out of
the box.

https://www.toptal.com/big-data/consistent-hashing

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
cache acts as a sort of "keyboard short-cut" or "hot-key" for the application to reference data that it frequently is
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

### 𝐂𝐚𝐜𝐡𝐞 𝐌𝐢𝐬𝐬 𝐀𝐭𝐭𝐚𝐜𝐤

One of the issues is 𝐂𝐚𝐜𝐡𝐞 𝐌𝐢𝐬𝐬 𝐀𝐭𝐭𝐚𝐜𝐤. Correct me if this is not the right term. It refers to the scenario where data
to fetch doesn't exist in the database and the data isn’t cached either. So every request hits the database eventually,
defeating the purpose of using a cache. If a malicious user initiates lots of queries with such keys, the database can
easily be overloaded.

One solution for the Cache Penetration attack would be to store the missing key in the cache (let's say with null
value), so that the DB is not hit next time as the key would've been found in the cache. Set a short TTL (Time to Live)
for keys with null value.

Another solution would be to use use Bloom filter. A Bloom filter is a data structure that can rapidly tell us whether
an element is present in a set or not. If the key exists, the request first goes to the cache and then queries the
database if needed. If the key doesn't exist in the data set, it means the key doesn’t exist in the cache/database. In
this case, the query will not hit the cache or database layer.

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

* MongoDB
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

### How to choose between SQL and NoSQL databases

| Consider SQL databases when…                                                          | Consider NoSQL databases when…                                                                                  |
|---------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| Your data is highly structured, and that structure doesn’t change frequently          | You’re working with large amounts of unstructured or semi-structured data that doesn’t fit the relational model |
| You support transaction-oriented systems such as accounting or financial applications | You require the flexibility of a dynamic schema or want more choice over the data model                         |
| You require a high degree of data integrity and security                              | You require a database system that can be scaled horizontally, perhaps across multiple geographic locations     |
| You routinely perform complex queries, including ad hoc requests                      | You want to streamline development and avoid the overhead of a more structured approach                         |
| You don’t require the scale-out capabilities that NoSQL offers                        | Your applications don’t require the level of data integrity offered by SQL databases                            |

Document oriented stores/dbs - mongoDB - https://neetcode.io/courses/lessons/mongodb
Graph dbs - Neo4j, DGraph
Key-value stores/dbs - DynamoDB, Redis, memcached, etcd
Columnar dbs - Cassandra, HBase
Time series dbs - OpenTSDB, Prometheus, InfluxDB, TimescaleDB
Wide column dbs
Geo-spatial dbs - Redis GeoHash, PostgreSQL with PostGIS extension
Object/Blob storage (for things like images/videos) - Amazon S3, but as such objects are usually static in nature,
so they might be cached in CDN, so see if CDN can be used for S3 data, but it really depends on the problem you're
solving
Replication
Redundancy

#### Time series dbs

There are many storage systems available that are optimized for time-series data. The optimization lets us use far fewer
servers to handle the same volume of data. Many of these databases also have custom query interfaces specially designed
for the analysis of time-series data that are much easier to use than SQL. Some even provide features to manage data
retention and data aggregation. Here are a few examples of time-series databases.

OpenTSDB is a distributed time-series database, but since it is based on Hadoop and HBase, running a Hadoop/HBase
cluster adds complexity. Twitter uses MetricsDB, and Amazon offers Timestream as a time-series database. According to
DB-engines, the two most popular time-series databases are InfluxDB and Prometheus, which are designed to store large
volumes of time-series data and quickly perform real-time analysis on that data. Both of them primarily rely on an
in-memory cache and on-disk storage. And they both handle durability and performance quite well. According to the
benchmark, an InfluxDB with 8 cores and 32GB RAM can handle over 250,000 writes per second.

Another feature of a strong time-series database is efficient aggregation and analysis of a large amount of time-series
data by labels, also known as tags in some databases. For example, InfluxDB builds indexes on labels to facilitate the
fast lookup of time-series by labels. It provides clear best-practice guidelines on how to use labels, without
overloading the database. The key is to make sure each label is of low cardinality (having a small set of possible
values). This feature is critical for visualization, and it would take a lot of effort to build this with a
general-purpose database.

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

#### When to shard

So, how do you know when to shard your database? Some good indicators that it may be time to consider sharding are when
you've started to max out data size, write throughput, and/or read throughput. Let's walk through each of these
categories.

##### Data Size

Data size can still be a driving factor for sharding though. One thing to consider is how large your working set is, and
how much of that fits into RAM. As less of your active data fits in memory and more queries need to read from disk,
query latency will increase.

Other database operations are also affected by the data size of a single MySQL cluster. The larger the database, the
longer backups (and restores!) take. The same is true for other operational tasks like provisioning new replicas and
making schema changes. This is the logic behind guidelines Vitess has made for shard sizing. Smaller data size per shard
improves manageability.

##### Write throughput

Another reason to consider sharding is when you've maxed out the write throughput of your cluster. This can show up in a
couple of ways.

When the primary is maxed on IOPS, writes will become less performant. Usually before that, however, replication lag
becomes a problem.

##### Read throughput

While running out of read throughput capacity can be solved through read-write splitting and the addition of read
replicas, that isn't without its own challenges. As mentioned in the previous section, replication lag can make this
complex or lead to a poor experience for your users.

Typically, this is earlier than we often think about sharding. However, by scaling read capacity through horizontal
sharding instead of by using replicas, application code does not need to account for the potential replication lag or
that multiple connection strings need to be managed and utilized depending on the data set you are trying to access.
Plus, sharding at this stage sets you up for future growth and you don't have to come back and shard later when write
throughput or data size would otherwise become an issue.

Why does Vitess recommend 250GB per MySQL server?
Performance is not limited to insert performance. Specifically, some management tasks become more difficult with
larger databases:

* Taking a full backup
* Provisioning a new read replica
* Restoring a backup
* Making a schema change
* Reducing replica delay

https://vitess.io/blog/2019-09-03-why-250gb-shards/#okay-why-the-limit

#### Shard Key => should be High Cardinality, Low Frequency and good for query patterns

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

A master database generally only supports write operations. A slave database gets copies of the data from the master
database and only supports read operations. All the data-modifying commands like insert, delete, or update must be sent
to the master database. Most applications require a much higher ratio of reads to writes; thus, the number of slave
databases in a system is usually larger than the number of master databases.

#### Database Replication

This involves copying data to multiple sources to prevent data loss and improve system performance. Different strategies
exist, like leader-follower, multi-leader, and leaderless replication, each with their own trade-offs.

Synchronous replication ensures data consistency but can be slow, while asynchronous replication sacrifices consistency
for speed. Multi-leader replication mitigates leader failures, but complexity increases.

Leaderless replication eliminates the leader-follower hierarchy but requires managing read and write capable replicas.
Including replicas is generally recommended for distributed systems, and the choice of strategy depends on your specific
needs.

### L1 vs L2 vs L3 caching

L1 is smallest and fastest typically integrated into the CPU itself
L2 is larger but slower than L1
L3 is larger but slower than L2, and is often shared between multiple CPU cores

TCP vs HTTP vs Websocket vs UDP ?

### HTTP Short vs Long Polling vs WebSockets vs SSE vs Webhooks vs gRPC

Web applications were originally designed as a simple client-server model where the web client initiates an HTTP request
requesting some data from the server.

As developers began to explore ways to implement more “real-time” applications. The HTTP protocol made these sorts of
use cases very challenging. As a result, creative ways to manipulate HTTP request-response model into a more real-time
model began to emerge.

#### HTTP Short Polling

In HTTP short polling, the client keeps making the HTTP requests at a regular interval for example every 1–2 seconds. In
this case, most of the requests might get an empty response because the server might not have any updates which are
useful for the client.

##### Disadvantages

* High HTTP Overhead
* Unnecessary network calls. Lots of requests going back and forth.
* Not suitable for real-time communication applications.

#### HTTP Long Polling

Because polling could be inefficient, the next progression is long polling. The client can make a request to the server
for the data using the HTTP request. But, here is the catch, the client waits for the server to provide the response.
There will be a connection opened till the server has a response to send back or a timeout occurs.

##### Advantages

* Low HTTP Overhead
* Easy to implement, good for small-scale projects.

##### Disadvantages

* A server has no good way to tell if a client is disconnected.
* Reliable message ordering can be an issue for multiple requests.
* It is inefficient for designing chat applications. If a user does not chat much, long polling still makes periodic
  connections after timeouts.
* Sender and receiver may not connect to the same chat server. HTTP based servers are usually stateless. If you use
  round robin for load balancing, the server that receives the message might not have a long-polling connection with the
  client who receives the message.

#### WebSocket

WebSocket is the most common solution for sending asynchronous updates from server to client.

A WebSocket is a persistent connection between a client and a server. WebSockets provide a bidirectional, full-duplex
communications channel that operates over HTTP through a single TCP/IP socket connection. At its core, the WebSocket
protocol facilitates message passing between a client and server. It is initiated by the client.

It starts its life as a HTTP connection and could be “upgraded” via some well-defined handshake to a WebSocket
connection. Through this persistent connection, a server could send updates to a client. WebSocket connections generally
work even if a firewall is in place. This is because they use port 80 or 443 which are also used by HTTP/HTTPS
connections. Since WebSocket connections are persistent, efficient connection management is critical on the server-side.

WebSocket uses its own custom WS protocol for communication.

Earlier we said that on the sender side HTTP is a fine protocol to use, but since WebSocket is bidirectional, there is
no strong technical reason not to use it also for sending.

##### Advantages

* Realtime data transfer
* Better origin-based security model
* Lightweight for both client and server
* Low communication overhead as we are doing the handshaking only once at the beginning.

##### Disadvantages

* Terminated connections aren't automatically recovered.
* Older browsers don't support WebSockets (becoming less relevant).

##### Applications

* Stock market ticker
* Live experiences
* Multi-player games
* Chat

No. of Websocket connections: https://josephmate.github.io/2022-04-14-max-connections/

#### Server Side Events

SSE is a server-push technology enabling a client to receive automatic updates from a server. Using SSE, the clients
make a persistent long-term connection with the server. Then, the server uses this connection to send the data to the
client. It is important to note that the client can’t send the data to the server using the SSE.

Hence unlike WebSockets, Server-Sent Events are a one-way communication channel where events flow from server to client
only.

##### Advantages

* Simple to implement and use for both client and server.
* Supported by most browsers.
* No trouble with firewalls.

##### Disadvantages

* Unidirectional nature can be limiting.
* Limitation for the maximum number of open connections.
* Does not support binary data.

##### Applications

* Deployment logs streaming like in Jenkins
* Breaking news
* Sports Score updates
* Real-time notifications
* Instagram live interactions
* Stock market updates

#### Webhooks

One of the simplest ways online applications share data is through the use of webhooks, a one-way communication format
for moving data from one application to another. It is used when you require Real-time one-way communication (from
source to destination).

Webhook communication is achieved by sending an HTTP request from a source application to a destination application.
When an event takes place in the source application, an HTTP request which might contain data relating to the event is
triggered. This HTTP request is sent to the destination application's endpoint (often referred to as the webhook URL).
Webhook requests can be sent using the POST or GET request methods. This depends on the webhook provider's preferences.

Webhooks close the socket connection on the receiving application once a response has been sent back. They use regular
HTTP protocol and synchronous.

Webhooks make calls to APIs. An API provides webhooks with the entry point to push data to an application. When an event
occurs in a source application, a webhook request is triggered to one of the API endpoints.

#### Pub/Sub

Pub/sub, which is short for publish/subscribe, is a communication system for distributing messages between a set of
publishers (message producers) and subscribers (message consumers). A pub/sub system buffers messages from producers and
routes them to subscribers through the use of dedicated channels known as topics. Publishers publish messages to topics
and subscribers express interest in subscribing to the topics.

Webhooks are a direct form of communication between the producer and consumer while pub/sub is a middle-man framework
that routes messages from publishers to subscribers.

#### gRPC

A remote procedural call (RPC) implementation developed by Google, supporting both uni and bidirectional communication.

##### Advantages:

* Enables efficient communication by making function calls between different systems.
* Faster and lighter compared to REST communication.
* Provides enhanced security features.

##### Disadvantages:

* Implementation complexity is high, requiring the definition of protobufs (data structures and services).
* Limited support on platforms especially on browsers

##### Appropriate Use Case:

Suitable for microservice communication where security and speed are high priorities and there is sufficient time for
implementation.

graphql-vs-rest

Proxy vs ReverseProxy - https://www.youtube.com/watch?v=4NB0NDtOwIQ&list=PLCRMIe5FDPsd0gVs500xeOewfySTsmEjf&index=19
Load-balancing (at different levels like web server, app server, db etc. as it should not be single point of failure)
Scaling
Caching

Kafka - it uses the sequential I/O and Zero copy (among others) principles which make it really
fast - https://www.youtube.com/watch?v=UNUz1-msbOM&list=PLCRMIe5FDPsd0gVs500xeOewfySTsmEjf&index=21

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

## Message Queues

### At-most once, at-least once, and exactly once

In modern architecture, systems are broken up into small and independent building blocks with well-defined interfaces
between them. Message queues provide communication and coordination for those building blocks.

#### 𝐀𝐭-𝐦𝐨𝐬𝐭 𝐨𝐧𝐜𝐞

As the name suggests, at-most once means a message will be delivered not more than once. Messages may be lost but are
not redelivered. This is how at-most once delivery works at the high level.

Use cases: It is suitable for use cases like monitoring metrics, where a small amount of data loss is acceptable.

#### 𝐀𝐭-𝐥𝐞𝐚𝐬𝐭 𝐨𝐧𝐜𝐞

With this data delivery semantic, it’s acceptable to deliver a message more than once, but no message should be lost.

Use cases: With at-least once, messages won’t be lost but the same message might be delivered multiple times. While not
ideal from a user perspective, at-least once delivery semantics are usually good enough for use cases where data
duplication is not a big issue or deduplication is possible on the consumer side. For example, with a unique key in each
message, a message can be rejected when writing duplicate data to the database.

#### 𝐄𝐱𝐚𝐜𝐭𝐥𝐲 𝐨𝐧𝐜𝐞

Exactly once is the most difficult delivery semantic to implement. It is friendly to users, but it has a high cost for
the system’s performance and complexity.

Use cases: Financial-related use cases (payment, trading, accounting, etc.). Exactly once is especially important when
duplication is not acceptable and the downstream service or third party doesn’t support idempotency.

## How to decide on the number of tiers your app should have

* You should choose a single tier architecture when you do not want any network latency
* Choose a two tier application when you need to minimize network latency and you need more control of data within your
  application
* You should choose a three tier architecture when you need control over the code/business logic of your application &
  want it to be secure, and you need control over data in your application.
* You should choose a N tier architecture when you need your application to scale and handle large amounts of data.

## The Secret To 10 Million Concurrent Connections -The Kernel Is The Problem, Not The Solution

http://highscalability.com/blog/2013/5/13/the-secret-to-10-million-concurrent-connections-the-kernel-i.html?currentPage=2

## Scale From Zero To Millions Of Users

### Horizontal or vertical scaling… Which is right for my app?

Build to deploy it on the cloud & always have horizontal scalability in mind right from the start. Here is a good
website for learning more about scalability. http://highscalability.com/

### CDN - one technique used for caching the static content

A content delivery network (CDN) refers to a geographically distributed servers (also called edge servers) which provide
fast delivery of static and dynamic content.

A CDN is a network of geographically dispersed servers used to deliver static content. CDN servers cache static content
like images, videos, CSS, JavaScript files, etc. When a user visits a website, a CDN server closest to the user will
deliver static content. Intuitively, the further users are from CDN servers, the slower the website loads.

Dynamic content caching: it enables the caching of HTML pages that are based on request path, query strings, cookies,
and request headers. Amazon’s CloudFront is an example of CDN.

https://www.youtube.com/watch?v=RI9np1LWzqw&list=PLCRMIe5FDPsd0gVs500xeOewfySTsmEjf&index=15

### Stateless web tier

Now it is time to consider scaling the web tier horizontally. For this, we need to move state (for instance user session
data) out of the web tier. A good practice is to store session data in the persistent storage such as relational
database or NoSQL. Each web server in the cluster can access state data from databases. This is called stateless web
tier.

### Stateful architecture

A stateful server and stateless server has some key differences. A stateful server remembers client data (state) from
one request to the next. A stateless server keeps no state information.

The issue is that every request from the same client must be routed to the same server. This can be done with sticky
sessions in most load balancers; however, this adds the overhead. Adding or removing servers is much more difficult with
this approach. It is also challenging to handle server failures.

### Stateless architecture

In the stateless architecture, HTTP requests from users can be sent to any web servers, which fetch state data from a
shared data store. State data is stored in a shared data store and kept out of web servers. A stateless system is
simpler, more robust, and scalable.

### Message queue

To further scale our system, we need to decouple different components of the system so they can be scaled independently.
Messaging queue is a key strategy employed by many real-world distributed systems to solve this problem.

A message queue is a durable component, stored in memory, that supports asynchronous communication. It serves as a
buffer and distributes asynchronous requests. The basic architecture of a message queue is simple. Input services,
called producers/publishers, create messages, and publish them to a message queue. Other services or servers, called
consumers/subscribers, connect to the queue, and perform actions defined by the messages.

Decoupling makes the message queue a preferred architecture for building a scalable and reliable application. With the
message queue, the producer can post a message to the queue when the consumer is unavailable to process it. The consumer
can read messages from the queue even when the producer is unavailable.

### Logging, metrics, automation

#### Logging

Monitoring error logs is important because it helps to identify errors and problems in the system. You can monitor error
logs at per server level or use tools to aggregate them to a centralized service for easy search and viewing.

#### Metrics

Collecting different types of metrics help us to gain business insights and understand the health status of the system.
Some of the following metrics are useful:

* Host level metrics: CPU, Memory, disk I/O, etc.
* Aggregated level metrics: for example, the performance of the entire database tier, cache tier, etc.
* Key business metrics: daily active users, retention, revenue, etc.

### Database scaling

As the data grows every day, your database gets more overloaded. It is time to scale the data tier.

There are two broad approaches for database scaling: vertical scaling and horizontal scaling.

### A summary of how we scale our system to support millions of users

* Keep web tier stateless
* Build redundancy at every tier
* Cache data as much as you can
* Support multiple data centers
* Host static assets in CDN
* Scale your data tier by sharding
* Split tiers into individual services
* Monitor your system and use automation tools

https://bytebytego.com/courses/system-design-interview/scale-from-zero-to-millions-of-users

## A Framework For System Design Interviews

Many think that system design interview is all about a person's technical design skills. It is much more than that. An
effective system design interview gives strong signals about a person's ability to collaborate, to work under pressure,
and to resolve ambiguity constructively. The ability to ask good questions is also an essential skill, and many
interviewers specifically look for this skill.

A good interviewer also looks for red flags. Over-engineering is a real disease of many engineers as they delight in
design purity and ignore tradeoffs. They are often unaware of the compounding costs of over-engineered systems, and many
companies pay a high price for that ignorance. You certainly do not want to demonstrate this tendency in a system design
interview. Other red flags include narrow mindedness, stubbornness, etc.

### A 4-step process for effective system design interview

Every system design interview is different. A great system design interview is open-ended and there is no
one-size-fits-all solution. However, there are steps and common ground to cover in every system design interview.

#### Step 1 - Understand the problem and establish design scope (3 - 10 minutes)

##### What kind of questions to ask?

Ask questions to understand the exact requirements. Here is a list of some sample questions to help you get started:

* What specific features are we going to build? Or, What are the most important features for the product?
* How many users does the product have?
* How fast does the company anticipate to scale up? What are the anticipated scales in 3 months, 6 months, and a year?
* What is the company’s technology stack? What existing services you might leverage to simplify the design?
* Is this a mobile app? Or a web app? Or both?
* What is the traffic volume?
* Can feed contain images, videos, or just text?
* What are the metrics for success? i.e. how would we know/measure that we've built the required system?

#### Step 2 - Propose high-level design and get buy-in (10 - 15 minutes)

In this step, we aim to develop a high-level design and reach an agreement with the interviewer on the design. It is a
great idea to collaborate with the interviewer during the process.

#### Step 3 - Design deep dive (10 - 25 minutes)

Time management is essential as it is easy to get carried away with minute details that do not demonstrate your
abilities.

#### Step 4 - Wrap up (3 - 5 minutes)

In this final step, the interviewer might ask you a few follow-up questions or give you the freedom to discuss other
additional points. Here are a few directions to follow:

* The interviewer might want you to identify the system bottlenecks and discuss potential improvements. Never say your
  design is perfect and nothing can be improved. There is always something to improve upon. This is a great opportunity
  to show your critical thinking and leave a good final impression.
* It could be useful to give the interviewer a recap of your design. This is particularly important if you suggested a
  few solutions. Refreshing your interviewer’s memory can be helpful after a long session.
* Error cases (server failure, network loss, etc.) are interesting to talk about.
* Operation issues are worth mentioning. How do you monitor metrics and error logs? How to roll out the system?
* How to handle the next scale curve is also an interesting topic. For example, if your current design supports 1
  million users, what changes do you need to make to support 10 million users?
* Propose other refinements you need if you had more time.

#### Summary

To wrap up, we summarize a list of the Dos and Don’ts.

##### Dos

* Always ask for clarification. Do not assume your assumption is correct.
* Understand the requirements of the problem.
* There is neither the right answer nor the best answer. A solution designed to solve the problems of a young startup is
  different from that of an established company with millions of users. Make sure you understand the requirements.
* Let the interviewer know what you are thinking. Communicate with your interview.
* Suggest multiple approaches if possible.
* Once you agree with your interviewer on the blueprint, go into details on each component. Design the most critical
  components first.
* Bounce ideas off the interviewer. A good interviewer works with you as a teammate.
* Never give up.

##### Don’ts

* Don't be unprepared for typical interview questions.
* Don’t jump into a solution without clarifying the requirements and assumptions.
* Don’t go into too much detail on a single component in the beginning. Give the high-level design first then drills
  down.
* If you get stuck, don't hesitate to ask for hints.
* Again, communicate. Don't think in silence.
* Don’t think your interview is done once you give the design. You are not done until your interviewer says you are
  done. Ask for feedback early and often.

https://bytebytego.com/courses/system-design-interview/a-framework-for-system-design-interviews