import java.text.DateFormat;
import java.text.SimpleDateFormat;

public String dateConverter(String date, String dateFormat, String convertedDateFormat) throws ParseException  {
	SimpleDateFormat utcFormatter = new SimpleDateFormat(dateFormat);
	utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
	//String date = utcFormatter.format(new Date()); // uncomment  this line for current Date....
	Date utcTimeInstance = utcFormatter.parse(date);
	SimpleDateFormat indianFormatter = new SimpleDateFormat(convertedDateFormat);
	indianFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
	return indianFormatter.format(utcTimeInstance);
}
