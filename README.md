# Epub Organizer 

This project is created to compare different [MongoDB](http://www.mongodb.org) clients and mappers. It currently consists of the following frameworks:

* `Spring Data - MongoDB` - [Spring Data](http://www.springsource.org/spring-data/mongodb) powered ORM.
* `Morphia` - A mature but sleeping ORM. I've used a [fork](https://github.com/jmkgreen/morphia).
* `MongoClient` - The [Java driver](http://docs.mongodb.org/ecosystem/tutorial/getting-started-with-java-driver/) for MongoDB
* `MongoJack` - A MongoDB [wrapper/mapper](http://http://mongojack.org/) based on [Jackson](http://wiki.fasterxml.com/JacksonHome).

The ORM mappers / clients are organized in packages, and share some common structures. 

Running `gradle test` should run all the test with default settings against your local database, and it should not fail. It works on my machine! ;)

### Why did you do this?

Because I asked a question on Stackoverflow, but it was closed due to being to subjective. So I had to find out myself. Feel free to fork / clone / copy and use however you'd like.

### You did it wrong!

Great! Let me know how I can improve things, so I can update the code and others will benefit too! Open an issue or submit a pull request! I'd love to learn from you!!

### Have questions?

I'd be happy to help. Contact me on Twitter, [@epragt](https://twitter.com/epragt)