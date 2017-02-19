package baconGame;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;

public class KevinBaconGame {

    private HashMap<String,String[]> actor2Movies;
    private HashMap<String, ArrayList<String>> movie2Actors;
    HashMap<String, ActorInfo> actor2BaconNumber;

    private final int NUM_ACTORS = 1175000;
    private final int NUM_ROLES = 3930000;
    private final int NUM_MOVIES = 335000;

    /**
     * Intializes both necessary hash maps
     * @param dataFileName path to file as a string
     */
    public KevinBaconGame(String dataFileName){
        initializeActor2Movies(dataFileName);
        initializeMovie2Actors();
    }

    /**
     * Run BFS to find a queryActor (any actor in the list)'s bacon number;
     * when queryActor is found, his/her bacon number is
     * set in their ActorInfo. Also, the currently linking movie is added to
     * the queryActor's baconLinks list in addition to the previous actor's movie
     * links to kevin bacon, giving a shortest path to kevin bacon for the queryActor.
     * @param queryActors list of actors
     * @return
     */
    public void getBaconNumbers(String[] queryActors){

        HashSet<String> actorList = new HashSet<>(queryActors.length);
        actor2BaconNumber = new HashMap<>(NUM_ACTORS);
        Queue<String> baconAssignedActors = new ArrayDeque<>(NUM_ACTORS);
        HashSet<String> processedMovies = new HashSet<>(NUM_MOVIES);

        for(String actor : queryActors)
            actorList.add(actor);

        actor2BaconNumber.put("Bacon, Kevin", new ActorInfo(0, new LinkedList<>()));
        baconAssignedActors.add("Bacon, Kevin");
        String currentRootActor;

        //while there are still actors whose immediate co-actors
        //have not all been processed...
        while (!baconAssignedActors.isEmpty()) {
            currentRootActor = baconAssignedActors.remove();

            for (String movie : actor2Movies.get(currentRootActor)) {

                if (!processedMovies.contains(movie)) {
                    //process the yet unprocessed movie
                    for (String actor : movie2Actors.get(movie)) {

                        if (actor2BaconNumber.containsKey(actor)) { }
                        //assign bacon numbers to yet unseen actors
                        else {
                            actor2BaconNumber.put(actor, new ActorInfo(
                                    actor2BaconNumber.get(currentRootActor).getBN() + 1, new LinkedList<>()));
                            setLinks(currentRootActor, actor, movie);
                            baconAssignedActors.add(actor);
                            actorList.remove(actor);

                            if (actorList.isEmpty())
                                return;

                        }
                    }
                    processedMovies.add(movie);
                }
            }
        }
    }

    /**
     * print an actor's links to kevin bacon
     * @param queryActor
     * @return
     */
    public String printLinks(String queryActor){
        String result = "";
        if(!actor2BaconNumber.containsKey(queryActor)){
            result += queryActor + " is not in the database \n";
        }
        else{
            Iterator it = actor2BaconNumber.get(queryActor).getLinks().iterator();
            while (it.hasNext())
                result += it.next() + "\n";
        }
        return result;
    }


    /**
     * Class used to hold an actor's bacon number and movie links to kevin bacon
     */
    private class ActorInfo{
        private int baconNumber;
        private LinkedList<String> baconLinks;

        private ActorInfo(int BN, LinkedList<String> links){
            baconNumber = BN;
            baconLinks = links;
        }

        private int getBN(){
            return baconNumber;
        }

        private LinkedList<String> getLinks(){
            return baconLinks;
        }
    }

    /**
     * set the queryActor's path (from current place in BFS) to kevin bacon
     * @param parentActor the node previous to queryActor
     * @param childActor queryActor
     * @param movie the movie linking queryActor to their ancestor actor
     */
    private void setLinks(String parentActor, String childActor, String movie){
        LinkedList<String> childLinks = actor2BaconNumber.get(childActor).getLinks();
        childLinks.add(movie + " with " + parentActor);
        LinkedList<String> parentLinks = actor2BaconNumber.get(parentActor).getLinks();
        Iterator it = parentLinks.iterator();
        while(it.hasNext())
            childLinks.add((String) it.next());
    }

    private void initializeActor2Movies(String dataFileName){
        try {
            BufferedInputStream b = new BufferedInputStream(new FileInputStream(dataFileName));
            ObjectInputStream in = new ObjectInputStream(b);
            actor2Movies = (HashMap<String, String[]>) in.readObject();
        }
        catch(Exception e){
            System.out.println("File not found, please try a different file path");
        }
    }
    private void initializeMovie2Actors(){
        movie2Actors = new HashMap<>(332000);
        for(String actor : actor2Movies.keySet()){
            for(String movie : actor2Movies.get(actor)){
                if(!movie2Actors.containsKey(movie))
                    movie2Actors.put(movie, new ArrayList<>(2 * NUM_ROLES / NUM_MOVIES));
                movie2Actors.get(movie).add(actor);
            }
        }
        trimLists(movie2Actors);
    }
    private void trimLists(HashMap<String, ArrayList<String>> map){
        Iterator it = map.values().iterator();
        while(it.hasNext()){
            ((ArrayList<String>)it.next()).trimToSize();
        }
    }
}
