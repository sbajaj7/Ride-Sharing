# Ride-Sharing
This repository contains implementation of our ride sharing algorithm, which is based on the city of the New York database.

<p>The goal is to provide a ride matching algorithm which is more efficient and provides the most dynamic way to match ride requests and ride offers in real time without pre-defining a meeting point in addition to their departure and destination locations.</p>

<p>We aim to minimize the total ridesharing travelling time of passengers, the total travel distance and travel time for vehicles.</p>

<p>We have following assumptions for our model:
No passenger will walk.
We will consider the distances along the road network using Graph hopper API.
We’ll consider the maximum capacity of a vehicle as 4.
We are not considering the traffic conditions.
The maximum waiting time of each trip is 5 minutes.
The delay for every passenger is assumed to be 20% of total travel time if ride is individual.
</p>
