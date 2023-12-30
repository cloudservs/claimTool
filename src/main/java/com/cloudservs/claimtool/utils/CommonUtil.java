package com.cloudservs.claimtool.utils;

import com.cloudservs.claimtool.domain.claim.Reference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.bson.types.ObjectId;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;


import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CommonUtil {
	Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	@Autowired
	private MappingJackson2HttpMessageConverter jsonMessageConverter;

	@Autowired
	Gson gson;

	/**
	 * returns true of the string is a valid number including +/-/. e.g. 123,
	 * 123.02, -123
	 *
	 * @param s
	 * @return
	 */
	public static boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}
	public Date getDateFromJsonObject(JSONObject jsonObject, String field) {
		Date date=null;
		try {
			String value = jsonObject.getString(field);
			String pickedFormat = "EEE, dd MMM yyyy HH:mm:ss zzz";
			if(value.endsWith("+00:00")){
				pickedFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
			}else if(value.endsWith("T18:30:00.000Z")){
				pickedFormat =  "yyyy-MM-dd'T'HH:mm:ss.SSX";
			}else if (value.indexOf("-") == 4) {
				pickedFormat =  "yyyy-MM-dd'T'HH:mm:ss.SS";
			}
			date = changeStringDateToSpecificFormat(jsonObject.getString(field),pickedFormat );
		}catch (Exception e){

		}
		return date;
	}
	public Date addCurrentTimeInDate(Date date){
		Calendar calendar = Calendar.getInstance();
		//Date currentDate = convertToUTCTZ(calendar.getTime());
		Date currentDate = calendar.getTime();
		int hr = currentDate.getHours();
		int mm = currentDate.getMinutes();
		int sec = currentDate.getSeconds();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,hr);
		calendar.set(Calendar.MINUTE,mm);
		calendar.set(Calendar.SECOND,sec);
		return calendar.getTime();
	}


	public static String getFinancialYear(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy");
		String year = df.format(date);
		df = new SimpleDateFormat("MM");
		int month = Integer.parseInt(df.format(date));
		if (month <= 3)
			return String.valueOf(Integer.parseInt(year) - 1);
		return year;
	}
	public static String getWeekDay(Date date){
		DateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
		return (simpleDateformat.format(date));
	}
	public static String getDay(Date date){
		DateFormat simpleDateformat = new SimpleDateFormat("dd"); // the day of the week spelled out completely
		return (simpleDateformat.format(date));
	}
	public static String getMonthName(Date date){
		DateFormat simpleDateformat = new SimpleDateFormat("MMMM"); // the day of the week spelled out completely
		return (simpleDateformat.format(date));
	}
	public static String getMonth(Date date) {
		DateFormat df = new SimpleDateFormat("MM");
		String month = df.format(date);
		return month;
	}

	public static String getContentType(String fileType) {
		String contentType = "application/pdf";
		fileType = fileType.toUpperCase();
		switch (fileType) {
			case "PDF":
				contentType = "application/pdf";
				break;

			case "CSV":
				contentType = "text/comma-separated-values";
				break;

			case "GIF":
				contentType = "image/gif";
				break;

			case "HTM":
			case "HTML":
				contentType = "text/html";
				break;

			case "JPEG":
			case "JPG":
			case "JPE":
				contentType = "image/jpeg";
				break;

			case "TXT":
				contentType = "text/plain";
				break;

			case "XLS":
				contentType = "application/vnd.ms-excel";
				break;
            case "XLSX":
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                break;
			case "ZIP":
				contentType = "application/zip";
				break;

		}

		return contentType;
	}


	public static String getFinancialDay(Date date) {
		return getYYMMDD(date);
	}

	/**
	 * converts String to date
	 *
	 * @param strDate
	 * @returnN
	 */
	public static final Date convertStringToDate(String strDate) {
		DateTimeFormatter df = DateTimeFormat.forPattern("dd/MM/yyyy");
		long millis = df.parseMillis(strDate);
		return new Date(millis);
	}
	public static final Date convertExpiryMMYYToDate(String strDate) {
		DateTimeFormatter df = DateTimeFormat.forPattern("MM/yy");
		long millis = df.parseMillis(strDate);
		return new Date(millis);
	}
	public static final Date convertJsonStringToDate(String date) throws ParseException {
		DateFormat format = new SimpleDateFormat("MMM dd, yyyy, HH:mm:ss a" );
		return format.parse(date);
	}
	public static final Date convertStringToDateUsingSimpleFormat(String strDate) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}
	public static final Date getDayBeforeDate(Date date){
		Date d = setDateToMidnightDay(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	public static final Date getDateOfMonth(int day){
		Date d = setDateToMidnightDay(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	/**
	 * converts String to dateTime format
	 *
	 * @param strDate
	 * @return
	 */
	public static final Date convertStringToDateTime(String strDate) {
		DateTimeFormatter df = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss.SS");
		long millis = df.parseMillis(strDate);
		return new Date(millis);
	}

	/**
	 * gets today's midnight date in DD/MM/YYYY format
	 *
	 * @return
	 */
	public static final String getTodayMidnightInddmmyyyy() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
		String strDate = sdf.format(setDateToMidnight(cal.getTime()));

		return strDate;
	}

	public static final Date getFinancialYear1stDay(Date aDate) {
		Calendar date = new GregorianCalendar();
		date.setTime(aDate);
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		date.set(Calendar.DAY_OF_MONTH, 1);
		date.set(Calendar.MONTH, 3);
		date.set(Calendar.YEAR, date.YEAR);
		return date.getTime();
	}

	/**
	 * resets the date to midnight time
	 *
	 * @param aDate
	 * @return
	 */
	public static final Date setDateToMidnight(Date aDate) {
		Calendar date = new GregorianCalendar();
		date.setTime(aDate);
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	}

	public static final Date setDateToEndOfDay(Date aDate) {
		Calendar date = new GregorianCalendar();
		date.setTime(aDate);
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);
		date.set(Calendar.MILLISECOND, 999);

		return date.getTime();
	}

	/**
	 * resets the date to midnight time of next day
	 *
	 * @param aDate
	 * @return
	 */
	public static final Date setDateToMidnightNextDay(Date aDate) {
		Calendar date = new GregorianCalendar();
		date.setTime(aDate);
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		// next day
		date.add(Calendar.DAY_OF_MONTH, 1);

		return date.getTime();
	}

	public static final Date setDateToMidnightDay(Date aDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse(sdf.format(aDate));
		} catch (ParseException e) {
			return null;
		}
	}
	public static final Date addHourToDate(Date aDate, int hour) {
		Calendar date = new GregorianCalendar();
		date.setTime(aDate);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		date.add(Calendar.HOUR, hour);
		return date.getTime();
	}
	public static final Date GetDateAfterDays(Date aDate, int n) {
		Calendar date = new GregorianCalendar();
		date.setTime(aDate);

		// after n days
		date.add(Calendar.DAY_OF_MONTH, n);

		return date.getTime();
	}

	public static Date addOneMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}// Added by MS

	/**
	 * sets the date to a specific time for the provided date
	 *
	 * @param aDate
	 * @return
	 */
	public static final Date setDateToCustomTime(Date aDate, int day, int hour, int min) {
		Calendar date = new GregorianCalendar();
		date.setTime(aDate);
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, hour);
		date.set(Calendar.MINUTE, min);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);

		date.add(Calendar.DAY_OF_MONTH, day);

		return date.getTime();
	}

	public static Date convertDefaultFormatStringToDate(String strDate) throws ParseException {
		SimpleDateFormat sdm = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy");
		return sdm.parse(strDate);
	}
	public static String convertDateToTime(Date date, String format){
		DateFormat tf = new SimpleDateFormat(format);
		String time = tf.format(date);
		return time;
	}
	public static String getYYMMDD(Date date) {
		DateFormat df = new SimpleDateFormat("yyMMdd");
		String reportDate = df.format(date);
		return reportDate;
	}
	public static String getMMDD(Date date) {
		DateFormat df = new SimpleDateFormat("MMdd");
		String reportDate = df.format(date);
		return reportDate;
	}
	public static String convertDateToString(Date date) {
		DateFormat df = new SimpleDateFormat("dd-MMM-yy");
		String reportDate = df.format(date);
		return reportDate;
	}
	public static String convertDateToStringInDDMMMYYYY(Date date) {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String reportDate = df.format(date);
		return reportDate;
	}
	public static String convertDateToHHMM(Date date) {
		DateFormat df = new SimpleDateFormat("HH:mm");
		String reportDate = df.format(date);
		return reportDate;
	}
	public static String convertDateToDateTimeHHMMss(Date date) {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		String reportDate = df.format(date);
		return reportDate;
	}

	public static String convertLocalDateToStringInDDMMMYYYY(LocalDate date) {
		String month = "00" + date.getMonthValue();
		month = month.substring( month.length()-2 );
		return date.getDayOfMonth()+"-"+month+"-"+date.getYear();
	}

	public static String getItemExpiryDateMMYY(Date date) {
		try {
			DateFormat df = new SimpleDateFormat("MM/yy");
			String expiryDate = df.format(date);
			return expiryDate;
		} catch (Exception e) {
			return null;

		}
	}
	public static boolean isDateEOM() {
		Calendar cal = Calendar.getInstance();
		if (String.valueOf(LocalDate.now().getDayOfMonth()).equals(String.valueOf(cal.getActualMaximum(Calendar.DATE)))) {
			return true;
		}
		return false;
	}

	public static boolean compareDates(Date date1, Date date2, String check) throws ParseException {
		SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = sdfo.parse(CommonUtil.getDateInyyyymmdd(date1));
		Date d2 = sdfo.parse(CommonUtil.getDateInyyyymmdd(date2));
		switch (check) {
			case "equals":
				if (d1.compareTo(d2) == 0) {
					return true;
				}
				break;
			case "greaterThan":
				if (d1.compareTo(d2) > 0) {
					return true;
				}
				break;
			case "lessThan":
				if (d1.compareTo(d2) < 0) {
					return true;
				}
				break;
			default:
				if (d1.compareTo(d2) == 0) {
					return true;
				}
				break;
		}
		return false;
	}

	// While Saving use this
	public static Date convertToUTCTZ(Date date) {
		TimeZone toTimezone = TimeZone.getTimeZone("UTC");
		long fromOffset = TimeZone.getDefault().getOffset(date.getTime());// get offset it will include daylight saving
		// offset
		long toOffset = toTimezone.getOffset(date.getTime());// get offset it will include daylight saving offset

		long convertedTime = date.getTime() + (fromOffset - toOffset);
		date = new Date(convertedTime);
		return date;
	}

	// while retrieving use this
	/*
	 * public static Date convertFromUTCTZ(Date date){ TimeZone toTimezone =
	 * TimeZone.getTimeZone("UTC"); long fromOffset =
	 * TimeZone.getDefault().getOffset(date.getTime());//get offset it will include
	 * daylight saving offset long toOffset =
	 * toTimezone.getOffset(date.getTime());//get offset it will include daylight
	 * saving offset
	 *
	 * long convertedTime = date.getTime() - (fromOffset - toOffset); date = new
	 * Date(convertedTime); return date; }
	 */
	public static Date truncateTime(Date value) {
		Date ret = null;
		if (value != null) {
			Calendar cal = internalTruncateTime(value);
			ret = new Date(cal.getTimeInMillis());
		}
		return ret;
	}

	private static Calendar internalTruncateTime(Date value) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(value);

		// set 0 to all of the following fields so that we can just return the date
		// portion of it
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.AM_PM, Calendar.AM);
		return cal;
	}

	public static Date endOfDay(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * Encodes the byte array into base64 string
	 *
	 * @param imageByteArray
	 *            - byte array
	 * @return String a {@link String}
	 */
	public static String encodeImage(byte[] imageByteArray) {
		return Base64.getEncoder().encodeToString(imageByteArray);
	}

	/**
	 * Encodes the byte array into base64 string
	 *
	 * @param imageByteArray
	 *            - byte array
	 * @return String a {@link String}
	 */
	public static String encodeImage(byte[] imageByteArray, String fileType) {
		return "data:" + fileType + ";base64," + Base64.getEncoder().encodeToString(imageByteArray);
	}

	/**
	 * Decodes the base64 string into byte array
	 *
	 * @param imageDataString
	 *            - a {@link String}
	 * @return byte array
	 */
	public static byte[] decodeImage(String imageDataString) {
		return Base64.getDecoder().decode(imageDataString);
	}

	/**
	 * Serilize the Object String
	 *
	 * @param o
	 * @return
	 */
	public static String serializeObject(Object o) {
		Gson gson = new Gson();
		String serializedObject = gson.toJson(o);
		return serializedObject;
	}

	/**
	 * de-serilize
	 *
	 * @param s
	 * @param o
	 * @return
	 */
	public static Object unserializeObject(String s, Object o) {
		Gson gson = new Gson();
		Object object = gson.fromJson(s, o.getClass());
		return object;
	}

	/**
	 * clone an object
	 *
	 * @param o
	 * @return
	 */
	public static Object cloneObject(Object o) {
		String s = serializeObject(o);
		Object object = unserializeObject(s, o);
		return object;
	}

	public String getDateTimeIndia() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
		sdf.setTimeZone(tz);
		Date date = new Date();
		return sdf.format(date);
	}





	// Added by Shyam --- adding this as to make the search queries generic across
	// various Collections.
	public static List<String> getCriteriaFields(String columnName) {
		List<String> fieldList = new ArrayList<String>();
		if (columnName.equals("email") || columnName.equals("any")) {
			fieldList.add("emailList");
		}
		if (columnName.equals("contact") || columnName.equals("any")) {
			fieldList.add("contactList.number");
		}
		if (columnName.equals("name") || columnName.equals("any")) {
			fieldList.add("alternateName");
		}
		if (columnName.equals("dl") || columnName.equals("any")) {
			fieldList.add("dlNo");
		}
		if (columnName.equals("refCode") || columnName.equals("any")) {
			fieldList.add("refCode");
		}
		if (columnName.equals("area") || columnName.equals("any")) {
			fieldList.add("addressList.street");
			fieldList.add("addressList.city");
			fieldList.add("addressList.district");
			fieldList.add("addressList.state");
			fieldList.add("addressList.zipCode");
			fieldList.add("addressList.country");
			fieldList.add("addressList.address1");
			fieldList.add("addressList.address2");
			fieldList.add("addressList.address3");

		}
		return fieldList;
	}


	public static String getPerrmissionErrorMsg() {
		return "getPermission Denied";
	}

	public static String getFileExtension(String fileName) {
		if (fileName.lastIndexOf('.') != -1) {
			return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
		}
		return "unknown";
	}


	public static String getDecimalString(String value) {
		if (value != null) {
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(df.format(Double.parseDouble(value)));
		}
		return null;
	}

	public static long getLong(String value) {
		if (value == null || value.isEmpty())
			return 0;
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
		}
		return 0;
	}

	public static Double getDecimalAmount(String value) {
		DecimalFormat df = new DecimalFormat("#.00");
		try {
			return Double.parseDouble(df.format(Double.parseDouble(value)));
		} catch (Exception e) {
		}
		return 0.0;
	}

	public static Double getDecimalAmount(Double value) {
		DecimalFormat df = new DecimalFormat("#.00");
		try {
			return Double.parseDouble(df.format(value));
		} catch (Exception e) {
		}
		return 0.0;
	}
	public static Double getRoundFigure(Double value) {
		DecimalFormat df = new DecimalFormat("#");
		try {
			return Double.parseDouble(df.format(value));
		} catch (Exception e) {
		}
		return 0.0;
	}


	private static String checkNull(String input) {
		if (input == null) {
			return "";
		} else {
			return input.trim();
		}
	}

	public static void updateRateMap(Map<String, Double> rateMap, String key, double value) {
		try {
			rateMap.put(key, getDecimalAmount(rateMap.get(key) != null ? (rateMap.get(key) + value) : value));
		} catch (Exception e) {
		}
	}
	public static void substractRateMap(Map<String, Double> rateMap, String key, double value) {
		try {
			rateMap.put(key, getDecimalAmount(rateMap.get(key) != null ? (rateMap.get(key) - value) : value*-1));
		} catch (Exception e) {
		}
	}
	public static String writeToFile(String filePath, String fileName, List<byte[]> dataList) {
		String fileExtension = null;
		String filePrefix = null;
		int timeStamp = 0;
		if (fileName.lastIndexOf('.') != -1) {
			fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
			filePrefix = fileName.substring(0, fileName.lastIndexOf('.') - 1);
			timeStamp = Calendar.getInstance().get(Calendar.MILLISECOND);
		}
		String newFileName = filePrefix + "_" + timeStamp + "." + fileExtension;
		filePath = filePath + File.separator + newFileName;
		try {

			byte[] result = new byte[dataList.get(0).length + dataList.get(1).length];
			int counter = 0;
			for (byte[] bytes : dataList) {
				for (int i = 0; i < bytes.length; i++) {
					result[counter] = bytes[i];
					counter++;
				}
			}

			/*
			 * System.arraycopy(dataList.get(0), 0, result, 0, dataList.get(0).length);
			 * System.arraycopy(dataList.get(1), 0, result, dataList.get(0).length,
			 * dataList.get(1).length);
			 */
			for (byte[] bytes : dataList) {
				FileOutputStream fos = null;
				fos = new FileOutputStream(filePath, true);
				fos.write(bytes);
				fos.close();
			}
			return filePath;
		} catch (Exception e) {
		}
		return null;
	}

	public static String generateBill(String filePath, String fileName, List<byte[]> files)
			throws IOException {
		String fileExtension = null;
		String filePrefix = null;
	/*	long timeStamp = 0;
		if (fileName.lastIndexOf('.') != -1) {
			fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
			filePrefix = fileName.substring(0, fileName.lastIndexOf('.') - 1);
			timeStamp = new Date().getTime();
		}
		String newFileName = filePrefix + "_" + timeStamp + "." + fileExtension;
		filePath = filePath + File.separator + newFileName;
		Document document = new Document();
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(filePath));
		document.open();
		PdfReader reader;
		int n;
		// loop over the documents you want to concatenate
		for (int i = 0; i < files.size(); i++) {
			reader = new PdfReader(files.get(i));
			// loop over the pages in that document
			n = reader.getNumberOfPages();
			for (int page = 0; page < n;) {
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			copy.freeReader(reader);
			reader.close();
		}
		// step 5
		document.close();
		return filePath;*/
		return null;
	}

	public static String createFile(String fileName, String text) {
		try {
			PrintWriter out = new PrintWriter(fileName);
			out.println(text);
			out.close();
			return fileName;
		} catch (Exception e) {
		}
		return null;
	}

	public static Object getValueFromObject(Object parentObj, String jsonfield) {
		Object childObject = new LinkedHashMap<String, Object>((LinkedHashMap) parentObj);
		if (jsonfield != null) {
			String[] fields = jsonfield.split("\\.");
			if (fields != null && fields.length > 0) {
				for (String field : fields) {
					childObject = getChildObject(childObject, field);
				}
			} else {
				childObject = getChildObject(childObject, jsonfield);
			}
		}
		return childObject;
	}

	public static Object getChildObject(Object parentObject, String child) {
		if (child != null) {
			return ((LinkedHashMap) parentObject).get(child);
		}
		return null;
	}

	public static Object getValueFromJSONObject(JSONObject parentObj, String jsonfield) throws JSONException {
		Object childObject = parentObj;
		if (jsonfield != null) {
			String[] fields = jsonfield.split("\\.");
			if (fields != null && fields.length > 0) {
				for (String field : fields) {
					childObject = getChildJSONObject(childObject, field);
				}
			} else {
				childObject = getChildJSONObject(childObject, jsonfield);
			}
		}
		return childObject;
	}

	public static Object getValueFromJSONObjectFromAnyLevel(JSONObject parentObj, String jsonfield) throws JSONException {	
		try {
			Object childObject = parentObj;
			if (jsonfield != null) {
				String[] fields = jsonfield.split("\\.");
				if (fields != null && fields.length > 0) {
					childObject = getChildJSONObjectFromField(childObject, fields, 0, fields.length - 1);
				/*System.out.println(childJSONObjectFromField);	
				for (String field : fields) {	
					childObject = getChildJSONObject(childObject, field);	
	
				}*/
				} else {
					childObject = getChildJSONObject(childObject, jsonfield);
				}
			}
			return childObject;
		}catch (Exception e){
			e.printStackTrace();
		}
		return "";
	}

	public static Object getChildJSONObject(Object parentObject, String child) throws JSONException {
		if (child != null) {
			if(parentObject instanceof JSONArray){
				return ((JSONArray) parentObject).getJSONObject(Integer.parseInt( child ));
			}else{
				//if(!((JSONObject) parentObject).isNull(childs[endIndex])) {
				if (!((JSONObject) parentObject).isNull(child) && ((JSONObject) parentObject).get(child) instanceof JSONObject) {
					return ((JSONObject) parentObject).getJSONObject(child);
				} else if (!((JSONObject) parentObject).isNull(child) && ((JSONObject) parentObject).get(child) instanceof JSONArray) {
					return ((JSONObject) parentObject).getJSONArray(child);
				} else if(!((JSONObject) parentObject).isNull(child)){
					return ((JSONObject) parentObject).get(child);
				} else{
					return null;
				}
			}
		}
		return null;
	}

	public static String writeToFile(String filePath, String fileName, byte[] data) {
		String fileExtension = null;
		String filePrefix = null;
		String timeStamp = null;
		if (fileName.lastIndexOf('.') != -1) {
			fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
			filePrefix = fileName.substring(0, fileName.lastIndexOf('.') - 1);
			timeStamp = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));
		}
		String newFileName = filePrefix + timeStamp + "." + fileExtension;
		filePath = filePath + File.separator + newFileName;
		try {
			FileOutputStream fos = null;
			fos = new FileOutputStream(filePath);
			fos.write(data);
			fos.close();
			return filePath;
		} catch (Exception e) {
		}
		return null;
	}

	public static String getTimeString() {
		Date date = new Date();
		return (new SimpleDateFormat("yyyymmddhhmmss")).format(date);
	}

	public static String getStoreAccount(String transactionMode) {
		switch (transactionMode) {
			case "CASH":
				return transactionMode + "_ACCO_01";
			case "CARD":
				return "BANK_ACCO_02";
			case "CHEQUE":
				return "BANK_ACCO_02";
			case "PAYTM":
				return transactionMode + "_ACCO_03";
			case "WALLET":
				return transactionMode + "_ACCO_04";
			case "OTHERS":
				return transactionMode + "_ACCO_05";
			default:
				return "OTHERS_ACCO_05";
		}
	}


	public static boolean changeInMap(Map<String, Double> oldMap, Map<String, Double> newMap) {
		boolean thereIsChange = false;
		for (String key : oldMap.keySet()) {
			double oldVal = oldMap.get(key) != null ? oldMap.get(key) : 0.0;
			double newVal = newMap.get(key) != null ? newMap.get(key) : 0.0;
			thereIsChange = oldVal != newVal;
			if (thereIsChange)
				return thereIsChange;
		}
		for (String key : newMap.keySet()) {
			double oldVal = oldMap.get(key) != null ? oldMap.get(key) : 0.0;
			double newVal = newMap.get(key) != null ? newMap.get(key) : 0.0;
			thereIsChange = oldVal != newVal;
			if (thereIsChange)
				return thereIsChange;
		}
		return false;
	}

	public static Map<String, List<String>> getDefaultNumberList() {
		String[] numberType = { "tel", "fax", "mob" };
		Map<String, List<String>> numList = new HashMap<>();
		for (String nType : numberType) {
			numList.put(nType, new ArrayList<>());
		}
		return numList;
	}


	public static String getTrimmedString(String string){
		if(string!=null && !string.trim().equals(""))
			return string.replaceAll("[^\\dA-Za-z]", "").toUpperCase();
		return null;
	}
	public static String removeSpecialCharecterBy(String string, String separator){
		if(string!=null && !string.trim().equals(""))
			return string.replaceAll("[^\\dA-Za-z]", separator).toUpperCase();
		return null;
	}
	public static String removeSpecialCharecterByWithSamerCase(String string, String separator){
		if(string!=null && !string.trim().equals(""))
			return string.replaceAll("[^\\dA-Za-z]", separator);
		return null;
	}

	public static String upperCase(String string){
		if(string!=null && !string.trim().equals(""))
			return string.toUpperCase();
		return null;
	}
	public static List<Date> getDaysBetweenDates(Date startdate, Date enddate)
	{
		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startdate);

		while (calendar.getTime().before(enddate))
		{
			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		return dates;
	}
	public static String getStringFromListOfString(List<String> stringList, String separator){
		if(stringList!=null){
			String result = "";
			for(String str:stringList){
				result = result + str +separator;
			}
			if(result.length()>1){
				result = result.substring(0,result.length()-separator.length());
			}
			return result ;
		}
		return null;
	}

	public static void createDirectory(String path){
		File theDir = new File(path);  // Defining Directory/Folder Name
		try{
			if (!theDir.exists()){  // Checks that Directory/Folder Doesn't Exists!
				theDir.mkdir();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static int getNoOfDaysBetweenDates(Date startdate, Date enddate){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startdate);
		int i=0;
		while (calendar.getTime().before(enddate)){
			Date result = calendar.getTime();
			calendar.add(Calendar.DATE, 1);
			i++;
		}
		return i;
	}

	public static Date convertMilliSecondToDate(long datemillisecond) {
		Date currentDate = new Date(datemillisecond);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss Z");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+530"));
		//System.out.println(sdf.format(currentDate));
		return currentDate;
	}
	public static String getRandomNumberString() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	public static String convertMaptoString(Map<?, ?> map) {
		String mapAsString = map.keySet().stream()
				.map(key -> key + "=" + map.get(key))
				.collect( Collectors.joining(", ", "{", "}"));
		return mapAsString;
	}
	public static String nameCapitalizer(String word){
		String[] words = word.split(" ");
		StringBuilder sb = new StringBuilder();
		if (words[0].length() > 0) {
			sb.append(Character.toUpperCase(words[0].charAt(0)) + words[0].subSequence(1, words[0].length()).toString().toLowerCase());
			for (int i = 1; i < words.length; i++) {
				sb.append(" ");
				sb.append(Character.toUpperCase(words[i].charAt(0)) + words[i].subSequence(1, words[i].length()).toString().toLowerCase());
			}
		}
		return  sb.toString();
	}
	public static String getValueFromJsonObject(String field, JSONObject jsonObject) throws JSONException {
		String value ="";
		if(field !=null){
			if(jsonObject.has(field) && jsonObject.get(field) !=null){
				value = jsonObject.get(field).toString();
			}
		}
		return  value;
	}
	
	public static Object getChildJSONObjectFromField(Object parentObject, String[] childs, int startIndex, int endIndex) throws JSONException {	
		if (startIndex == endIndex || startIndex > endIndex)	
		{	
			if(parentObject instanceof JSONArray){	
				return ((JSONArray) parentObject).get(Integer.parseInt( childs[endIndex] ));	
			}else if(parentObject instanceof ArrayList){	
				try {	
					return new JSONObject(new Gson().toJson(((ArrayList) parentObject).get(Integer.parseInt(childs[endIndex]))));	
				} catch (Exception e){	
					return null;	
				}	
	
			} else if (parentObject instanceof LinkedHashMap) {
				return ((LinkedHashMap) parentObject).get( childs[endIndex] );
			}else {
				if(!((JSONObject) parentObject).isNull(childs[endIndex])) {
					if (((JSONObject) parentObject).get(childs[endIndex]) instanceof JSONObject) {
						return ((JSONObject) parentObject).getJSONObject(childs[endIndex]);
					} else if (((JSONObject) parentObject).get(childs[endIndex]) instanceof JSONArray) {
						return ((JSONObject) parentObject).getJSONArray(childs[endIndex]);
					} else if (((JSONObject) parentObject).get(childs[endIndex]) instanceof ArrayList) {
						return new JSONArray((ArrayList<JSONObject>) ((JSONObject) parentObject).get(childs[endIndex]));
					} else if (((JSONObject) parentObject).get(childs[endIndex]) instanceof ArrayList) {
						return new JSONArray((ArrayList<JSONObject>) ((JSONObject) parentObject).get(childs[endIndex]));
					} else {
						return ((JSONObject) parentObject).get(childs[endIndex]);
					}
				}else {
					return null;
				}
			}
		}	
		else /*if (startIndex< endIndex)*/{	
			if(parentObject instanceof JSONArray){	
				return getChildJSONObjectFromField(((JSONArray) parentObject).get(Integer.parseInt( childs[startIndex] )),childs, startIndex+1, endIndex);	
			}else if(parentObject instanceof ArrayList){	
				try {	
					return getChildJSONObjectFromField(new JSONObject(new Gson().toJson(((ArrayList) parentObject).get(Integer.parseInt(childs[startIndex])))), childs, startIndex+1, endIndex);	
				} catch (Exception e){	
					return null;	
				}	
			} else if (parentObject instanceof LinkedHashMap) {	
				return getChildJSONObjectFromField(((LinkedHashMap) parentObject).get(  childs[startIndex]) ,childs, startIndex+1, endIndex);	
			} else {
				if(!((JSONObject) parentObject).isNull(childs[startIndex])) {
					if (((JSONObject) parentObject).get(childs[startIndex]) instanceof JSONObject) {
						return getChildJSONObjectFromField(((JSONObject) parentObject).getJSONObject(childs[startIndex]), childs, startIndex + 1, endIndex);
					} else if (((JSONObject) parentObject).get(childs[startIndex]) instanceof JSONArray) {
						return getChildJSONObjectFromField(((JSONObject) parentObject).getJSONArray(childs[startIndex]), childs, startIndex + 1, endIndex);
					} else if (((JSONObject) parentObject).get(childs[startIndex]) instanceof ArrayList) {
						return getChildJSONObjectFromField(new JSONArray((ArrayList<JSONObject>) ((JSONObject) parentObject).get(childs[startIndex])), childs, startIndex + 1, endIndex);
					} else if (((JSONObject) parentObject).get(childs[startIndex]) instanceof ArrayList) {
						return getChildJSONObjectFromField(new JSONArray((ArrayList<JSONObject>) ((JSONObject) parentObject).get(childs[startIndex])), childs, startIndex + 1, endIndex);
					} else {
						return getChildJSONObjectFromField(((JSONObject) parentObject).get(childs[startIndex]), childs, startIndex + 1, endIndex);
					}
				} else {
					return null;
				}
			}
		}	
	}	
	
	
	public static String removeSpecialCharecterWith(String string, String replacement){
		if(string!=null && !string.trim().equals(""))
			return string.replaceAll("[^\\dA-Za-z]", replacement).toUpperCase();
		return null;
	}

	public void populateFieldFromTo(JSONObject from, JSONObject to, String field) {
		try {
			if(from.has(field) && !from.isNull(field)) {
				to.put( field, from.get( field ) );
			}else{
				logger.info( field + " does not exist in from_Json" );
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Date getNthWorkingDayFromDate(Date date, int noOfDays,List<String> holidays){
		Date result=null;
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		for(int i=0; i<noOfDays;i++){
			cal.add( Calendar.DATE, 1 );
			if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				cal.add( Calendar.DATE, 1 );
			}else {
					while(holidays.contains(getDateInyyyymmdd(cal.getTime()))){
						cal.add( Calendar.DATE, 1 );
					}
			}
		}
		return cal.getTime();
	}
	public Map <String, Object> getReferenceObject(Object object) throws JSONException {
		Map <String, Object> obj = new HashMap <>();
		JSONObject il = new JSONObject( new Gson().toJson(object) );
		try {
			obj.put( "_id", !il.isNull( "_id" )?il.get( "_id" ).toString():"");
			obj.put( "code", !il.isNull( "code" ) ? il.get( "code" ).toString() : (!il.isNull( "serialId" ) ? il.get( "serialId" ).toString():"") );
			obj.put( "name", !il.isNull( "name" )? il.get( "name" ).toString(): obj.get( "code"));
			if(il.has("type")){
				obj.put( "type", il.get( "type" ).toString() );
			}
			if(!il.isNull( "version" )){
				obj.put( "version", il.get( "version"));
			}else{
				obj.put( "version",0);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject getReferenceAsJSONObject(Object object) throws JSONException {
		JSONObject obj = new JSONObject(  );
		JSONObject il = new JSONObject( new Gson().toJson(object) );
		try {
			obj.put( "_id", !il.isNull( "_id" )?il.get( "_id" ).toString():"");
			obj.put( "code", !il.isNull( "code" ) ? il.get( "code" ).toString() : (il.has( "serialId" ) ? il.get( "serialId" ).toString():"") );
			obj.put( "name", !il.isNull( "name" )? il.get( "name" ).toString(): obj.get( "code"));
			if(!il.isNull("type")){
				obj.put( "type", il.get( "type" ).toString() );
			}
			if(!il.isNull( "version" )){
				obj.put( "version", il.get( "version"));
			}else{
				obj.put( "version",0);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	public Map <String, Object> getReferenceJsonObject(JSONObject il) {
		Map <String, Object> obj = new HashMap <>();
		try {
			obj.put( "_id", il.isNull( "_id" )?"":il.get( "_id" ).toString());

			if(!il.isNull( "code" )){
				obj.put( "code", il.get( "code" ).toString());
			}else if(!il.isNull( "serialId" )){
				obj.put( "code", il.get( "serialId" ).toString() );
			}
			obj.put( "name", il.isNull( "name" )?obj.get( "code"): il.get( "name" ).toString());
			if(!il.isNull( "type" )){
				obj.put( "type", il.get( "type" ).toString());
			}
			if(!il.isNull( "version" )){
				obj.put( "version", il.get( "version"));
			}else{
				obj.put( "version",0);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return obj;
	}

	public static final String getDateInyyyymmdd(Date date) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(date);
		return strDate;
	}

	public static final String getDateInyyyymmdd(String date ) {
		try {
			if(date!=null && !date.contains("/")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(sdf.parse(date));
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
				return sdf.format(sdf2.parse(date));
			}
		} catch (ParseException e) {
			return date;
		}
	}

	public static final String getTimeIn24HoursFormat(String time ) {
		try {
			// Change the pattern into 24 hour format
			SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
			SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
			Date date = parseFormat.parse(time);
			return displayFormat.format(date);
		} catch (Exception e) {
			return time;
		}
	}

	public static final String getTimeIn12HoursFormat(String time ) {
		try {
			// Format of the date defined in the input String
			// Change the pattern into 24 hour format
			SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
			SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
			Date date = parseFormat.parse(time);
			return displayFormat.format(date);
		} catch (Exception e) {
			return time;
		}
	}

	public static String convertDateToStandardDateTimeHHMMss(Date date) {
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		String reportDate = df.format(date);
		return reportDate;
	}
	public static String getJsonAcceptableDate(Date date) {
		if (date !=null) {
			DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
			String reportDate = df.format(date);
			return reportDate;
		} else {
			return null;
		}
	}
	public static Date setTimeInDate(Date date, String timeString){
		String time[] = timeString.split(":");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
		cal.set(Calendar.SECOND, Integer.parseInt(time[2]));
		return cal.getTime();
	}

	public static String convertDateToStandardDateTimeWithFormat(Date date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		String reportDate = df.format(date);
		return reportDate;
	}

	public static Date changeStringDateToSpecificFormat(String date, String pattern) throws ParseException {
		SimpleDateFormat sdf =new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}



	public static Date getDate(String invdate) {
		String pattern = null;
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
		SimpleDateFormat sdm = null;
		Date dummyDate = (new GregorianCalendar(2099, 11, 2)).getTime();
		if (isNumeric(invdate)) {
			return DateUtil.getJavaDate(Double.parseDouble(invdate));
		} else {
			try {
				if (invdate != null && !"".equals(invdate) && !"null".equals(invdate) && !"0".equals(invdate) && !"N-A".equals(invdate.trim()) && !"N/A".equals(invdate.trim()) && !"N.A.".equals(invdate.trim()) && !"N.A".equals(invdate.trim())) {
					invdate = invdate.replace(" ", "");
					invdate = invdate.replace("/", "-");
					if (invdate.length() == 5) {
						pattern = "MM-yy";
					} else if (invdate.length() == 11) {
						pattern = "dd-MMM-yyyy";
					} else if (invdate.length() == 10) {
						if(invdate.indexOf( "-" )>3){
							pattern="yyyy-MM-dd";
						}else {
							pattern = "dd-MM-yyyy";
						}
					} else if (invdate.length() == 9) {
						pattern = "dd-MMM-yy";
					} else if (invdate.length() == 7) {
						pattern = "MM-yyyy";
					} else if (invdate.length() == 8) {
						if (invdate.indexOf("-") == 2) {
							pattern = "dd-MM-yy";
						} else {
							pattern = "MMM-yyyy";
						}
					}

					if (pattern != null) {
						sdm = new SimpleDateFormat(pattern);
						Date date = null;
						date = sdm.parse(invdate);
						return date;
					} else {
						return dummyDate;
					}
				} else {
					return dummyDate;
				}
			} catch (ParseException var6) {
				return dummyDate;
			}
		}
	}
	public void sanitizeJson(JSONObject jsonObject) {
		Iterator<String> keys = jsonObject.keys();
		JSONArray names = jsonObject.names();
		if (names != null && names.length() > 0) {
			for (int i = 0; i < names.length(); i++) {
				try {
					String key = names.get(i).toString();
					if (!jsonObject.isNull(key) &&
							(CommonUtil.getTrimmedString(this.getValueFromJSONObject(jsonObject, key).toString()) == null
									|| CommonUtil.getTrimmedString(this.getValueFromJSONObject(jsonObject, key).toString()).equals("")
									)) {
						jsonObject.remove(key);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public Map<String, Object> getResultChildObject(String field, Object object) {
		Map <String, Object> mapObject = new HashMap<>();
		mapObject.put( "field", field);
		mapObject.put( "data", object );
		return mapObject;
	}
	public Reference getReferenceFromJson(JSONObject il) {
		Reference obj = new Reference();
		try {
			obj.set_id(il.has( "_id" )?il.get( "_id" ).toString():"");

			if(!il.isNull( "code" )){
				obj.setCode(il.get( "code" ).toString());
			}else if(il.has( "serialId" ) && il.getString( "serialId" )!=null){
				obj.setCode(il.get( "serialId" ).toString() );
			}
			obj.setName( il.has( "name" )? il.get( "name" ).toString(): obj.getCode());
		}catch (Exception e){
			e.printStackTrace();
		}
		return obj;
	}


	public JSONObject getSavedObject(Map<String,Object> resultObj) throws JSONException {
		JSONObject savedJsonObject =null;
		try {
			Object data =null;
			if (resultObj.get("data")!=null) {
				data = resultObj.get( "data" );
				savedJsonObject = new JSONObject( gson.toJson( data ) );
			}
		}catch(Exception e){

		}
		return savedJsonObject;
	}

	public Reference getReference(LinkedHashMap retrivedobject) {
		Reference reference;
		JSONObject queryJSONObject = new JSONObject( retrivedobject );
		reference = getReferenceFromJson( queryJSONObject);
		return reference;
	}
	public Reference getReference(Object object) throws JSONException {
		Reference reference;
		JSONObject queryJSONObject = new JSONObject( gson.toJson( object ) );
		reference = getReferenceFromJson( queryJSONObject );
		return reference;
	}


	public Reference getReferenceObject(JSONObject queryJSONObject) throws JSONException {
		Reference reference = new Reference();
		try  {
			reference.set_id( queryJSONObject.has( "_id" ) ? queryJSONObject.getString( "_id" ) : null );
			reference.setName( queryJSONObject.has( "name" ) ? queryJSONObject.getString( "name" ) : null );
			reference.setCode( queryJSONObject.has( "code" ) && getTrimmedString( queryJSONObject.getString( "code" ))!=null ?  queryJSONObject.getString( "code" ): queryJSONObject.getString( "serialId" ) );
			return reference;
		}catch (Exception e){
			logger.info( "Error while getReferenceObject : {}",e.getMessage() );
			logger.info( "Retrieved Object: {}",queryJSONObject);
		}
		return null;
	}
	public Reference getReferenceFromResponseObject(Map<String,Object> resultObj) throws JSONException {
		Reference reference=null;
		JSONObject dept =null;
		try {
			Object data =null;
			if (resultObj.get("data")!=null) {
				data = resultObj.get( "data" );
				LinkedHashMap template = jsonMessageConverter.getObjectMapper().convertValue(data, new TypeReference<LinkedHashMap>() {
				} );
				reference=getReference( template );
			}else if ( resultObj.get("persisted_object")!=null){
				dept = (JSONObject) resultObj.get( "persisted_object" );
				reference = getReferenceObject( dept );
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return reference;
	}
	public List<String> getIdFromListOfReference(List<Reference> references) {
		List<String> _ids = new ArrayList<>();
		if(references!=null && !references.isEmpty()) {
			references.forEach(department -> {
				_ids.add(department.get_id());
			});
		}
		return _ids;
	}
	public Set<String> getIdSetFromListOfReference(List<Reference> references) {
		Set<String> _ids = new HashSet<>();
		if(references!=null && !references.isEmpty()) {
			references.forEach(department -> {
				_ids.add(department.get_id());
			});
		}
		return _ids;
	}
	public List<Object> getObjectIdFromListOfReference(List<Reference> references) {
		List<Object> _ids = new ArrayList<>();
		if(references!=null && !references.isEmpty()) {
			references.forEach(department -> {
				_ids.add(new ObjectId(department.get_id()));
			});
		}
		return _ids;
	}
	public Set<Object> getObjectIdSetFromListOfReference(List<Reference> references) {
		Set<Object> _ids = new HashSet<>();
		if(references!=null && !references.isEmpty()) {
			references.forEach(department -> {
				_ids.add(new ObjectId(department.get_id()));
			});
		}
		return _ids;
	}
	public String getString(Object obj) {
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}
	public Double getDoubleFromObject(Object obj) {
		if (obj != null) {
			try {
				return Double.parseDouble( obj.toString().toUpperCase() );
			} catch (Exception e) {

			}
		}
		return 0.0;
	}
	public Reference updateBlankReference(Reference reference){
		if(reference==null) {
			reference = new Reference();
			reference.setName( " " );
			reference.setCode( " " );
			reference.setType( " " );
		}
		return reference;
	}
	public Map<String,String> allobject = new HashMap(){{
		put( "_id","ALL");
		put("name","All");
		put("code","ALL");
	}};

	public String getStackTraceAsString(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	public String generateRandomPassword(){
		return UUID.randomUUID().toString().substring( 0,4 )+"@"+String.valueOf( Math.random()).substring( 0,4 );
	}

	public String convertListToString(List<Reference> refList){
		String result = "";
		if(refList !=null && !refList.isEmpty()){
			result = refList.stream()
					.map(ref -> String.valueOf(ref.getName()))
					.collect(Collectors.joining(","));
		}
		return result
				;

	}
	public String convertDateToString(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		String reportDate = df.format(date);
		return reportDate;
	}


	public double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit.equals('K')) {
			dist = dist * 1.609344;
		} else if (unit.equals('N')) {
			dist = dist * 0.8684;
		}
		return (dist);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::  This function converts decimal degrees to radians             :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	public double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::  This function converts radians to decimal degrees             :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	public double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	public String getDateFormat(String value){
			for(int i=0;i<dateFormats.size();i++) {
				Date date = null;
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(dateFormats.get(i));
					date = sdf.parse(value);
					if (!value.equals(sdf.format(date))) {
						date = null;
					}
				} catch (ParseException ex) {
					logger.info("getDateFormat is checking date format {}",ex.getMessage());
				}
				if(date != null) return dateFormats.get(i);
			}
		logger.info("getDateFormat could not identify DateFormat for {}",value);
			if(value.endsWith("+00:00")){
				return "yyyy-MM-dd'T'HH:mm:ss.SSSX";
			}else if(value.endsWith("T18:30:00.000Z")){
				return "yyyy-MM-dd'T'HH:mm:ss.SSX";
			}
		return null;
	}
	List<String> dateFormats = Arrays.asList(
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			"EEE, dd MMM yyyy HH:mm:ss zzz",
			"MMM d, yyyy, HH:mm:ss",
			"MMM d, yyyy, HH:mm:ss a",
			"MMM d, yyyy, K:mm:ss a",
			"MMM d, yyyy, KK:mm:ss a",
			"yyyy-MM-dd'T'HH:mm:ss.SS",
			"yyyy-MM-dd'T'HH:mm:ss.SSS",
			"yyyy-MM-dd'T'HH:mm:ss.SSSX",
			"dd/MMM/YYYY","dd-MMM-yyyy",
			"dd/MM/yyyy","yyyy-MM-dd",
			"dd/MM/yyyy HH:mm:ss.SS",
			"EEE MMM d HH:mm:ss z yyyy"
	);
	public String getDateTimeIndiaDashSeparatedYYYYMMDD() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
		sdf.setTimeZone(tz);
		Date date = new Date();
		return sdf.format(date);
	}


	public void removeKeysFromJsonObject(JSONObject dataJson,List list){
		try{
			list.forEach(key ->{
				dataJson.remove(key.toString());
			});
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public static <T> List<T> getObjectList(Gson gson,String jsonString,Class<T> cls){
		List<T> list = new ArrayList<T>();
		try {
			JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
			for (JsonElement jsonElement : arry) {
				list.add(gson.fromJson(jsonElement, cls));
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	public boolean isValidEmail(String email){
		String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";


		Pattern pat = Pattern.compile(emailRegex);
		if (pat.matcher(email).matches()) {
			return true;
		} else return false;
	}

	public String financialYearInYyyy(int numberOfYearPreviousOrNext){ //Zero mean current, 01 previous, ,+1 next etc...
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
		if (currentMonth < 4) {
			return "" + (currentYear - 1 + numberOfYearPreviousOrNext) + "-" + (currentYear + numberOfYearPreviousOrNext);
		} else {
			return "" + (currentYear+numberOfYearPreviousOrNext) + "-" + (currentYear + 1 + numberOfYearPreviousOrNext);
		}
 	}


	public void sumValues( String key, Object value, Map<String, Object> map,  Class typeOfValue, Class typeOfValueIfMap){
			if(key!=null)
			{
				if(typeOfValue.getName().equalsIgnoreCase(Double.class.getName())){
					if(map.containsKey(key))
						map.put(key, Double.parseDouble(map.get(key).toString())+ Double.parseDouble(value.toString()));
					else map.put(key, Double.parseDouble(value.toString()));
				} else if(typeOfValue.getName().equalsIgnoreCase(Integer.class.getName())){
					if(map.containsKey(key))
						map.put(key, Integer.parseInt(map.get(key).toString())+ Integer.parseInt(value.toString()));
					else map.put(key, Integer.parseInt(value.toString()));
				} else if(typeOfValue.getName().equalsIgnoreCase(Map.class.getName())){
					if(map.containsKey(key)) {
						if(typeOfValueIfMap.getName().equalsIgnoreCase(Double.class.getName())){
							double valuesSum = ((Map<String, Double>)value).values().stream().reduce((double) 0, Double::sum);
							map.put(key, Double.parseDouble(map.get(key).toString()) + valuesSum);
						} else if(typeOfValueIfMap.getName().equalsIgnoreCase(Integer.class.getName())){
							int valuesSum = ((Map<String, Integer>)value).values().stream().reduce((int) 0, Integer::sum);
							map.put(key, Integer.parseInt(map.get(key).toString()) + valuesSum);
						}
					} else {
						if(typeOfValueIfMap.getName().equalsIgnoreCase(Double.class.getName())){
							double valuesSum = ((Map<String, Double>)value).values().stream().reduce((double) 0, Double::sum);
							map.put(key, valuesSum);
						} else if(typeOfValueIfMap.getName().equalsIgnoreCase(Integer.class.getName())){
							int valuesSum = ((Map<String, Integer>)value).values().stream().reduce((int) 0, Integer::sum);
							map.put(key, valuesSum);
						}
					}
				}
			};
		}
	public boolean hasKey(List<String> listOfStrings,String key) {
		return listOfStrings.stream()
				.filter(k -> key!=null && key.contains(k)).collect(Collectors.toList()).size() > 0;
	}

	public boolean hasKeyEqualsIgnoreCase(List<String> listOfStrings,String key) {
		return listOfStrings.stream()
				.filter(k -> key!=null && key.equalsIgnoreCase(k)).collect(Collectors.toList()).size() > 0;
	}
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
	{
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
