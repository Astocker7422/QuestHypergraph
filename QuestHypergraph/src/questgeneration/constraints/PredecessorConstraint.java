package questgeneration.constraints;

import java.util.ArrayList;

import questgeneration.Action;

public class PredecessorConstraint implements Constraint
{
    private ArrayList<Action> _actions;
    private Action _theAction;
    
    public PredecessorConstraint(ArrayList<Action> preActions, Action theAction)
    {
        _actions = preActions;
        _theAction = theAction;
    }
}
