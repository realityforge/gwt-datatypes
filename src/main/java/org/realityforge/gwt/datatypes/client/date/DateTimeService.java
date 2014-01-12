package org.realityforge.gwt.datatypes.client.date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface DateTimeService
{
  @Nonnull
  String formatDate( @Nullable RDate date );

  @Nonnull
  String formatDate( @Nullable RDate date, final String pattern );

  @Nullable
  RDate parseDate( @Nonnull String dateStr );

  @Nonnull
  RDate today();
}
