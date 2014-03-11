# Akka In Action - Building a Key-Value Store

## Draft Schedule:

- **DONE**: Basic project setup, SBT, etc.
- **DONE**: Basic KV actor
- **DONE**: Test it
- **DONE**: Add events
- **DONE**: Test those
- **DONE**: Using a Settings extension

- Add buckets as children
- Test should still work
- Add Spray REST-api
- Testing Spray routes
- ways of connection spray routes with actors
    - Futures vs Single-use actors
- Advanced Spray features
    - modularizing your routes using traits


- Add basic actor initialization using become()
- Actor crash handling
    - readding back data from another actor
    - Akka persistence
- Talking to external systems
- Writing (de)serializers using type classes
- Spray, Akka and the Cake pattern
- Going multi-node with akka cluster
    - why not to use a single master
- Dynamo-style replication using distributed events
- ...
- Profit
