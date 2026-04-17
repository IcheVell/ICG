package controllers.actions;

import enums.Command;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.EnumMap;
import java.util.Map;

public class ActionFactory {
    private final Map<Command, Action> actionMap = new EnumMap<>(Command.class);

    public Action getAction(Command command) {
        return actionMap.get(command);
    }

    public void addAction(Command command, Action action) {
        actionMap.put(command, action);
    }

    public static Action action(String name, Runnable runnable) {
        return new AbstractAction(name) {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runnable.run();
            }
        };
    }
}
