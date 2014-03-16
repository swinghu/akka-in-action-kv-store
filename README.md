# Akka In Action - Building a Key-Value Store

## Draft Schedule:

### Discussed in Lesson 1

- Basic project setup, SBT, etc.
- Basic KV actor
- Test it
- Add events
- Test those
- Using a Settings extension


### Discussed in Lesson 2

- Add support for KV buckets using child actors
- Adjust the tests
- The basics of Spray (i.e. Akka Http)


### To be discussed (DRAFT)

This list of topics can be as long as people want.
Please send a PR or open an issue if you want to add topics

- Add a basic Spray REST-api for getting and putting keys and values
- Testing Spray routes
- ways of connection spray routes with actors
    - Futures vs Single-use actors
- Advanced Spray features
    - Advanced routing DSL
    - Modularizing your routes using traits
    - Spray, Akka and the Cake pattern
    - Writing (de)serializers using type classes
    - Custom directives
    - ...
- Add basic actor initialization using become()
- Talking to external systems
    - interacting with blocking libraries
    - using spray-client
    - wrapping database drivers in an Akka extension
    - ...
- Actor crash handling / fault tolerance
    - Supervision, restarting, durability of state
    - Akka persistence
- Going multi-node with akka cluster
- Dynamo-style distributed replication using Akka cluster and events
- ...
- Profit
