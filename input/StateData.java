package input;

import java.util.ArrayList;

public class StateData
{
    protected int noOfActions;
    protected ArrayList<ActionData> actionList;
    protected double value;
    
    public StateData()
    {
        actionList = new ArrayList<ActionData>();
    }
    
    public StateData(StateData s)
    {
        this.noOfActions = s.getNoOfActions();
        this.value = s.getValue();
        this.actionList = new ArrayList<ActionData>();
        ArrayList<ActionData> tempList = s.getActionList();
        for(int i=0; i<noOfActions; i++)
        {
            actionList.add(new ActionData(tempList.get(i)));
        }
    }
    
    public void appendActionList(ActionData val)
    {
        this.actionList.add(val);
    }
    
    public int getNoOfActions()
    {
        return noOfActions;
    }
    public void setNoOfActions(int noOfActions)
    {
        this.noOfActions = noOfActions;
    }
    public ArrayList<ActionData> getActionList()
    {
        return actionList;
    }
    public void setActionList(ArrayList<ActionData> actionList)
    {
        this.actionList = actionList;
    }
    public double getValue()
    {
        return value;
    }
    public void setValue(double value)
    {
        this.value = value;
    }

    public void normalizeProb()
    {
        double sum = 0.0;
        for(int i=0; i<noOfActions; i++)
        {
            sum = sum + actionList.get(i).getProbDist();                                    
        }
        ActionData action;
        for(int i=0; i<noOfActions; i++)
        {
            action = actionList.get(i);
            action.setProbDist(action.getProbDist()/sum);
            actionList.set(i, action);
        }
    }
}
