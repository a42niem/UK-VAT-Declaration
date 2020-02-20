package uk.gov.hmrc.model;

public class VATObligation {

    private String due;

    private String start;

    private String end;

    private String received;

    private String periodKey;

    private String status;

    public String getDue ()
    {
        return due;
    }

    public void setDue (String due)
    {
        this.due = due;
    }

    public String getStart ()
    {
        return start;
    }

    public void setStart (String start)
    {
        this.start = start;
    }

    public String getEnd ()
    {
        return end;
    }

    public void setEnd (String end)
    {
        this.end = end;
    }

    public String getReceived ()
    {
        return received;
    }

    public void setReceived (String received)
    {
        this.received = received;
    }

    public String getPeriodKey ()
    {
        return periodKey;
    }

    public void setPeriodKey (String periodKey)
    {
        this.periodKey = periodKey;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [due = "+due+", start = "+start+", end = "+end+", received = "+received+", periodKey = "+periodKey+", status = "+status+"]";
    }
}
