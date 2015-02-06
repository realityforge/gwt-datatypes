package org.realityforge.gwt.datatypes.client.date;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Nonnull;

/**
 * The client-side GWT representation of a date.
 */
public final class RDate
  implements Comparable<RDate>, Serializable
{
  private int _year;
  private int _month;
  private int _day;

  ///Constructor required for GWT serialization
  @SuppressWarnings( { "UnusedDeclaration" } )
  private RDate()
  {
  }

  public RDate( final int year, final int month, final int day )
  {
    assert ( year > 0 || year < 2050 );
    assert ( month > 0 || month <= 12 );
    assert ( day > 0 || day < 31 );
    _year = year;
    _month = month;
    _day = day;
  }

  public int getDay()
  {
    return _day;
  }

  public int getMonth()
  {
    return _month;
  }

  public int getYear()
  {
    return _year;
  }

  @Override
  public String toString()
  {
    return getYear() + "-" + getMonth() + "-" + getDay();
  }

  @Nonnull
  public RDate addDays( final int dayDelta )
  {
    return addDays( this, dayDelta );
  }

  @Nonnull
  public RDate addMonths( final int monthDelta )
  {
    return addMonths( this, monthDelta );
  }

  @Override
  public int hashCode()
  {
    int h = getDay() * 31;
    h *= getMonth() * 13;
    h *= getYear() * 7;
    return h;
  }

  public boolean before( @Nonnull final RDate other )
  {
    return compareTo( other ) < 0;
  }

  public boolean after( @Nonnull final RDate other )
  {
    return compareTo( other ) > 0;
  }

  @Override
  public int compareTo( @Nonnull final RDate other )
  {
    if ( getYear() != other.getYear() )
    {
      return getYear() - other.getYear();
    }
    else if ( getMonth() != other.getMonth() )
    {
      return getMonth() - other.getMonth();
    }
    else if ( getDay() != other.getDay() )
    {
      return getDay() - other.getDay();
    }
    else
    {
      return 0;
    }
  }

  @Override
  public boolean equals( final Object object )
  {
    if ( null == object || !( object instanceof RDate ) )
    {
      return false;
    }
    final RDate other = (RDate) object;
    return getYear() == other.getYear() &&
           getMonth() == other.getMonth() &&
           getDay() == other.getDay();
  }

  public static RDate today()
  {
    return fromDate( new Date() );
  }

  @SuppressWarnings( { "deprecation" } )
  public static RDate fromDate( @Nonnull final Date date )
  {
    return new RDate( date.getYear() + 1900, date.getMonth() + 1, date.getDate() );
  }

  @SuppressWarnings( { "deprecation" } )
  public static Date toDate( @Nonnull final RDate date )
  {
    return new Date( date.getYear() - 1900, date.getMonth() - 1, date.getDay() );
  }

  @Nonnull
  public static RDate addDays( @Nonnull final RDate date, final int dayDelta )
  {
    int year = date.getYear();
    int month = date.getMonth();
    int day = date.getDay() + dayDelta;
    while ( day <= 0 )
    {
      month -= 1;
      if ( 0 == month )
      {
        year -= 1;
        month = 12;
      }
      day += getDaysInMonth( year, month );
    }

    while ( day > getDaysInMonth( year, month ) )
    {
      day -= getDaysInMonth( year, month );
      month += 1;
      if ( 13 == month )
      {
        year += 1;
        month = 1;
      }
    }

    return new RDate( year, month, day );
  }

  /**
   * Add a number of months to the date and return a new instance.
   *
   * The day of the month unless it would exceed the number of days in the
   * month, in which case the date is set at the last day of the month.
   */
  @Nonnull
  public static RDate addMonths( @Nonnull final RDate date, final int monthDelta )
  {
    int year = date.getYear();
    int month = date.getMonth();
    int day = date.getDay();

    month += monthDelta;

    while( month <= 0 )
    {
      month += 12;
      year -= 1;
    }

    while( month > 12 )
    {
      month -= 12;
      year += 1;
    }

    final int daysInMonth = getDaysInMonth( year, month );
    day = Math.min( day, daysInMonth );

    return new RDate( year, month, day );
  }

  /**
   * Return the number of days in the specified month.
   *
   * @param year  the year.
   * @param month the month.
   * @return the number of days in specified month.
   */
  public static int getDaysInMonth( final int year, final int month )
  {
    switch ( month )
    {
      case 1:
        return 31;
      case 2:
        if ( 0 == year % 4 && ( 0 != year % 100 || ( 0 == year % 400 ) ) )
        {
          return 29;
        }
        else
        {
          return 28;
        }
      case 3:
        return 31;
      case 4:
        return 30;
      case 5:
        return 31;
      case 6:
        return 30;
      case 7:
        return 31;
      case 8:
        return 31;
      case 9:
        return 30;
      case 10:
        return 31;
      case 11:
        return 30;
      case 12:
        return 31;
      default:
        throw new IllegalStateException();
    }
  }

  public static RDate parse( @Nonnull final String text )
  {
    final int length = text.length();
    int i = 0;

    try
    {
      final StringBuilder sb = new StringBuilder();
      while ( i < length && Character.isDigit( text.charAt( i ) ) )
      {
        sb.append( text.charAt( i ) );
        i++;
      }
      final int year = Integer.parseInt( sb.toString() );
      sb.setLength( 0 );

      //skip the -
      i++;

      while ( i < length && Character.isDigit( text.charAt( i ) ) )
      {
        sb.append( text.charAt( i ) );
        i++;
      }
      final int month = Integer.parseInt( sb.toString() );
      sb.setLength( 0 );

      //skip the -
      i++;

      while ( i < length && Character.isDigit( text.charAt( i ) ) )
      {
        sb.append( text.charAt( i ) );
        i++;
      }
      final int day = Integer.parseInt( sb.toString() );

      if ( i != length )
      {
        throw poorlyFormattedException( text );
      }

      return new RDate( year, month, day );
    }
    catch ( final NumberFormatException nfe )
    {
      throw poorlyFormattedException( text );
    }
  }

  private static IllegalArgumentException poorlyFormattedException( final String text )
  {
    final String message = "Poorly formatted date '" + text + "'";
    return new IllegalArgumentException( message );
  }
}
