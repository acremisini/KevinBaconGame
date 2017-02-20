# KevinBaconGame

Use bacon.ser to run the code. 

Overview:

This is essentially an implementation of breath first search with the goal of replicating the "bacon game",
which consists of finding a given actor's degree of separation to Kevin Bacon (called the
actor's "bacon number"). A bacon number is defined as a shortest path from a given actor to
Kevin Bacon (the edges representing movies, actors representing nodes).
An actor has a bacon number of 1 if they have acted in a movie with
Kevin Bacon, a bacon number of 2 if they have never acted with Kevin Bacon but have
appeared in a movie with an actor who has directly acted with Kevin Bacon. Higher
bacon numbers are defined in the same manner; 3 if the actor has worked with an actor
who has worked with an actor who has worked with Kevin Bacon, etc.
Kevin Bacon's bacon number is 0.

Details:

As you can see in main, the program is initialized with a file called "bacon.ser", which is a
serialized hash map containing around 1,171,600 actors/actresses playing 3,925,000 roles spread
out over 331,500 movies. This input hash map is in the form of <<String, String[]>>, where each row
gives an actor's name and a list of the movies they've appeared in. The BFS happens in the method
getBaconNumbers. Here, I am allowing the user to pass in an array of any number of actors
and processing them all at once in order to run a single iteration of BFS instead of one for
every query. If I were to implement this online I'd keep a database of all the previously
queried actors and their numbers and only run BFS if I don't already know the actor's bacon number.
In addition to returning the queried actor's bacon number I am giving the "path" from the actor
back to Kevin Bacon (ie. the movies that connect the actor to Kevin Bacon). Note that if there
are multiple paths of the same length from an actor to Kevin Bacon the program outputs any valid
list of movies (so if an actor has acted in 2 movies with Kevin Bacon any one of them will be
output as a link).

I am using Hash Tables extensively in order to take advantage of the fast search, and since
I know the size of my input beforehand setting each of them to an approximate size to avoid
rehashing while populating the tables. The searches should all be relatively close to O(1),
given that any given actor will likely not have acted in number of movies so large as to make
linear searching slow (in the worst case that Java implements the rows in HashMap as lists,
which is probably not the case).

