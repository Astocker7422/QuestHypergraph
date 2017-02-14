package questgeneration.constraints;

import java.util.ArrayList;

import questgeneration.Action;

public class ConcurrencyConstraint extends Constraint
{
    private ArrayList<Action> _actions;
    private Action _theAction;
    
    public ConcurrencyConstraint(ArrayList<Action> concurrentActions, Action theAction)
    {
        _actions = concurrentActions;
        _theAction = theAction;
    }
}
