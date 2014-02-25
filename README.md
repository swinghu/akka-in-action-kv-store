Akka In Action - Building a Key-Value Store
============================================


Schedule:
~~~~~~~~~

- DONE: Basic project setup, SBT, etc.
- DONE: Basic KV actor
- DONE: Test it
- DONE: Add events
- DONE: Test those
- Add buckets as children
- Test should still work
- Add basic initialization using become()
- ...
- DONE: Using a Settings extension
- Add Spray REST-api
- ways of connection spray routes with actors
    - Futures vs Single-use actors
- Testing Spray routes
- ...
- Profit


Options for the Future:
~~~~~~~~~~~~~~~~~~~~~~~

- Actor crash handling
    - readding back data from another actor
    - Akka persistence
- talking to external systems
- Writing (de)serializers using type classes
