package org.realityforge.gwt.datatypes.client.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * An implementation of the DateTimeService useful for in JVM testing.
 */
public class JavaDateTimeService
  implements DateTimeService
{
  private final SimpleDateFormat _dateFormat = new SimpleDateFormat( "EEE d MMM" );

  @Nonnull
  @Override
  public String formatDate( @Nullable final RDate date )
  {
    if ( null == date )
    {
      return "";
    }
    else
    {
      return _dateFormat.format( RDate.toDate( date ) );
    }
  }

  @Nonnull
  @Override
  public String formatDate( @Nullable final RDate date, final String pattern )
  {
    if ( null == date )
    {
      return "";
    }
    else
    {
      return new SimpleDateFormat( pattern ).format( RDate.toDate( date ) );
    }
  }

  @Nullable
  @Override
  public RDate parseDate( @Nonnull final String dateStr )
  {
    try
    {
      return RDate.fromDate( _dateFormat.parse( dateStr ) );
    }
    catch ( ParseException e )
    {
      return null;
    }
  }

  @Nonnull
  @Override
  public RDate today()
  {
    return RDate.fromDate( new Date() );
  }
}
