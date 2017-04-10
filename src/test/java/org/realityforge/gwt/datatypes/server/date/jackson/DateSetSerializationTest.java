package org.realityforge.gwt.datatypes.server.date.jackson;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.realityforge.gwt.datatypes.client.date.RDate;
import org.testng.annotations.Test;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public final class DateSetSerializationTest
{
  @Test
  public void serialize()
    throws Exception
  {
    final RDate rDate1 = new RDate( 2001, 1, 1 );
    final RDate rDate2 = new RDate( 2001, 2, 28 );
    final RDate rDate3 = new RDate( 2001, 12, 28 );
    final RDate[] dates = new RDate[]{ rDate1, rDate2, rDate3 };

    final ArrayList<Date> datesCollection = new ArrayList<Date>();
    for ( final RDate date : dates )
    {
      datesCollection.add( DateTestUtil.toDayDate( date.getYear(), date.getMonth(), date.getDay() ) );
    }

    final JsonGenerator generator = mock( JsonGenerator.class );

    new DateListSerializer().serialize( datesCollection, generator, mock( SerializerProvider.class ) );

    verify( generator ).writeStartArray();
    verify( generator ).writeString( "2001-1-01" );
    verify( generator ).writeString( "2001-2-28" );
    verify( generator ).writeString( "2001-12-28" );
    verify( generator ).writeEndArray();

    final JsonParser parser = mock( JsonParser.class );
    when( parser.isExpectedStartArrayToken() ).thenReturn( true );
    when( parser.nextToken() ).
      thenReturn( JsonToken.VALUE_STRING ).
      thenReturn( JsonToken.VALUE_STRING ).
      thenReturn( JsonToken.END_ARRAY );
    when( parser.getText() ).
      thenReturn( "2001-1-01" ).
      thenReturn( "2001-2-28" ).
      thenReturn( "2001-12-28" );
    final Set<Date> result =
      new DateSetDeserializer().deserialize( parser, mock( DeserializationContext.class ) );

    assertEquals( result.size(), 2 );
    for ( final Date date : result )
    {
      assertTrue( RDate.fromDate( date ).equals( rDate1 ) || RDate.fromDate( date ).equals( rDate2 ) );
    }
  }

  @Test
  public void raiseExceptionOnDeserializeNonArray()
    throws Exception
  {
    final JsonParser parser = mock( JsonParser.class );
    when( parser.isExpectedStartArrayToken() ).thenReturn( false );
    final DeserializationContext context = mock( DeserializationContext.class );
    final JsonMappingException exception = new JsonMappingException( "" );
    when( context.mappingException( Set.class ) ).thenReturn( exception );
    try
    {
      new DateSetDeserializer().deserialize( parser, context );
    }
    catch ( final JsonMappingException e )
    {
      assertEquals( e, exception );
      return;
    }
    fail( "Exception expected" );
  }

  @Test
  public void raiseExceptionOnNullValue()
    throws Exception
  {
    final JsonParser parser = mock( JsonParser.class );
    when( parser.isExpectedStartArrayToken() ).thenReturn( true );
    when( parser.nextToken() ).thenReturn( JsonToken.VALUE_NULL );

    final DeserializationContext context = mock( DeserializationContext.class );
    final JsonMappingException exception = new JsonMappingException( "" );
    when( context.mappingException( Set.class ) ).thenReturn( exception );
    try
    {
      new DateSetDeserializer().deserialize( parser, context );
    }
    catch ( final JsonMappingException e )
    {
      assertEquals( e, exception );
      return;
    }
    fail( "Exception expected" );
  }

}
