## 0.10:

* Remove `DateTimeService` and `GwtDateTimeService` as they are no loner used by
  any downstream libraries and it means we can remove `<inherits name='com.google.gwt.i18n.I18N'/>`
  which adds a significant amount of code that will be compiled and then eliminated
  due to dead code elimination.

## 0.9:

* Align the format of RDates and Dates formatted by the jackson DateSerializers.

## 0.8:

* Fix bug in RDate.addDays() where -1 on the first day of the month resulted in day 0 of
  the same month rather than the last day of the previous month.

## 0.7:

* Add Rdate.today() to replace DateUtil code and remove today() method from DateTimeService.

## 0.6:

* Fix bug in Rdate.addMonths() that did not always correctly update the years.

## 0.5:

* Update Rdate.getDaysInMonth() to add javadocs and make the method public.
* Add Rdate.addMonths() utility method to help with date manipulation.

## 0.4:

* Support before() and after() on RDate.

## 0.3:

* Restore Java 6 compatibility. Submitted by James Walker.

## 0.2:

* Add RDate.addDays utility method.
* Extract DateUtil from GwtDateTimeService.

## 0.1:

* Initial release
