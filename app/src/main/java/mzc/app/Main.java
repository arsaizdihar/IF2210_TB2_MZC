package mzc.app;

import javafx.application.Application;
import mzc.app.adapter.Datastore;
import mzc.app.bootstrap.App;
import mzc.app.bootstrap.AppManager;
import mzc.app.bootstrap.GUIApp;
import mzc.app.modules.task.CleanRedundantCustomer;
import mzc.app.modules.task.Seed;
import mzc.app.utils.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        App app = AppManager.get();
        app.init();
        app.postInit();

        List<Runnable> launchTask = new ArrayList<>();

        for (var arg : args) {
            if (arg.startsWith("--seed")) {
                var split = arg.split("=");

                if (split.length < 2) {
                    throw new RuntimeException("Invalid command. Format: --seed=\"filepath\"");
                }

                launchTask.add(new Seed(Datastore.getManager().getAdapter(), split[1]));
            }
        }

        launchTask.add(new CleanRedundantCustomer(Datastore.getManager().getAdapter()));

        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        launchTask.forEach(threadPool::submit);
        threadPool.shutdown();

        Application.launch(GUIApp.class);

        FileManager.getImageLoaderExecutor().shutdown();
    }
}