# Ride-Sharing
This repository contains implementation of our ride sharing algorithm, which is based on the city of the New York database.

<p>The goal is to provide a ride matching algorithm which is more efficient and provides the most dynamic way to match ride requests and ride offers in real time without pre-defining a meeting point in addition to their departure and destination locations.</p>

<p>We aim to minimize the total ridesharing travelling time of passengers, the total travel distance and travel time for vehicles.</p>

<p>We have following assumptions for our model:
<ul>
<li>No passenger will walk.</li>
<li>We will consider the distances along the road network using Graph hopper API.</li>
<li>We’ll consider the maximum capacity of a vehicle as 4.</li>
<li>We are not considering the traffic conditions.</li>
<li>The maximum waiting time of each trip is 5 minutes.</li>
<li>The delay for every passenger is assumed to be 20% of total travel time if ride is individual.</li>
</ul>
</p>

<p>
The problem is solved via four steps:
<ul>
<li>Computing a pairwise request-vehicle shareability graph (RV-graph).</li>
<li>Computing a graph of feasible trips and the vehicles that can serve    them (RTV-graph).</li>
<li>Assignment of vehicles to trips.</li>
<li>We are not considering the traffic conditions.</li>
<li>Rebalancing the remaining idle vehicles.</li>
</ul>
</p>

-------------------------------------------------------------------------------------------------
Steps to Run the Project:

1. Clone the repo in Eclipse or any other IDE as a Maven Project.
2. You might need to install Maven and a git plugin.
3. All jar dependencies will be automatically resolved as they are mentioned in the pom.xml file.
4. To run the algorithm through the UI: Run the MainWindow.java as a Java Application. 
    * This will open a window with the user allowed to enter the travel delay time as well as pool size. 
    * Waiting time is kept as constant and not taken by the user as it causes efficiency issues for the algorithm.
    * Once the user has selected the values, hit the submit button. This will start the algorithm, and once completed, the shareability percentage as well as total requests and shared requests will be displayed.
    * Additionally, a graph is displayed depicting how the shareability changes with respect to the delay constraint.
5. To run the algorithm through the back end program directly: Run the MainClass.java file, and the travel delay and pool size values can be entered as arguments inside the method call in this main method. 

-------------------------------------------------------------------------------------------------
References:

* https://graphhopper.com/api
* https://maps.googleapis.com
* http://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java
* https://www.stackflow.com
* http://www.pnas.org/content/114/3/462.full.pdf 
 
