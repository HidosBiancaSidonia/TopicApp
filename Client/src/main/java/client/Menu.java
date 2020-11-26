package client;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Menu {
    private final String name;
    private final LinkedHashMap<String, Runnable> actionsMap = new LinkedHashMap<>();

    /**
     * Constructor
     */
    public Menu(String name) {
        this.name = name;
    }

    /**
     * Function that puts an action into the LinkedHashMap
     */
    void putAction(String name, Runnable action) {
        actionsMap.put(name, action);
    }

    /**
     * Function that generates name of the menu and it's choices
     */
    String generateText() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" \n");
        List<String> actionNames = new ArrayList<>(actionsMap.keySet());
        for (int i = 0; i < actionNames.size(); i++) {
            sb.append(String.format(" %d. %s%n", i + 1, actionNames.get(i)));
        }
        return sb.toString();
    }

    /**
     * Function that execute the action choice if it's correct
     */
    void executeAction(int actionNumber) {
        int effectiveActionNumber = actionNumber - 1;
        if (effectiveActionNumber < 0 || effectiveActionNumber >= actionsMap.size()) {
            System.out.println("Ignoring menu choice: " + actionNumber);
        } else {
            List<Runnable> actions = new ArrayList<>(actionsMap.values());
            actions.get(effectiveActionNumber).run();
        }
    }
}
