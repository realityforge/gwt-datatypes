package org.realityforge.gwt.datatypes.server.date.jackson;

import java.util.Calendar;
import java.util.Date;

final class DateTestUtil
{
  static Date toDayDate( final int year, final int month, final int day )
  {
    final Calendar calendar = Calendar.getInstance();
    calendar.set( Calendar.YEAR, year );
    calendar.set( Calendar.MONTH, month - 1 );
    calendar.set( Calendar.DAY_OF_MONTH, day );
    calendar.set( Calendar.HOUR, 0 );
    calendar.set( Calendar.MINUTE, 0 );
    calendar.set( Calendar.SECOND, 0 );
    calendar.set( Calendar.MILLISECOND, 0 );
    return calendar.getTime();
  }
}
