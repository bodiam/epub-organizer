# Epub Organizer 

[![Build Status](https://travis-ci.org/bodiam/epub-organizer.png?branch=master)](https://travis-ci.org/bodiam/epub-organizer)  
[![Build Status](https://drone.io/github.com/bodiam/epub-organizer/status.png)](https://drone.io/github.com/bodiam/epub-organizer/latest)

This project is created to compare different [MongoDB](http://www.mongodb.org) clients and mappers. It currently consists of the following frameworks:

* `Spring Data - MongoDB` - [Spring Data](http://www.springsource.org/spring-data/mongodb) powered ORM.
* `Morphia` - A mature but sleeping ORM. I've used a [fork](https://github.com/jmkgreen/morphia).
* `MongoClient` - The [Java driver](http://docs.mongodb.org/ecosystem/tutorial/getting-started-with-java-driver/) for MongoDB
* `MongoJack` - A MongoDB [wrapper/mapper](http://http://mongojack.org/) based on [Jackson](http://wiki.fasterxml.com/JacksonHome).

The ORM mappers / clients are organized in packages, and share some common structures. 

Running `gradle test` should run all the test with default settings against your local database, and it should not fail. It works on my machine! ;)

### What does it do?

There's a fix amount of tests, which run over all implementations. The test are different in implementation, but not in intent. The goal of this
project was to find out which framework was easier to work with, and to experience if all requirements were possible using each framework (they are!).

So, some operations which are tested:
* CRUD, including one to many relations
* Complex queryes
* Binary data (no GridFS)

And more!!

### Why did you do this?

Because I asked a question on Stackoverflow, but it was closed due to being to subjective. So I had to find out myself. Feel free to fork / clone / copy and use however you'd like.

### You did it wrong!

Great! Let me know how I can improve things, so I can update the code and others will benefit too! Open an issue or submit a pull request! I'd love to learn from you!!

### Have questions?

I'd be happy to help. Contact me on Twitter, [@epragt](https://twitter.com/epragt)
