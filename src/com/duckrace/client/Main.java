package com.duckrace.client;

import com.duckrace.DuckRacer;
import com.duckrace.app.DuckRaceApp;

class Main {
    public static void main(String[] args) {
        // Instantiate the controllers and makes it go.
        DuckRaceApp app = new DuckRaceApp();
        app.execute();
    }

}