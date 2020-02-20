package uk.gov.hmrc.model;

public class VATObligationResponse {
	

    /**
	 * Generated json field names
	 *
	 * {"obligations":[{"start":"2017-01-01","end":"2017-03-31","due":"2017-05-07","status":"O","periodKey":"18A1"}]}
	 * {"obligations":[{"start":"2017-01-01","end":"2017-03-31","due":"2017-05-07","status":"F","periodKey":"18A1","received":"2017-05-06"},{"start":"2017-04-01","end":"2017-06-30","due":"2017-08-07","status":"O","periodKey":"18A2"}]}
	 * 
	 */
	static public final class FIELD_NAMES {
		public static final String OBLIGATIONS = "obligations";
	}

	private VATObligation[] obligations;

	public VATObligation[] getObligations ()
	{
		return obligations;
	}

	public void setObligations (VATObligation[] obligations)
	{
		this.obligations = obligations;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [obligations = "+obligations+"]";
	}

}


