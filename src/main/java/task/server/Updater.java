package task.server;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.TimerTask;

public class Updater extends TimerTask {

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper();
    }
}
