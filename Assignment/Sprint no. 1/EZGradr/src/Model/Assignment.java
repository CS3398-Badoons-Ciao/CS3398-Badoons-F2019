package Model;

public class Assignment
{
    private String name;
    private float potentialGrade;
    private float currentGrade;

    public Assignment(String name, float potentialGrade, float currentGrade)
    {
        this.name = name;
        this.potentialGrade = potentialGrade;
        this.currentGrade = currentGrade;
    }

    public Assignment(String name, float currentGrade)
    {
        this.name = name;
        this.potentialGrade = currentGrade;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getPotentialGrade()
    {
        return potentialGrade;
    }

    public void setPotentialGrade(float potentialGrade)
    {
        this.potentialGrade = potentialGrade;
    }

    public float getCurrentGrade()
    {
        return currentGrade;
    }

    public void setCurrentGrade(float currentGrade)
    {
        this.currentGrade = currentGrade;
    }
}
