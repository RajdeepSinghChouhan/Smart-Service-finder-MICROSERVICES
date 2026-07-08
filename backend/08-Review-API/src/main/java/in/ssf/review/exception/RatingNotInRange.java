package in.ssf.review.exception;

public class RatingNotInRange extends RuntimeException{

	public RatingNotInRange(String msg)
	{
		super(msg);
	}

}
