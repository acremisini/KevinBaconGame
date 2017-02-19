import baconGame.KevinBaconGame;

import java.io.IOException;

public class BaconTester {

    public BaconTester() {
    }

    public static void main(String[] args) throws IOException {
        long start_time = System.currentTimeMillis();
        KevinBaconGame KBG = new KevinBaconGame("bacon.ser");
        System.out.println("Finished reading data file. Processing queries ...");
        String[] actors = {"iAmNotAnActor", "Barkin, Ellen", "Forbes, Gail", "Weidenheft, John", "Shepard, Joan",
                            "McCabe, Elias", "Abrams, Katharine", "Helfer, Tricia", "Hedley, Hugo",
                            "Versace, Gianni", "Nakasone, Rino", "Enright, Brock", "Harvey, Don (I)",
                            "Metelmann, Henry", "Allan, Patti", "Reece, Deborah"};
        KBG.getBaconNumbers(actors);
        System.out.println();
        for(String a : actors)
            System.out.println(a + "\n" + KBG.printLinks(a));
        Runtime rt = Runtime.getRuntime();
        int vmavail = (int)rt.totalMemory();
        int vmfree = (int)rt.freeMemory();
        int vmsize = vmavail - vmfree;
        System.out.println("Virtual machine available: " + vmavail + " Bytes");
        System.out.println("Virtual machine size: " + vmsize + " Bytes");
        long end_time = System.currentTimeMillis();
        System.out.println("Execution time = " + (end_time - start_time) + " milliseconds");
    }
}
