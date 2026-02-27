package controller.actions;

import enums.Command;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.EnumMap;
import java.util.Map;

public class ActionFactory {
    private final Map<Command, Action> actions = new EnumMap<Command, Action>(Command.class);

    public Action getAction(Command command) {
        return actions.get(command);
    }

    public void addAction(Command command, Action action) {
        actions.put(command, action);
    }

    public static Action action(String name, Runnable r) {
        return new AbstractAction(name) {
            public void actionPerformed(ActionEvent e) {
                r.run();
            }
        };
    }
}
