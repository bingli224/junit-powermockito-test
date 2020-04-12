
// JUnit4
import org.junit.BeforeClass;   // == BeforeAll
import org.junit.Test;

// JUnit5
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;

// JUnit4: required for PowerMockito compatibility
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith ( PowerMockRunner.class )
@PrepareForTest ( { A.class } )
@Suite.SuiteClasses ( { A.class, B.class } )
public class ATest {

    static final B b = PowerMockito.mock ( B.class );

    //@BeforeAll  // JUnit5
    @BeforeClass
    public static void setUp ( ) throws Exception {
        MockitoAnnotations.initMocks ( ATest.class );

        PowerMockito.whenNew ( B.class )
                .withAnyArguments ( )
                //.withArguments ( Integer.class )  // try specific param
                .thenReturn ( b );
        // the original value should be 8888 as defined in A.java
        PowerMockito.doReturn ( 12345 ).when ( b ).getX ( );

        System.out.println ( "b.mock.x=" + b.x );
        System.out.println ( "b.mock.getX=" + b.getX ( ) );
    }

    @Test
    public void testMockInAnotherClass ( ) {

        A a = PowerMockito.spy ( new A ( ) );
        //A a = new A ( );  // try without spy

        System.out.println ( "a.b.x=" + a.createB ( ).x );
        System.out.println ( "a.b.getX=" + a.createB ( ).getX ( ) );

        a = PowerMockito.spy ( a );

        System.out.println ( "a.b.x=" + a.createB ( ).x );
        System.out.println ( "a.b.getX=" + a.createB ( ).getX ( ) );

        assertEquals ( b.getX ( ), 12345 );
        assertEquals ( a.createB ( ).getX (), 12345 );
    }
}