# Akka & Spray In Action - A Series of Live Coding Sessions

This repository contains a work in progress [Akka](http://akka.io/) /
[Spray](http://spray.io/) workshop currently being given inside Xebia as a
series of live-coding sessions. The idea is to test this stuff on our own
people, followed by turning it into an open and/or commercial training.

The subject and end goal for this series is to build a simplistic Key-Value
Store loosely based on [Riak](http://basho.com/riak/). The resulting KV store
will **not** be in any way safe for production usage and it is quite likely it
will loose all your data, but that's beside the point. It is eventually
complete, with "eventually" slowly but surely approaching "never".


## Schedule:

### Session 1 (2014-02-25)

- Basic project setup, SBT, etc.
- Basic KV actor
- Test it
- Add events
- Test those
- Using a Settings extension


### Session 2 (2014-03-11)

- Add support for KV buckets using child actors
- Adjust the tests
- The basics of Spray (i.e. Akka Http)


### Plan for next session (2014-04-08)

- Testing Spray routes
- Add a basic Spray REST-api for getting and putting keys and values
    - Matching paths and prefixes
    - Extracting path elements
- ways of connection spray routes with actors
    - Creating actors from Spray
    - Futures vs single-use actors


### Upcoming Topics

This list of topics can be as long as people want.
Please send a PR or open an issue if you want to add topics

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
