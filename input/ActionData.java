package input;

import java.util.ArrayList;

public class ActionData
{
    
    public ActionData(ActionData a)
    {
        this.noOfStates = a.getNoOfStates();
        this.probDist = a.getProbDist();
        this.reward = a.getReward();
        ArrayList<Double> list = new ArrayList<Double>();
        ArrayList<Double> oldList = a.getTransitionFunc();
        for(int i=0; i<oldList.size(); i++)
        {
            list.add(i, oldList.get(i));
        }
        this.transitionFunc = list;
        this.valueContri = a.getValueContri();
    }
    
    public ActionData(int noOfStates)
    {
        this.noOfStates = noOfStates;
    }
    
    public ActionData(int noOfStates, double probDist, double reward, ArrayList<Double> transitionFunc)
    {
        this.noOfStates = noOfStates;
        this.probDist = probDist;
        this.reward = reward;
        this.transitionFunc = transitionFunc;
    }
    
    protected final int noOfStates;
    protected double probDist;
    protected double reward;
    protected ArrayList<Double> transitionFunc;
    protected double valueContri;
    
    
    public void calculateValueContribution(ArrayList<StateData> stateList, double lambda)
    {
        calculateValueContribution(stateList, lambda, false);
    }
    
    public void calculateValueContribution(ArrayList<StateData> stateList, double gamma, boolean shouldIncludeReward)
    {
        int noOfStates = stateList.size();
        double contri = 0.0;
        for(int i=0; i<noOfStates; i++)
        {
            contri = contri + transitionFunc.get(i)*stateList.get(i).getValue();
        }
        contri = contri * gamma; 
        if(shouldIncludeReward)
        {
            contri = contri + reward;
        }
        contri = contri * probDist;
        setValueContri(contri);
    }
    
    public void calculateNewProbDistAfterTransformation(ArrayList<StateData> stateList, double lambda)
    {
        int noOfStates = stateList.size();
        double contri = 0.0;
        for(int i=0; i<noOfStates; i++)
        {
            contri = contri + transitionFunc.get(i)*stateList.get(i).getValue();
        }
        contri = contri * lambda;
        contri = contri + reward;
        //contri = contri * probDist;
        double prob = getValueContri()/contri;
        setProbDist(prob);                
    }
    
    public double getProbDist()
    {
        return probDist;
    }

    public void setProbDist(double probDist)
    {
        /*
        if( (probDist > 1.0) || (probDist < 0.0) )
        {
            throw new IncorrectProbabilityException(probDist);
        }
        */
        this.probDist = probDist;
    }

    public double getReward()
    {
        return reward;
    }

    public void setReward(double reward)
    {
        this.reward = reward;
    }

    public ArrayList<Double> getTransitionFunc()
    {
        return transitionFunc;
    }

    public void setTransitionFunc(ArrayList<Double> transitionFunc)
    {
        this.transitionFunc = transitionFunc;
    }

    public double getValueContri()
    {
        return valueContri;
    }

    public void setValueContri(double valueContri)
    {
        this.valueContri = valueContri;
    }

    public int getNoOfStates()
    {
        return noOfStates;
    }    
}
