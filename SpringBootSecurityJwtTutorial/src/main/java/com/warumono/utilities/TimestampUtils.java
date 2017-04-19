package com.warumono.utilities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TimestampUtils
{
	public static final ZoneOffset SEOUL_OFFSET = ZoneOffset.ofHours(9);
	
	public static final LocalDateTime now()
	{
		return LocalDateTime.now();
	}
	
	public static final LocalDateTime after(Long days)
	{
		return now().plusDays(days);
	}
	
	public static final LocalDateTime before(Long days)
	{
		return now().minusDays(days);
	}
	
	public static final Date nowToDate()
	{
		return Date.from(now().toInstant(SEOUL_OFFSET));
	}
	
	public static final Date afterToDate(Long days)
	{
		return Date.from(after(days).toInstant(SEOUL_OFFSET));
	}
	
	public static final Date beforeToDate(Long days)
	{
		return Date.from(before(days).toInstant(SEOUL_OFFSET));
	}
	
	public static final Date zonedDateTimeToDateAtSystemDefault()
	{
		return Date.from(now().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static final Date afterZonedDateTimeToMinutesAtSystemDefault(Long minutes)
	{
		return Date.from(now().plusMinutes(minutes).atZone(ZoneId.systemDefault()).toInstant());
	}
}
