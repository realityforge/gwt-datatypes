package org.realityforge.gwt.datatypes.client.date;

public final class DateUtil
{
  private DateUtil()
  {
  }

  /**
   * @return today based on local date.
   */
  public static RDate localToday()
  {
    return RDate.parse( getTodayAsString() );
  }

  private static native String getTodayAsString() /*-{
    var date = new Date();
    return date.getFullYear() + "-" + ( date.getMonth() + 1 ) + "-" + date.getDate();
  }-*/;
}