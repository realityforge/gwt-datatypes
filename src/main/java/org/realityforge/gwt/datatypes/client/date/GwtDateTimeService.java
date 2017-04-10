package org.realityforge.gwt.datatypes.client.date;

import com.google.gwt.i18n.client.DateTimeFormat;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A service for accessing client-side date / time services.
 *
 * <p>This is implemented as a service so that it can be mocked out during testing as
 * the underlying GWT implementation uses JSNI.</p>
 */
public class GwtDateTimeService
  implements DateTimeService
{
  private final DateTimeFormat _dateFormat = DateTimeFormat.getFormat( "EEE d MMM" );

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
      return DateTimeFormat.getFormat( pattern ).format( RDate.toDate( date ) );
    }
  }

  @Nullable
  @Override
  public RDate parseDate( @Nonnull final String dateStr )
  {
    return RDate.fromDate( _dateFormat.parse( dateStr ) );
  }
}
