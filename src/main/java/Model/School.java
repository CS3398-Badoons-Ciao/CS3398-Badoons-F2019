package Model;

public class School implements java.io.Serializable
{
    private String name;

    public School(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
