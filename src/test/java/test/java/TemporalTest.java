package test.java;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TemporalTest {
	
	public void convert() {
		
		// local date time -> time stamp
		Timestamp.valueOf(LocalDateTime.now()); 
		
		// time stamp -> local date time
		new Timestamp(System.currentTimeMillis()).toLocalDateTime();
		
		// local date time -> String (Pattern)
		LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void convertString() {
		LocalDateTime ldt = LocalDateTime.of(2018, 7, 9, 18, 10, 30);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String ldtString = "2018-07-09 18:10:30";
		
		Assertions.assertEquals(
				ldtString, 
				ldt.format(formatter)
				);
		
		Assertions.assertEquals(ldt, LocalDateTime.parse(ldtString, formatter));
	}
	
	@Test
	public void convertSQLTime() {
		LocalDateTime current = LocalDateTime.now();
		
		Assertions.assertEquals(current, Timestamp.valueOf(current).toLocalDateTime());
	}
	
	@Test
	public void period() {
		
		Assertions.assertEquals(
				Period.between(LocalDate.of(2018, 7, 1), LocalDate.of(2018, 7, 8)).getDays(), 
				7
				);
		
		Assertions.assertNotEquals(
				Period.between(LocalDate.of(2017, 7, 1), LocalDate.of(2018, 7, 8)).getDays(), 
				7 + 365
				);
		Assertions.assertEquals(
				ChronoUnit.DAYS.between(LocalDate.of(2017,  7, 1), LocalDate.of(2018, 7, 8)), 
				7 + 365
				);
		
	}
	
	@Test
	public void duration() {
		Assertions.assertEquals(
				Duration.between(LocalDateTime.of(2018, 7, 1, 18, 10), LocalDateTime.of(2018, 7, 8, 18, 10)),
				Duration.ofDays(7)
				);
		
		Assertions.assertEquals(
				ChronoUnit.DAYS.between(LocalDateTime.of(2018, 7, 1, 18, 00), LocalDateTime.of(2018, 7, 8, 18, 10)), 
				7
				);
	}
}
