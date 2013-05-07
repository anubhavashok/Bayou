Bayou
=====

Anti-Entropy protocol implemented in shared playlist storage

===========================================================
Glossary
==========================================================

Client : Interface in which updates to playlist are made
Replica : External Server where replicated copies of the playlist are stored
Primary Replica: External server which officially performs writes to database (called committing)
Anti-Entropy: Pairwise updating of Replicas using a gossip protocol
Version Vector: Vector that stores accept numbers of each replica
Accept Number: Order in which write was accepted by the replica.
Commit Sequence Number (CSN): Incremental number that keeps track of committed writes.
CommittedLog: Log of committed writes.
WriteLog: Log of tentative writes.

=============================================================

Basic Outline and function of Program
==================================================================

When theres no constant connectivity to a main database of some sort, a good way to store and and updates 
entries is to use an Anti-Entropy protocol, in this case Bayou.

A write is made via a client to a single replica. This write is not written to the database yet but is stored in the write log.
To prevent long waiting times, the writeLog is propagated instead of the entire database.

Each replica shares its writes to another database a pair at a time.
Eventually, all the Replicas will have all the writes. This is the basic outline of a gossip protocol.

All the writes will eventually come across the Primary, which orders writes and makes sure there are no discrepancies.
Eventually, when the primary communicates with a replica, the replica will know which writes to commit.

In this way, consistent storage will be achieved amongst all the replicas.
